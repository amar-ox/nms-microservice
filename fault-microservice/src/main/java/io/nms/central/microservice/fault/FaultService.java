package io.nms.central.microservice.fault;

import java.util.List;

import io.vertx.codegen.annotations.Fluent;
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
public interface FaultService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "fault-eb-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "service.fault";
	
	@Fluent	
	FaultService initializePersistence(Handler<AsyncResult<List<Integer>>> resultHandler);

	
	/* Example */
	/* @Fluent	
	TopologyService addVsubnet(Vsubnet vsubnet, Handler<AsyncResult<Void>> resultHandler);
	
	@Fluent	
	TopologyService getVsubnet(String vsubnetId, Handler<AsyncResult<Vsubnet>> resultHandler);
	
	@Fluent
	TopologyService getAllVsubnets(Handler<AsyncResult<List<Vsubnet>>> resultHandler);
	
	@Fluent	
	TopologyService deleteVsubnet(String vsubnetId, Handler<AsyncResult<Void>> resultHandler);
	
	@Fluent 
	TopologyService updateVsubnet(String id, Vsubnet vsubnet, Handler<AsyncResult<Vsubnet>> resultHandler); */
}
