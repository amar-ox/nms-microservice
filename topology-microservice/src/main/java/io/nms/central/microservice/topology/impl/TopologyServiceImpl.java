package io.nms.central.microservice.topology.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.nms.central.microservice.common.service.JdbcRepositoryWrapper;
import io.nms.central.microservice.topology.TopologyService;
import io.nms.central.microservice.topology.api.RestTopologyAPIVerticle;
import io.nms.central.microservice.topology.model.ModelHelper;
import io.nms.central.microservice.topology.model.Vctp;
import io.nms.central.microservice.topology.model.Vlink;
import io.nms.central.microservice.topology.model.VlinkConn;
import io.nms.central.microservice.topology.model.Vltp;
import io.nms.central.microservice.topology.model.Vnode;
import io.nms.central.microservice.topology.model.Vsubnet;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * JDBC implementation of {@link io.vertx.blueprint.microservice.product.ProductService}.
 *
 * @author Eric Zhao
 */
public class TopologyServiceImpl extends JdbcRepositoryWrapper implements TopologyService {

	private static final Logger logger = LoggerFactory.getLogger(TopologyServiceImpl.class);
	// private static final int PAGE_LIMIT = 10;

	public TopologyServiceImpl(Vertx vertx, JsonObject config) {
		super(vertx, config);
	}

	@Override
	public TopologyService initializePersistence(Handler<AsyncResult<List<Integer>>> resultHandler) {
		List<String> statements = new ArrayList<String>();
		statements.add(Sql.CREATE_TABLE_VSUBNET);
		statements.add(Sql.CREATE_TABLE_VNODE);
		statements.add(Sql.CREATE_TABLE_VLTP);
		statements.add(Sql.CREATE_TABLE_VLINK);
		statements.add(Sql.CREATE_TABLE_VCTP);
		statements.add(Sql.CREATE_TABLE_VLINKCONN);
		client.getConnection(connHandler(resultHandler, connection -> {
			connection.batch(statements, r -> {
				resultHandler.handle(r);
				connection.close();
			});
		}));
		return this;
	}

	/********** Vsubnet **********/
	@Override
	// INSERT INTO Vsubnet (id, label, description)
	public TopologyService addVsubnet(Vsubnet vsubnet, Handler<AsyncResult<Void>> resultHandler) {
		logger.debug("addSubnet: "+vsubnet.toString());
		JsonArray params = new JsonArray()
				.add(vsubnet.getId())
				.add(vsubnet.getLabel())
				.add(vsubnet.getDescription());
		executeNoResult(params, Sql.INSERT_VSUBNET, resultHandler);
		return this;
	}

	@Override
	public TopologyService getVsubnet(String vsubnetId, Handler<AsyncResult<Vsubnet>> resultHandler) {
		this.retrieveOne(vsubnetId, Sql.FETCH_VSUBNET)
			.map(option -> option.map(Vsubnet::new).orElse(null))
			.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService deleteVsubnet(String vsubnetId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vsubnetId, Sql.DELETE_VSUBNET, resultHandler);
		return this;
	}

	@Override
	public TopologyService getAllVsubnets(Handler<AsyncResult<List<Vsubnet>>> resultHandler) {
		this.retrieveAll(Sql.FETCH_ALL_VSUBNETS)
		.map(rawList -> rawList.stream()
				.map(Vsubnet::new)
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}



	/********** Vnode **********/

	@Override
	// id, label, description, posx, posy, location, type, managed, status, vsubnetId
	public TopologyService addVnode(Vnode vnode, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vnode.getId())
				.add(vnode.getLabel())
				.add(vnode.getDescription())
				.add(vnode.getPosx())
				.add(vnode.getPosy())
				.add(vnode.getLocation())
				.add(vnode.getType())
				.add(vnode.getManaged())
				.add(vnode.getStatus())
				.add(vnode.getVsubnetId());
		executeNoResult(params, Sql.INSERT_VNODE, resultHandler);
		return this;
	}

