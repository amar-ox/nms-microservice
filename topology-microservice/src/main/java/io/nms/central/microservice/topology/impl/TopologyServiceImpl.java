package io.nms.central.microservice.topology.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.nms.central.microservice.common.service.JdbcRepositoryWrapper;
import io.nms.central.microservice.topology.TopologyService;
import io.nms.central.microservice.topology.model.ModelObjectMapper;
import io.nms.central.microservice.topology.model.Vctp;
import io.nms.central.microservice.topology.model.Vlink;
import io.nms.central.microservice.topology.model.VlinkConn;
import io.nms.central.microservice.topology.model.Vltp;
import io.nms.central.microservice.topology.model.Vnode;
import io.nms.central.microservice.topology.model.Vsubnet;
import io.nms.central.microservice.topology.model.Vtrail;
import io.nms.central.microservice.topology.model.Vxc;
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
	// INSERT_VSUBNET = "INSERT INTO Vsubnet (name, label, description, info, status) "
	@Override
	public TopologyService addVsubnet(Vsubnet vsubnet, Handler<AsyncResult<Void>> resultHandler) {
		logger.debug("addSubnet: "+vsubnet.toString());
		JsonArray params = new JsonArray()
				.add(vsubnet.getName())
				.add(vsubnet.getLabel())
				.add(vsubnet.getDescription())
				.add(vsubnet.getStatus())
				.add(new JsonObject(vsubnet.getInfo()).encode());
		executeNoResult(params, Sql.INSERT_VSUBNET, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVsubnet(String vsubnetId, Handler<AsyncResult<Vsubnet>> resultHandler) {
		this.retrieveOne(vsubnetId, Sql.FETCH_VSUBNET_BY_ID)
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
		this.retrieveAll(Sql.FETCH_ALL_VSUBNETS)
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
		this.removeOne(vsubnetId, Sql.DELETE_VSUBNET, resultHandler);
		return this;
	}


	/********** Vnode **********/
	// INSERT_VNODE = "INSERT INTO Vnode (name, label, description, info, status, posx, posy, location, type, vsubnetId) "
	@Override
	public TopologyService addVnode(Vnode vnode, Handler<AsyncResult<Void>> resultHandler) {
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
		executeNoResult(params, Sql.INSERT_VNODE, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVnode(String vnodeId, Handler<AsyncResult<Vnode>> resultHandler) {
		JsonArray params = new JsonArray().add(vnodeId);
		this.retrieveMany(params, Sql.FETCH_VNODE_BY_ID)
		.map(rawList -> ModelObjectMapper.toVnodeFromJsonRows(rawList))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVnodes(Handler<AsyncResult<List<Vnode>>> resultHandler) {
		this.retrieveAll(Sql.FETCH_ALL_VNODES)
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
		this.retrieveMany(params, Sql.FETCH_VNODES_BY_VSUBNET)
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
	// INSERT_VLTP = "INSERT INTO Vltp (name, label, description, info, status, busy, vnodeId) "
	@Override 
	public TopologyService addVltp(Vltp vltp, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vltp.getName())
				.add(vltp.getLabel())
				.add(vltp.getDescription())
				.add(new JsonObject(vltp.getInfo()).encode())
				.add(vltp.getStatus())
				.add(vltp.isBusy())
				.add(vltp.getVnodeId());
		executeNoResult(params, Sql.INSERT_VLTP, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVltp(String vltpId, Handler<AsyncResult<Vltp>> resultHandler) {
		JsonArray params = new JsonArray().add(vltpId);
		this.retrieveMany(params, Sql.FETCH_VLTP_BY_ID)
		.map(rawList -> ModelObjectMapper.toVltpFromJsonRows(rawList))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVltps(Handler<AsyncResult<List<Vltp>>> resultHandler) {
		this.retrieveAll(Sql.FETCH_ALL_VLTPS)
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
		this.retrieveMany(params, Sql.FETCH_VLTPS_BY_VNODE)
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
		this.removeOne(vltpId, Sql.DELETE_VLTP, resultHandler);
		return this;
	}


	/********** Vctp **********/
	// INSERT_VCTP = "INSERT INTO Vctp (name, label, description, info, status, vltpId) "
	@Override
	public TopologyService addVctp(Vctp vctp, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vctp.getName())
				.add(vctp.getLabel())
				.add(vctp.getDescription())
				.add(new JsonObject(vctp.getInfo()).encode())
				.add(vctp.getStatus())				
				.add(vctp.getVltpId());
		executeNoResult(params, Sql.INSERT_VCTP, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVctp(String vctpId, Handler<AsyncResult<Vctp>> resultHandler) {
		this.retrieveOne(vctpId, Sql.FETCH_VCTP_BY_ID)
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
		this.retrieveAll(Sql.FETCH_ALL_VCTPS)
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
		this.retrieveMany(params, Sql.FETCH_VCTPS_BY_VLTP)
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
		this.removeOne(vctpId, Sql.DELETE_VCTP, resultHandler);
		return this;
	}


	/********** Vlink **********/
	// INSERT_VLINK = "INSERT INTO Vlink (name, label, description, info, status, type, srcVltpId, destVltpId) "
	@Override 
	public TopologyService addVlink(Vlink vlink, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vlink.getLabel())
				.add(vlink.getName())
				.add(vlink.getDescription())
				.add(new JsonObject(vlink.getInfo()).encode())
				.add(vlink.getStatus())
				.add(vlink.getSrcVltpId())
				.add(vlink.getDestVltpId());
		executeNoResult(params, Sql.INSERT_VLINK, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVlink(String vlinkId, Handler<AsyncResult<Vlink>> resultHandler) {
		JsonArray params = new JsonArray().add(vlinkId);
		this.retrieveMany(params, Sql.FETCH_VLINK_BY_ID)
		.map(rawList -> ModelObjectMapper.toVlinkFromJsonRows(rawList))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVlinks(Handler<AsyncResult<List<Vlink>>> resultHandler) {
		this.retrieveAll(Sql.FETCH_ALL_VLINKS)
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
		this.retrieveMany(params, Sql.FETCH_VLINKS_BY_VSUBNET)
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
		this.removeOne(vlinkId, Sql.DELETE_VLINK, resultHandler);
		return this;
	}


	/********** VlinkConn **********/
	// INSERT_VLINKCONN = "INSERT INTO VlinkConn (name, label, description, info, status, srcVctpId, destVctpId) "
	@Override
	public TopologyService addVlinkConn(VlinkConn vlinkConn, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vlinkConn.getName())
				.add(vlinkConn.getLabel())
				.add(vlinkConn.getDescription())
				.add(new JsonObject(vlinkConn.getInfo()).encode())
				.add(vlinkConn.getStatus())
				.add(vlinkConn.getSrcVctpId())
				.add(vlinkConn.getDestVctpId());
		executeNoResult(params, Sql.INSERT_VLINKCONN, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVlinkConn(String vlinkConnId, Handler<AsyncResult<VlinkConn>> resultHandler) {
		this.retrieveOne(vlinkConnId, Sql.FETCH_VLINKCONN_BY_ID)
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
		this.retrieveAll(Sql.FETCH_ALL_VLINKCONNS)
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
		this.retrieveMany(params, Sql.FETCH_VLINKCONNS_BY_VLINK)
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
		this.removeOne(vlinkConnId, Sql.DELETE_VLINKCONN, resultHandler);
		return this;
	}


	/********** Vtrail **********/
	// INSERT_VTRAIL = "INSERT INTO Vtrail (name, label, description, info, status, srcVctpId, destVctpId) "
	@Override
	public TopologyService addVtrail(Vtrail vtrail, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vtrail.getName())
				.add(vtrail.getLabel())
				.add(vtrail.getDescription())
				.add(new JsonObject(vtrail.getInfo()).encode())
				.add(vtrail.getStatus())
				.add(vtrail.getSrcVctpId())
				.add(vtrail.getDestVctpId());
		executeNoResult(params, Sql.INSERT_VTRAIL, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVtrail(String vtrailId, Handler<AsyncResult<Vtrail>> resultHandler) {
		JsonArray params = new JsonArray().add(vtrailId);
		this.retrieveMany(params, Sql.FETCH_VTRAIL_BY_ID)
				.map(rawList -> ModelObjectMapper.toVtrailFromJsonRows(rawList))
				.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVtrails(Handler<AsyncResult<List<Vtrail>>> resultHandler) {
		this.retrieveAll(Sql.FETCH_ALL_VTRAILS)
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
		this.removeOne(vtrailId, Sql.DELETE_VTRAIL, resultHandler);
		return this;
	}


	/********** Vxc **********/
	// INSERT_VXC = "INSERT INTO Vxc (name, label, description, info, status, type, vnodeId, vtrailId, srcVctpId, destVctpId, dropVctpId) "
	@Override
	public TopologyService addVxc(Vxc vxc, Handler<AsyncResult<Void>> resultHandler) {
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
				.add(vxc.getDestVctpId())
				.add(vxc.getDropVctpId());
		executeNoResult(params, Sql.INSERT_VXC, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVxc(String vxcId, Handler<AsyncResult<Vxc>> resultHandler) {
		this.retrieveOne(vxcId, Sql.FETCH_VXC_BY_ID)
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
		this.retrieveAll(Sql.FETCH_ALL_VXCS)
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
		this.retrieveMany(params, Sql.FETCH_VXC_BY_VTRAIL)
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
		this.removeOne(vxcId, Sql.DELETE_VXC, resultHandler);
		return this;
	}
}
