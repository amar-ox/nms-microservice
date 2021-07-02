package io.nms.central.microservice.crossconnect;

import static io.nms.central.microservice.crossconnect.CrossconnectService.SERVICE_ADDRESS;
import static io.nms.central.microservice.crossconnect.CrossconnectService.SERVICE_NAME;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.nms.central.microservice.crossconnect.api.RestCrossconnectAPIVerticle;
import io.nms.central.microservice.crossconnect.impl.CrossconnectServiceImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ProxyHelper;


/**
 * A verticle publishing the crossconnect service.
 */
public class CrossconnectVerticle extends BaseMicroserviceVerticle {

  @Override
  public void start(Future<Void> future) throws Exception {
    super.start();
    // create the service instance
    CrossconnectService crossconnectService = new CrossconnectServiceImpl(vertx, config());
    // register the service proxy on event bus
    ProxyHelper.registerService(CrossconnectService.class, vertx, crossconnectService, SERVICE_ADDRESS);
    
    initCrossconnectDatabase(crossconnectService)
    	.compose(databaseOkay -> publishEventBusService(SERVICE_NAME, SERVICE_ADDRESS, CrossconnectService.class))
    	.compose(servicePublished -> deployRestVerticle(crossconnectService))
    	.onComplete(future);
  }
  
  private Future<Void> initCrossconnectDatabase(CrossconnectService service) {
	  Promise<Void> initPromise = Promise.promise();
	    service.initializePersistence(initPromise);
	    return initPromise.future();
  }

  private Future<Void> deployRestVerticle(CrossconnectService service) {
    Promise<String> promise = Promise.promise();
    vertx.deployVerticle(new RestCrossconnectAPIVerticle(service),
      new DeploymentOptions().setConfig(config()), promise);
    return promise.future().map(r -> null);
  }

}