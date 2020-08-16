package io.nms.central.microservice.notification.api;

import io.nms.central.microservice.common.RestAPIVerticle;
import io.nms.central.microservice.notification.NotificationService;
import io.nms.central.microservice.notification.model.Event;
import io.nms.central.microservice.notification.model.Fault;
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
	private static final String API_ALL_STATUS = "/status/all";
	
	private static final String API_ONE_EVENT = "/event/:eventId";
	private static final String API_ALL_EVENT = "/event/all";
	
	private static final String API_ONE_FAULT = "/fault/:faultId";
	private static final String API_ALL_FAULT = "/fault/all";


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

		router.get(API_ALL_STATUS).handler(this::checkAdminRole).handler(this::apiGetAllStatus); 
		router.put(API_ONE_STATUS).handler(this::checkAgentRole).handler(this::apiPutStatus);
		router.delete(API_ONE_STATUS).handler(this::checkAdminRole).handler(this::apiDeleteStatus);
		
		router.get(API_ALL_EVENT).handler(this::checkAdminRole).handler(this::apiGetAllEvents); 
		router.put(API_ONE_EVENT).handler(this::checkAgentRole).handler(this::apiPutEvent);
		router.delete(API_ONE_EVENT).handler(this::checkAdminRole).handler(this::apiDeleteEvent);
		
		router.get(API_ALL_FAULT).handler(this::checkAdminRole).handler(this::apiGetAllFaults); 
		router.put(API_ONE_FAULT).handler(this::checkAgentRole).handler(this::apiPutFault);
		router.delete(API_ONE_FAULT).handler(this::checkAdminRole).handler(this::apiDeleteFault);
		
		
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

	private void apiPutStatus(RoutingContext context) {
		JsonObject principal = new JsonObject(context.request().getHeader("user-principal"));
		int resId = principal.getInteger("nodeId", 0);
		String statusId = context.request().getParam("statusId");
		Status status = new Status(context.getBodyAsJson());
		status.setId(statusId);
		status.setResId(resId);
		status.setResType("node");
		JsonObject result = new JsonObject().put("message", "report processed");
		service.processStatus(status, resultVoidHandler(context, result));
	}

	private void apiGetAllStatus(RoutingContext context) {		
		service.retrieveAllStatus(resultHandlerNonEmpty(context));
	}

	private void apiDeleteStatus(RoutingContext context) {
		String statusId = context.request().getParam("statusId");		
		service.removeStatus(statusId, deleteResultHandler(context));
	}
	
	
	private void apiPutEvent(RoutingContext context) {
		JsonObject principal = new JsonObject(context.request().getHeader("user-principal"));
		int origin = principal.getInteger("nodeId");
		String eventId = context.request().getParam("eventId");
		Event event = new Event(context.getBodyAsJson());
		event.setId(eventId);
		event.setOrigin(origin);
		JsonObject result = new JsonObject().put("message", "event_saved");
		service.saveEvent(event, resultVoidHandler(context, result));
	}

	private void apiGetAllEvents(RoutingContext context) {		
		service.retrieveAllEvents(resultHandlerNonEmpty(context));
	}

	private void apiDeleteEvent(RoutingContext context) {
		String eventId = context.request().getParam("eventId");		
		service.removeEvent(eventId, deleteResultHandler(context));
	}
	
	
	private void apiPutFault(RoutingContext context) {
		JsonObject principal = new JsonObject(context.request().getHeader("user-principal"));
		int origin = principal.getInteger("nodeId");
		String faultId = context.request().getParam("faultId");
		Fault fault = new Fault(context.getBodyAsJson());
		fault.setId(faultId);
		fault.setOrigin(origin);
		JsonObject result = new JsonObject().put("message", "fault_saved");
		service.saveFault(fault, resultVoidHandler(context, result));
	}

	private void apiGetAllFaults(RoutingContext context) {		
		service.retrieveAllFaults(resultHandlerNonEmpty(context));
	}

	private void apiDeleteFault(RoutingContext context) {
		String faultId = context.request().getParam("faultId");		
		service.removeFault(faultId, deleteResultHandler(context));
	}

}
