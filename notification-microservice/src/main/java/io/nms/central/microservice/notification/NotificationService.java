package io.nms.central.microservice.notification;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

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
public interface NotificationService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "notification-eb-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "service.notification";
	
	
	String REPORTS_ADDRESS = "notification.reports";
	String UPDATE_ADDRESS = "notification.update";
	
	
	public void processReport(JsonObject report, Handler<AsyncResult<JsonObject>> resultHandler);

}
