package io.nms.central.microservice.fault;

import static io.nms.central.microservice.fault.FaultService.SERVICE_ADDRESS;
import static io.nms.central.microservice.fault.FaultService.SERVICE_NAME;

import java.util.List;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.nms.central.microservice.fault.api.RestFaultAPIVerticle;
import io.nms.central.microservice.fault.impl.FaultServiceImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ProxyHelper;


/**
 * A verticle publishing the product service.
 *
 * @author Eric Zhao
 */
public class FaultVerticle extends BaseMicroserviceVerticle {

  @Override
  public void start(Future<Void> future) throws Exception {
    super.start();
    // create the service instance
    FaultService faultService = new FaultServiceImpl(vertx, config());
    // register the service proxy on event bus
    ProxyHelper.registerService(FaultService.class, vertx, faultService, SERVICE_ADDRESS);
    
    initFaultDatabase(faultService)
    	.compose(databaseOkay -> publishEventBusService(SERVICE_NAME, SERVICE_ADDRESS, FaultService.class))
    	.compose(servicePublished -> deployRestVerticle(faultService))
    	.onComplete(future);
  }
  
  private Future<Void> initFaultDatabase(FaultService service) {
	  /* Promise<List<Integer>> initPromise = Promise.promise();
	    service.initializePersistence(initPromise);
	    return initPromise.future().map(v -> {
	      ExampleHelper.initData(vertx, config());
	      return null;
	    });*/
	    Promise<Void> initPromise = Promise.promise();
		  initPromise.complete();
		  return initPromise.future();
  }

  private Future<Void> deployRestVerticle(FaultService service) {
    Promise<String> promise = Promise.promise();
    vertx.deployVerticle(new RestFaultAPIVerticle(service),
      new DeploymentOptions().setConfig(config()), promise.future());
    return promise.future().map(r -> null);
  }

}