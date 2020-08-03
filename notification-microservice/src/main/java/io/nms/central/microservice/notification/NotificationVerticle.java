package io.nms.central.microservice.notification;

import static io.nms.central.microservice.notification.NotificationService.SERVICE_ADDRESS;
import static io.nms.central.microservice.notification.NotificationService.SERVICE_NAME;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.nms.central.microservice.notification.api.RestNotificationAPIVerticle;
import io.nms.central.microservice.notification.impl.NotificationServiceImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ProxyHelper;


/**
 * A verticle publishing the product service.
 *
 * @author Eric Zhao
 */
public class NotificationVerticle extends BaseMicroserviceVerticle {

  @Override
  public void start(Future<Void> future) throws Exception {
    super.start();
    // create the service instance
    NotificationService notificationService = new NotificationServiceImpl(vertx, config());
    // register the service proxy on event bus
    ProxyHelper.registerService(NotificationService.class, vertx, notificationService, SERVICE_ADDRESS);
    
    publishEventBusService(SERVICE_NAME, SERVICE_ADDRESS, NotificationService.class)
    	.compose(servicePublished ->  publishMessageSource("status-message-source", NotificationService.STATUS_ADDRESS))
    	.compose(sourcePublished -> deployRestVerticle(notificationService))
    	.onComplete(future);
  }

  private Future<Void> deployRestVerticle(NotificationService service) {
    Promise<String> promise = Promise.promise();
    vertx.deployVerticle(new RestNotificationAPIVerticle(service),
      new DeploymentOptions().setConfig(config()), promise.future());
    return promise.future().map(r -> null);
  }

}