	@Override
	public TopologyService getVnode(String vnodeId, Handler<AsyncResult<Vnode>> resultHandler) {
		JsonArray params = new JsonArray().add(vnodeId);
		this.retrieveMany(params, Sql.FETCH_VNODE)
			.map(rawList -> ModelHelper.toVnodeFromJsonRows(rawList))
			.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getAllVnodes(Handler<AsyncResult<List<Vnode>>> resultHandler) {
		this.retrieveAll(Sql.FETCH_ALL_VNODES)
		.map(rawList -> rawList.stream()
				.map(Vnode::new)
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService deleteVnode(String vnodeId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vnodeId, Sql.DELETE_VNODE, resultHandler);
		return this;
	}



	/********** Vltp **********/

	@Override
	// INSERT INTO Vltp (id, label, description, status, busy, vnodeId) 
	public TopologyService addVltp(Vltp vltp, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vltp.getId())
				.add(vltp.getLabel())
				.add(vltp.getDescription())
				.add(vltp.getStatus())
				.add(vltp.isBusy())
				.add(vltp.getVnodeId());
		executeNoResult(params, Sql.INSERT_VLTP, resultHandler);
		return this;
	}

	@Override
	public TopologyService getVltp(String vltpId, Handler<AsyncResult<Vltp>> resultHandler) {
		JsonArray params = new JsonArray().add(vltpId);
		this.retrieveMany(params, Sql.FETCH_VLTP)
		.map(rawList -> ModelHelper.toVltpFromJsonRows(rawList))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVltps(Handler<AsyncResult<List<Vltp>>> resultHandler) {
		this.retrieveAll(Sql.FETCH_ALL_VLTPS)
		.map(rawList -> rawList.stream()
				.map(Vltp::new)
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getVltpsByVnode(String vnodeId, Handler<AsyncResult<List<Vltp>>> resultHandler) {
		JsonArray params = new JsonArray().add(vnodeId);
		this.retrieveMany(params, Sql.FETCH_VLTPS_BY_VNODE)
		.map(rawList -> rawList.stream()
				.map(Vltp::new)
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService deleteVltp(String vltpId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vltpId, Sql.DELETE_VLTP, resultHandler);
		return this;
	}



	/********** Vctp **********/

	@Override
	// INSERT INTO Vctp (id, label, description, connType, connValue, status, vltpId, vlinkId)
	public TopologyService addVctp(Vctp vctp, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vctp.getId())
				.add(vctp.getLabel())
				.add(vctp.getDescription())
				.add(vctp.getConnType())
				.add(vctp.getConnValue())
				.add(vctp.getStatus())
				.add(vctp.getVltpId())
				.add(vctp.getVlinkId());
		executeNoResult(params, Sql.INSERT_VCTP, resultHandler);
		return this;
	}

	@Override
	public TopologyService getVctp(String vctpId, Handler<AsyncResult<Vctp>> resultHandler) {
		this.retrieveOne(vctpId, Sql.FETCH_VCTP)
		.map(option -> option.map(Vctp::new).orElse(null))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVctps(Handler<AsyncResult<List<Vctp>>> resultHandler) {
		this.retrieveAll(Sql.FETCH_ALL_VCTPS)
		.map(rawList -> rawList.stream()
				.map(Vctp::new)
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getVctpsByVltp(String vltpId, Handler<AsyncResult<List<Vctp>>> resultHandler) {
		JsonArray params = new JsonArray().add(vltpId);
		this.retrieveMany(params, Sql.FETCH_VCTPS_BY_VLTP)
		.map(rawList -> rawList.stream()
				.map(Vctp::new)
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getVctpsByVlink(String vlinkId, Handler<AsyncResult<List<Vctp>>> resultHandler) {
		JsonArray params = new JsonArray().add(vlinkId);
		this.retrieveMany(params, Sql.FETCH_VCTPS_BY_VLINK)
		.map(rawList -> rawList.stream()
				.map(Vctp::new)
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService deleteVctp(String vctpId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vctpId, Sql.DELETE_VCTP, resultHandler);
		return this;
	}



	/********** Vlink **********/
	@Override
	// INSERT INTO Vlink (id, label, description, status, speed, srcVltpId, destVltpId) 
	public TopologyService addVlink(Vlink vlink, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vlink.getId())
				.add(vlink.getLabel())
				.add(vlink.getDescription())
				.add(vlink.getStatus())
				.add(vlink.getSpeed())
				.add(vlink.getSrcVltpId())
				.add(vlink.getDestVltpId());
		executeNoResult(params, Sql.INSERT_VLINK, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVlink(String vlinkId, Handler<AsyncResult<Vlink>> resultHandler) {
		JsonArray params = new JsonArray().add(vlinkId);
		this.retrieveMany(params, Sql.FETCH_VLINK)
			.map(rawList -> ModelHelper.toVlinkFromJsonRows(rawList))
			.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVlinks(Handler<AsyncResult<List<Vlink>>> resultHandler) {
		this.retrieveAll(Sql.FETCH_ALL_VLINKS)
		.map(rawList -> rawList.stream()
				.map(Vlink::new)
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService deleteVlink(String vlinkId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vlinkId, Sql.DELETE_VLINK, resultHandler);
		return this;
	}



	/********** VlinkConn **********/
	@Override
	// INSERT INTO VlinkConn (id, label, description, srcVctpId, destVctpId, status)
	public TopologyService addVlinkConn(VlinkConn vlinkConn, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vlinkConn.getId())
				.add(vlinkConn.getLabel())
				.add(vlinkConn.getDescription())
				.add(vlinkConn.getSrcVctpId())
				.add(vlinkConn.getDestVctpId())
				.add(vlinkConn.getStatus());
		logger.debug(vlinkConn.toString());
		executeNoResult(params, Sql.INSERT_VLINKCONN, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVlinkConn(String vlinkConnId, Handler<AsyncResult<VlinkConn>> resultHandler) {
		this.retrieveOne(vlinkConnId, Sql.FETCH_VLINKCONN)
		.map(option -> option.map(VlinkConn::new).orElse(null))
		.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getAllVlinkConns(Handler<AsyncResult<List<VlinkConn>>> resultHandler) {
		this.retrieveAll(Sql.FETCH_ALL_VLINKCONNS)
		.map(rawList -> rawList.stream()
				.map(VlinkConn::new)
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService deleteVlinkConn(String vlinkConnId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vlinkConnId, Sql.DELETE_VLINKCONN, resultHandler);
		return this;
	}



}
