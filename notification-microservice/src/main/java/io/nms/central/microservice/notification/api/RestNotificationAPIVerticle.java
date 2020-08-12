package io.nms.central.microservice.notification.api;

import io.nms.central.microservice.common.RestAPIVerticle;
import io.nms.central.microservice.notification.NotificationService;
import io.nms.central.microservice.notification.model.Status;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


/**
 * This verticle exposes a HTTP endpoint to process shopping products management with REST APIs.
 *
 * @author Eric Zhao
 */
public class RestNotificationAPIVerticle extends RestAPIVerticle {

	// private static final Logger logger = LoggerFactory.getLogger(RestNotificationAPIVerticle.class);

	public static final String SERVICE_NAME = "notification-rest-api";
	

	private static final String API_VERSION = "/v";

	private static final String API_ONE_STATUS = "/status/:statusId";
	private static final String API_STATUS = "/status";
	
	
	private static final String API_AGENT_STATUS = "/ag/status/:statusId";


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

		router.put(API_AGENT_STATUS).handler(this::apiProcessStatus);

		router.get(API_ONE_STATUS).handler(this::apiGetStatus);
		// TODO: get all status
		router.delete(API_ONE_STATUS).handler(this::apiDeleteStatus);
		router.delete(API_STATUS).handler(this::apiDeleteAllStatus);

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

	private void apiProcessStatus(RoutingContext context) {
		JsonObject principal = new JsonObject(context.request().getHeader("user-principal"));
		int resId = principal.getInteger("nodeId", 0);
		if (resId == 0) {
			badRequest(context, new IllegalStateException("Wrong nodeId"));
		} else {
			String statusId = context.request().getParam("statusId");
			Status status = new Status(context.getBodyAsJson());
			status.setId(statusId);
			status.setResId(resId);
			status.setResType("node");
			JsonObject result = new JsonObject().put("message", "report processed");
			service.processStatus(status, resultVoidHandler(context, result));
		}
	}

	private void apiGetStatus(RoutingContext context) {
		String statusId = context.request().getParam("statusId");		
		service.retrieveStatus(statusId, resultHandlerNonEmpty(context));
	}

	private void apiDeleteStatus(RoutingContext context) {
		String statusId = context.request().getParam("statusId");		
		service.removeStatus(statusId, deleteResultHandler(context));
	}

	private void apiDeleteAllStatus(RoutingContext context) {		
		service.removeAllStatus(deleteResultHandler(context));
	}
}
