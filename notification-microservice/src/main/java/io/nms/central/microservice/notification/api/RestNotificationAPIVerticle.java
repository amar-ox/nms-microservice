package io.nms.central.microservice.notification.api;

import io.nms.central.microservice.common.RestAPIVerticle;
import io.nms.central.microservice.notification.NotificationService;
import io.nms.central.microservice.notification.model.Event;
import io.nms.central.microservice.notification.model.Fault;
import io.nms.central.microservice.notification.model.Status;
import io.nms.central.microservice.notification.model.Status.ResTypeEnum;
import io.vertx.core.Future;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


/**
 * This verticle exposes a HTTP endpoint to process notifications with REST APIs.
 */
public class RestNotificationAPIVerticle extends RestAPIVerticle {

	private static final Logger logger = LoggerFactory.getLogger(RestNotificationAPIVerticle.class);

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
		router.put(API_ONE_STATUS).handler(this::apiPutStatus);
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
		Status status;
		try {
			status = Json.decodeValue(context.getBodyAsString(), Status.class);
		} catch (DecodeException e) {
			badRequest(context, new Throwable("wrong or missing request body"));
			return;
		}
		status.setId(context.request().getParam("statusId"));

		JsonObject principal = new JsonObject(context.request().getHeader("user-principal"));
		// For NDN Agent only
		if (principal.containsKey("nodeId")) {
			status.setResId(principal.getInteger("nodeId"));
			status.setResType(ResTypeEnum.NODE);
		}

		service.processStatus(status, resultVoidHandler(context, 201));
	}

	private void apiGetAllStatus(RoutingContext context) {		
		service.retrieveAllStatus(resultHandlerNonEmpty(context));
	}

	private void apiDeleteStatus(RoutingContext context) {
		String statusId = context.request().getParam("statusId");		
		service.removeStatus(statusId, deleteResultHandler(context));
	}
	
	
	private void apiPutEvent(RoutingContext context) {
		Event event;
		try {
			event = Json.decodeValue(context.getBodyAsString(), Event.class);
		} catch (DecodeException e) {
			badRequest(context, new Throwable("wrong or missing request body"));
			return;
		}
		
		if (event.getCode() == null) {
			badRequest(context, new Throwable("code is missing"));
			return;
		}
		if (event.getTimestamp() == null) {
			badRequest(context, new Throwable("timestamp is missing"));
			return;
		}
		if (event.getMsg() == null) {
			badRequest(context, new Throwable("msg is missing"));
			return;
		}
		if (event.getSeverity() == null) {
			badRequest(context, new Throwable("severity is missing"));
			return;
		}
		
		String eventId = context.request().getParam("eventId");
		
		JsonObject principal = new JsonObject(context.request().getHeader("user-principal"));
		int origin = principal.getInteger("nodeId");
		
		event.setId(eventId);
		event.setOrigin(origin);
		
		service.saveEvent(event, resultVoidHandler(context, 201));
	}

	private void apiGetAllEvents(RoutingContext context) {		
		service.retrieveAllEvents(resultHandlerNonEmpty(context));
	}

	private void apiDeleteEvent(RoutingContext context) {
		String eventId = context.request().getParam("eventId");		
		service.removeEvent(eventId, deleteResultHandler(context));
	}
	
	
	private void apiPutFault(RoutingContext context) {
		Fault fault;
		try {
			fault = Json.decodeValue(context.getBodyAsString(), Fault.class);
		} catch (DecodeException e) {
			badRequest(context, new Throwable("wrong or missing request body"));
			return;
		}
		
		if (fault.getCode() == null) {
			badRequest(context, new Throwable("code is missing"));
			return;
		}
		if (fault.getTimestamp() == null) {
			badRequest(context, new Throwable("timestamp is missing"));
			return;
			
		}
		if (fault.getMsg() == null) {
			badRequest(context, new Throwable("msg is missing"));
			return;
		}
		
		String faultId = context.request().getParam("faultId");
		
		JsonObject principal = new JsonObject(context.request().getHeader("user-principal"));
		int origin = principal.getInteger("nodeId");
		
		fault.setId(faultId);
		fault.setOrigin(origin);
		
		service.saveFault(fault, resultVoidHandler(context, 201));
	}

	private void apiGetAllFaults(RoutingContext context) {		
		service.retrieveAllFaults(resultHandlerNonEmpty(context));
	}

	private void apiDeleteFault(RoutingContext context) {
		String faultId = context.request().getParam("faultId");		
		service.removeFault(faultId, deleteResultHandler(context));
	}

}
