package io.nms.central.microservice.gateway;

import java.util.List;
import java.util.Optional;

import io.nms.central.microservice.account.AccountService;
import io.nms.central.microservice.account.model.Account;
import io.nms.central.microservice.common.RestAPIVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.auth.jwt.JWTOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.types.EventBusService;
import io.vertx.servicediscovery.types.HttpEndpoint;

/**
 * A verticle for global API gateway.
 * This API gateway uses HTTP-HTTP pattern. It's also responsible for
 * load balance and failure handling.
 *
 * @author Eric Zhao
 */
public class APIGatewayVerticle extends RestAPIVerticle {

	private static final int DEFAULT_PORT = 8787;

	private static final Logger logger = LoggerFactory.getLogger(APIGatewayVerticle.class);
	private JWTAuth jwt;

	@Override
	public void start(Promise<Void> promise) throws Exception {
		super.start();

		// get HTTP host and port from configuration, or use default value
		String host = config().getString("api.gateway.http.address", "localhost");
		int port = config().getInteger("api.gateway.http.port", DEFAULT_PORT);

		Router router = Router.router(vertx);
		// cookie and session handler
		enableLocalSession(router);

		enableCorsSupport(router);


		// Create a JWT Auth Provider
		jwt = JWTAuth.create(vertx, new JWTAuthOptions()
				.addPubSecKey(new PubSecKeyOptions()
						.setAlgorithm("HS256")
						.setPublicKey("keyboard cat")
						.setSymmetric(true)));

		// protect the API
		router.route("/api/*").handler(JWTAuthHandler.create(jwt));

		// body handler
		router.route().handler(BodyHandler.create());

		// this route is excluded from the auth handler
		router.post("/login/agent").handler(this::agentLoginHandler);
		router.post("/login/user").handler(this::userLoginHandler);
		router.post("/logout").handler(this::logoutHandler);

		// version handler
		router.get("/api/v").handler(this::apiVersion);

		// api dispatcher    
		router.route("/api/*").handler(this::dispatchRequests);

		// static content
		// router.route("/*").handler(StaticHandler.create());

		// enable HTTPS
		HttpServerOptions httpServerOptions = new HttpServerOptions()
				.setSsl(true)
				.setKeyStoreOptions(new JksOptions().setPath("server.jks").setPassword("123456"));
		/* .setPemKeyCertOptions(
    		  new PemKeyCertOptions()
    		  		.setKeyPath("certs/nms.controller.key.pem")
    		  		.setCertPath("certs/nms.controller.cert.pem")); */

		// create http server
		vertx.createHttpServer(httpServerOptions)
		.requestHandler(router)
		.listen(port, host, ar -> {
			if (ar.succeeded()) {
				publishApiGateway(host, port)
				.compose(r -> deployEventbusVerticle())
				.onComplete(promise);
				logger.info("API Gateway is running on port " + port);
				// publish log
				publishGatewayLog("api_gateway_init_success:" + port);
			} else {
				promise.fail(ar.cause());
			}
		});
	}

	private Future<Void> deployEventbusVerticle() {
		Promise<String> promise = Promise.promise();
		vertx.deployVerticle(new NmsUIVerticle(),
				new DeploymentOptions().setConfig(config()), promise.future());
		return promise.future().map(r -> null);
	}


	private void dispatchRequests(RoutingContext context) {
		int initialOffset = 5; // length of `/api/`
		// run with circuit breaker in order to deal with failure
		circuitBreaker.execute(future -> {
			getAllEndpoints().onComplete(ar -> {
				if (ar.succeeded()) {
					List<Record> recordList = ar.result();
					// get relative path and retrieve prefix to dispatch client
					String path = context.request().uri();          

					if (path.length() <= initialOffset) {
						notFound(context);
						future.complete();
						return;
					}
					String prefix = (path.substring(initialOffset)
							.split("/"))[0];                  

					// generate new relative path
					String newPath = path.substring(initialOffset + prefix.length());         

					// get one relevant HTTP client, may not exist
					Optional<Record> client = recordList.stream()
							.filter(record -> record.getMetadata().getString("api.name") != null)
							.filter(record -> record.getMetadata().getString("api.name").equals(prefix))
							.findAny(); // simple load balance

					if (client.isPresent()) {
						doDispatch(context, newPath, discovery.getReference(client.get()).get(), future.future());
					} else {
						notFound(context);
						future.complete();
					}
				} else {
					future.fail(ar.cause());
				}
			});
		}).onComplete(ar -> {
			if (ar.failed()) {
				badGateway(ar.cause(), context);
			}
		});
	}

