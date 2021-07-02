package io.nms.central.microservice.crossconnect;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * A service interface managing crossconnect.
 * <p>
 * This service is an event bus service (aka. service proxy)
 * </p>
 */
@VertxGen
@ProxyGen
public interface CrossconnectService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "crossconnect-eb-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "service.crossconnect";
	
	
	String UI_ADDRESS = "nms.to.ui";

	String EVENT_ADDRESS = "crossconnect.event";
	
	
	@Fluent	
	CrossconnectService initializePersistence(Handler<AsyncResult<Void>> resultHandler);

}