package io.nms.central.microservice.configuration.api;

import io.nms.central.microservice.common.RestAPIVerticle;
import io.nms.central.microservice.configuration.ConfigurationService;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


/**
 * This verticle exposes a HTTP endpoint to process shopping products management with REST APIs.
 *
 * @author Amar Abane
 */
public class RestConfigurationAPIVerticle extends RestAPIVerticle {

	// private static final Logger logger = LoggerFactory.getLogger(RestConfigurationAPIVerticle.class);

	public static final String SERVICE_NAME = "configuration-rest-api";

	private static final String API_VERSION = "/v";
	
	private static final String API_ALL_CANDIDATE_CONFIGS = "/candidate-configs";
	private static final String API_CANDIDATE_CONFIG = "/candidate-config/:nodeId";
	
	private static final String API_RUNNING_CONFIG = "/running-config/:nodeId";

	
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

		router.get(API_CANDIDATE_CONFIG).handler(this::apiGetCandidateConfig);
		router.delete(API_ALL_CANDIDATE_CONFIGS).handler(this::apiDeleteAllCandidateConfigs);
	
		router.patch(API_RUNNING_CONFIG).handler(this::apiUpdateRunningConfig);

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
		service.getCandidateConfig(Integer.valueOf(nodeId), resultHandlerNonEmpty(context));
	}
	private void apiDeleteAllCandidateConfigs(RoutingContext context) {		
		service.removeAllCandidateConfigs(deleteResultHandler(context));
	}
	
	
	private void apiUpdateRunningConfig(RoutingContext context) {
		String nodeId = context.request().getParam("nodeId");		
		service.updateRunningConfig(nodeId, context.getBodyAsJson(), resultVoidHandler(context, 204));
	}
}
