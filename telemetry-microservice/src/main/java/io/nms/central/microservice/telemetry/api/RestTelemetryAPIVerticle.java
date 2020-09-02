package io.nms.central.microservice.telemetry.api;

import io.nms.central.microservice.common.RestAPIVerticle;
import io.nms.central.microservice.telemetry.TelemetryService;
import io.nms.central.microservice.telemetry.casper.CasperTelemetryAPIVerticle;
import io.nms.central.microservice.telemetry.model.Interrupt;
import io.nms.central.microservice.telemetry.model.Specification;
import io.vertx.core.Future;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


/**
 * This verticle exposes a HTTP endpoint to process telemetry operations with REST APIs.
 */
public class RestTelemetryAPIVerticle extends RestAPIVerticle {

	public static final String SERVICE_NAME = "telemetry-rest-api";

	private static final String API_VERSION = "/v";

	private static final String API_ALL_CAPABILITIES = "/capabilities";

	private static final String API_SPECIFICATION = "/specification";
	private static final String API_ALL_SPECIFICATIONS = "/specifications";

	private static final String API_INTERRUPT = "/interrupt";

	private static final String API_ALL_RECEIPTS = "/receipts";

	private static final String API_ALL_OPERATIONS = "/operations/:type";
	private static final String API_ONE_OPERATION = "/operation/:opId";
	private static final String API_RESULTS_BY_OPS = "/operation/:opId/results";
	
	private static final String API_ONE_RESULT = "/result/:resId";

	private final TelemetryService service;
	private final CasperTelemetryAPIVerticle casper;

	public RestTelemetryAPIVerticle(TelemetryService service, CasperTelemetryAPIVerticle casper) {
		this.service = service;
		this.casper = casper;
	}

	@Override
	public void start(Future<Void> future) throws Exception {
		super.start();
		final Router router = Router.router(vertx);

		// body handler
		router.route().handler(BodyHandler.create());

		// API route handler
		router.get(API_VERSION).handler(this::apiVersion);

		router.get(API_ALL_CAPABILITIES).handler(this::checkAdminRole).handler(this::apiGetAllCapabilities);

		router.get(API_ALL_SPECIFICATIONS).handler(this::checkAdminRole).handler(this::apiGetAllSpecifications);
		router.post(API_SPECIFICATION).handler(this::checkAdminRole).handler(this::apiSendSpecification);

		router.post(API_INTERRUPT).handler(this::checkAdminRole).handler(this::apiSendInterrupt);

		router.get(API_ALL_RECEIPTS).handler(this::checkAdminRole).handler(this::apiGetAllReceipts);


		router.get(API_ALL_OPERATIONS).handler(this::checkAdminRole).handler(this::apiGetAllOperations);
		router.delete(API_ONE_OPERATION).handler(this::checkAdminRole).handler(this::apiDeleteOperation);

		router.get(API_RESULTS_BY_OPS).handler(this::checkAdminRole).handler(this::apiGetResultsByOp);
		
		router.get(API_ONE_RESULT).handler(this::checkAdminRole).handler(this::apiGetResult);

		// get HTTP host and port from configuration, or use default value
		String host = config().getString("telemetry.http.address", "0.0.0.0");
		int port = config().getInteger("telemetry.http.port", 8082);

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

	private void apiGetAllCapabilities(RoutingContext context) {
		JsonObject principal = new JsonObject(context.request().getHeader("user-principal"));
		String role = principal.getString("role");
		service.getCapabilitiesByRole(role, resultHandlerNonEmpty(context));
	}

	private void apiGetAllSpecifications(RoutingContext context) {
		service.getAllSpecifications(resultHandlerNonEmpty(context));
	}

	private void apiSendSpecification(RoutingContext context) {
		String specId = context.request().getParam("specId");
		Specification spec;
		try {
			spec = Json.decodeValue(context.getBodyAsString(), Specification.class);
		} catch (DecodeException e) {
			badRequest(context, new Throwable("wrong or missing request body"));
			return;
		}
		JsonObject principal = new JsonObject(context.request().getHeader("user-principal"));
		spec.setToken(principal.getString("role"));
		casper.processSpecification(specId, spec, resultHandlerNonEmpty(context));
	}

	private void apiSendInterrupt(RoutingContext context) {
		String itrId = context.request().getParam("itrId");
		Interrupt itr;
		try {
			itr = Json.decodeValue(context.getBodyAsString(), Interrupt.class);
		} catch (DecodeException e) {
			badRequest(context, new Throwable("wrong or missing request body"));
			return;
		}
		JsonObject principal = new JsonObject(context.request().getHeader("user-principal"));
		itr.setToken(principal.getString("role"));
		casper.processInterrupt(itrId, itr, resultHandlerNonEmpty(context));
	}

	private void apiGetAllReceipts(RoutingContext context) {
		service.getAllReceipts(resultHandlerNonEmpty(context));
	}

	private void apiGetAllOperations(RoutingContext context) {
		String type = context.request().getParam("type");
		service.getAllResultOperations(type, resultHandlerNonEmpty(context));
	}

	private void apiDeleteOperation(RoutingContext context) {
		String opId = context.request().getParam("opId");
		service.removeResultsByOperation(opId, deleteResultHandler(context));
	}

	private void apiGetResultsByOp(RoutingContext context) {
		String opId = context.request().getParam("opId");
		service.getResultsByOperation(opId, resultHandlerNonEmpty(context));
	}
	
	private void apiGetResult(RoutingContext context) {
		String resId = context.request().getParam("resId");
		service.getResult(resId, resultHandlerNonEmpty(context));
	}
}
