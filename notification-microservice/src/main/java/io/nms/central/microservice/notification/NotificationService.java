package io.nms.central.microservice.notification;

import io.nms.central.microservice.common.Status;
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
	
	
	String STATUS_ADDRESS = "notification.status";
	
	
	public void processStatus(Status status, Handler<AsyncResult<Void>> resultHandler);
	public void saveStatus(Status status, Handler<AsyncResult<Void>> resultHandler);
	public void retrieveStatus(String id, Handler<AsyncResult<Status>> resultHandler);
	public void removeStatus(String id, Handler<AsyncResult<Void>> resultHandler);
	public void removeAllStatus(Handler<AsyncResult<Void>> resultHandler);

}
