package io.nms.central.microservice.configuration.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.nms.central.microservice.common.Status;
import io.nms.central.microservice.common.service.JdbcRepositoryWrapper;
import io.nms.central.microservice.configuration.ConfigurationService;
import io.nms.central.microservice.configuration.model.ConfigObj;
import io.nms.central.microservice.configuration.model.ConfigFace;
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
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.mongo.UpdateOptions;
import io.vertx.servicediscovery.ServiceDiscovery;

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
	
	/* Rest API */
	@Override
	public void saveCandidateConfig(ConfigObj config, Handler<AsyncResult<Void>> resultHandler) {
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
	public void removeAllCandidateConfigs(Handler<AsyncResult<Void>> resultHandler) {
		client.removeDocuments(COLL_CANDIDATE_CONFIG, new JsonObject(), ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void updateRunningConfig(String nodeId, JsonObject diff, Handler<AsyncResult<Void>> resultHandler) {
		resultHandler.handle(Future.succeededFuture());
	}
	
	
	
	/* processing */
	@Override
	public void computeConfigurations(List<Route> routes, List<Face> faces, List<Vnode> nodes, 
			Handler<AsyncResult<List<ConfigObj>>> resultHandler) {
		
		Map<Integer,ConfigObj> configsMap = new HashMap<Integer,ConfigObj>();
		for (Vnode node : nodes) {
			if (!node.getStatus().equals("DISCONN")) {
				ConfigObj c = new ConfigObj();
				c.setNodeId(node.getId());
				configsMap.put(node.getId(), c);
			}
		}
		
		for (Face face : faces) {
			int nodeId = face.getVnodeId();
			
			if (face.getStatus().equals("UP")) {
				ConfigFace cFace = new ConfigFace();
				cFace.setId(face.getId());
				cFace.setLocal(face.getLocal());
				cFace.setRemote(face.getRemote());
				cFace.setScheme(face.getScheme());
				configsMap.get(nodeId).getConfig().addFace(cFace);
			}
		}
		
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
			logger.info("******CONFIG******: " + cg.toString());
			JsonObject query = new JsonObject().put("nodeId", cg.getNodeId());
			JsonObject fields = new JsonObject().put("_id", 0);
			client.findOne(COLL_CANDIDATE_CONFIG, query, fields, ar -> {
				if (ar.succeeded()) {
					if (ar.result() != null) {
						ConfigObj currConfig = new ConfigObj(ar.result());
						if (!currConfig.equals(cg)) {
							cg.setVersion(currConfig.getVersion() + 1);
							Promise<MongoClientUpdateResult> p = Promise.promise();
							fts.add(p.future());
							client.replaceDocuments(COLL_CANDIDATE_CONFIG, query, cg.toJson(), p);
						}
					} else {
						cg.setVersion(1);
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
}




/* JsonObject doc = new JsonObject().put("$set", cg.toJson());
UpdateOptions opts = new UpdateOptions().setUpsert(true);
client.updateCollectionWithOptions(COLL_CANDIDATE_CONFIG, query, doc, opts, pSave); */





