package io.nms.central.microservice.topology;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.nms.central.microservice.common.service.ExampleHelper;
import io.nms.central.microservice.topology.api.RestTopologyAPIVerticle;
import io.nms.central.microservice.topology.impl.TopologyServiceImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ProxyHelper;

import static io.nms.central.microservice.topology.TopologyService.SERVICE_ADDRESS;
import static io.nms.central.microservice.topology.TopologyService.SERVICE_NAME;


/**
 * A verticle publishing the product service.
 *
 * @author Eric Zhao
 */
public class TopologyVerticle extends BaseMicroserviceVerticle {

  @Override
  public void start(Future<Void> future) throws Exception {
    super.start();

    // create the service instance
    TopologyService topologyService = new TopologyServiceImpl(vertx, config());
    // register the service proxy on event bus
    ProxyHelper.registerService(TopologyService.class, vertx, topologyService, SERVICE_ADDRESS);
    
    initDatabase(topologyService)
    	.compose(databaseOkay -> publishEventBusService(SERVICE_NAME, SERVICE_ADDRESS, TopologyService.class))
    	.compose(servicePublished -> deployRestVerticle(topologyService))
    	.onComplete(future);
  }
  
  private Future<Void> initDatabase(TopologyService service) {	   
	  Promise<Void> initPromise = Promise.promise();
	  initPromise.complete();
	  return initPromise.future();
  }

  private Future<Void> deployRestVerticle(TopologyService service) {
    Promise<String> promise = Promise.promise();
    vertx.deployVerticle(new RestTopologyAPIVerticle(service),
      new DeploymentOptions().setConfig(config()), promise.future());
    return promise.future().map(r -> null);
  }

}