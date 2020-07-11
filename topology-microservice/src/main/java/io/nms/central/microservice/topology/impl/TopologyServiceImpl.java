package io.nms.central.microservice.topology.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.nms.central.microservice.common.service.JdbcRepositoryWrapper;
import io.nms.central.microservice.topology.TopologyService;
import io.nms.central.microservice.topology.model.Edge;
import io.nms.central.microservice.topology.model.Face;
import io.nms.central.microservice.topology.model.ModelObjectMapper;
import io.nms.central.microservice.topology.model.Node;
import io.nms.central.microservice.topology.model.PrefixAnn;
import io.nms.central.microservice.topology.model.Route;
import io.nms.central.microservice.topology.model.Vctp;
import io.nms.central.microservice.topology.model.Vlink;
import io.nms.central.microservice.topology.model.VlinkConn;
import io.nms.central.microservice.topology.model.Vltp;
import io.nms.central.microservice.topology.model.Vnode;
import io.nms.central.microservice.topology.model.Vsubnet;
import io.nms.central.microservice.topology.model.Vtrail;
import io.nms.central.microservice.topology.model.Vxc;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 *
 */
public class TopologyServiceImpl extends JdbcRepositoryWrapper implements TopologyService {

	// private static final Logger logger = LoggerFactory.getLogger(TopologyServiceImpl.class);
	private Routing routing;

	public TopologyServiceImpl(Vertx vertx, JsonObject config) {
		super(vertx, config);
		routing = new Routing();
	}

	@Override
	public TopologyService initializePersistence(Handler<AsyncResult<List<Integer>>> resultHandler) {
		List<String> statements = new ArrayList<String>();
		statements.add(ApiSql.CREATE_TABLE_VSUBNET);
		statements.add(ApiSql.CREATE_TABLE_VNODE);
		statements.add(ApiSql.CREATE_TABLE_VLTP);
		statements.add(ApiSql.CREATE_TABLE_VLINK);
		statements.add(ApiSql.CREATE_TABLE_VCTP);
		statements.add(ApiSql.CREATE_TABLE_VLINKCONN);
		statements.add(ApiSql.CREATE_TABLE_VTRAIL);
		statements.add(ApiSql.CREATE_TABLE_VXC);
		statements.add(ApiSql.CREATE_TABLE_PREFIX_ANN);
		statements.add(ApiSql.CREATE_TABLE_FACE);
		statements.add(ApiSql.CREATE_TABLE_ROUTE);
		client.getConnection(connHandler(resultHandler, connection -> {
			connection.batch(statements, r -> {
				resultHandler.handle(r);
				connection.close();
			});
		}));
		return this;
	}


