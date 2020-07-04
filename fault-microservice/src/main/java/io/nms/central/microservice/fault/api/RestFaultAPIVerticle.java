package io.nms.central.microservice.fault.api;

import io.nms.central.microservice.common.RestAPIVerticle;
import io.nms.central.microservice.fault.FaultService;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


/**
 * This verticle exposes a HTTP endpoint to process shopping products management with REST APIs.
 *
 * @author Eric Zhao
 */
public class RestFaultAPIVerticle extends RestAPIVerticle {

	// private static final Logger logger = LoggerFactory.getLogger(RestRoutingAPIVerticle.class);

	public static final String SERVICE_NAME = "fault-rest-api";

	private static final String API_VERSION = "/v";

	// private static final String API_ADD_PREFIX = "/prefix";
	// private static final String API_GET_PREFIX = "/prefix/:prefixId";
	// private static final String API_GET_ALL_PREFIXES = "/pefixes";
	// private static final String API_DELETE_PREFIX = "/prefix/:prefixId";
	// private static final String API_UPDATE_PREFIX = "/prefix";


	private final FaultService service;

	public RestFaultAPIVerticle(FaultService service) {
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

		// router.post(API_ADD_PREFIX).handler(this::apiAddPrefix);
		// router.get(API_GET_ALL_PREFIXES).handler(this::apiGetAllPrefixes);
		// router.get(API_GET_PREFIX).handler(this::apiGetPrefixById);
		// router.delete(API_DELETE_PREFIX).handler(this::apiDeletePrefix);
		// router.patch(API_UPDATE_PREFIX).handler(this::apiUpdatePrefix);

		// get HTTP host and port from configuration, or use default value
		String host = config().getString("fault.http.address", "0.0.0.0");
		int port = config().getInteger("fault.http.port", 8086);

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
	
}