	/**
	 * Dispatch the request to the downstream REST layers.
	 *
	 * @param context routing context instance
	 * @param path    relative path
	 * @param client  relevant HTTP client
	 */
	private void doDispatch(RoutingContext context, String path, HttpClient client, Future<Object> cbFuture) {
		HttpClientRequest toReq = client
				.request(context.request().method(), path, response -> {
					response.bodyHandler(body -> {        	
						if (response.statusCode() > 501) {
							cbFuture.fail(response.statusCode() + ": " + body.toString());
						} else {
							HttpServerResponse toRsp = context.response()
									.setStatusCode(response.statusCode());
							response.headers().forEach(header -> {
								toRsp.putHeader(header.getKey(), header.getValue());
							});
							// send response
							toRsp.end(body);
							cbFuture.complete();
						}
						ServiceDiscovery.releaseServiceObject(discovery, client);
					});
				});
		// set headers
		context.request().headers().forEach(header -> {
			toReq.putHeader(header.getKey(), header.getValue());
		});
		if (context.user() != null) {
			toReq.putHeader("user-principal", context.user().principal().encode());
		}
		// send request
		if (context.getBody() == null) {
			toReq.end();
		} else {
			toReq.end(context.getBody());
		}
	}

	private void apiVersion(RoutingContext context) {
		context.response().end(new JsonObject().put("version", "v1").encodePrettily());
	}

	/**
	 * Get all REST endpoints from the service discovery infrastructure.
	 *
	 * @return async result
	 */
	private Future<List<Record>> getAllEndpoints() {
		Promise<List<Record>> promise = Promise.promise();
		discovery.getRecords(record -> record.getType().equals(HttpEndpoint.TYPE), promise);
		return promise.future();
	}

	// log methods
	private void publishGatewayLog(String info) {
		JsonObject message = new JsonObject()
				.put("info", info)
				.put("time", System.currentTimeMillis());
		publishLogEvent("gateway", message);
	}

	// auth

	private void userLoginHandler(RoutingContext context) {
		String username = context.getBodyAsJson().getString("username");
		String password = context.getBodyAsJson().getString("password");

		EventBusService.getProxy(discovery, AccountService.class, ar -> {
			if (ar.succeeded()) {
				AccountService service = ar.result();
				service.authenticateUser(username, password, res -> {
					ServiceDiscovery.releaseServiceObject(discovery, service);
					if (res.succeeded() && res.result() != null) {
						Account acc = res.result();
						context.response().putHeader("content-type", "application/json");
						JsonObject principal = new JsonObject()
								.put("username", acc.getUsername())
								.put("role", acc.getRole());
						context.response().end(jwt.generateToken(principal, new JWTOptions().setExpiresInMinutes(60)));
					} else {
						unauthorized(context);
					}
				});
			} else {
				internalError(context, ar.cause());
			}
		});
	}

	private void agentLoginHandler(RoutingContext context) {
		String username = context.getBodyAsJson().getString("username");
		String password = context.getBodyAsJson().getString("password");

		EventBusService.getProxy(discovery, AccountService.class, ar -> {
			if (ar.succeeded()) {
				AccountService service = ar.result();
				service.authenticateAgent(username, password, res -> {
					ServiceDiscovery.releaseServiceObject(discovery, service);
					if (res.succeeded() && res.result() != null) {
						Account acc = res.result();
						context.response().putHeader("content-type", "application/json");
						JsonObject principal = new JsonObject()
								.put("username", acc.getUsername())
								.put("role", acc.getRole())
								.put("nodeId", acc.getNodeId());
						context.response().end(jwt.generateToken(principal, new JWTOptions().setExpiresInMinutes(60)));
					} else {
						unauthorized(context);
					}
				});
			} else {
				internalError(context, ar.cause());
			}
		});
	}

	private void logoutHandler(RoutingContext context) {
		context.clearUser();
		context.session().destroy();
		context.response().setStatusCode(204).end();
	}

	/* private String buildHostURI() {
    int port = config().getInteger("api.gateway.http.port", DEFAULT_PORT);
    final String host = config().getString("api.gateway.http.address.external", "localhost");
    return String.format("https://%s:%d", host, port);
  } */
}
