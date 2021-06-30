package io.nms.central.microservice.account.api;

import io.nms.central.microservice.account.AccountService;
import io.nms.central.microservice.account.model.Agent;
import io.nms.central.microservice.common.RestAPIVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


/**
 * This verticle exposes a HTTP endpoint to process user accounts management with REST APIs.
 */
public class RestAccountAPIVerticle extends RestAPIVerticle {

	public static final String SERVICE_NAME = "account-rest-api";
	
	private static final String API_VERSION = "/v";
	
	private static final String API_ALL_AGENTS = "/agent/all";
	private static final String API_ONE_AGENT = "/agent/:username";

	
	private final AccountService service;

	public RestAccountAPIVerticle(AccountService service) {
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
		
		router.put(API_ONE_AGENT).handler(this::checkAdminRole).handler(this::apiPutAgent);
		router.get(API_ALL_AGENTS).handler(this::checkAdminRole).handler(this::apiGetAllAgents);
		router.delete(API_ONE_AGENT).handler(this::checkAdminRole).handler(this::apiDeleteAgent);
		

		// get HTTP host and port from configuration, or use default value
		String host = config().getString("account.http.address", "0.0.0.0");
		int port = config().getInteger("account.http.port", 8083);

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
	
	private void apiPutAgent(RoutingContext context) {
		String username = context.request().getParam("username");
		Agent agent = Json.decodeValue(context.getBodyAsString(), Agent.class);
		agent.setUsername(username);
		service.saveAgent(agent, resultVoidHandler(context, 201));
	}
	private void apiGetAllAgents(RoutingContext context) {
		service.retrieveAllAgents(resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteAgent(RoutingContext context) {
		String username = context.request().getParam("username");
		service.removeAgent(username, deleteResultHandler(context));
	}
}
