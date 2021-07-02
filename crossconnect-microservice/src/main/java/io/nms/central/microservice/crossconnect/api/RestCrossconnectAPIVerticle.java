package io.nms.central.microservice.crossconnect.api;

import io.nms.central.microservice.common.RestAPIVerticle;
import io.nms.central.microservice.crossconnect.CrossconnectService;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * This verticle exposes a HTTP endpoint to process crossconnect operations with
 * REST APIs.
 */
public class RestCrossconnectAPIVerticle extends RestAPIVerticle {

	private static final Logger logger = LoggerFactory.getLogger(RestCrossconnectAPIVerticle.class);

	public static final String SERVICE_NAME = "crossconnect-rest-api";

	private static final String API_VERSION = "/v";

	private final CrossconnectService service;

	public RestCrossconnectAPIVerticle(CrossconnectService service) {
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

		// get HTTP host and port from configuration, or use default value
		String host = config().getString("crossconnect.http.address", "0.0.0.0");
		int port = config().getInteger("crossconnect.http.port", 8089);

		// create HTTP server and publish REST service
		createHttpServer(router, host, port).compose(serverCreated -> publishHttpEndpoint(SERVICE_NAME, host, port))
				.onComplete(future);
	}

	private void apiVersion(RoutingContext context) {
		context.response().end(new JsonObject().put("name", SERVICE_NAME).put("version", "v1").encodePrettily());
	}
}
