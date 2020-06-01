package io.nms.central.microservice.topology.api;

import io.nms.central.microservice.common.RestAPIVerticle;
import io.nms.central.microservice.topology.TopologyService;
import io.nms.central.microservice.topology.model.Node;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


/**
 * This verticle exposes a HTTP endpoint to process shopping products management with REST APIs.
 *
 * @author Eric Zhao
 */
public class RestTopologyAPIVerticle extends RestAPIVerticle {

  public static final String SERVICE_NAME = "topology-rest-api";

  private static final String API_VERSION = "/v";
  private static final String API_ADD_NODE = "/node";
  private static final String API_ALL_NODES = "/nodes";
  // private static final String API_RETRIEVE_PRICE = "/:productId/price";
  // private static final String API_RETRIEVE = "/:productId";
  // private static final String API_UPDATE = "/:productId";
  // private static final String API_DELETE = "/:productId";
  // private static final String API_DELETE_ALL = "/all";

  private final TopologyService service;

  public RestTopologyAPIVerticle(TopologyService service) {
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
    
    router.post(API_ADD_NODE).handler(this::apiAddNode);
    router.get(API_ALL_NODES).handler(this::apiGetAllNodes);
    // router.get(API_RETRIEVE_ALL).handler(this::apiRetrieveAll);
    // router.get(API_RETRIEVE_PRICE).handler(this::apiRetrievePrice);
    // router.get(API_RETRIEVE).handler(this::apiRetrieve);
    // router.patch(API_UPDATE).handler(this::apiUpdate);
    // router.delete(API_DELETE).handler(this::apiDelete);
    // router.delete(API_DELETE_ALL).handler(context -> requireLogin(context, this::apiDeleteAll));

    // get HTTP host and port from configuration, or use default value
    String host = config().getString("topology.http.address", "0.0.0.0");
    int port = config().getInteger("topology.http.port", 8085);

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
  
  private void apiAddNode(RoutingContext context) {
	  final Node node = Json.decodeValue(context.getBodyAsString(), Node.class);
	  if (!node.checkForAdding()) {
	      badRequest(context, new IllegalStateException(node.getError()));
	  } else {
	      JsonObject result = new JsonObject().put("message", "node_added");
	      service.addNode(node, resultVoidHandler(context, result));
	  }
  }
  
  private void apiGetAllNodes(RoutingContext context) {
	  service.getAllNodes(resultHandlerNonEmpty(context));
  }

}
