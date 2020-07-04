package io.nms.central.microservice.telemetry.api;

import io.nms.central.microservice.common.RestAPIVerticle;
import io.nms.central.microservice.telemetry.TelemetryService;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


/**
 * This verticle exposes a HTTP endpoint to process shopping products management with REST APIs.
 *
 * @author Eric Zhao
 */
public class RestTelemetryAPIVerticle extends RestAPIVerticle {

  public static final String SERVICE_NAME = "telemetry-rest-api";

  private static final String API_VERSION = "/v";
  // private static final String API_RETRIEVE_BY_PAGE = "/products";
  // private static final String API_RETRIEVE_ALL = "/products";
  // private static final String API_RETRIEVE_PRICE = "/:productId/price";
  // private static final String API_RETRIEVE = "/:productId";
  // private static final String API_UPDATE = "/:productId";
  // private static final String API_DELETE = "/:productId";
  // private static final String API_DELETE_ALL = "/all";

  private final TelemetryService service;

  public RestTelemetryAPIVerticle(TelemetryService service) {
    this.service = service;
  }

  @Override
  public void start(Future<Void> future) throws Exception {
    super.start();
    final Router router = Router.router(vertx);
    // body handler
    router.route().handler(BodyHandler.create());
    // API route handler
    router.get(API_VERSION).handler(this::apiVersion);
    // router.get(API_RETRIEVE_BY_PAGE).handler(this::apiRetrieveByPage);
    // router.get(API_RETRIEVE_ALL).handler(this::apiRetrieveAll);
    // router.get(API_RETRIEVE_PRICE).handler(this::apiRetrievePrice);
    // router.get(API_RETRIEVE).handler(this::apiRetrieve);
    // router.patch(API_UPDATE).handler(this::apiUpdate);
    // router.delete(API_DELETE).handler(this::apiDelete);
    // router.delete(API_DELETE_ALL).handler(context -> requireLogin(context, this::apiDeleteAll));

    // get HTTP host and port from configuration, or use default value
    String host = config().getString("telemetry.http.address", "0.0.0.0");
    int port = config().getInteger("telemetry.http.port", 8082);

    // create HTTP server and publish REST service
    createHttpServer(router, host, port)
      .compose(serverCreated -> publishHttpEndpoint(SERVICE_NAME, host, port))
      .setHandler(future.completer());
  }

  private void apiVersion(RoutingContext context) {	  
	  context.response()
      .end(new JsonObject()
    		  .put("name", SERVICE_NAME)
    		  .put("version", "v1").encodePrettily());
  }

}
