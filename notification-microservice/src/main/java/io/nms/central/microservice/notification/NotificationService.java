package io.nms.central.microservice.notification;

import java.util.List;

import io.nms.central.microservice.notification.model.Event;
import io.nms.central.microservice.notification.model.Fault;
import io.nms.central.microservice.notification.model.Status;
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
	public void retrieveAllStatus(Handler<AsyncResult<List<Status>>> resultHandler);
	public void removeStatus(String id, Handler<AsyncResult<Void>> resultHandler);
	
	public void saveEvent(Event event, Handler<AsyncResult<Void>> resultHandler);
	public void retrieveAllEvents(Handler<AsyncResult<List<Event>>> resultHandler);
	public void removeEvent(String id, Handler<AsyncResult<Void>> resultHandler);
	
	public void saveFault(Fault fault, Handler<AsyncResult<Void>> resultHandler);
	public void retrieveAllFaults(Handler<AsyncResult<List<Fault>>> resultHandler);
	public void removeFault(String id, Handler<AsyncResult<Void>> resultHandler);

}
