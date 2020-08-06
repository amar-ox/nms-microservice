package io.nms.central.microservice.configuration;

import java.util.List;

import io.nms.central.microservice.topology.model.Route;
import io.nms.central.microservice.topology.model.Vnode;
import io.nms.central.microservice.topology.model.Face;
import io.nms.central.microservice.configuration.model.ConfigObj;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
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
public interface ConfigurationService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "configuration-eb-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "service.configuration";
	

	String UI_ADDRESS = "nms.to.ui";
	
	
	void initializePersistence(Handler<AsyncResult<List<Integer>>> resultHandler);

	
	/* API */
	// void saveCandidateConfig(ConfigObj config, Handler<AsyncResult<Void>> resultHandler);
	void getCandidateConfig(int nodeId, Handler<AsyncResult<ConfigObj>> resultHandler);
	void removeAllCandidateConfigs(Handler<AsyncResult<Void>> resultHandler);
	
	
	void upsertRunningConfig(int nodeId, ConfigObj config, Handler<AsyncResult<Void>> resultHandler);
	void updateRunningConfig(int nodeId, JsonArray patch, Handler<AsyncResult<Void>> resultHandler);
	void getRunningConfig(int nodeId, Handler<AsyncResult<ConfigObj>> resultHandler);
	void removeAllRunningConfigs(Handler<AsyncResult<Void>> resultHandler);
	
		
	/* Processing */
	void computeConfigurations(List<Route> routes, List<Face> faces, List<Vnode> nodes, Handler<AsyncResult<List<ConfigObj>>> resultHandler);
	void upsertCandidateConfigs(List<ConfigObj> configs, Handler<AsyncResult<Void>> resultHandler);
	
}
