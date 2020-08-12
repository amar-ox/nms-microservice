package io.nms.central.microservice.account.api;

import io.nms.central.microservice.account.AccountService;
import io.nms.central.microservice.account.model.Agent;
import io.nms.central.microservice.account.model.User;
import io.nms.central.microservice.common.RestAPIVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


/**
 * This verticle exposes a HTTP endpoint to process shopping products management with REST APIs.
 *
 * @author Amar Abane
 */
public class RestAccountAPIVerticle extends RestAPIVerticle {

	public static final String SERVICE_NAME = "account-rest-api";
	
	private static final String API_VERSION = "/v";
	
	private static final String API_ALL_USERS = "/users";
	private static final String API_ONE_USER = "/user/:username";
	private static final String API_USERS_BY_ROLE = "/users/role/:role";
	
	private static final String API_ALL_AGENTS = "/agents";
	private static final String API_ONE_AGENT = "/agent/:agentId";

	
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
		
		router.put(API_ONE_USER).handler(this::apiPutUser);
		router.get(API_ONE_USER).handler(this::apiGetUser);
		router.delete(API_ONE_USER).handler(this::apiDeleteUser);
		router.get(API_ALL_USERS).handler(this::apiGetAllUsers);
		router.get(API_USERS_BY_ROLE).handler(this::apiGetUsersByRole);
		
		router.put(API_ONE_AGENT).handler(this::apiPutAgent);
		router.get(API_ONE_AGENT).handler(this::apiGetAgent);
		router.delete(API_ONE_AGENT).handler(this::apiDeleteAgent);
		router.get(API_ALL_AGENTS).handler(this::apiGetAllAgents);

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
	
	
	private void apiPutUser(RoutingContext context) {
		final String username = context.request().getParam("username");
		User user = Json.decodeValue(context.getBodyAsString(), User.class);
		user.setUsername(username);
		JsonObject result = new JsonObject()
				.put("message", "user_added")
				.put("username", username);
		service.saveUser(user, resultVoidHandler(context, result));
	}
	private void apiGetUser(RoutingContext context) {
		String username = context.request().getParam("username");		
		service.retrieveUser(username, resultHandlerNonEmpty(context));
	}
	private void apiGetAllUsers(RoutingContext context) {
		service.retrieveAllUsers(resultHandler(context, Json::encodePrettily));
	}	
	private void apiGetUsersByRole(RoutingContext context) {
		String role = context.request().getParam("role");
		service.retrieveUsersByRole(role, resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteUser(RoutingContext context) {
		String username = context.request().getParam("username");		
		service.removeUser(username, deleteResultHandler(context));
	}
	
	
	private void apiPutAgent(RoutingContext context) {
		final String username = context.request().getParam("agentId");
		Agent agent = Json.decodeValue(context.getBodyAsString(), Agent.class);
		agent.setUsername(username);
		JsonObject result = new JsonObject()
				.put("message", "agent_added")
				.put("username", username);
		service.saveAgent(agent, resultVoidHandler(context, result));
	}
	private void apiGetAgent(RoutingContext context) {
		String username = context.request().getParam("agentId");		
		service.retrieveAgent(username, resultHandlerNonEmpty(context));
	}
	private void apiGetAllAgents(RoutingContext context) {
		service.retrieveAllAgents(resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteAgent(RoutingContext context) {
		String username = context.request().getParam("agentId");
		service.removeAgent(username, deleteResultHandler(context));
	}
}
