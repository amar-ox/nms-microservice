package io.nms.central.microservice.dss;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.nms.central.microservice.common.service.ExampleHelper;
import io.nms.central.microservice.dss.api.RestDataStreamingAPIVerticle;
import io.nms.central.microservice.dss.impl.DataStreamingServiceImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ProxyHelper;

import static io.nms.central.microservice.dss.DataStreamingService.SERVICE_ADDRESS;


/**
 * A verticle publishing the product service.
 *
 * @author Eric Zhao
 */
public class DataStreamingVerticle extends BaseMicroserviceVerticle {

  @Override
  public void start(Future<Void> future) throws Exception {
    super.start();

    // create the service instance
    DataStreamingService productService = new DataStreamingServiceImpl(vertx, config());
    // register the service proxy on event bus
    ProxyHelper.registerService(DataStreamingService.class, vertx, productService, SERVICE_ADDRESS);
    // publish the service in the discovery infrastructure    
    initProductDatabase(productService)
      .compose(databaseOkay -> publishEventBusService(DataStreamingService.SERVICE_NAME, SERVICE_ADDRESS, DataStreamingService.class))
      .compose(servicePublished -> deployRestService(productService))
      .setHandler(future.completer());
  }

  private Future<Void> initProductDatabase(DataStreamingService service) {
    /*Future<Void> initFuture = Future.future();
    service.initializePersistence(initFuture.completer());
    return initFuture.map(v -> {
      ExampleHelper.initData(vertx, config());
      return null;
    });*/
	  Promise<Void> initPromise = Promise.promise();
	  initPromise.complete();
	  return initPromise.future();
  }

  private Future<Void> deployRestService(DataStreamingService service) {
    Future<String> future = Future.future();
    vertx.deployVerticle(new RestDataStreamingAPIVerticle(service),
      new DeploymentOptions().setConfig(config()),
      future.completer());
    return future.map(r -> null);
  }

}
