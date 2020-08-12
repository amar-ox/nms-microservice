package io.nms.central.microservice.configuration.api;

import io.nms.central.microservice.common.RestAPIVerticle;
import io.nms.central.microservice.configuration.ConfigurationService;
import io.nms.central.microservice.configuration.model.ConfigObj;
import io.nms.central.microservice.notification.model.Status;
import io.vertx.core.Future;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.core.http.HttpHeaders;


/**
 * This verticle exposes a HTTP endpoint to process shopping products management with REST APIs.
 *
 * @author Amar Abane
 */
public class RestConfigurationAPIVerticle extends RestAPIVerticle {

	// private static final Logger logger = LoggerFactory.getLogger(RestConfigurationAPIVerticle.class);

	public static final String SERVICE_NAME = "configuration-rest-api";
	

	private static final String API_VERSION = "/v";

	private static final String API_ALL_CANDIDATE_CONFIG = "/candidate-config/all";
	private static final String API_ONE_CANDIDATE_CONFIG = "/candidate-config/:nodeId";

	private static final String API_ALL_RUNNING_CONFIG = "/running-config/all";
	private static final String API_ONE_RUNNING_CONFIG = "/running-config/:nodeId";

	
	private static final String API_AGENT_CANDIDATE_CONFIG = "/ag/candidate-config";
	private static final String API_AGENT_RUNNING_CONFIG = "/ag/running-config";


	private final ConfigurationService service;

	public RestConfigurationAPIVerticle(ConfigurationService service) {
		this.service = service;
	}

	@Override
	public void start(Future<Void> future) throws Exception {
		super.start();
		final Router router = Router.router(vertx);
		// body handler
		router.route().handler(BodyHandler.create());

		// API route handler
		router.get(API_VERSION).handler(this::apiVersion);

		router.get(API_ONE_CANDIDATE_CONFIG).handler(this::apiGetCandidateConfig);
		// TODO: get all, delete one
		router.delete(API_ALL_CANDIDATE_CONFIG).handler(this::apiDeleteAllCandidateConfigs);

		router.get(API_ONE_RUNNING_CONFIG).handler(this::apiGetRunningConfig);
		// TODO: get all, delete one
		router.delete(API_ALL_RUNNING_CONFIG).handler(this::apiDeleteAllRunningConfigs);


		// Agent only
		router.get(API_AGENT_CANDIDATE_CONFIG).handler(this::apiGetAgentCandidateConfig);
		router.put(API_AGENT_RUNNING_CONFIG).handler(this::apiPutAgentRunningConfig);
		router.patch(API_AGENT_RUNNING_CONFIG).handler(this::apiPatchAgentRunningConfig);

		// get HTTP host and port from configuration, or use default value
		String host = config().getString("configuration.http.address", "0.0.0.0");
		int port = config().getInteger("configuration.http.port", 8088);

		// create HTTP server and publish REST service
		createHttpServer(router, host, port)
		.compose(serverCreated -> publishHttpEndpoint(SERVICE_NAME, host, port))
		.onComplete(future);
	}

	private void apiVersion(RoutingContext context) { 
		context.response()
		.end(new JsonObject()
				.put("name", SERVICE_NAME)
				.put("version", "v1").encodePrettily());
	}

	private void apiGetCandidateConfig(RoutingContext context) {
		String nodeId = context.request().getParam("nodeId");		
		service.getCandidateConfig(Integer.valueOf(nodeId), res -> {
			if (res.succeeded()) {
				ConfigObj cg = res.result();
				if (cg == null) {
					notFound(context);
				} else {
					String ifNoneMatch = context.request().headers().get(HttpHeaders.IF_NONE_MATCH);
					String etag = String.valueOf(cg.hashCode());
					if (ifNoneMatch != null && ifNoneMatch.equals(etag)) {
						notChanged(context);
					} else {
						context.response()
						.putHeader(HttpHeaders.ETAG, etag)
						.putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
						.end(cg.getConfig().toString());
					}
				}
			} else {
				internalError(context, res.cause());
			}
		});
	}
	private void apiDeleteAllCandidateConfigs(RoutingContext context) {		
		service.removeAllCandidateConfigs(deleteResultHandler(context));
	}


	private void apiGetRunningConfig(RoutingContext context) {
		String nodeId = context.request().getParam("nodeId");		
		service.getRunningConfig(Integer.valueOf(nodeId), resultHandlerNonEmpty(context));
	}

	private void apiDeleteAllRunningConfigs(RoutingContext context) {		
		service.removeAllRunningConfigs(deleteResultHandler(context));
	}


	// for agent only 
	private void apiGetAgentCandidateConfig(RoutingContext context) {
		JsonObject principal = new JsonObject(context.request().getHeader("user-principal"));
		int nodeId = principal.getInteger("nodeId", 0);
		service.getCandidateConfig(nodeId, res -> {
			if (res.succeeded()) {
				ConfigObj cg = res.result();
				if (cg == null) {
					notFound(context);
				} else {
					String ifNoneMatch = context.request().headers().get(HttpHeaders.IF_NONE_MATCH);
					String etag = String.valueOf(cg.hashCode());
					if (ifNoneMatch != null && ifNoneMatch.equals(etag)) {
						notChanged(context);
					} else {
						context.response()
							.putHeader(HttpHeaders.ETAG, etag)
							.putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
							.end(cg.getConfig().toString());
					}
				}
			} else {
				internalError(context, res.cause());
			}
		});
	}
	// support PUT and PATCH: json patch an be larger than the whole put object 
	private void apiPutAgentRunningConfig(RoutingContext context) {
		JsonObject principal = new JsonObject(context.request().getHeader("user-principal"));
		int nodeId = principal.getInteger("nodeId", 0);
		ConfigObj config = new ConfigObj(context.getBodyAsJson());
		service.upsertRunningConfig(nodeId, config, resultVoidHandler(context, 200));
	}

	private void apiPatchAgentRunningConfig(RoutingContext context) {
		JsonObject principal = new JsonObject(context.request().getHeader("user-principal"));
		int nodeId = principal.getInteger("nodeId", 0);
		try {
			Object patch = Json.decodeValue(context.getBodyAsString());
			if (patch instanceof JsonArray) {
				service.updateRunningConfig(nodeId, (JsonArray) patch, resultVoidHandler(context, 200));
			} else {
				badRequest(context, new IllegalStateException("patch must be an array"));
			}
		} catch (DecodeException e) {
			badRequest(context, e);
		}
	}
}
