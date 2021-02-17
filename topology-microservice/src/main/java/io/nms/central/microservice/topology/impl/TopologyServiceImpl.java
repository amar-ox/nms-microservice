package io.nms.central.microservice.topology.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import io.nms.central.microservice.common.functional.JSONUtils;
import io.nms.central.microservice.common.service.JdbcRepositoryWrapper;
import io.nms.central.microservice.notification.model.Status.StatusEnum;
import io.nms.central.microservice.topology.TopologyService;
import io.nms.central.microservice.topology.model.Edge;
import io.nms.central.microservice.topology.model.EtherConnInfo;
import io.nms.central.microservice.topology.model.NdnConnInfo;
import io.nms.central.microservice.topology.model.Node;
import io.nms.central.microservice.topology.model.PrefixAnn;
import io.nms.central.microservice.topology.model.Route;
import io.nms.central.microservice.topology.model.Vconnection;
import io.nms.central.microservice.topology.model.Vctp;
import io.nms.central.microservice.topology.model.Vctp.ConnTypeEnum;
import io.nms.central.microservice.topology.model.Vlink;
import io.nms.central.microservice.topology.model.VlinkConn;
import io.nms.central.microservice.topology.model.Vltp;
import io.nms.central.microservice.topology.model.Vnode;
import io.nms.central.microservice.topology.model.Vsubnet;
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

	private static final Logger logger = LoggerFactory.getLogger(TopologyServiceImpl.class);
	private Routing routing;

	public TopologyServiceImpl(Vertx vertx, JsonObject config) {
		super(vertx, config);
		routing = new Routing();
	}

	@Override
	public TopologyService initializePersistence(Handler<AsyncResult<Void>> resultHandler) {
		List<String> statements = new ArrayList<String>();
		statements.add(ApiSql.CREATE_TABLE_VSUBNET);
		statements.add(ApiSql.CREATE_TABLE_VNODE);
		statements.add(ApiSql.CREATE_TABLE_VLTP);
		statements.add(ApiSql.CREATE_TABLE_VCTP);
		statements.add(ApiSql.CREATE_TABLE_VLINK);
		statements.add(ApiSql.CREATE_TABLE_VLINKCONN);
		statements.add(ApiSql.CREATE_TABLE_VCONNECTION);
		statements.add(ApiSql.CREATE_TABLE_PA);
		statements.add(ApiSql.CREATE_TABLE_ROUTE);
		client.getConnection(conn -> {
			if (conn.succeeded()) {
				conn.result().batch(statements, r -> {
					conn.result().close();
					if (r.succeeded()) {
						initializeStatus(resultHandler);
					} else {
						resultHandler.handle(Future.failedFuture(r.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(conn.cause()));
			}
		});
		return this;
	}

	private void initializeStatus(Handler<AsyncResult<Void>> resultHandler) {
		this.retrieveAll(InternalSql.FETCH_ALL_NODE_IDS).onComplete(res -> {
			if (res.succeeded()) {
				List<JsonObject> nodeIds = res.result();
				List<Future> futures = new ArrayList<>();
				for (JsonObject nId : nodeIds) {
					Promise<Void> p = Promise.promise();
					futures.add(p.future());
					updateNodeStatus(nId.getInteger("id"), StatusEnum.DOWN, p);
				}
				CompositeFuture.all(futures).map((Void) null).onComplete(resultHandler);
			} else {
				resultHandler.handle(Future.failedFuture(res.cause()));
			}
		});
	}

	/********** Vsubnet **********/
	@Override
	public TopologyService addVsubnet(Vsubnet vsubnet, Handler<AsyncResult<Integer>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vsubnet.getName())
				.add(vsubnet.getLabel())
				.add(vsubnet.getDescription())
				.add(JSONUtils.pojo2Json(vsubnet.getInfo(), false));
		insertAndGetId(params, ApiSql.INSERT_VSUBNET, resultHandler);
		return this;
	}

	@Override
	public TopologyService getVsubnet(String vsubnetId, Handler<AsyncResult<Vsubnet>> resultHandler) {
		this.retrieveOne(vsubnetId, ApiSql.FETCH_VSUBNET_BY_ID).map(option -> option.map(json -> {
			return JSONUtils.json2Pojo(json.encode(), Vsubnet.class);
		}).orElse(null)).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getAllVsubnets(Handler<AsyncResult<List<Vsubnet>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VSUBNETS).map(rawList -> rawList.stream().map(row -> {
			return JSONUtils.json2Pojo(row.encode(), Vsubnet.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
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
				.add(JSONUtils.pojo2Json(vsubnet.getInfo(), false))
				.add(id);
		executeNoResult(params, ApiSql.UPDATE_VSUBNET, resultHandler);
		return this;
	}

	/********** Vnode **********/
	@Override
	public TopologyService addVnode(Vnode vnode, Handler<AsyncResult<Integer>> resultHandler) {
		vnode.setStatus(StatusEnum.DOWN);

		String macAddr = validateAndConvertMAC(vnode.getHwaddr());
		if (macAddr.isEmpty()) {
				resultHandler.handle(Future.failedFuture("MAC address not valid"));
				return this;
		}
		vnode.setHwaddr(macAddr);

		JsonArray params = new JsonArray()
				.add(vnode.getName())
				.add(vnode.getLabel())
				.add(vnode.getDescription())
				.add(JSONUtils.pojo2Json(vnode.getInfo(), false))
				.add(vnode.getStatus().getValue())
				.add(vnode.getPosx())
				.add(vnode.getPosy())
				.add(vnode.getLocation())
				.add(vnode.getType())
				.add(vnode.getVsubnetId())
				.add(vnode.getHwaddr());
		insertAndGetId(params, ApiSql.INSERT_VNODE, resultHandler);
		return this;
	}

	@Override
	public TopologyService getVnode(String vnodeId, Handler<AsyncResult<Vnode>> resultHandler) {
		this.retrieveOne(vnodeId, ApiSql.FETCH_VNODE_BY_ID).map(option -> option.map(json -> {
			return JSONUtils.json2Pojo(json.encode(), Vnode.class);
		}).orElse(null)).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getAllVnodes(Handler<AsyncResult<List<Vnode>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VNODES).map(rawList -> rawList.stream().map(row -> {
			return JSONUtils.json2Pojo(row.encode(), Vnode.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getVnodesByVsubnet(String vsubnetId, Handler<AsyncResult<List<Vnode>>> resultHandler) {
		JsonArray params = new JsonArray().add(vsubnetId);
		this.retrieveMany(params, ApiSql.FETCH_VNODES_BY_VSUBNET).map(rawList -> rawList.stream().map(row -> {
			return JSONUtils.json2Pojo(row.encode(), Vnode.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService deleteVnode(String vnodeId, Handler<AsyncResult<Void>> resultHandler) {
		Promise<Void> vltpsDeleted = Promise.promise();
		JsonArray pVnodeId = new JsonArray().add(vnodeId);

		UUID op = UUID.randomUUID();
		beginTxnAndLock(Entity.NODE, op, InternalSql.LOCK_TABLES_FOR_NODE).onComplete(ar -> {
			if (ar.succeeded()) {
				globalRetrieveMany(pVnodeId, ApiSql.FETCH_VLTPS_BY_VNODE).onComplete(res -> {
					if (res.succeeded()) {
						Vltp[] vltps = JSONUtils.json2Pojo(new JsonArray(res.result()).encode(), Vltp[].class);
						List<Future> futures = new ArrayList<>();
						for (Vltp vltp : vltps) {
							Promise<Void> p = Promise.promise();
							futures.add(p.future());
							deleteVltp(op, vltp.getId(), p);
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
				globalExecute(pVnodeId, ApiSql.DELETE_VNODE).compose(r -> commitTxnAndUnlock(Entity.NODE, op))
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
				.add(JSONUtils.pojo2Json(vnode.getInfo(), false))
				.add(vnode.getStatus().getValue())
				.add(vnode.getPosx()).add(vnode.getPosy())
				.add(vnode.getLocation())
				.add(vnode.getHwaddr())
				.add(id);
		executeNoResult(params, ApiSql.UPDATE_VNODE, resultHandler);
		return this;
	}

	/********** Vltp **********/
	@Override
	public TopologyService addVltp(Vltp vltp, Handler<AsyncResult<Integer>> resultHandler) {
		vltp.setStatus(StatusEnum.DOWN);
		JsonArray params = new JsonArray()
				.add(vltp.getName())
				.add(vltp.getLabel())
				.add(vltp.getDescription())
				.add(JSONUtils.pojo2Json(vltp.getInfo(), false))
				.add(vltp.getStatus().getValue())
				.add(vltp.getVnodeId())
				.add(vltp.getPort())
				.add(vltp.getBandwidth())
				.add(vltp.getMtu())
				.add(vltp.isBusy());
		insertAndGetId(params, ApiSql.INSERT_VLTP, resultHandler);
		return this;
	}

	@Override
	public TopologyService getVltp(String vltpId, Handler<AsyncResult<Vltp>> resultHandler) {
		this.retrieveOne(vltpId, ApiSql.FETCH_VLTP_BY_ID).map(option -> option.map(json -> {
			return JSONUtils.json2Pojo(json.encode(), Vltp.class);
		}).orElse(null)).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getAllVltps(Handler<AsyncResult<List<Vltp>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VLTPS).map(rawList -> rawList.stream().map(row -> {
			return JSONUtils.json2Pojo(row.encode(), Vltp.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getVltpsByVnode(String vnodeId, Handler<AsyncResult<List<Vltp>>> resultHandler) {
		JsonArray params = new JsonArray().add(vnodeId);
		this.retrieveMany(params, ApiSql.FETCH_VLTPS_BY_VNODE).map(rawList -> rawList.stream().map(row -> {
			return JSONUtils.json2Pojo(row.encode(), Vltp.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService deleteVltp(String vltpId, Handler<AsyncResult<Void>> resultHandler) {
		final UUID op = UUID.randomUUID();
		deleteVltp(op, Integer.valueOf(vltpId), resultHandler);
		return this;
	}

	@Override
	public TopologyService updateVltp(String id, Vltp vltp, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vltp.getLabel())
				.add(vltp.getDescription())
				.add(JSONUtils.pojo2Json(vltp.getInfo(), false))
				.add(vltp.getStatus().getValue())
				.add(vltp.isBusy())
				.add(id);
		executeNoResult(params, ApiSql.UPDATE_VLTP, resultHandler);
		return this;
	}

	private void deleteVltp(UUID op, int vltpId, Handler<AsyncResult<Void>> resultHandler) {
		Promise<Void> linkDeleted = Promise.promise();
		JsonArray pVltpId = new JsonArray().add(vltpId);
		beginTxnAndLock(Entity.LTP, op, InternalSql.LOCK_TABLES_FOR_LTP).onComplete(ar -> {
			if (ar.succeeded()) {
				globalRetrieveOne(pVltpId, InternalSql.FETCH_LINK_BY_LTP).map(option -> option.orElse(null))
						.onComplete(res -> {
							if (res.succeeded()) {
								if (res.result() != null) {
									Vlink vlink = JSONUtils.json2Pojo(res.result().encode(), Vlink.class);
									deleteVlink(op, vlink.getId(), linkDeleted);
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
						.compose(r -> commitTxnAndUnlock(Entity.LTP, op))
						.onComplete(resultHandler);
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	/********** Vctp **********/
	@Override
	public TopologyService addVctp(Vctp vctp, Handler<AsyncResult<Integer>> resultHandler) {
		ConnTypeEnum ct = vctp.getConnType();
		if (ct.equals(ConnTypeEnum.Ether)) {
			String macAddr = validateAndConvertMAC(((EtherConnInfo)vctp.getConnInfo()).getAddress());
			if (macAddr.isEmpty()) {
				resultHandler.handle(Future.failedFuture("MAC address not valid"));
				return this;
			}
			((EtherConnInfo)vctp.getConnInfo()).setAddress(macAddr);
		}
		if (ct.equals(ConnTypeEnum.NDN)) {
			String lMacAddr = validateAndConvertMAC(((NdnConnInfo) vctp.getConnInfo()).getLocal());
			String rMacAddr = validateAndConvertMAC(((NdnConnInfo) vctp.getConnInfo()).getRemote());
			if (lMacAddr.isEmpty() || rMacAddr.isEmpty()){
				resultHandler.handle(Future.failedFuture("MAC address not valid"));
				return this;
			}
			((NdnConnInfo) vctp.getConnInfo()).setLocal(lMacAddr);
			((NdnConnInfo) vctp.getConnInfo()).setRemote(rMacAddr);
		}
		
		vctp.setStatus(StatusEnum.DOWN);

		String querryNode;
		String querryInsert;
		JsonArray pQuerryNode = new JsonArray().add(vctp.getParentId());

		if (vctp.getConnType().equals(ConnTypeEnum.Ether)) {
			querryNode = InternalSql.GET_LTP_NODE;
			querryInsert = ApiSql.INSERT_VCTP_VLTP;
		} else {
			querryNode = InternalSql.GET_CTP_NODE;
			querryInsert = ApiSql.INSERT_VCTP_VCTP;
		}
		
		this.retrieveOne(pQuerryNode, querryNode).onComplete(ar -> {
			if (ar.succeeded()) {
				if (!ar.result().isPresent()) {
					resultHandler.handle(Future.failedFuture("Corresponing Node not found"));
				} else {
					int nodeId = ar.result().get().getInteger("vnodeId");
					JsonArray params = new JsonArray()
							.add(vctp.getName())
							.add(vctp.getLabel())
							.add(vctp.getDescription())
							.add(JSONUtils.pojo2Json(vctp.getInfo(), false))
							.add(vctp.getConnType())
							.add(JSONUtils.pojo2Json(vctp.getConnInfo(), false))
							.add(vctp.getStatus().getValue())
							.add(vctp.getParentId())
							.add(nodeId);
					insertAndGetId(params, querryInsert, resultHandler);
				}
			} else {
				resultHandler.handle(Future.failedFuture("Failed to check CTP"));
			}
		});
		return this;
	}

	@Override
	public TopologyService getVctp(String vctpId, Handler<AsyncResult<Vctp>> resultHandler) {
		this.retrieveOne(vctpId, ApiSql.FETCH_VCTP_BY_ID).map(option -> option.map(json -> {
			if (json.getInteger("vltpId") == null) {
				json.remove("vltpId");
			}
			if (json.getInteger("vctpId") == null) {
				json.remove("vctpId");
			}
			return JSONUtils.json2Pojo(json.encode(), Vctp.class);
		}).orElse(null)).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getAllVctps(Handler<AsyncResult<List<Vctp>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VCTPS).map(rawList -> rawList.stream().map(row -> {
			if (row.getInteger("vltpId") == null) {
				row.remove("vltpId");
			}
			if (row.getInteger("vctpId") == null) {
				row.remove("vctpId");
			}
			return JSONUtils.json2Pojo(row.encode(), Vctp.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getVctpsByType(String type, Handler<AsyncResult<List<Vctp>>> resultHandler) {
		JsonArray params = new JsonArray().add(type);
		this.retrieveMany(params, ApiSql.FETCH_VCTPS_BY_TYPE).map(rawList -> rawList.stream().map(row -> {
			if (row.getInteger("vltpId") == null) {
				row.remove("vltpId");
			}
			if (row.getInteger("vctpId") == null) {
				row.remove("vctpId");
			}
			return JSONUtils.json2Pojo(row.encode(), Vctp.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getVctpsByVltp(String vltpId, Handler<AsyncResult<List<Vctp>>> resultHandler) {
		JsonArray params = new JsonArray().add(vltpId);
		this.retrieveMany(params, ApiSql.FETCH_VCTPS_BY_VLTP).map(rawList -> rawList.stream().map(row -> {
			row.remove("vctpId");
			return JSONUtils.json2Pojo(row.encode(), Vctp.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getVctpsByVctp(String vctpId, Handler<AsyncResult<List<Vctp>>> resultHandler) {
		JsonArray params = new JsonArray().add(vctpId);
		this.retrieveMany(params, ApiSql.FETCH_VCTPS_BY_VCTP).map(rawList -> rawList.stream().map(row -> {
			row.remove("vltpId");
			return JSONUtils.json2Pojo(row.encode(), Vctp.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getVctpsByVnode(String vnodeId, Handler<AsyncResult<List<Vctp>>> resultHandler) {
		JsonArray params = new JsonArray().add(vnodeId);
		this.retrieveMany(params, ApiSql.FETCH_VCTPS_BY_VNODE).map(rawList -> rawList.stream().map(row -> {
			if (row.getInteger("vltpId") == null) {
				row.remove("vltpId");
			}
			if (row.getInteger("vctpId") == null) {
				row.remove("vctpId");
			}
			return JSONUtils.json2Pojo(row.encode(), Vctp.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
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
				.add(JSONUtils.pojo2Json(vctp.getInfo(), false))
				.add(vctp.getStatus().getValue())
				.add(id);
		executeNoResult(params, ApiSql.UPDATE_VCTP, resultHandler);
		return this;
	}

	/********** Vlink **********/
	@Override
	public TopologyService addVlink(Vlink vlink, Handler<AsyncResult<Integer>> resultHandler) {
		vlink.setStatus(StatusEnum.DOWN);
		JsonArray pVlink = new JsonArray()
				.add(vlink.getName())
				.add(vlink.getLabel())
				.add(vlink.getDescription())
				.add(JSONUtils.pojo2Json(vlink.getInfo(), false))
				.add(vlink.getStatus().getValue())
				.add(vlink.getSrcVltpId())
				.add(vlink.getDestVltpId());
		JsonArray updSrcLtp = new JsonArray().add(true).add(vlink.getSrcVltpId());
		JsonArray updDestLtp = new JsonArray().add(true).add(vlink.getDestVltpId());

		UUID opp = UUID.randomUUID();
		beginTxnAndLock(Entity.LINK, opp, InternalSql.LOCK_TABLES_FOR_LINK).onComplete(ar -> {
			if (ar.succeeded()) {
				Future<Integer> vlinkId = globalInsert(pVlink, ApiSql.INSERT_VLINK);
				vlinkId.compose(r -> globalExecute(updSrcLtp, InternalSql.UPDATE_LTP_BUSY))
						.compose(r -> globalExecute(updDestLtp, InternalSql.UPDATE_LTP_BUSY))
						.compose(r -> commitTxnAndUnlock(Entity.LINK, opp)).map(vlinkId.result())
						.onComplete(resultHandler);
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
		return this;
	}

	@Override
	public TopologyService getVlink(String vlinkId, Handler<AsyncResult<Vlink>> resultHandler) {
		this.retrieveOne(vlinkId, ApiSql.FETCH_VLINK_BY_ID).map(option -> option.map(json -> {
			return JSONUtils.json2Pojo(json.encode(), Vlink.class);
		}).orElse(null)).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getAllVlinks(Handler<AsyncResult<List<Vlink>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VLINKS).map(rawList -> rawList.stream().map(row -> {
			return JSONUtils.json2Pojo(row.encode(), Vlink.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getVlinksByVsubnet(String vsubnetId, Handler<AsyncResult<List<Vlink>>> resultHandler) {
		JsonArray params = new JsonArray().add(vsubnetId);
		this.retrieveMany(params, ApiSql.FETCH_VLINKS_BY_VSUBNET).map(rawList -> rawList.stream().map(row -> {
			return JSONUtils.json2Pojo(row.encode(), Vlink.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService deleteVlink(String vlinkId, Handler<AsyncResult<Void>> resultHandler) {
		final UUID op = UUID.randomUUID();
		deleteVlink(op, Integer.valueOf(vlinkId), resultHandler);
		return this;
	}

	@Override
	public TopologyService updateVlink(String id, Vlink vlink, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vlink.getLabel())
				.add(vlink.getDescription())
				.add(JSONUtils.pojo2Json(vlink.getInfo(), false))
				.add(vlink.getStatus().getValue())
				.add(id);
		executeNoResult(params, ApiSql.UPDATE_VLINK, resultHandler);
		return this;
	}

	public void deleteVlink(UUID op, int vlinkId, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray pVlinkId = new JsonArray().add(vlinkId);
		beginTxnAndLock(Entity.LINK, op, InternalSql.LOCK_TABLES_FOR_LINK).onComplete(ar -> {
			if (ar.succeeded()) {
				globalRetrieveOne(pVlinkId, ApiSql.FETCH_VLINK_BY_ID).map(option -> option.orElse(null))
						.onComplete(res -> {
							if (res.succeeded()) {
								if (res.result() != null) {
									Vlink vlink = JSONUtils.json2Pojo(res.result().encode(), Vlink.class);
									JsonArray pSrcLtp = new JsonArray().add(false).add(vlink.getSrcVltpId());
									JsonArray pDestLtp = new JsonArray().add(false).add(vlink.getDestVltpId());

									globalExecute(pVlinkId, ApiSql.DELETE_VLINK)
											.compose(r -> globalExecute(pSrcLtp, InternalSql.UPDATE_LTP_BUSY))
											.compose(r -> globalExecute(pDestLtp, InternalSql.UPDATE_LTP_BUSY))
											.compose(r -> commitTxnAndUnlock(Entity.LINK, op))
											.onComplete(resultHandler);
								} else {
									rollbackAndUnlock();
									resultHandler.handle(Future.failedFuture("Vlink not found"));	
								}
							} else {
								resultHandler.handle(Future.failedFuture(res.cause()));
							}
						});
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	/********** VlinkConn **********/
	@Override
	public TopologyService addVlinkConn(VlinkConn vlinkConn, Handler<AsyncResult<Integer>> resultHandler) {
		// TODO: check CTPs type
		vlinkConn.setStatus(StatusEnum.DOWN);
		JsonArray params = new JsonArray()
				.add(vlinkConn.getName())
				.add(vlinkConn.getLabel())
				.add(vlinkConn.getDescription())
				.add(JSONUtils.pojo2Json(vlinkConn.getInfo(), false))
				.add(vlinkConn.getStatus().getValue())
				.add(vlinkConn.getSrcVctpId())
				.add(vlinkConn.getDestVctpId())
				.add(vlinkConn.getVlinkId());
		insertAndGetId(params, ApiSql.INSERT_VLINKCONN, resultHandler);
		return this;
	}

	@Override
	public TopologyService getVlinkConn(String vlinkConnId, Handler<AsyncResult<VlinkConn>> resultHandler) {
		this.retrieveOne(vlinkConnId, ApiSql.FETCH_VLINKCONN_BY_ID).map(option -> option.map(json -> {
			return JSONUtils.json2Pojo(json.encode(), VlinkConn.class);
		}).orElse(null)).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getAllVlinkConns(Handler<AsyncResult<List<VlinkConn>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VLINKCONNS).map(rawList -> rawList.stream().map(row -> {
			return JSONUtils.json2Pojo(row.encode(), VlinkConn.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getVlinkConnsByVlink(String vlinkId, Handler<AsyncResult<List<VlinkConn>>> resultHandler) {
		JsonArray params = new JsonArray().add(vlinkId);
		this.retrieveMany(params, ApiSql.FETCH_VLINKCONNS_BY_VLINK).map(rawList -> rawList.stream().map(row -> {
			return JSONUtils.json2Pojo(row.encode(), VlinkConn.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getVlinkConnsByVsubnet(String vsubnetId, Handler<AsyncResult<List<VlinkConn>>> resultHandler) {
		JsonArray params = new JsonArray().add(vsubnetId);
		this.retrieveMany(params, ApiSql.FETCH_VLINKCONNS_BY_VSUBNET).map(rawList -> rawList.stream().map(row -> {
			return JSONUtils.json2Pojo(row.encode(), VlinkConn.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService deleteVlinkConn(String vlinkConnId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vlinkConnId, ApiSql.DELETE_VLINKCONN, resultHandler);
		return this;
	}

	@Override
	public TopologyService updateVlinkConn(String id, VlinkConn vlinkConn, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vlinkConn.getLabel())
				.add(vlinkConn.getDescription())
				.add(JSONUtils.pojo2Json(vlinkConn.getInfo(), false))
				.add(vlinkConn.getStatus().getValue())
				.add(id);
		executeNoResult(params, ApiSql.UPDATE_VLINKCONN, resultHandler);
		return this;
	}

	/********** Vconnection **********/
	@Override
	public TopologyService addVconnection(Vconnection vconnection, Handler<AsyncResult<Integer>> resultHandler) {
		// TODO: check CTPs type
		vconnection.setStatus(StatusEnum.DOWN);
		JsonArray params = new JsonArray()
				.add(vconnection.getName())
				.add(vconnection.getLabel())
				.add(vconnection.getDescription())
				.add(JSONUtils.pojo2Json(vconnection.getInfo(), false))
				.add(vconnection.getStatus().getValue())
				.add(vconnection.getSrcVctpId())
				.add(vconnection.getDestVctpId());
		insertAndGetId(params, ApiSql.INSERT_VCONNECTION, resultHandler);
		return this;
	}

	@Override
	public TopologyService getVconnection(String vconnectionId, Handler<AsyncResult<Vconnection>> resultHandler) {
		this.retrieveOne(vconnectionId, ApiSql.FETCH_VCONNECTION_BY_ID).map(option -> option.map(json -> {
			return JSONUtils.json2Pojo(json.encode(), Vconnection.class);
		}).orElse(null)).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getAllVconnections(Handler<AsyncResult<List<Vconnection>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_VCONNECTIONS).map(rawList -> rawList.stream().map(row -> {
			return JSONUtils.json2Pojo(row.encode(), Vconnection.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getVconnectionsByType(String type, Handler<AsyncResult<List<Vconnection>>> resultHandler) {
		JsonArray params = new JsonArray().add(type);
		this.retrieveMany(params, ApiSql.FETCH_VCONNECTIONS_BY_TYPE).map(rawList -> rawList.stream().map(row -> {
			return JSONUtils.json2Pojo(row.encode(), Vconnection.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getVconnectionsByVsubnet(String vsubnetId, Handler<AsyncResult<List<Vconnection>>> resultHandler) {
		JsonArray params = new JsonArray().add(vsubnetId);
		this.retrieveMany(params, ApiSql.FETCH_VCONNECTIONS_BY_VSUBNET).map(rawList -> rawList.stream().map(row -> {
			return JSONUtils.json2Pojo(row.encode(), Vconnection.class);
		}).collect(Collectors.toList())).onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService deleteVconnection(String vconnectionId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vconnectionId, ApiSql.DELETE_VCONNECTION, resultHandler);
		return this;
	}

	@Override
	public TopologyService updateVconnection(String id, Vconnection vconnection, Handler<AsyncResult<Void>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vconnection.getLabel())
				.add(vconnection.getDescription())
				.add(JSONUtils.pojo2Json(vconnection.getInfo(), false))
				.add(vconnection.getStatus().getValue())
				.add(id);
		executeNoResult(params, ApiSql.UPDATE_VCONNECTION, resultHandler);
		return this;
	}

	/********** PrefixAnn **********/
	@Override
	public TopologyService addPrefixAnn(PrefixAnn pa, Handler<AsyncResult<Void>> resultHandler) {
		UUID op = UUID.randomUUID();
		beginTxnAndLock(Entity.PA, op, InternalSql.LOCK_TABLES_FOR_ROUTE).onComplete(tx -> {
			if (tx.succeeded()) {
				JsonArray pNode = new JsonArray().add(pa.getOriginId());
				globalRetrieveOne(pNode, InternalSql.GET_NODE_STATUS).map(option -> option.orElse(null))
						.onComplete(res -> {
							if (res.succeeded()) {
								if (res.result() != null) {
									String status = res.result().getString("status");
									JsonArray params = new JsonArray().add(pa.getName()).add(pa.getOriginId())
											.add(status.equals(StatusEnum.UP.getValue()));
									globalInsert(params, ApiSql.INSERT_PA).onComplete(paId -> {
										if (paId.succeeded()) {
											// TODO: EVB to routing service
											generateRoutesToPrefix(op, pa.getName(), ar -> {
												if (ar.succeeded()) {
													commitTxnAndUnlock(Entity.PA, op).onComplete(resultHandler);
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
									resultHandler.handle(Future.failedFuture("Origin not found"));
								}
							} else {
								resultHandler.handle(Future.failedFuture(res.cause()));
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
		this.retrieveOne(paId, ApiSql.FETCH_PA_BY_ID).map(option -> option.map(PrefixAnn::new).orElse(null))
				.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getAllPrefixAnns(Handler<AsyncResult<List<PrefixAnn>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_PAS)
				.map(rawList -> rawList.stream().map(PrefixAnn::new).collect(Collectors.toList()))
				.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getPrefixAnnsByVsubnet(String vsubnetId,
			Handler<AsyncResult<List<PrefixAnn>>> resultHandler) {
		JsonArray params = new JsonArray().add(vsubnetId);
		this.retrieveMany(params, ApiSql.FETCH_PAS_BY_VSUBNET)
				.map(rawList -> rawList.stream().map(PrefixAnn::new).collect(Collectors.toList()))
				.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getPrefixAnnsByVnode(String nodeId, Handler<AsyncResult<List<PrefixAnn>>> resultHandler) {
		JsonArray params = new JsonArray().add(nodeId);
		this.retrieveMany(params, ApiSql.FETCH_PAS_BY_NODE)
				.map(rawList -> rawList.stream().map(PrefixAnn::new).collect(Collectors.toList()))
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
		JsonArray params = new JsonArray().add(route.getPaId()).add(route.getNodeId()).add(route.getNextHopId())
				.add(route.getFaceId()).add(route.getCost()).add(route.getOrigin());
		insertAndGetId(params, ApiSql.INSERT_ROUTE, resultHandler);
		return this;
	}

	@Override
	public TopologyService getRoute(String routeId, Handler<AsyncResult<Route>> resultHandler) {
		this.retrieveOne(routeId, ApiSql.FETCH_ROUTE_BY_ID).map(option -> option.map(Route::new).orElse(null))
				.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getAllRoutes(Handler<AsyncResult<List<Route>>> resultHandler) {
		this.retrieveAll(ApiSql.FETCH_ALL_ROUTES)
				.map(rawList -> rawList.stream().map(Route::new).collect(Collectors.toList()))
				.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getRoutesByVsubnet(String vsubnetId, Handler<AsyncResult<List<Route>>> resultHandler) {
		JsonArray params = new JsonArray().add(vsubnetId);
		this.retrieveMany(params, ApiSql.FETCH_ROUTES_BY_VSUBNET)
				.map(rawList -> rawList.stream().map(Route::new).collect(Collectors.toList()))
				.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService getRoutesByNode(String nodeId, Handler<AsyncResult<List<Route>>> resultHandler) {
		JsonArray params = new JsonArray().add(nodeId);
		this.retrieveMany(params, ApiSql.FETCH_ROUTES_BY_NODE)
				.map(rawList -> rawList.stream().map(Route::new).collect(Collectors.toList()))
				.onComplete(resultHandler);
		return this;
	}

	@Override
	public TopologyService deleteRoute(String routeId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(routeId, ApiSql.DELETE_ROUTE, resultHandler);
		return this;
	}

	public void generateRoutesToPrefix(UUID op, String name, Handler<AsyncResult<List<Route>>> resultHandler) {
		UUID opp[] = { op };
		if (op == null) {
			opp[0] = UUID.randomUUID();
		}
		beginTxnAndLock(Entity.ROUTE, opp[0], InternalSql.LOCK_TABLES_FOR_ROUTE).onComplete(ar -> {
			if (ar.succeeded()) {
				Future<List<Node>> nodes = this.globalRetrieveAll(InternalSql.FETCH_ROUTEGEN_NODES)
						.map(rawList -> rawList.stream().map(Node::new).collect(Collectors.toList()));
				Future<List<Edge>> edges = this.globalRetrieveAll(InternalSql.FETCH_ROUTEGEN_CONNECTIONS)
						.map(rawList -> rawList.stream().map(Edge::new).collect(Collectors.toList()));
				JsonArray params = new JsonArray().add(name);
				Future<List<PrefixAnn>> pas = this.globalRetrieveMany(params, InternalSql.FETCH_ROUTEGEN_PAS_BY_NAME)
						.map(rawList -> rawList.stream().map(PrefixAnn::new).collect(Collectors.toList()));

				Future<List<Route>> routes = CompositeFuture.all(Arrays.asList(nodes, edges, pas))
						.compose(r -> routing.computeRoutes(nodes.result(), edges.result(), pas.result()));
				routes.compose(r -> upsertRoutes(r, false)).compose(r -> commitTxnAndUnlock(Entity.ROUTE, opp[0]))
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
	}

	public void generateAllRoutes(UUID op, Handler<AsyncResult<List<Route>>> resultHandler) {
		UUID opp[] = { op };
		if (op == null) {
			opp[0] = UUID.randomUUID();
		}
		beginTxnAndLock(Entity.ROUTE, opp[0], InternalSql.LOCK_TABLES_FOR_ROUTE).onComplete(ar -> {
			if (ar.succeeded()) {
				Future<List<Node>> nodes = this.globalRetrieveAll(InternalSql.FETCH_ROUTEGEN_NODES)
						.map(rawList -> rawList.stream().map(Node::new).collect(Collectors.toList()));
				Future<List<Edge>> edges = this.globalRetrieveAll(InternalSql.FETCH_ROUTEGEN_CONNECTIONS)
						.map(rawList -> rawList.stream().map(Edge::new).collect(Collectors.toList()));
				Future<List<PrefixAnn>> pas = this.globalRetrieveAll(InternalSql.FETCH_ROUTEGEN_ALL_PAS)
						.map(rawList -> rawList.stream().map(PrefixAnn::new).collect(Collectors.toList()));

				Future<List<Route>> routes = CompositeFuture.all(Arrays.asList(nodes, edges, pas))
						.compose(r -> routing.computeRoutes(nodes.result(), edges.result(), pas.result()));
				routes.compose(r -> upsertRoutes(r, true)).compose(r -> commitTxnAndUnlock(Entity.ROUTE, opp[0]))
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


	/* ------------------ STATUS ----------------- */
	@Override
	public TopologyService updateNodeStatus(int id, StatusEnum status, Handler<AsyncResult<Void>> resultHandler) {
		UUID op = UUID.randomUUID();
		JsonArray params = new JsonArray().add(status.getValue()).add(id);
		beginTxnAndLock(Entity.NODE, op, InternalSql.LOCK_TABLES).onComplete(tx -> {
			if (tx.succeeded()) {
				globalExecute(params, InternalSql.UPDATE_NODE_STATUS).onComplete(u -> {
					if (u.succeeded()) {
						params.remove(0);
						globalRetrieveMany(params, ApiSql.FETCH_VLTPS_BY_VNODE).onComplete(ar -> {
							if (ar.succeeded()) {
								List<Future> futures = new ArrayList<>();

								// Update LTPs status
								Vltp[] ltps = JSONUtils.json2Pojo(new JsonArray(ar.result()).encode(), Vltp[].class);
								for (Vltp ltp : ltps) {
									Promise<Void> p = Promise.promise();
									futures.add(p.future());
									updateLtpStatus(ltp.getId(), status, op.toString(), p);
								}

								// Update PAs availability
								Promise<Void> p = Promise.promise();
								futures.add(p.future());
								JsonArray updPAs = new JsonArray().add(status.equals(StatusEnum.UP)).add(id);
								globalExecute(updPAs, InternalSql.UPDATE_PA_STATUS_BY_NODE).onComplete(p);

								CompositeFuture.all(futures).map((Void) null).onComplete(done -> {
									if (done.succeeded()) {
										commitTxnAndUnlock(Entity.NODE, op).onComplete(resultHandler);
									} else {
										rollbackAndUnlock();
										resultHandler.handle(Future.failedFuture(done.cause()));
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

	@Override
	public TopologyService updateLtpStatus(int id, StatusEnum status, String op, Handler<AsyncResult<Void>> resultHandler) {
		UUID opp[] = { getUUID(op) };
		beginTxnAndLock(Entity.LTP, opp[0], InternalSql.LOCK_TABLES).onComplete(tx -> {
			if (tx.succeeded()) {
				JsonArray params = new JsonArray().add(status.getValue()).add(id);
				globalExecute(params, InternalSql.UPDATE_LTP_STATUS).onComplete(u -> {
					if (u.succeeded()) {
						List<Future> fLtp = new ArrayList<>();

						// Update Link status
						Promise<Void> pUpdateLink = Promise.promise();
						fLtp.add(pUpdateLink.future());
						params.remove(0);
						globalRetrieveOne(params, InternalSql.FETCH_LINK_BY_LTP).map(option -> option.orElse(null))
								.onComplete(ar -> {
							if (ar.succeeded()) {
								if (ar.result() != null) {
									Vlink vlink = JSONUtils.json2Pojo(ar.result().encode(), Vlink.class);
									updateLinkStatus(vlink.getId(), status, opp[0].toString(), pUpdateLink);
								} else {
									pUpdateLink.complete();
								}
							} else {
								pUpdateLink.fail(ar.cause());
							}
						});

						// Update CTPs status
						Promise<Void> pUpdateCtp = Promise.promise();
						fLtp.add(pUpdateCtp.future());
						globalRetrieveMany(params, ApiSql.FETCH_VCTPS_BY_VLTP).onComplete(ar -> {
							if (ar.succeeded()) {
								List<Future> fCtpStatus = new ArrayList<>();
								Vctp[] vctps = JSONUtils.json2Pojo(new JsonArray(ar.result()).encode(), Vctp[].class);
								for (Vctp vctp : vctps) {
									Promise<Void> p = Promise.promise();
									fCtpStatus.add(p.future());
									updateCtpStatus(vctp.getId(), vctp.getConnType(), status, opp[0].toString(), p);
								}
								CompositeFuture.all(fCtpStatus).map((Void) null).onComplete(pUpdateCtp);
							} else {
								pUpdateCtp.fail(ar.cause());
							}		
						});

						CompositeFuture.all(fLtp).map((Void) null).onComplete(done -> {
							if (done.succeeded()) {
								commitTxnAndUnlock(Entity.LTP, opp[0]).onComplete(resultHandler);
							} else {
								rollbackAndUnlock();
								resultHandler.handle(Future.failedFuture(done.cause()));
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

	@Override
	public TopologyService updateCtpStatus(int id, ConnTypeEnum type, StatusEnum status, String op, Handler<AsyncResult<Void>> resultHandler) {
		UUID opp[] = { getUUID(op) };
		beginTxnAndLock(Entity.CTP, opp[0], InternalSql.LOCK_TABLES).onComplete(tx -> {
			if (tx.succeeded()) {
				Promise<ConnTypeEnum> pType = Promise.promise();
				if (type == null) {
					JsonArray ctpId = new JsonArray().add(id);
					globalRetrieveOne(ctpId, InternalSql.GET_CTP_TYPE).map(option -> option.orElse(null)).onComplete(ar -> {
						if (ar.succeeded()) {
							if (ar.result() != null) {
								pType.complete(ConnTypeEnum.valueOf(ar.result().getString("connType")));
							} else {
								pType.fail("CTP type not found");
							}
						} else {
							pType.fail(ar.cause());
						}
					});
				} else {
					pType.complete(type);
				}
				pType.future().onComplete(go -> {
					if (go.succeeded()) {
						ConnTypeEnum ctpType = go.result();
						JsonArray params = new JsonArray().add(status.getValue()).add(id);
						globalExecute(params, InternalSql.UPDATE_CTP_STATUS).onComplete(u -> {
							if (u.succeeded()) {
								List<Future> fCtp = new ArrayList<>();
						
								// Update LC or Connection status
								Promise<Void> pUpdate = Promise.promise();
								fCtp.add(pUpdate.future());
								params.remove(0);
								if (ctpType.equals(ConnTypeEnum.Ether)) {
									globalRetrieveOne(params, InternalSql.FETCH_LC_BY_CTP).map(option -> option.orElse(null)).onComplete(ar -> {
										if (ar.succeeded()) {
											if (ar.result() != null) {
												VlinkConn vlc = JSONUtils.json2Pojo(ar.result().encode(), VlinkConn.class);
												updateLcStatus(vlc.getId(), status, opp[0].toString(), pUpdate);
											} else {
												pUpdate.complete();
											}
										} else {
											pUpdate.fail(ar.cause());
										}
									});
								} else {
									globalRetrieveOne(params, InternalSql.FETCH_CONNECTION_BY_CTP).map(option -> option.orElse(null))
											.onComplete(ar -> {
										if (ar.succeeded()) {
											if (ar.result() != null) {
												Vconnection vcon = JSONUtils.json2Pojo(ar.result().encode(), Vconnection.class);
												updateConnectionStatus(vcon.getId(), status, opp[0].toString(), pUpdate);
											} else {
												pUpdate.complete();
											}
										} else {
											pUpdate.fail(ar.cause());
										}
									});
								}

								// Update CTPs status
								Promise<Void> pUpdateCtp = Promise.promise();
								fCtp.add(pUpdateCtp.future());
								globalRetrieveMany(params, ApiSql.FETCH_VCTPS_BY_VCTP).onComplete(ar -> {
									if (ar.succeeded()) {
										List<Future> fCtpStatus = new ArrayList<>();
										Vctp[] vctps = JSONUtils.json2Pojo(new JsonArray(ar.result()).encode(), Vctp[].class);
										for (Vctp vctp : vctps) {
											Promise<Void> p = Promise.promise();
											fCtpStatus.add(p.future());
											updateCtpStatus(vctp.getId(), vctp.getConnType(), status, opp[0].toString(), p);
										}
										CompositeFuture.all(fCtpStatus).map((Void) null).onComplete(pUpdateCtp);
									} else {
										pUpdateCtp.fail(ar.cause());
									}		
								});

								CompositeFuture.all(fCtp).map((Void) null).onComplete(done -> {
									if (done.succeeded()) {
										commitTxnAndUnlock(Entity.CTP, opp[0]).onComplete(resultHandler);
									} else {
										rollbackAndUnlock();
										resultHandler.handle(Future.failedFuture(done.cause()));
									}
								});
							} else {
								resultHandler.handle(Future.failedFuture(u.cause()));
							}
						});
					} else {
						resultHandler.handle(Future.failedFuture(go.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(tx.cause()));
			}
		});
		return this;
	}

	public TopologyService updateLinkStatus(int id, StatusEnum status, String op, Handler<AsyncResult<Void>> resultHandler) {
		UUID opp[] = { getUUID(op) };
		beginTxnAndLock(Entity.LINK, opp[0], InternalSql.LOCK_TABLES).onComplete(tx -> {
			if (tx.succeeded()) {
				Promise<String> linkStatus = Promise.promise();
				JsonArray params = new JsonArray().add(id);
				globalRetrieveMany(params, InternalSql.FETCH_LINK_LTP_STATUS).onComplete(ar -> {
					if (ar.succeeded()) {
						if (ar.result().size() == 2) {
							String s0 = ar.result().get(0).getString("status");
							String s1 = ar.result().get(1).getString("status");

							if (s0.equals("UP") && s1.equals("UP")) {
								linkStatus.complete("UP");
							} else {
								linkStatus.complete("DOWN");
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
						globalExecute(uParams, InternalSql.UPDATE_LINK_STATUS).onComplete(done -> {
							if (done.succeeded()) {
								commitTxnAndUnlock(Entity.LINK, opp[0]).onComplete(resultHandler);
							} else {
								rollbackAndUnlock();
								resultHandler.handle(Future.failedFuture(done.cause()));
							}
						});
					} else {
						resultHandler.handle(Future.failedFuture(res.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(tx.cause()));
			}
		});
		return this;
	}

	public TopologyService updateLcStatus(int id, StatusEnum status, String op, Handler<AsyncResult<Void>> resultHandler) {
		UUID opp[] = { getUUID(op) };
		beginTxnAndLock(Entity.LC, opp[0], InternalSql.LOCK_TABLES).onComplete(tx -> {
			if (tx.succeeded()) {
				Promise<String> lcStatus = Promise.promise();
				JsonArray params = new JsonArray().add(id);
				globalRetrieveMany(params, InternalSql.FETCH_LC_CTP_STATUS).onComplete(ar -> {
					if (ar.succeeded()) {
						if (ar.result().size() == 2) {
							String s0 = ar.result().get(0).getString("status");
							String s1 = ar.result().get(1).getString("status");

							if (s0.equals("UP") && s1.equals("UP")) {
								lcStatus.complete("UP");
							} else {
								lcStatus.complete("DOWN");
							}
						} else {
							lcStatus.fail("Failed to fetch CTPs by LC");
						}
					} else {
						lcStatus.fail(ar.cause());
					}
				});
				lcStatus.future().onComplete(res -> {
					if (res.succeeded()) {
						JsonArray uParams = new JsonArray().add(res.result()).add(id);
						globalExecute(uParams, InternalSql.UPDATE_LC_STATUS).onComplete(done -> {
							if (done.succeeded()) {
								commitTxnAndUnlock(Entity.LC, opp[0]).onComplete(resultHandler);
							} else {
								rollbackAndUnlock();
								resultHandler.handle(Future.failedFuture(done.cause()));
							}
						});
					} else {
						resultHandler.handle(Future.failedFuture(res.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(tx.cause()));
			}
		});
		return this;
	}

	public TopologyService updateConnectionStatus(int id, StatusEnum status, String op, Handler<AsyncResult<Void>> resultHandler) {
		UUID opp[] = { getUUID(op) };
		beginTxnAndLock(Entity.CONNECTION, opp[0], InternalSql.LOCK_TABLES).onComplete(tx -> {
			if (tx.succeeded()) {
				Promise<String> conStatus = Promise.promise();
				JsonArray params = new JsonArray().add(id);
				globalRetrieveMany(params, InternalSql.FETCH_CONNECTION_CTP_STATUS).onComplete(ar -> {
					if (ar.succeeded()) {
						if (ar.result().size() == 2) {
							String s0 = ar.result().get(0).getString("status");
							String s1 = ar.result().get(1).getString("status");

							if (s0.equals("UP") && s1.equals("UP")) {
								conStatus.complete("UP");
							} else {
								conStatus.complete("DOWN");
							}
						} else {
							conStatus.fail("Failed to fetch CTPs by Connection");
						}
					} else {
						conStatus.fail(ar.cause());
					}
				});
				conStatus.future().onComplete(res -> {
					if (res.succeeded()) {
						JsonArray uParams = new JsonArray().add(res.result()).add(id);
						globalExecute(uParams, InternalSql.UPDATE_CONNECTION_STATUS).onComplete(arr -> {
							if (arr.succeeded()) {
								// Generate routes (NDN)
								generateAllRoutes(opp[0], done -> {
									if (done.succeeded()) {
										commitTxnAndUnlock(Entity.CONNECTION, opp[0]).onComplete(resultHandler);
									} else {
										rollbackAndUnlock();
										resultHandler.handle(Future.failedFuture(done.cause()));
									}
								});
							} else {
								resultHandler.handle(Future.failedFuture(arr.cause()));
							}
						});
					} else {
						resultHandler.handle(Future.failedFuture(res.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(tx.cause()));
			}
		});
		return this;
	}

	private UUID getUUID(String op) {
		if (op == null) {
			return UUID.randomUUID();
		}
		try {
			return UUID.fromString(op);
		} catch (IllegalArgumentException e) {
			return UUID.randomUUID();
		}
	}

	private String validateAndConvertMAC(String str) {
		if (str == null) {
			return "";
		}
		// String regex = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
		String regex = "^([0-9A-Fa-f]{2}[:-])"
                       + "{5}([0-9A-Fa-f]{2})|"
                       + "([0-9a-fA-F]{4}\\."
                       + "[0-9a-fA-F]{4}\\."
                       + "[0-9a-fA-F]{4})$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if (m.matches()){
			String norm = str.replaceAll("[^a-fA-F0-9]", "");
			return norm.replaceAll("(.{2})", "$1"+":").substring(0,17);
		} else {
			return "";
		}
	}
}