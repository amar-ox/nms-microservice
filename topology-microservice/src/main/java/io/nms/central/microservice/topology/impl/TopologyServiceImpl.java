package io.nms.central.microservice.topology.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.nms.central.microservice.topology.TopologyService;
import io.nms.central.microservice.topology.model.Ctp;
import io.nms.central.microservice.topology.model.Link;
import io.nms.central.microservice.topology.model.LinkConn;
import io.nms.central.microservice.topology.model.Ltp;
import io.nms.central.microservice.topology.model.Node;
import io.nms.central.microservice.topology.model.Topology;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/**
 * JDBC implementation of {@link io.vertx.blueprint.microservice.product.ProductService}.
 *
 * @author Eric Zhao
 */
public class TopologyServiceImpl implements TopologyService {

	private static final String NODE = "node";
	private static final String LTP = "ltp";
	private static final String CTP = "ctp";
	private static final String LINK = "link";
	private static final String LINK_CONN = "linkConn";
	private static final String FACE = "face";
	
	private final MongoClient client;

	public TopologyServiceImpl(Vertx vertx, JsonObject config) {		
		this.client = MongoClient.createShared(vertx, config);
	}

	@Override
	public void addNode(Node node, Handler<AsyncResult<Void>> resultHandler) {
		client.save(NODE, node.toJson(), ar -> {
			if (ar.succeeded()) {							
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void getNode(String nodeId, Handler<AsyncResult<Node>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getNodes(List<String> nodeIds, Handler<AsyncResult<List<Node>>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getAllNodes(Handler<AsyncResult<List<Node>>> resultHandler) {		
		client.find(NODE, new JsonObject(), ar -> {
			if (ar.succeeded()) {
				if (ar.result() == null) {
					resultHandler.handle(Future.succeededFuture());
				} else {
					
					List<Node> nodes = ar.result().stream().map(Node::new).collect(Collectors.toList());					
					resultHandler.handle(Future.succeededFuture(nodes));
				}
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void deleteNode(String nodeId, Handler<AsyncResult<Void>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addLtp(Ltp ltp, Handler<AsyncResult<Void>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getLtp(String ltpId, Handler<AsyncResult<Ltp>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteLtp(String ltpId, Handler<AsyncResult<Void>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addCtp(Ctp ctp, Handler<AsyncResult<Void>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getCtp(String ctpId, Handler<AsyncResult<Ctp>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCtp(String ctpId, Handler<AsyncResult<Void>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addLink(Link linke, Handler<AsyncResult<Void>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getLink(String linkId, Handler<AsyncResult<Link>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getAllLinks(Handler<AsyncResult<List<Link>>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteLink(String linkId, Handler<AsyncResult<Void>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addLinkConn(LinkConn linkConn, Handler<AsyncResult<Void>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getLinkConn(String linkConnId, Handler<AsyncResult<LinkConn>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getAllLinkConns(Handler<AsyncResult<List<LinkConn>>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteLinkConn(String linkConnId, Handler<AsyncResult<Void>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getTopology(int level, Handler<AsyncResult<Topology>> resultHandler) {
		// TODO Auto-generated method stub

	}

}
