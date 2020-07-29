package io.nms.central.microservice.notification.api;

import io.nms.central.microservice.common.RestAPIVerticle;
import io.nms.central.microservice.notification.NotificationService;
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
public class RestNotificationAPIVerticle extends RestAPIVerticle {

	// private static final Logger logger = LoggerFactory.getLogger(RestRoutingAPIVerticle.class);

	public static final String SERVICE_NAME = "notification-rest-api";

	private static final String API_VERSION = "/v";
	
	private static final String API_ONE_REPORT = "/report/:reportId";


	private final NotificationService service;

	public RestNotificationAPIVerticle(NotificationService service) {
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
		
		router.put(API_ONE_REPORT).handler(this::apiProcessReport);

		// get HTTP host and port from configuration, or use default value
		String host = config().getString("notification.http.address", "0.0.0.0");
		int port = config().getInteger("notification.http.port", 8086);

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

	// Report API (for TEST)
		private void apiProcessReport(RoutingContext context) {
			String reportId = context.request().getParam("reportId");
			final JsonObject report = context.getBodyAsJson();
			// JsonObject result = new JsonObject().put("message", "report processed");
			service.processReport(report, resultHandler(context, JsonObject::encode));
		}
}
