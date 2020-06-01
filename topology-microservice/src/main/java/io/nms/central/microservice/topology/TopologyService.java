package io.nms.central.microservice.topology;

import java.util.List;

import io.nms.central.microservice.topology.model.Ctp;
import io.nms.central.microservice.topology.model.Link;
import io.nms.central.microservice.topology.model.LinkConn;
import io.nms.central.microservice.topology.model.Ltp;
import io.nms.central.microservice.topology.model.Node;
import io.nms.central.microservice.topology.model.Topology;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * A service interface managing products.
 * <p>
 * This service is an event bus service (aka. service proxy)
 * </p>
 *
 * @author Eric Zhao
 */
@VertxGen
@ProxyGen
public interface TopologyService {

  /**
   * The name of the event bus service.
   */
  String SERVICE_NAME = "topology-eb-service";

  /**
   * The address on which the service is published.
   */
  String SERVICE_ADDRESS = "service.topology";

  /**
   * Add a node to the persistence.
   *
   * @param node       a node entity that we want to add
   * @param resultHandler the result handler will be called as soon as the product has been added. The async result indicates
   *                      whether the operation was successful or not.
   */
  void addNode(Node node, Handler<AsyncResult<Void>> resultHandler);
  void getNode(String nodeId, Handler<AsyncResult<Node>> resultHandler);
  void getNodes(List<String> nodeIds, Handler<AsyncResult<List<Node>>> resultHandler);	
  void getAllNodes(Handler<AsyncResult<List<Node>>> resultHandler);
  void deleteNode(String nodeId, Handler<AsyncResult<Void>> resultHandler);

  void addLtp(Ltp ltp, Handler<AsyncResult<Void>> resultHandler);
  void getLtp(String ltpId, Handler<AsyncResult<Ltp>> resultHandler);
  void deleteLtp(String ltpId, Handler<AsyncResult<Void>> resultHandler);
	
  void addCtp(Ctp ctp, Handler<AsyncResult<Void>> resultHandler);
  void getCtp(String ctpId, Handler<AsyncResult<Ctp>> resultHandler);
  void deleteCtp(String ctpId, Handler<AsyncResult<Void>> resultHandler);
	
  void addLink(Link linke, Handler<AsyncResult<Void>> resultHandler);
  void getLink(String linkId, Handler<AsyncResult<Link>> resultHandler);
  void getAllLinks(Handler<AsyncResult<List<Link>>> resultHandler);
  void deleteLink(String linkId, Handler<AsyncResult<Void>> resultHandler);
	
  void addLinkConn(LinkConn linkConn, Handler<AsyncResult<Void>> resultHandler);
  void getLinkConn(String linkConnId, Handler<AsyncResult<LinkConn>> resultHandler);
  void getAllLinkConns(Handler<AsyncResult<List<LinkConn>>> resultHandler);
  void deleteLinkConn(String linkConnId, Handler<AsyncResult<Void>> resultHandler);

  void getTopology(int level, Handler<AsyncResult<Topology>> resultHandler);  
  
}
