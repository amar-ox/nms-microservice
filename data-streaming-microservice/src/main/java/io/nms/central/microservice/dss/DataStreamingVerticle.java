package io.nms.central.microservice.dss;

import static io.nms.central.microservice.dss.DataStreamingService.SERVICE_ADDRESS;
import static io.nms.central.microservice.dss.DataStreamingService.SERVICE_NAME;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.nms.central.microservice.dss.api.RestDataStreamingAPIVerticle;
import io.nms.central.microservice.dss.impl.DataStreamingServiceImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ProxyHelper;


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
    DataStreamingService dataStreamingService = new DataStreamingServiceImpl(vertx, config());
    // register the service proxy on event bus
    ProxyHelper.registerService(DataStreamingService.class, vertx, dataStreamingService, SERVICE_ADDRESS);
    // publish the service in the discovery infrastructure    
    initDatabase(dataStreamingService)
      .compose(databaseOkay -> publishEventBusService(SERVICE_NAME, SERVICE_ADDRESS, DataStreamingService.class))
      .compose(servicePublished -> deployRestVerticle(dataStreamingService))
      .onComplete(future);
  }

  private Future<Void> initDatabase(DataStreamingService service) {
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

  private Future<Void> deployRestVerticle(DataStreamingService service) {
    Future<String> future = Future.future();
    vertx.deployVerticle(new RestDataStreamingAPIVerticle(service),
      new DeploymentOptions().setConfig(config()),
      future.completer());
    return future.map(r -> null);
  }

}