	/********** Vsubnet **********/
	// INSERT_VSUBNET = "INSERT INTO Vsubnet (name, label, description, info, status) "
	@Override
	public TopologyService addVsubnet(Vsubnet vsubnet, Handler<AsyncResult<Integer>> resultHandler) {
		// logger.debug("addSubnet: "+vsubnet.toString());
		JsonArray params = new JsonArray()
				.add(vsubnet.getName())
				.add(vsubnet.getLabel())
				.add(vsubnet.getDescription())
				.add(new JsonObject(vsubnet.getInfo()).encode())
				.add(vsubnet.getStatus());
		insertAndGetId(params, ApiSql.INSERT_VSUBNET, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVsubnet(String vsubnetId, Handler<AsyncResult<Vsubnet>> resultHandler) {
		this.retrieveOne(vsubnetId, ApiSql.FETCH_VSUBNET_BY_ID)
		.map(option -> option.map(json -> {
			Vsubnet vsubnet = new Vsubnet(json);
			vsubnet.setInfo(new JsonObject(json.getString("info")).getMap());
			return vsubnet;
		}).orElse(null))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVsubnets(Handler<AsyncResult<List<Vsubnet>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VSUBNETS)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vsubnet vsubnet = new Vsubnet(row);
					vsubnet.setInfo(new JsonObject(row.getString("info")).getMap());
					return vsubnet;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService deleteVsubnet(String vsubnetId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vsubnetId, ApiSql.DELETE_VSUBNET, resultHandler);
		return this;
	}
	// UPDATE_VSUBNET = "UPDATE Vsubnet SET label = ?, description = ?, info = ?, status = ? WHERE id = ?";
	@Override
	public TopologyService updateVsubnet(String id, Vsubnet vsubnet, Handler<AsyncResult<Vsubnet>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vsubnet.getLabel())
				.add(vsubnet.getDescription())
				.add(new JsonObject(vsubnet.getInfo()).encode())
				.add(vsubnet.getStatus())
				.add(id);
		this.execute(params, ApiSql.UPDATE_VSUBNET, vsubnet, resultHandler);
		return this;
	}


	/********** Vnode **********/
	// INSERT_VNODE = "INSERT INTO Vnode (name, label, description, info, status, posx, posy, location, type, vsubnetId) "
	@Override
	public TopologyService addVnode(Vnode vnode, Handler<AsyncResult<Integer>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vnode.getName())
				.add(vnode.getLabel())
				.add(vnode.getDescription())
				.add(new JsonObject(vnode.getInfo()).encode())
				.add(vnode.getStatus())
				.add(vnode.getPosx())
				.add(vnode.getPosy())
				.add(vnode.getLocation())
				.add(vnode.getType())	
				.add(vnode.getVsubnetId());
		insertAndGetId(params, ApiSql.INSERT_VNODE, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVnode(String vnodeId, Handler<AsyncResult<Vnode>> resultHandler) {
		JsonArray params = new JsonArray().add(vnodeId);
		this.retrieveMany(params, ApiSql.FETCH_VNODE_BY_ID)
		.map(rawList -> ModelObjectMapper.toVnodeFromJsonRows(rawList))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVnodes(Handler<AsyncResult<List<Vnode>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VNODES)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vnode vnode = new Vnode(row);
					vnode.setInfo(new JsonObject(row.getString("info")).getMap());
					return vnode;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getVnodesByVsubnet(String vsubnetId, Handler<AsyncResult<List<Vnode>>> resultHandler) {
		JsonArray params = new JsonArray().add(vsubnetId);
		this.retrieveMany(params, ApiSql.FETCH_VNODES_BY_VSUBNET)
		.map(rawList -> rawList.stream()
				.map(Vnode::new)
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService deleteVnode(String vnodeId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vnodeId, ApiSql.DELETE_VNODE, resultHandler);
		return this;
	}
	// UPDATE_VNODE = "UPDATE Vnode SET label = ?, description = ?, info = ?, status = ?, posx = ?, posy = ?, location = ?, type = ? WHERE id = ?";
	@Override
	public TopologyService updateVnode(String id, Vnode vnode, Handler<AsyncResult<Vnode>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vnode.getLabel())
				.add(vnode.getDescription())
				.add(new JsonObject(vnode.getInfo()).encode())
				.add(vnode.getStatus())
				.add(vnode.getPosx())
				.add(vnode.getPosy())
				.add(vnode.getLocation())
				.add(vnode.getType())
				.add(id);
		this.execute(params, ApiSql.UPDATE_VNODE, vnode, resultHandler);
		return this;
	}


	/********** Vltp **********/
	// INSERT_VLTP = "INSERT INTO Vltp (name, label, description, info, status, busy, vnodeId) "
	@Override 
	public TopologyService addVltp(Vltp vltp, Handler<AsyncResult<Integer>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vltp.getName())
				.add(vltp.getLabel())
				.add(vltp.getDescription())
				.add(new JsonObject(vltp.getInfo()).encode())
				.add(vltp.getStatus())
				.add(vltp.isBusy())
				.add(vltp.getVnodeId());
		insertAndGetId(params, ApiSql.INSERT_VLTP, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVltp(String vltpId, Handler<AsyncResult<Vltp>> resultHandler) {
		JsonArray params = new JsonArray().add(vltpId);
		this.retrieveMany(params, ApiSql.FETCH_VLTP_BY_ID)
		.map(rawList -> ModelObjectMapper.toVltpFromJsonRows(rawList))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVltps(Handler<AsyncResult<List<Vltp>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VLTPS)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vltp vltp = new Vltp(row);
					vltp.setInfo(new JsonObject(row.getString("info")).getMap());
					return vltp;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getVltpsByVnode(String vnodeId, Handler<AsyncResult<List<Vltp>>> resultHandler) {
		JsonArray params = new JsonArray().add(vnodeId);
		this.retrieveMany(params, ApiSql.FETCH_VLTPS_BY_VNODE)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vltp vltp = new Vltp(row);
					vltp.setInfo(new JsonObject(row.getString("info")).getMap());
					return vltp;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService deleteVltp(String vltpId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vltpId, ApiSql.DELETE_VLTP, resultHandler);
		return this;
	}
	// UPDATE_VLTP = "UPDATE Vltp SET label = ?, description = ?, info = ?, status = ?, busy = ? WHERE id = ?";
	@Override
	public TopologyService updateVltp(String id, Vltp vltp, Handler<AsyncResult<Vltp>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vltp.getLabel())
				.add(vltp.getDescription())
				.add(new JsonObject(vltp.getInfo()).encode())
				.add(vltp.getStatus())
				.add(vltp.isBusy())				
				.add(id);
		this.execute(params, ApiSql.UPDATE_VLTP, vltp, resultHandler);
		return this;
	}


	/********** Vctp **********/
	// INSERT_VCTP = "INSERT INTO Vctp (name, label, description, info, status, vltpId) "
	@Override
	public TopologyService addVctp(Vctp vctp, Handler<AsyncResult<Integer>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vctp.getName())
				.add(vctp.getLabel())
				.add(vctp.getDescription())
				.add(new JsonObject(vctp.getInfo()).encode())
				.add(vctp.getStatus())
				.add(vctp.isBusy())
				.add(vctp.getVltpId());
		insertAndGetId(params, ApiSql.INSERT_VCTP, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVctp(String vctpId, Handler<AsyncResult<Vctp>> resultHandler) {
		this.retrieveOne(vctpId, ApiSql.FETCH_VCTP_BY_ID)
		.map(option -> option.map(json -> {
			Vctp vctp = new Vctp(json);
			vctp.setInfo(new JsonObject(json.getString("info")).getMap());
			return vctp;
		}).orElse(null))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVctps(Handler<AsyncResult<List<Vctp>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VCTPS)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vctp vctp = new Vctp(row);
					vctp.setInfo(new JsonObject(row.getString("info")).getMap());
					return vctp;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getVctpsByVltp(String vltpId, Handler<AsyncResult<List<Vctp>>> resultHandler) {
		JsonArray params = new JsonArray().add(vltpId);
		this.retrieveMany(params, ApiSql.FETCH_VCTPS_BY_VLTP)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vctp vctp = new Vctp(row);
					vctp.setInfo(new JsonObject(row.getString("info")).getMap());
					return vctp;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getVctpsByVnode(String vnodeId, Handler<AsyncResult<List<Vctp>>> resultHandler) {
		JsonArray params = new JsonArray().add(vnodeId);
		this.retrieveMany(params, ApiSql.FETCH_VCTPS_BY_VNODE)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vctp vctp = new Vctp(row);
					vctp.setInfo(new JsonObject(row.getString("info")).getMap());
					return vctp;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getVctpsByVlink(String vlinkId, Handler<AsyncResult<List<Vctp>>> resultHandler) {
		JsonArray params = new JsonArray().add(vlinkId);
		this.retrieveMany(params, ApiSql.FETCH_VCTPS_BY_VLINK)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vctp vctp = new Vctp(row);
					vctp.setInfo(new JsonObject(row.getString("info")).getMap());
					return vctp;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService deleteVctp(String vctpId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vctpId, ApiSql.DELETE_VCTP, resultHandler);
		return this;
	}
	// UPDATE_VCTP = "UPDATE Vctp SET label = ?, description = ?, info = ?, status = ? WHERE id = ?";
	@Override
	public TopologyService updateVctp(String id, Vctp vctp, Handler<AsyncResult<Vctp>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vctp.getLabel())
				.add(vctp.getDescription())
				.add(new JsonObject(vctp.getInfo()).encode())
				.add(vctp.getStatus())
				.add(vctp.isBusy())
				.add(id);
		this.execute(params, ApiSql.UPDATE_VCTP, vctp, resultHandler);
		return this;
	}


	/********** Vlink **********/
	// INSERT_VLINK = "INSERT INTO Vlink (name, label, description, info, status, type, srcVltpId, destVltpId) "
	@Override 
	public TopologyService addVlink(Vlink vlink, Handler<AsyncResult<Integer>> resultHandler) {
		JsonArray params = new JsonArray()				
				.add(vlink.getName())
				.add(vlink.getLabel())
				.add(vlink.getDescription())
				.add(new JsonObject(vlink.getInfo()).encode())
				.add(vlink.getStatus())
				.add(vlink.getType())
				.add(vlink.getSrcVltpId())
				.add(vlink.getDestVltpId());
		insertAndGetId(params, ApiSql.INSERT_VLINK, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVlink(String vlinkId, Handler<AsyncResult<Vlink>> resultHandler) {
		JsonArray params = new JsonArray().add(vlinkId);
		this.retrieveMany(params, ApiSql.FETCH_VLINK_BY_ID)
		.map(rawList -> ModelObjectMapper.toVlinkFromJsonRows(rawList))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVlinks(Handler<AsyncResult<List<Vlink>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VLINKS)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vlink vlink = new Vlink(row);
					vlink.setInfo(new JsonObject(row.getString("info")).getMap());
					return vlink;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getVlinksByVsubnet(String vsubnetId, Handler<AsyncResult<List<Vlink>>> resultHandler) {
		JsonArray params = new JsonArray().add(vsubnetId);
		this.retrieveMany(params, ApiSql.FETCH_VLINKS_BY_VSUBNET)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vlink vlink = new Vlink(row);
					vlink.setInfo(new JsonObject(row.getString("info")).getMap());
					return vlink;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService deleteVlink(String vlinkId, Handler<AsyncResult<Void>> resultHandler) {		
		this.removeOne(vlinkId, ApiSql.DELETE_VLINK, resultHandler);
		return this;
	}
	// UPDATE_VLINK = "UPDATE Vlink SET label = ?, description = ?, info = ?, status = ?, type = ? WHERE id = ?";
	@Override
	public TopologyService updateVlink(String id, Vlink vlink, Handler<AsyncResult<Vlink>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vlink.getLabel())
				.add(vlink.getDescription())
				.add(new JsonObject(vlink.getInfo()).encode())
				.add(vlink.getStatus())
				.add(vlink.getType())
				.add(id);
		this.execute(params, ApiSql.UPDATE_VLINK, vlink, resultHandler);
		return this;
	}


	/********** VlinkConn **********/
	// INSERT_VLINKCONN = "INSERT INTO VlinkConn (name, label, description, info, status, srcVctpId, destVctpId) "
	@Override
	public TopologyService addVlinkConn(VlinkConn vlinkConn, Handler<AsyncResult<Integer>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vlinkConn.getName())
				.add(vlinkConn.getLabel())
				.add(vlinkConn.getDescription())
				.add(new JsonObject(vlinkConn.getInfo()).encode())
				.add(vlinkConn.getStatus())
				.add(vlinkConn.getSrcVctpId())
				.add(vlinkConn.getDestVctpId())
				.add(vlinkConn.getVlinkId());
		insertAndGetId(params, ApiSql.INSERT_VLINKCONN, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVlinkConn(String vlinkConnId, Handler<AsyncResult<VlinkConn>> resultHandler) {
		this.retrieveOne(vlinkConnId, ApiSql.FETCH_VLINKCONN_BY_ID)
		.map(option -> option.map(json -> {
			VlinkConn vlinkConn = new VlinkConn(json);
			vlinkConn.setInfo(new JsonObject(json.getString("info")).getMap());
			return vlinkConn;
		}).orElse(null))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVlinkConns(Handler<AsyncResult<List<VlinkConn>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VLINKCONNS)
		.map(rawList -> rawList.stream()
				.map(row -> {
					VlinkConn vlinkConn = new VlinkConn(row);
					vlinkConn.setInfo(new JsonObject(row.getString("info")).getMap());
					return vlinkConn;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getVlinkConnsByVlink(String vlinkId, Handler<AsyncResult<List<VlinkConn>>> resultHandler) {
		JsonArray params = new JsonArray().add(vlinkId);
		this.retrieveMany(params, ApiSql.FETCH_VLINKCONNS_BY_VLINK)
		.map(rawList -> rawList.stream()
				.map(row -> {
					VlinkConn vlinkConn = new VlinkConn(row);
					vlinkConn.setInfo(new JsonObject(row.getString("info")).getMap());
					return vlinkConn;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getVlinkConnsByVsubnet(String vsubnetId, Handler<AsyncResult<List<VlinkConn>>> resultHandler) {
		JsonArray params = new JsonArray().add(vsubnetId);
		this.retrieveMany(params, ApiSql.FETCH_VLINKCONNS_BY_VSUBNET)
		.map(rawList -> rawList.stream()
				.map(row -> {
					VlinkConn vlinkConn = new VlinkConn(row);
					vlinkConn.setInfo(new JsonObject(row.getString("info")).getMap());
					return vlinkConn;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService deleteVlinkConn(String vlinkConnId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vlinkConnId, ApiSql.DELETE_VLINKCONN, resultHandler);
		return this;
	}
	// UPDATE_VLINKCONN = "UPDATE VlinkConn SET label = ?, description = ?, info = ?, status = ? WHERE id = ?";
	@Override
	public TopologyService updateVlinkConn(String id, VlinkConn vlinkConn, Handler<AsyncResult<VlinkConn>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vlinkConn.getLabel())
				.add(vlinkConn.getDescription())
				.add(new JsonObject(vlinkConn.getInfo()).encode())
				.add(vlinkConn.getStatus())
				.add(id);
		this.execute(params, ApiSql.UPDATE_VLINKCONN, vlinkConn, resultHandler);
		return this;
	}


	/********** Vtrail **********/
	// INSERT_VTRAIL = "INSERT INTO Vtrail (name, label, description, info, status, srcVctpId, destVctpId) "
	@Override
	public TopologyService addVtrail(Vtrail vtrail, Handler<AsyncResult<Integer>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vtrail.getName())
				.add(vtrail.getLabel())
				.add(vtrail.getDescription())
				.add(new JsonObject(vtrail.getInfo()).encode())
				.add(vtrail.getStatus())
				.add(vtrail.getSrcVctpId())
				.add(vtrail.getDestVctpId());
		insertAndGetId(params, ApiSql.INSERT_VTRAIL, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVtrail(String vtrailId, Handler<AsyncResult<Vtrail>> resultHandler) {
		JsonArray params = new JsonArray().add(vtrailId);
		this.retrieveMany(params, ApiSql.FETCH_VTRAIL_BY_ID)
		.map(rawList -> ModelObjectMapper.toVtrailFromJsonRows(rawList))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVtrails(Handler<AsyncResult<List<Vtrail>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VTRAILS)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vtrail vtrail = new Vtrail(row);
					vtrail.setInfo(new JsonObject(row.getString("info")).getMap());
					return vtrail;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getVtrailsByVsubnet(String vsubnetId, Handler<AsyncResult<List<Vtrail>>> resultHandler) {
		JsonArray params = new JsonArray().add(vsubnetId);
		this.retrieveMany(params, ApiSql.FETCH_VTRAILS_BY_VSUBNET)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vtrail vtrail = new Vtrail(row);
					vtrail.setInfo(new JsonObject(row.getString("info")).getMap());
					return vtrail;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService deleteVtrail(String vtrailId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vtrailId, ApiSql.DELETE_VTRAIL, resultHandler);
		return this;
	}
	// UPDATE_VTRAIL = "UPDATE Vtrail SET label = ?, description = ?, info = ?, status = ? WHERE id = ?";
	@Override
	public TopologyService updateVtrail(String id, Vtrail vtrail, Handler<AsyncResult<Vtrail>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vtrail.getLabel())
				.add(vtrail.getDescription())
				.add(new JsonObject(vtrail.getInfo()).encode())
				.add(vtrail.getStatus())
				.add(id);
		this.execute(params, ApiSql.UPDATE_VTRAIL, vtrail, resultHandler);
		return this;
	}


	/********** Vxc **********/
	// INSERT_VXC = "INSERT INTO Vxc (name, label, description, info, status, type, vnodeId, vtrailId, srcVctpId, destVctpId, dropVctpId) "
	@Override
	public TopologyService addVxc(Vxc vxc, Handler<AsyncResult<Integer>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vxc.getName())
				.add(vxc.getLabel())
				.add(vxc.getDescription())
				.add(new JsonObject(vxc.getInfo()).encode())
				.add(vxc.getStatus())
				.add(vxc.getType())
				.add(vxc.getVnodeId())
				.add(vxc.getVtrailId())
				.add(vxc.getSrcVctpId())
				.add(vxc.getDestVctpId());				
		if(vxc.getDropVctpId() == 0) {
			insertAndGetId(params, ApiSql.INSERT_VXC_1, resultHandler);
		} else {
			params.add(vxc.getDropVctpId());
			insertAndGetId(params, ApiSql.INSERT_VXC, resultHandler);
		}
		return this;
	}
	@Override
	public TopologyService getVxc(String vxcId, Handler<AsyncResult<Vxc>> resultHandler) {
		this.retrieveOne(vxcId, ApiSql.FETCH_VXC_BY_ID)
		.map(option -> option.map(json -> {
			Vxc vxc = new Vxc(json);
			vxc.setInfo(new JsonObject(json.getString("info")).getMap());
			return vxc;
		}).orElse(null))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVxcs(Handler<AsyncResult<List<Vxc>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VXCS)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vxc vxc = new Vxc(row);
					vxc.setInfo(new JsonObject(row.getString("info")).getMap());
					return vxc;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getVxcsByVtrail(String vtrailId, Handler<AsyncResult<List<Vxc>>> resultHandler) {
		JsonArray params = new JsonArray().add(vtrailId);
		this.retrieveMany(params, ApiSql.FETCH_VXC_BY_VTRAIL)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vxc vxc = new Vxc(row);
					vxc.setInfo(new JsonObject(row.getString("info")).getMap());
					return vxc;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getVxcsByVnode(String vnodeId, Handler<AsyncResult<List<Vxc>>> resultHandler) {
		JsonArray params = new JsonArray().add(vnodeId);
		this.retrieveMany(params, ApiSql.FETCH_VXC_BY_VNODE)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vxc vxc = new Vxc(row);
					vxc.setInfo(new JsonObject(row.getString("info")).getMap());
					return vxc;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService deleteVxc(String vxcId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vxcId, ApiSql.DELETE_VXC, resultHandler);
		return this;
	}
	// UPDATE_VXC = "UPDATE Vxc SET label = ?, description = ?, info = ?, status = ?, type = ? WHERE id = ?";
	@Override
	public TopologyService updateVxc(String id, Vxc vxc, Handler<AsyncResult<Vxc>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vxc.getLabel())
				.add(vxc.getDescription())
				.add(new JsonObject(vxc.getInfo()).encode())
				.add(vxc.getStatus())
				.add(vxc.getType())
				.add(id);
		this.execute(params, ApiSql.UPDATE_VXC, vxc, resultHandler);
		return this;
	}

	
	/********** PrefixAnn **********/
	// INSERT_PREFIX_ANN = "INSERT INTO PrefixAnn (name, originId, expiration) 
	@Override
	public TopologyService addPrefixAnn(PrefixAnn prefixAnn, Handler<AsyncResult<Integer>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(prefixAnn.getName())
				.add(prefixAnn.getOriginId())
				.add(prefixAnn.getExpiration());
		insertAndGetId(params, ApiSql.INSERT_PREFIX_ANN, resultHandler);
		return this;
	}
	@Override
	public TopologyService getPrefixAnn(String prefixAnnId, Handler<AsyncResult<PrefixAnn>> resultHandler) {
		this.retrieveOne(prefixAnnId, ApiSql.FETCH_PREFIX_ANN_BY_ID)
		.map(option -> option.map(json -> {
			PrefixAnn pa = new PrefixAnn(json);
			return pa;
		}).orElse(null))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllPrefixAnns(Handler<AsyncResult<List<PrefixAnn>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_PREFIX_ANNS)
		.map(rawList -> rawList.stream()
				.map(PrefixAnn::new)
				.collect(Collectors.toList()))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService deletePrefixAnn(String prefixAnnId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(prefixAnnId, ApiSql.DELETE_PREFIX_ANN, resultHandler);
		return this;
	}

	
	/********** PrefixAnn **********/
	// INSERT_ROUTING_ENTRY = "INSERT INTO RoutingEntry (paId, nodeId, nextHopId, faceId, cost, origin)
	@Override
	public TopologyService addRoute(Route route, Handler<AsyncResult<Integer>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(route.getPaId())
				.add(route.getNodeId())
				.add(route.getNextHopId())
				.add(route.getFaceId())
				.add(route.getCost())				
				.add(route.getOrigin());				
		insertAndGetId(params, ApiSql.INSERT_ROUTE, resultHandler);
		return this;
	}
	@Override
	public TopologyService getRoute(String routeId, Handler<AsyncResult<Route>> resultHandler) {
		this.retrieveOne(routeId, ApiSql.FETCH_ROUTE_BY_ID)
		.map(option -> option.map(Route::new).orElse(null))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllRoutes(Handler<AsyncResult<List<Route>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_ROUTES)
		.map(rawList -> rawList.stream()
				.map(Route::new)
				.collect(Collectors.toList()))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getRoutesByNode(String nodeId, Handler<AsyncResult<List<Route>>> resultHandler) {
		JsonArray params = new JsonArray().add(nodeId);
		this.retrieveMany(params, ApiSql.FETCH_ROUTES_BY_NODE)
		.map(rawList -> rawList.stream()
				.map(Route::new)
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService deleteRoute(String routeId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(routeId, ApiSql.DELETE_ROUTE, resultHandler);
		return this;
	}
	@Override
	public TopologyService generateRoutesToPrefix(String name, Handler<AsyncResult<List<Route>>> resultHandler) { 
		Future<List<Node>> nodes = this.retrieveAll(InternalSql.FETCH_ROUTEGEN_NODES)
				.map(rawList -> rawList.stream()
						.map(Node::new)
						.collect(Collectors.toList()));
		Future<List<Edge>> edges =this.retrieveAll(InternalSql.FETCH_ROUTEGEN_LCS)
				.map(rawList -> rawList.stream()
						.map(Edge::new)
						.collect(Collectors.toList()));
		JsonArray params = new JsonArray().add(name);
		Future<List<PrefixAnn>> pas = this.retrieveMany(params, InternalSql.FETCH_ROUTEGEN_PAS_BY_NAME)
				.map(rawList -> rawList.stream()
						.map(PrefixAnn::new)
						.collect(Collectors.toList()));
		CompositeFuture.all(Arrays.asList(nodes, edges, pas)).onComplete(ar -> {
			  if (ar.succeeded()) {
				  routing.computeRoutes(nodes.result(), edges.result(), pas.result(), resultHandler);
			  } else {
				  resultHandler.handle(Future.failedFuture(ar.cause()));
			  }
			 });
		return this;
	}

	
	/********** Face **********/
	// INSERT INTO Face (label, local, remote, scheme, vctpId, vlinkConnId)
	@Override
	public TopologyService addFace(Face face, Handler<AsyncResult<Integer>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(face.getLabel())
				.add(face.getLocal())
				.add(face.getRemote())
				.add(face.getScheme())
				.add(face.getVctpId())
				.add(face.getVlinkConnId());
		insertAndGetId(params, ApiSql.INSERT_FACE, resultHandler);
		return this;
	}

	@Override
	public TopologyService getFace(String faceId, Handler<AsyncResult<Face>> resultHandler) {
		this.retrieveOne(faceId, ApiSql.FETCH_FACE_BY_ID)
			.map(option -> option.map(Face::new).orElse(null))
			.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getAllFaces(Handler<AsyncResult<List<Face>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_FACES)
			.map(rawList -> rawList.stream().map(Face::new)
				.collect(Collectors.toList()))
			.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getFacesByNode(String nodeId, Handler<AsyncResult<List<Face>>> resultHandler) {
		JsonArray params = new JsonArray().add(nodeId);
		this.retrieveMany(params, ApiSql.FETCH_FACES_BY_NODE)
			.map(rawList -> rawList.stream().map(Face::new)
				.collect(Collectors.toList()))
			.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService deleteFace(String faceId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(faceId, ApiSql.DELETE_FACE, resultHandler);
		return this;
	}
	
	@Override
	public TopologyService generateFacesForLc(String linkConnId, Handler<AsyncResult<Void>> resultHandler) {
		this.retrieveOne(linkConnId, InternalSql.FETCH_FACEGEN_INFO)
			.onComplete(res -> {
				if (res.succeeded()) {
					if (res.result().isPresent()) {						
						JsonObject faceGenInfo = res.result().get();						
						JsonObject sLtpInfo = new JsonObject(faceGenInfo.getString("sLtpInfo"));
						JsonObject dLtpInfo = new JsonObject(faceGenInfo.getString("dLtpInfo"));
						
						Face face = new Face();
						face.setLabel("autogen face");
						face.setScheme("ether");
						face.setVlinkConnId(faceGenInfo.getInteger("vlinkConnId"));						
						face.setVctpId(faceGenInfo.getInteger("sVctpId"));						
						face.setLocal(sLtpInfo.getString("port", ""));
						face.setRemote(dLtpInfo.getString("port", ""));											
						
						addFace(face, r1 -> {
							if (r1.succeeded()) {
								face.setVctpId(faceGenInfo.getInteger("dVctpId"));
								face.setLocal(dLtpInfo.getString("port", ""));
								face.setRemote(sLtpInfo.getString("port", ""));
								
								addFace(face, r2 -> {
									if (r2.succeeded()) {
										resultHandler.handle(Future.succeededFuture());
									} else {
										resultHandler.handle(Future.failedFuture(res.cause()));
									}
								});
							} else {
								resultHandler.handle(Future.failedFuture(res.cause()));
							}
						});
					} else {
						resultHandler.handle(Future.failedFuture("LinkConnId not valid"));
					}
				} else {
					resultHandler.handle(Future.failedFuture(res.cause()));
				}
					
			});
		return this;
	}

	
	
	
	/* ---------------------------------- */
	protected Future<Void> setVltpBusy(Integer vltpId, Boolean busy) {
		Promise<Void> promise = Promise.promise();
		JsonArray params = new JsonArray().add(busy).add(vltpId);
		executeNoResult(params, InternalSql.UPDATE_LTP_BUSY, ar -> {
			if (ar.succeeded()) {
				promise.complete();
			} else {
				promise.fail(ar.cause());
			}
		});
		return promise.future();
	}

	@Override
	public TopologyService updatePrefixAnn(String id, PrefixAnn prefixAnn,
			Handler<AsyncResult<PrefixAnn>> resultHandler) {
		// TODO Auto-generated method stub
		return null;
	}
}


