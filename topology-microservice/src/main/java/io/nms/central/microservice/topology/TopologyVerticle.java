package io.nms.central.microservice.topology;

import static io.nms.central.microservice.topology.TopologyService.SERVICE_ADDRESS;
import static io.nms.central.microservice.topology.TopologyService.SERVICE_NAME;

import java.util.List;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.nms.central.microservice.topology.api.RestTopologyAPIVerticle;
import io.nms.central.microservice.topology.impl.TopologyServiceImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ProxyHelper;


/**
 * A verticle publishing the tepology service.
 */
public class TopologyVerticle extends BaseMicroserviceVerticle {

  @Override
  public void start(Future<Void> future) throws Exception {
    super.start();
    // create the service instance
    TopologyService topologyService = new TopologyServiceImpl(vertx, config());
    // register the service proxy on event bus
    ProxyHelper.registerService(TopologyService.class, vertx, topologyService, SERVICE_ADDRESS);
    
    initTopologyDatabase(topologyService)
    	.compose(databaseOkay -> publishEventBusService(SERVICE_NAME, SERVICE_ADDRESS, TopologyService.class))
    	.compose(servicePublished ->  publishMessageSource("config-message-source", TopologyService.CONFIG_ADDRESS))
    	.compose(sourcePublished -> deployHandler(topologyService))
    	.compose(handlerPrepared -> deployRestVerticle(topologyService))
    	.onComplete(future);
  }
  
  private Future<Void> initTopologyDatabase(TopologyService service) {
	  Promise<Void> initPromise = Promise.promise();
	    service.initializePersistence(initPromise);
	    return initPromise.future();
  }
  
  private Future<Void> deployHandler(TopologyService service) {
	    Promise<String> promise = Promise.promise();
	    vertx.deployVerticle(new StatusHandler(service),
	      new DeploymentOptions().setConfig(config()), promise);
	    return promise.future().map(r -> null);
	 }

  private Future<Void> deployRestVerticle(TopologyService service) {
    Promise<String> promise = Promise.promise();
    vertx.deployVerticle(new RestTopologyAPIVerticle(service),
      new DeploymentOptions().setConfig(config()), promise);
    return promise.future().map(r -> null);
  }

}