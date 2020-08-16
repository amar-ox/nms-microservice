package io.nms.central.microservice.account;

import java.util.List;

import io.nms.central.microservice.account.model.Account;
import io.nms.central.microservice.account.model.Agent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * A service interface managing products.
 * <p>
 * This service is an event bus service (aka. service proxy)
 * </p>
 *
 * @author Eric Zhao
 */
@VertxGen
@ProxyGen
public interface AccountService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "account-eb-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "service.account";
	

	String UI_ADDRESS = "nms.to.ui";
	
	
	void initializePersistence(Handler<AsyncResult<Void>> resultHandler);

	
	/* API */
	void authenticateAgent(String username, String password, Handler<AsyncResult<Account>> resultHandler);
	void authenticateUser(String username, String password, Handler<AsyncResult<Account>> resultHandler);
	
	void saveAgent(Agent agent, Handler<AsyncResult<Void>> resultHandler);
	void retrieveAllAgents(Handler<AsyncResult<List<Agent>>> resultHandler);
	void removeAgent(String username, Handler<AsyncResult<Void>> resultHandler);
}
