package io.nms.central.microservice.topology.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import io.nms.central.microservice.common.functional.Functional;
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
import io.vertx.core.impl.CompositeFutureImpl;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 *
 */
public class TopologyServiceImpl extends JdbcRepositoryWrapper implements TopologyService {

	private static final Logger logger = LoggerFactory.getLogger(TopologyServiceImpl.class);
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
	@Override
	public TopologyService updateVsubnet(String id, Vsubnet vsubnet, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vsubnet.getLabel())
				.add(vsubnet.getDescription())
				.add(new JsonObject(vsubnet.getInfo()).encode())
				.add(vsubnet.getStatus())
				.add(id);
		executeNoResult(params, ApiSql.UPDATE_VSUBNET, resultHandler);
		return this;
	}


	/********** Vnode **********/
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
		this.retrieveOneNested(vnodeId, ApiSql.FETCH_VNODE_BY_ID)
		.map(option -> option.map(json -> {
			return ModelObjectMapper.toVnodeFromJsonRows(json);
		}).orElse(null))
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
		Promise<Void> vltpsDeleted = Promise.promise();
		JsonArray pVnodeId = new JsonArray().add(vnodeId);
		
		beginTxnAndLock(Entity.NODE, InternalSql.LOCK_TABLES_FOR_NODE).onComplete(ar -> {
			if (ar.succeeded()) {
				globalRetrieveMany(pVnodeId, InternalSql.FETCH_LTPS_BY_NODE).onComplete(res -> {
					if (res.succeeded()) {
						List<JsonObject> ltps = res.result();
						List<Future> futures = new ArrayList<>();
						for (JsonObject ltp : ltps) {
							Promise<Void> p = Promise.promise();
							futures.add(p.future());				
							deleteVltp(String.valueOf(ltp.getInteger("id")), p);
						}
						CompositeFuture.all(futures).map((Void) null).onComplete(vltpsDeleted);
					} else {
						resultHandler.handle(Future.failedFuture(res.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
		
		vltpsDeleted.future().onComplete(ar -> {
			if (ar.succeeded()) {
				globalExecute(pVnodeId, ApiSql.DELETE_VNODE)
						.compose(r -> commitTxnAndUnlock(Entity.NODE))
						.onComplete(resultHandler);
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
		return this;
	}
	@Override
	public TopologyService updateVnode(String id, Vnode vnode, Handler<AsyncResult<Void>> resultHandler) {
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
		executeNoResult(params, ApiSql.UPDATE_VNODE, resultHandler);
		return this;
	}


	/********** Vltp **********/
	@Override 
	public TopologyService addVltp(Vltp vltp, Handler<AsyncResult<Integer>> resultHandler) {
		if (! isValidMACAddress((String)vltp.getInfo().get("port"))) {
			resultHandler.handle(Future.failedFuture("Port must be a MAC address"));
			return this;
		}
		getVnode(String.valueOf(vltp.getVnodeId()), ar -> {
			if (ar.succeeded()) {
				Vnode vnode = ar.result();
				if (vnode.getId() > 0) {
					JsonArray params = new JsonArray()
							.add(vltp.getName())
							.add(vltp.getLabel())
							.add(vltp.getDescription())
							.add(new JsonObject(vltp.getInfo()).encode())
							.add(vnode.getStatus())
							.add(vltp.isBusy())
							.add(vltp.getVnodeId());
					insertAndGetId(params, ApiSql.INSERT_VLTP, resultHandler);
				} else {
					resultHandler.handle(Future.failedFuture(new IllegalStateException("Node not found")));
				}
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
		return this;
	}
	@Override
	public TopologyService getVltp(String vltpId, Handler<AsyncResult<Vltp>> resultHandler) {
		this.retrieveOneNested(vltpId, ApiSql.FETCH_VLTP_BY_ID)
		.map(option -> option.map(json -> {
			return ModelObjectMapper.toVltpFromJsonRows(json);
		}).orElse(null))
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
		Promise<Void> linkDeleted = Promise.promise();
		JsonArray pVltpId = new JsonArray().add(vltpId);
		
		beginTxnAndLock(Entity.LTP, InternalSql.LOCK_TABLES_FOR_LTP).onComplete(ar -> {
			if (ar.succeeded()) {
				globalRetrieveOne(pVltpId, InternalSql.FETCH_LINK_BY_LTP)
					.map(option -> option.orElse(null))
					.onComplete(res -> {
						if (res.succeeded()) {
							if (res.result() != null) {
								JsonObject link = res.result();
								deleteVlink(String.valueOf(link.getInteger("id")), linkDeleted);
							} else {
								linkDeleted.complete();
							}
						} else {
							resultHandler.handle(Future.failedFuture(res.cause()));
						}
				});
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
		
		linkDeleted.future().onComplete(ar -> {
			if (ar.succeeded()) {
				globalExecute(pVltpId, ApiSql.DELETE_VLTP)
					.compose(r -> commitTxnAndUnlock(Entity.LTP))
					.onComplete(resultHandler);
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
		return this;
	}
	@Override
	public TopologyService updateVltp(String id, Vltp vltp, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vltp.getLabel())
				.add(vltp.getDescription())
				.add(new JsonObject(vltp.getInfo()).encode())
				.add(vltp.getStatus())
				.add(vltp.isBusy())				
				.add(id);
		executeNoResult(params, ApiSql.UPDATE_VLTP, resultHandler);
		return this;
	}


	/********** Vctp **********/	
	@Override
	public TopologyService addVctp(Vctp vctp, Handler<AsyncResult<Integer>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vctp.getName())
				.add(vctp.getLabel())
				.add(vctp.getDescription())
				.add(new JsonObject(vctp.getInfo()).encode())
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
	public TopologyService deleteVctp(String vctpId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vctpId, ApiSql.DELETE_VCTP, resultHandler);
		return this;
	}
	@Override
	public TopologyService updateVctp(String id, Vctp vctp, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vctp.getLabel())
				.add(vctp.getDescription())
				.add(new JsonObject(vctp.getInfo()).encode())
				.add(id);
		executeNoResult(params, ApiSql.UPDATE_VCTP, resultHandler);
		return this;
	}


	/********** Vlink **********/
	@Override 
	public TopologyService addVlink(Vlink vlink, Handler<AsyncResult<Integer>> resultHandler) {
		JsonArray pVlink = new JsonArray()
				.add(vlink.getName())
				.add(vlink.getLabel())
				.add(vlink.getDescription())
				.add(new JsonObject(vlink.getInfo()).encode())
				.add(vlink.getStatus())
				.add(vlink.getType())
				.add(vlink.getSrcVltpId())
				.add(vlink.getDestVltpId());
		JsonArray updSrcLtp = new JsonArray().add(true).add(vlink.getSrcVltpId());
		JsonArray updDestLtp = new JsonArray().add(true).add(vlink.getDestVltpId());

		beginTxnAndLock(Entity.LINK, InternalSql.LOCK_TABLES_FOR_LINK).onComplete(ar -> {
			if (ar.succeeded()) {
				Future<Integer> vlinkId = globalInsert(pVlink, ApiSql.INSERT_VLINK);
				vlinkId
						.compose(r -> globalExecute(updSrcLtp, InternalSql.UPDATE_LTP_BUSY))
						.compose(r -> globalExecute(updDestLtp, InternalSql.UPDATE_LTP_BUSY))
						.compose(r -> commitTxnAndUnlock(Entity.LINK))
						.map(vlinkId.result())
						.onComplete(resultHandler);
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
		return this;
	}
	@Override
	public TopologyService getVlink(String vlinkId, Handler<AsyncResult<Vlink>> resultHandler) {
		this.retrieveOneNested(vlinkId, ApiSql.FETCH_VLINK_BY_ID)
		.map(option -> option.map(json -> {
			return ModelObjectMapper.toVlinkFromJsonRows(json);
		}).orElse(null))
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
		Promise<Void> vlcsDeleted = Promise.promise();
		JsonArray pVlinkId = new JsonArray().add(vlinkId);
		
		beginTxnAndLock(Entity.LINK, InternalSql.LOCK_TABLES_FOR_LINK).onComplete(ar -> {
			if (ar.succeeded()) {
				globalRetrieveMany(pVlinkId, InternalSql.FETCH_LCS_BY_LINK).onComplete(res -> {
					if (res.succeeded()) {
						List<JsonObject> lcs = res.result();
						List<Future> futures = new ArrayList<>();
						for (JsonObject lc : lcs) {
							Promise<Void> p = Promise.promise();
							futures.add(p.future());
							deleteVlinkConn(String.valueOf(lc.getInteger("id")), p);
						}
						CompositeFuture.all(futures).map((Void) null).onComplete(vlcsDeleted);
					} else {
						resultHandler.handle(Future.failedFuture(res.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
		
		vlcsDeleted.future().onComplete(ar -> {
			if (ar.succeeded()) {
				globalRetrieveOne(pVlinkId, ApiSql.FETCH_VLINK_BY_ID)
					.map(option -> option.map(Vlink::new).orElse(null))
					.onComplete(res -> {
						if (res.succeeded() && res.result() != null) {
							JsonArray pSrcLtp = new JsonArray().add(false).add(res.result().getSrcVltpId());
							JsonArray pDestLtp = new JsonArray().add(false).add(res.result().getDestVltpId());
							
							globalExecute(pVlinkId, ApiSql.DELETE_VLINK)
								.compose(r -> globalExecute(pSrcLtp, InternalSql.UPDATE_LTP_BUSY))
								.compose(r -> globalExecute(pDestLtp, InternalSql.UPDATE_LTP_BUSY))
								.compose(r -> commitTxnAndUnlock(Entity.LINK))
								.onComplete(resultHandler);
						} else {
							// TODO: unlock
							resultHandler.handle(Future.failedFuture("Vlink not found"));
						}
					});
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});		
		return this;
	}
	@Override
	public TopologyService updateVlink(String id, Vlink vlink, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vlink.getLabel())
				.add(vlink.getDescription())
				.add(new JsonObject(vlink.getInfo()).encode())
				.add(vlink.getStatus())
				.add(vlink.getType())
				.add(id);
		executeNoResult(params, ApiSql.UPDATE_VLINK, resultHandler);
		return this;
	}


	/********** VlinkConn **********/
	@Override
	public TopologyService addVlinkConn(VlinkConn vlinkConn, Handler<AsyncResult<Integer>> resultHandler) {
		beginTxnAndLock(Entity.LC, InternalSql.LOCK_TABLES_FOR_LC_AUTO).onComplete(ar -> {
			if (ar.succeeded()) {
				generateCtps(vlinkConn).onComplete(res -> {
					if (res.succeeded()) {
						List<Vctp> vctps = res.result();
						vlinkConn.setSrcVctpId(vctps.get(0).getId());
						vlinkConn.setDestVctpId(vctps.get(1).getId());

						JsonArray vlc = new JsonArray()
								.add(vlinkConn.getName())
								.add(vlinkConn.getLabel())
								.add(vlinkConn.getDescription())
								.add(new JsonObject(vlinkConn.getInfo()).encode())
								.add(vlinkConn.getStatus())
								.add(vlinkConn.getSrcVctpId())
								.add(vlinkConn.getDestVctpId())
								.add(vlinkConn.getVlinkId());
						globalInsert(vlc, ApiSql.INSERT_VLINKCONN).onComplete(vlcId -> {
							if (vlcId.succeeded()) {
								generateFaces(vlcId.result())
										.compose(r -> commitTxnAndUnlock(Entity.LC))
										.onComplete(done -> {
											if (done.succeeded()) {
												resultHandler.handle(Future.succeededFuture(vlcId.result()));
											} else {
												rollbackAndUnlock();
												resultHandler.handle(Future.failedFuture(done.cause()));
											}
										});
							} else {
								resultHandler.handle(Future.failedFuture(vlcId.cause()));
							}
						});
					} else {
						rollbackAndUnlock();
						resultHandler.handle(Future.failedFuture(res.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
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
		JsonArray pVlcId = new JsonArray().add(vlinkConnId);
		
		beginTxnAndLock(Entity.LC, InternalSql.LOCK_TABLES_FOR_LC).onComplete(ar -> {
			if (ar.succeeded()) {
				globalRetrieveOne(pVlcId, ApiSql.FETCH_VLINKCONN_BY_ID)
					.map(option -> option.map(VlinkConn::new).orElse(null))
					.onComplete(res -> {
						if (res.succeeded() && res.result() != null) {
							JsonArray delSrcVctp = new JsonArray().add(res.result().getSrcVctpId());
							JsonArray delDestVctp = new JsonArray().add(res.result().getDestVctpId());
							
							globalExecute(pVlcId, ApiSql.DELETE_VLINKCONN)
								.compose(r -> globalExecute(delSrcVctp, ApiSql.DELETE_VCTP))
								.compose(r -> globalExecute(delDestVctp, ApiSql.DELETE_VCTP))
								.compose(r -> commitTxnAndUnlock(Entity.LC))
								.onComplete(resultHandler);
						} else {
							// TODO: unlock
							resultHandler.handle(Future.failedFuture("VlinkConn not found"));
						}
					});
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
		return this;
	}
	@Override
	public TopologyService updateVlinkConn(String id, VlinkConn vlinkConn, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vlinkConn.getLabel())
				.add(vlinkConn.getDescription())
				.add(new JsonObject(vlinkConn.getInfo()).encode())
				.add(vlinkConn.getStatus())
				.add(id);
		executeNoResult(params, ApiSql.UPDATE_VLINKCONN, resultHandler);
		return this;
	}


	/********** Vtrail **********/
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
		this.retrieveOneNested(vtrailId, ApiSql.FETCH_VTRAIL_BY_ID)
		.map(option -> option.map(json -> {
			return ModelObjectMapper.toVtrailFromJsonRows(json);
		}).orElse(null))
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
	@Override
	public TopologyService updateVtrail(String id, Vtrail vtrail, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vtrail.getLabel())
				.add(vtrail.getDescription())
				.add(new JsonObject(vtrail.getInfo()).encode())
				.add(vtrail.getStatus())
				.add(id);
		executeNoResult(params, ApiSql.UPDATE_VTRAIL, resultHandler);
		return this;
	}


	/********** Vxc **********/
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
	@Override
	public TopologyService updateVxc(String id, Vxc vxc, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vxc.getLabel())
				.add(vxc.getDescription())
				.add(new JsonObject(vxc.getInfo()).encode())
				.add(vxc.getStatus())
				.add(vxc.getType())
				.add(id);
		executeNoResult(params, ApiSql.UPDATE_VXC, resultHandler);
		return this;
	}


	/********** PrefixAnn **********/ 
	@Override
	public TopologyService addPrefixAnn(PrefixAnn pa, Handler<AsyncResult<Void>> resultHandler) {
		beginTxnAndLock(Entity.PA, InternalSql.LOCK_TABLES_FOR_ROUTE).onComplete(tx -> {
			if (tx.succeeded()) {
				JsonArray params = new JsonArray()
						.add(pa.getName())
						.add(pa.getOriginId())
						.add(pa.getAvailable());
				globalInsert(params, ApiSql.INSERT_PA).onComplete(paId -> {
					if (paId.succeeded()) {
						generateRoutesToPrefix(pa.getName(), ar -> {
							if (ar.succeeded()) {
								commitTxnAndUnlock(Entity.PA)
										// .map(paId.result())
										.onComplete(resultHandler);
							} else {
								rollbackAndUnlock();
								resultHandler.handle(Future.failedFuture(ar.cause()));
							}
						});
					} else {
						resultHandler.handle(Future.failedFuture(paId.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(tx.cause()));
			}
		});
		return this;
	}
	@Override
	public TopologyService getPrefixAnn(String paId, Handler<AsyncResult<PrefixAnn>> resultHandler) {
		this.retrieveOne(paId, ApiSql.FETCH_PA_BY_ID)
			.map(option -> option.map(PrefixAnn::new).orElse(null))
			.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllPrefixAnns(Handler<AsyncResult<List<PrefixAnn>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_PAS)
		.map(rawList -> rawList.stream()
				.map(PrefixAnn::new)
				.collect(Collectors.toList()))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getPrefixAnnsByVsubnet(String vsubnetId, Handler<AsyncResult<List<PrefixAnn>>> resultHandler) {
		JsonArray params = new JsonArray().add(vsubnetId);
		this.retrieveMany(params, ApiSql.FETCH_PAS_BY_VSUBNET)
		.map(rawList -> rawList.stream()
				.map(PrefixAnn::new)
				.collect(Collectors.toList()))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getPrefixAnnsByVnode(String nodeId, Handler<AsyncResult<List<PrefixAnn>>> resultHandler) {
		JsonArray params = new JsonArray().add(nodeId);
		this.retrieveMany(params, ApiSql.FETCH_PAS_BY_NODE)
		.map(rawList -> rawList.stream()
				.map(PrefixAnn::new)
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService deletePrefixAnn(String prefixAnnId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(prefixAnnId, ApiSql.DELETE_PA, resultHandler);
		return this;
	}
	@Override
	public TopologyService deletePrefixAnnByName(int originId, String name, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray().add(originId).add(name);
		this.executeNoResult(params, ApiSql.DELETE_PA_BY_NAME, resultHandler);
		return this;
	}


	/********** Route **********/
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
	public TopologyService getRoutesByVsubnet(String vsubnetId, Handler<AsyncResult<List<Route>>> resultHandler) {
		JsonArray params = new JsonArray().add(vsubnetId);
		this.retrieveMany(params, ApiSql.FETCH_ROUTES_BY_VSUBNET)
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


	/********** Face **********/
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
	public TopologyService getFacesByVsubnet(String vsubnetId, Handler<AsyncResult<List<Face>>> resultHandler) {
		JsonArray params = new JsonArray().add(vsubnetId);
		this.retrieveMany(params, ApiSql.FETCH_FACES_BY_VSUBNET)
		.map(rawList -> rawList.stream()
				.map(Face::new)
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
	public TopologyService generateRoutesToPrefix(String name, Handler<AsyncResult<List<Route>>> resultHandler) {
		beginTxnAndLock(Entity.ROUTE, InternalSql.LOCK_TABLES_FOR_ROUTE).onComplete(ar -> {
			if (ar.succeeded()) {
				Future<List<Node>> nodes = this.globalRetrieveAll(InternalSql.FETCH_ROUTEGEN_NODES)
						.map(rawList -> rawList.stream()
								.map(Node::new)
								.collect(Collectors.toList()));
				Future<List<Edge>> edges =this.globalRetrieveAll(InternalSql.FETCH_ROUTEGEN_LCS)
						.map(rawList -> rawList.stream()
								.map(Edge::new)
								.collect(Collectors.toList()));
				JsonArray params = new JsonArray().add(name);
				Future<List<PrefixAnn>> pas = this.globalRetrieveMany(params, InternalSql.FETCH_ROUTEGEN_PAS_BY_NAME)
						.map(rawList -> rawList.stream()
								.map(PrefixAnn::new)
								.collect(Collectors.toList()));

				Future<List<Route>> routes = CompositeFuture
						.all(Arrays.asList(nodes, edges, pas))
						.compose(r -> routing.computeRoutes(nodes.result(), edges.result(), pas.result()));
				routes
						.compose(r -> upsertRoutes(r, false))
						.compose(r -> commitTxnAndUnlock(Entity.ROUTE))
						.onComplete(done -> {
							if (done.succeeded()) {
								resultHandler.handle(Future.succeededFuture(routes.result()));
							} else {
								rollbackAndUnlock();
								resultHandler.handle(Future.failedFuture(done.cause()));
							}
						});
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
		return this;
	}
	@Override
	public TopologyService generateAllRoutes(Handler<AsyncResult<List<Route>>> resultHandler) {
		beginTxnAndLock(Entity.ROUTE, InternalSql.LOCK_TABLES_FOR_ROUTE).onComplete(ar -> {
			if (ar.succeeded()) {
				Future<List<Node>> nodes = this.globalRetrieveAll(InternalSql.FETCH_ROUTEGEN_NODES)
						.map(rawList -> rawList.stream()
						.map(Node::new)
						.collect(Collectors.toList()));
				Future<List<Edge>> edges =this.globalRetrieveAll(InternalSql.FETCH_ROUTEGEN_LCS)
						.map(rawList -> rawList.stream()
						.map(Edge::new)
						.collect(Collectors.toList()));
				Future<List<PrefixAnn>> pas = this.globalRetrieveAll(InternalSql.FETCH_ROUTEGEN_ALL_PAS)
						.map(rawList -> rawList.stream()
						.map(PrefixAnn::new)
						.collect(Collectors.toList()));

				Future<List<Route>> routes = CompositeFuture
						.all(Arrays.asList(nodes, edges, pas))
						.compose(r -> routing.computeRoutes(nodes.result(), edges.result(), pas.result()));
				routes
						.compose(r -> upsertRoutes(r, false))
						.compose(r -> commitTxnAndUnlock(Entity.ROUTE))
						.onComplete(done -> {
							if (done.succeeded()) {
								resultHandler.handle(Future.succeededFuture(routes.result()));
							} else {
								rollbackAndUnlock();
								resultHandler.handle(Future.failedFuture(done.cause()));
							}
						});
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
		return this;
	}
	
	
	/* ---------------- BG ------------------ */
	private Future<List<Vctp>> generateCtps(VlinkConn vlc) {
		// always called after global TXN was init
		
		Promise<List<Vctp>> promise = Promise.promise();
		
		JsonArray params = new JsonArray().add(vlc.getVlinkId());
		Future<List<Vctp>> vctps = globalRetrieveOne(params, InternalSql.FETCH_CTPGEN_INFO)
				.map(option -> option.orElseGet(JsonObject::new))
				.compose(info -> routing.computeCtps(vlc.getName(), info));

		vctps.compose(f -> insertVctps(f))
				.onComplete(promise);
		return promise.future();
	}

	private Future<List<Face>> generateFaces(int linkConnId) {
		// always called after global TXN was init
		
		Promise<List<Face>> promise = Promise.promise();
		
		JsonArray params = new JsonArray().add(linkConnId);
		Future<List<Face>> faces = globalRetrieveOne(params, InternalSql.FETCH_FACEGEN_INFO)
				.map(option -> option.orElseGet(JsonObject::new))
				.compose(info -> routing.computeFaces(info));
		faces.compose(f -> upsertFaces(f)).onComplete(ar -> {
			if (ar.succeeded()) {
				generateAllRoutes(res -> {
					if (res.succeeded()) {
						promise.complete(faces.result());
					} else {
						promise.fail(res.cause());
					}
				});
			} else {
				promise.fail(ar.cause());
			}
		});
		return promise.future();
	}
	
	private Future<Void> upsertRoutes(List<Route> routes, boolean clean) {
		// always called after global TXN was init
		
		Promise<Void> promise = Promise.promise();
		
		Promise<Void> pClean = Promise.promise();
		if (clean) {
			globalExecute(InternalSql.DELETE_ALL_ROUTES).onComplete(pClean);
		} else {
			pClean.complete();
		}

		pClean.future().onComplete(ar -> {
			if (ar.succeeded()) {
				List<Future> fts = new ArrayList<>();
				for (Route r : routes) {
					Promise<Void> p = Promise.promise();
					fts.add(p.future());
					JsonArray params = new JsonArray()
						.add(r.getPaId())
						.add(r.getNodeId())
						.add(r.getNextHopId())
						.add(r.getFaceId())
						.add(r.getCost())				
						.add(r.getOrigin());
					globalExecute(params, InternalSql.UPDATE_ROUTE).onComplete(p.future());
				}
				CompositeFuture.all(fts).map((Void) null).onComplete(promise);
			} else {
				promise.fail(ar.cause());
			}
		});
		return promise.future();
	}

	private Future<Void> upsertFaces(List<Face> faces) {
		// always called after global TXN was init
		
		Promise<Void> promise = Promise.promise();
		
		List<Future> fts = new ArrayList<>();
		for (Face face : faces) {
			Promise<Void> p = Promise.promise();
			fts.add(p.future());
			JsonArray params = new JsonArray()
					.add(face.getLabel())
					.add(face.getStatus())
					.add(face.getLocal())
					.add(face.getRemote())
					.add(face.getScheme().getValue())
					.add(face.getVctpId())
					.add(face.getVlinkConnId());
			globalExecute(params, InternalSql.UPDATE_FACE).onComplete(p);
		}
		CompositeFuture.all(fts).map((Void) null).onComplete(promise);
		return promise.future();
	}

	private Future<List<Vctp>> insertVctps(List<Vctp> vctps) {
		// always called after global TXN was init
		
		Promise<List<Vctp>> promise = Promise.promise();
		
		List<Future<Void>> fts = new ArrayList<>();
		for (Vctp vctp : vctps) {
			Promise<Void> p = Promise.promise();
			fts.add(p.future());
			JsonArray params = new JsonArray()
					.add(vctp.getName())
					.add(vctp.getLabel())
					.add(vctp.getDescription())
					.add(new JsonObject().encode())
					.add(vctp.getVltpId());
			globalInsert(params, ApiSql.INSERT_VCTP).onComplete(id -> {
				if (id.succeeded()) {
					vctp.setId(id.result());
					p.complete();
				} else {
					p.fail(id.cause());
				}
			});
		}
		CompositeFutureImpl.all(fts.toArray(new Future[fts.size()]))
			.map(vctps)
			.onComplete(promise);
		return promise.future();
	}

	/* ------------------ STATUS ----------------- */
	@Override
	public TopologyService updateNodeStatus(int id, String status, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray().add(status).add(id);
		beginTxnAndLock(Entity.NODE, InternalSql.LOCK_TABLES_FOR_NODE).onComplete(tx -> {
			if (tx.succeeded()) {
				globalExecute(params, InternalSql.UPDATE_NODE_STATUS).onComplete(u -> {
					if (u.succeeded()) {
						params.remove(0);
						globalRetrieveMany(params, InternalSql.FETCH_LTPS_BY_NODE).onComplete(ar -> {
							if (ar.succeeded()) {
								List<Future> futures = new ArrayList<>();
								
								// Update LTPs status
								List<JsonObject> ltps = ar.result();
								for (JsonObject ltp : ltps) {
									Promise<Void> p = Promise.promise();
									futures.add(p.future());				
									updateLtpStatus(ltp.getInteger("id"), status).onComplete(p);
								}
								
								// Update PAs availability
								Promise<Void> p = Promise.promise();
								futures.add(p.future());
								JsonArray updPAs = new JsonArray()
										.add((!status.equals("DOWN")))
										.add(id);
								globalExecute(updPAs, InternalSql.UPDATE_PA_STATUS_BY_NODE).onComplete(p);

								CompositeFuture.all(futures).map((Void) null).onComplete(arr -> {
									if (arr.succeeded()) {
										generateAllRoutes(done -> {
											if (done.succeeded()) {
												commitTxnAndUnlock(Entity.NODE).onComplete(resultHandler);
											} else {
												rollbackAndUnlock();
												resultHandler.handle(Future.failedFuture(done.cause()));
											}
										});
									} else {
										rollbackAndUnlock();
										resultHandler.handle(Future.failedFuture(arr.cause()));
									}
								});
							} else {
								resultHandler.handle(Future.failedFuture(ar.cause()));
							}
						});
					} else {
						resultHandler.handle(Future.failedFuture(u.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(tx.cause()));
			}
		});
		return this;
	}

	private Future<Void> updateLtpStatus(int id, String status) {
		Promise<Void> promise = Promise.promise();
		JsonArray params = new JsonArray().add(status).add(id);
		globalExecute(params, InternalSql.UPDATE_LTP_STATUS).onComplete(u -> {
			if (u.succeeded()) {
				params.remove(0);
				globalRetrieveOne(params, InternalSql.FETCH_LINK_BY_LTP)
					.map(option -> option.orElse(null))
					.onComplete(ar -> {
						if (ar.succeeded()) {
							if (ar.result() != null) {
								JsonObject link = ar.result();
								updateLinkStatus(link.getInteger("id"), status).onComplete(promise);
							} else {
								promise.complete();
							}
						} else {
							promise.fail("Failed to fetch Link");
						}
					});
			} else {
				promise.fail(u.cause());
			}
		});
		return promise.future();
	}

	private Future<Void> updateLinkStatus(int id, String status) {
		Promise<Void> promise = Promise.promise();

		Promise<String> linkStatus = Promise.promise();
		JsonArray params = new JsonArray().add(id);
		globalRetrieveMany(params, InternalSql.FETCH_LINK_LTP_STATUS).onComplete(ar -> {
			if (ar.succeeded()) { 
				if (ar.result().size() == 2) {
					String s0 = ar.result().get(0).getString("status");
					String s1 = ar.result().get(1).getString("status");
					
					if (s0.equals("DOWN") || s1.equals("DOWN")) {
						linkStatus.complete("DOWN");
					} else if (s0.equals("DISCONN") || s1.equals("DISCONN")) {
						linkStatus.complete("DISCONN");
					} else if (s0.equals("UP") && s1.equals("UP")) {
						linkStatus.complete("UP");
					} else {
						linkStatus.fail("Unexpected LTPs status");
					}
				} else {	
					linkStatus.fail("Failed to fetch LTPs by link");
				}
			} else {
				linkStatus.fail(ar.cause());
			}
		});
		linkStatus.future().onComplete(res -> {
			if (res.succeeded()) {
				JsonArray uParams = new JsonArray().add(res.result()).add(id);
				globalExecute(uParams, InternalSql.UPDATE_LINK_STATUS).onComplete(u -> {
					if (u.succeeded()) {
						uParams.remove(0);
						globalRetrieveMany(uParams, InternalSql.FETCH_LCS_BY_LINK).onComplete(ar -> {
							if (ar.succeeded()) {
								List<JsonObject> lcs = ar.result();
								List<Future> futures = new ArrayList<>();
								for (JsonObject lc : lcs) {
									Promise<Void> p = Promise.promise();
									futures.add(p.future());				
									updateLcStatus(lc.getInteger("id"), res.result()).onComplete(p);
								}
								CompositeFuture.all(futures).map((Void) null).onComplete(promise);
							} else {
								promise.fail(ar.cause());
							}
						});
					} else {
						promise.fail(u.cause());
					}
				});
			} else {
				promise.fail(res.cause());
			}
		});
		return promise.future();
	}

	private Future<Void> updateLcStatus(int id, String status) {
		Promise<Void> promise = Promise.promise();
		JsonArray params = new JsonArray().add(status).add(id);
		globalExecute(params, InternalSql.UPDATE_LC_STATUS).onComplete(u -> {
			if (u.succeeded()) {
				params.remove(0);
				globalRetrieveMany(params, InternalSql.FETCH_FACES_BY_LC).onComplete(ar -> {
					if (ar.succeeded()) {
						List<JsonObject> faces = ar.result();
						JsonArray updFace1 = new JsonArray().add(status).add(faces.get(0).getInteger("id"));
						JsonArray updFace2 = new JsonArray().add(status).add(faces.get(1).getInteger("id"));
						globalExecute(updFace1, InternalSql.UPDATE_FACE_STATUS)
							.compose(fOk -> globalExecute(updFace2, InternalSql.UPDATE_FACE_STATUS)).onComplete(promise);
					} else {
						promise.fail(ar.cause());
					}
				});
			} else {
				promise.fail(u.cause());
			}
		});
		return promise.future();
	}
	
	private boolean isValidMACAddress(String str) {
		if (str == null) {
			return false;
		}
		String regex = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.matches();
	}
}





