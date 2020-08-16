package io.nms.central.microservice.configuration.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.nms.central.microservice.configuration.ConfigurationService;
import io.nms.central.microservice.configuration.model.ConfigFace;
import io.nms.central.microservice.configuration.model.ConfigObj;
import io.nms.central.microservice.configuration.model.ConfigRoute;
import io.nms.central.microservice.topology.model.Face;
import io.nms.central.microservice.topology.model.Route;
import io.nms.central.microservice.topology.model.Vnode;
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
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.mongo.UpdateOptions;


/* import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonPatch;
import javax.json.JsonReader; */

/**
 *
 */
public class ConfigurationServiceImpl implements ConfigurationService {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);
	
	private static final String COLL_CANDIDATE_CONFIG = "canconfig";
	private static final String COLL_RUNNING_CONFIG = "runconfig";
	
	private final MongoClient client;
	private final Vertx vertx;
	// private final ServiceDiscovery discovery;

	public ConfigurationServiceImpl(Vertx vertx, JsonObject config) {
		this.vertx = vertx;
		// this.discovery = discovery;
		this.client = MongoClient.create(vertx, config);
	}
	
	@Override
	public void initializePersistence(Handler<AsyncResult<List<Integer>>> resultHandler) {
		resultHandler.handle(Future.succeededFuture());
	}
	
	@Override
	public void getCandidateConfig(int nodeId, Handler<AsyncResult<ConfigObj>> resultHandler) {
		JsonObject query = new JsonObject().put("nodeId", nodeId);
		client.findOne(COLL_CANDIDATE_CONFIG, query, null, ar -> {
			if (ar.succeeded()) {
				if (ar.result() == null) {
					resultHandler.handle(Future.succeededFuture());
				} else {
					ConfigObj result = new ConfigObj(ar.result());
					resultHandler.handle(Future.succeededFuture(result));
				}
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	@Override
	public void removeCandidateConfig(int nodeId, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject query = new JsonObject().put("nodeId", nodeId);
		client.removeDocuments(COLL_CANDIDATE_CONFIG, query, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	

	@Override
	public void upsertRunningConfig(int nodeId, ConfigObj config, Handler<AsyncResult<Void>> resultHandler) {
		config.setNodeId(nodeId);
		JsonObject query = new JsonObject().put("nodeId", nodeId);
		UpdateOptions opts = new UpdateOptions().setUpsert(true);
		JsonObject upd = new JsonObject().put("$set", config.toJson());
		client.updateCollectionWithOptions(COLL_RUNNING_CONFIG, query, upd, opts, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	@Override
	public void updateRunningConfig(int nodeId, JsonArray patch, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject query = new JsonObject().put("nodeId", nodeId);
		JsonObject fields = new JsonObject().put("_id", 0);
		client.findOne(COLL_RUNNING_CONFIG, query, fields, ar -> {
			if (ar.succeeded()) {
				JsonObject jConfig;
				if (ar.result() == null) {
					ConfigObj cg = new ConfigObj();
					cg.setNodeId(nodeId);
					jConfig = cg.toJson();
				} else {
					jConfig = ar.result();
				}
				try {
					JsonObject uDoc = computePatched(jConfig, patch);
					uDoc.put("nodeId", nodeId);
					client.replaceDocuments(COLL_RUNNING_CONFIG, query, uDoc, done -> {
						if (done.succeeded()) {
							resultHandler.handle(Future.succeededFuture());
						} else {
							resultHandler.handle(Future.failedFuture(ar.cause()));
						}
					});
				} catch (IllegalArgumentException e) {
					resultHandler.handle(Future.failedFuture(e.getMessage()));
				}
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	@Override
	public void getRunningConfig(int nodeId, Handler<AsyncResult<ConfigObj>> resultHandler) {
		JsonObject query = new JsonObject().put("nodeId", nodeId);
		client.findOne(COLL_RUNNING_CONFIG, query, null, ar -> {
			if (ar.succeeded()) {
				if (ar.result() == null) {
					resultHandler.handle(Future.succeededFuture());
				} else {
					ConfigObj result = new ConfigObj(ar.result());
					resultHandler.handle(Future.succeededFuture(result));
				}
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	@Override
	public void removeRunningConfig(int nodeId, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject query = new JsonObject().put("nodeId", nodeId);
		client.removeDocuments(COLL_RUNNING_CONFIG, query, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	
	
	/* processing */
	@Override
	public void computeConfigurations(List<Route> routes, List<Face> faces, List<Vnode> nodes, 
			Handler<AsyncResult<List<ConfigObj>>> resultHandler) {
		
		// Timestamp ts = new Timestamp(new Date().getTime());
		Map<Integer,ConfigObj> configsMap = new HashMap<Integer,ConfigObj>();
		for (Vnode node : nodes) {
				ConfigObj c = new ConfigObj();
				// c.setTimestamp(ts.toString());
				c.setNodeId(node.getId());
				configsMap.put(node.getId(), c);
		}
		
		for (Face face : faces) {
			int nodeId = face.getVnodeId();
			if (!face.getStatus().equals("DOWN")) {
				ConfigFace cFace = new ConfigFace();
				cFace.setId(face.getId());
				cFace.setLocal(face.getLocal());
				cFace.setRemote(face.getRemote());
				cFace.setScheme(face.getScheme());
				configsMap.get(nodeId).getConfig().addFace(cFace);
			}
		}
		// TODO: in routing => cond: not DOWN
		for (Route route : routes) {
			int nodeId = route.getNodeId();
			ConfigRoute cRoute = new ConfigRoute();
			cRoute.setPrefix(route.getPrefix());
			cRoute.setNextHop(route.getFaceId());
			cRoute.setCost(route.getCost());
			cRoute.setOrigin(route.getOrigin());
			configsMap.get(nodeId).getConfig().addRoute(cRoute);
		}
		List<ConfigObj> config = new ArrayList<ConfigObj>(configsMap.values());
		resultHandler.handle(Future.succeededFuture(config));
	}
	@Override
	public void upsertCandidateConfigs(List<ConfigObj> configs, Handler<AsyncResult<Void>> resultHandler) {
		List<Future> fts = new ArrayList<Future>();
		for (ConfigObj cg : configs) {
			JsonObject query = new JsonObject().put("nodeId", cg.getNodeId());
			JsonObject fields = new JsonObject().put("_id", 0);
			client.findOne(COLL_CANDIDATE_CONFIG, query, fields, ar -> {
				if (ar.succeeded()) {
					if (ar.result() != null) {
						ConfigObj currConfig = new ConfigObj(ar.result());
						if (!currConfig.equals(cg)) {
							Promise<MongoClientUpdateResult> p = Promise.promise();
							fts.add(p.future());
							client.replaceDocuments(COLL_CANDIDATE_CONFIG, query, cg.toJson(), p);
						}
					} else {
						Promise<String> p = Promise.promise();
						fts.add(p.future());
						client.save(COLL_CANDIDATE_CONFIG, cg.toJson(), p);
					}
				} else {
					resultHandler.handle(Future.failedFuture(ar.cause()));
				}
			});
		}
		CompositeFuture.all(fts).map((Void) null).onComplete(resultHandler);
	}

	
	
	private JsonObject computePatched(JsonObject origin, JsonArray patch) throws IllegalArgumentException {
		// TODO: filter for add, remove, replace
		String sPatched = rawPatch(origin.encode(), patch.encode());
		return new JsonObject(sPatched);
	}
	
	private String rawPatch(String sOrigin, String sPatch) throws IllegalArgumentException {
		try {
			javax.json.JsonReader origReader = javax.json.Json.createReader(new ByteArrayInputStream(sOrigin.getBytes()));
			javax.json.JsonObject origin = origReader.readObject();
			
			javax.json.JsonReader patchReader = javax.json.Json.createReader(new ByteArrayInputStream(sPatch.getBytes()));
			javax.json.JsonPatch patch = javax.json.Json.createPatch(patchReader.readArray());
		
			patchReader.close();
			origReader.close();
		
			javax.json.JsonObject patched = patch.apply(origin);
			
			return patched.toString();
		} catch (javax.json.JsonException e) {
			throw new IllegalArgumentException("Error in json patch");
		}
	}
}

