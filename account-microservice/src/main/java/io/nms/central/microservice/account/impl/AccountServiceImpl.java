package io.nms.central.microservice.account.impl;

import java.util.List;
import java.util.stream.Collectors;

import io.nms.central.microservice.account.AccountService;
import io.nms.central.microservice.account.model.Account;
import io.nms.central.microservice.account.model.Agent;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.mongo.UpdateOptions;

/**
 *
 */
public class AccountServiceImpl implements AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	private static final String COLL_USER = "users";
	private static final String COLL_AGENT = "agents";
	
	private final MongoClient client;
	private final Vertx vertx;

	public AccountServiceImpl(Vertx vertx, JsonObject config) {
		this.vertx = vertx;
		this.client = MongoClient.create(vertx, config);
	}
	
	@Override
	public void initializePersistence(Handler<AsyncResult<Void>> resultHandler) {
		JsonObject jAdmin = new JsonObject()
				.put("username", "admin")
				.put("password", "admin")
				.put("role", "admin");
		JsonObject query = new JsonObject().put("username", "admin");
		UpdateOptions opts = new UpdateOptions().setUpsert(true);
		JsonObject upd = new JsonObject().put("$set", jAdmin);
		client.updateCollectionWithOptions(COLL_USER, query, upd, opts, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void authenticateAgent(String username, String password, Handler<AsyncResult<Account>> resultHandler) {
		JsonObject query = new JsonObject()
				.put("username", username)
				.put("password", password);
		JsonObject fields = new JsonObject().put("password", 0);
		
		client.findOne(COLL_AGENT, query, fields, ar -> {
			if (ar.succeeded()) {
				if (ar.result() == null) {
					resultHandler.handle(Future.succeededFuture(null));
				} else {
					Account result = new Account(ar.result());
					resultHandler.handle(Future.succeededFuture(result));
				}
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	@Override
	public void authenticateUser(String username, String password, Handler<AsyncResult<Account>> resultHandler) {
		JsonObject query = new JsonObject()
				.put("username", username)
				.put("password", password);
		JsonObject fields = new JsonObject().put("password", 0);
		
		client.findOne(COLL_USER, query, fields, ar -> {
			if (ar.succeeded()) {
				if (ar.result() == null) {
					resultHandler.handle(Future.succeededFuture(null));
				} else {
					Account result = new Account(ar.result());
					resultHandler.handle(Future.succeededFuture(result));
				}
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	
	
	/* ---------------------------- */
	@Override
	public void saveAgent(Agent agent, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject query = new JsonObject().put("username", agent.getUsername());
		UpdateOptions opts = new UpdateOptions().setUpsert(true);
		JsonObject upd = new JsonObject().put("$set", agent.toJson());
		client.updateCollectionWithOptions(COLL_AGENT, query, upd, opts, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	@Override
	public void retrieveAllAgents(Handler<AsyncResult<List<Agent>>> resultHandler) {
		client.find(COLL_AGENT, new JsonObject(), ar -> {
			if (ar.succeeded()) {
				if (ar.result() == null) {
					resultHandler.handle(Future.succeededFuture());
				} else {
					List<Agent> result = ar.result().stream()
						.map(Agent::new)
						.collect(Collectors.toList());
					resultHandler.handle(Future.succeededFuture(result));
				}
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	@Override
	public void removeAgent(String username, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject query = new JsonObject().put("username", username);
		client.removeDocument(COLL_AGENT, query, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	
	/* @Override
	public void authenticateAgent(String username, String password, Handler<AsyncResult<Account>> resultHandler) {
		JsonObject query = new JsonObject()
				.put("username", username)
				.put("password", password);
		JsonObject fields = new JsonObject().put("password", 0);
		
		client.findOne(COLL_AGENT, query, fields, ar -> {
			if (ar.succeeded()) {
				if (ar.result() == null) {
					resultHandler.handle(Future.succeededFuture(null));
				} else {
					Account result = new Account(ar.result());
					resultHandler.handle(Future.succeededFuture(result));
				}
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	} */
	
}

