package io.nms.central.microservice.telemetry;

import static io.nms.central.microservice.telemetry.TelemetryService.SERVICE_ADDRESS;
import static io.nms.central.microservice.telemetry.TelemetryService.SERVICE_NAME;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.nms.central.microservice.telemetry.api.RestTelemetryAPIVerticle;
import io.nms.central.microservice.telemetry.impl.TelemetryServiceImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ProxyHelper;
import io.vertx.serviceproxy.ServiceBinder;


/**
 * A verticle publishing the product service.
 *
 * @author Eric Zhao
 */
public class TelemetryVerticle extends BaseMicroserviceVerticle {

  @Override
  public void start(Future<Void> future) throws Exception {
    super.start();

    // create the service instance
    TelemetryService telemetryService = new TelemetryServiceImpl(vertx, config());
    
    // register the service proxy on event bus
    // ProxyHelper.registerService(TelemetryService.class, vertx, telemetryService, SERVICE_ADDRESS);
    ServiceBinder binder = new ServiceBinder(vertx);
    binder.setAddress(SERVICE_ADDRESS);
    binder.register(TelemetryService.class, telemetryService);
    
    
    
    // publish the service in the discovery infrastructure    
    initDatabase(telemetryService)
      .compose(databaseOkay -> publishEventBusService(SERVICE_NAME, SERVICE_ADDRESS, TelemetryService.class))
      .compose(servicePublished -> deployRestVerticle(telemetryService))
      .onComplete(future);
  }

  private Future<Void> initDatabase(TelemetryService service) {
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

  private Future<Void> deployRestVerticle(TelemetryService service) {
    Future<String> future = Future.future();
    vertx.deployVerticle(new RestTelemetryAPIVerticle(service),
      new DeploymentOptions().setConfig(config()),
      future.completer());
    return future.map(r -> null);
  }

}
