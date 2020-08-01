package io.nms.central.microservice.gateway;

import java.util.HashSet;
import java.util.Set;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

/**
 * A simple SPA front-end for the online shopping microservice application.
 *
 * @author Eric Zhao
 */
public class NmsUIVerticle extends BaseMicroserviceVerticle {

  private static final Logger logger = LoggerFactory.getLogger(NmsUIVerticle.class);

  @Override
  public void start(Promise<Void> promise) throws Exception {
    super.start();
    Router router = Router.router(vertx);
    
    Set<String> allowHeaders = new HashSet<>();
    allowHeaders.add("x-requested-with");
    allowHeaders.add("Access-Control-Allow-Origin");
    allowHeaders.add("origin");
    allowHeaders.add("Content-Type");
    allowHeaders.add("accept");
    Set<HttpMethod> allowMethods = new HashSet<>();
    allowMethods.add(HttpMethod.GET);   

    router.route().handler(CorsHandler.create("http://localhost:8080")
      .allowedHeaders(allowHeaders)
      .allowedMethods(allowMethods));
    
    // event bus bridge
    SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
    BridgeOptions bridgeOptions = new BridgeOptions()
			.addOutboundPermitted(new PermittedOptions().setAddressRegex("nms.*"));
	sockJSHandler.bridge(bridgeOptions);
	
    router.route("/eventbus/*").handler(sockJSHandler);

    String host = config().getString("api.gateway.http.address", "localhost");
    int port = config().getInteger("nms.ui.http.port", 8888);

    // create HTTP server
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(port, host, ar -> {
        if (ar.succeeded()) {
        	promise.complete();
        } else {
        	promise.fail(ar.cause());
        }
      });
  }
}
