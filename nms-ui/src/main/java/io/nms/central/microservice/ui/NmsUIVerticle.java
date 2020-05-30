package io.nms.central.microservice.ui;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

/**
 * A simple SPA front-end for the online shopping microservice application.
 *
 * @author Eric Zhao
 */
public class NmsUIVerticle extends BaseMicroserviceVerticle {

  private static final Logger logger = LoggerFactory.getLogger(NmsUIVerticle.class);

  @Override
  public void start(Future<Void> future) throws Exception {
    super.start();
    Router router = Router.router(vertx);

    // event bus bridge
    SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
    router.route("/eventbus/*").handler(sockJSHandler);

    // static content
    router.route("/*").handler(StaticHandler.create());

    // get HTTP host and port from configuration, or use default value
    String host = config().getString("nms.ui.http.address", "0.0.0.0");
    int port = config().getInteger("nms.ui.http.port", 8080);

    // create HTTP server
    vertx.createHttpServer()
      .requestHandler(router::accept)
      .listen(port, ar -> {
        if (ar.succeeded()) {
          future.complete();
          logger.info(String.format("NMS UI service is running at %d", port));
        } else {
          future.fail(ar.cause());
        }
      });
  }
}
