package io.nms.central.microservice.notification.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.nms.central.microservice.common.service.JdbcRepositoryWrapper;
import io.nms.central.microservice.notification.NotificationService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.servicediscovery.ServiceDiscovery;

/**
 *
 */
public class NotificationServiceImpl implements NotificationService {

	// private static final Logger logger = LoggerFactory.getLogger(FaultServiceImpl.class);

	private static final String COLL_NOTIFICATION = "notification";

	  private final MongoClient client;
	  private final Vertx vertx;
	  private final ServiceDiscovery discovery;

	  public NotificationServiceImpl(Vertx vertx, ServiceDiscovery discovery, JsonObject config) {
		  this.vertx = vertx;
		 this.discovery = discovery;
	    this.client = MongoClient.create(vertx, config);
	  }
	  
	  public void processReport(JsonObject report, Handler<AsyncResult<JsonObject>> resultHandler) {		  
		  String type = report.getString("type");
			int id = report.getInteger("id", 0);
			String name = report.getString("name", "");
			String status = report.getString("status");
		    if (id == 0 && name.isEmpty()) {
		      resultHandler.handle(Future.failedFuture(new IllegalStateException("Id/name missing")));
		    } else {
		    	// TODO: store Report?
		    	sendReportAwaitResult(report).onComplete(ar -> {
		    		if (ar.succeeded()) {
			    		publishUpdate();
			    		resultHandler.handle(Future.succeededFuture(ar.result()));
		    		} else {
		    			resultHandler.handle(Future.failedFuture(ar.cause()));
		    		}
		    	});
		    }
	  }
	  
	  private Future<JsonObject> sendReportAwaitResult(JsonObject report) {
		  Promise<JsonObject> promise = Promise.promise();
		    vertx.eventBus().request(NotificationService.REPORTS_ADDRESS, report, reply -> {
		      if (reply.succeeded()) {
		        promise.complete((JsonObject) reply.result().body());
		      } else {
		        promise.fail(reply.cause());
		      }
		    });
		    return promise.future();
	  }

	  private void publishUpdate() {
		  // Promise<JsonObject> promise = Promise.promise();
		    vertx.eventBus().publish(NotificationService.UPDATE_ADDRESS, new JsonObject());
		  // return promise.future();
	  }

	  /* @Override
	  public void saveStore(Store store, Handler<AsyncResult<Void>> resultHandler) {
	    client.save(COLLECTION, new JsonObject().put("_id", store.getSellerId())
	        .put("name", store.getName())
	        .put("description", store.getDescription())
	        .put("openTime", store.getOpenTime()),
	      ar -> {
	        if (ar.succeeded()) {
	          resultHandler.handle(Future.succeededFuture());
	        } else {
	          resultHandler.handle(Future.failedFuture(ar.cause()));
	        }
	      }
	    );
	  }

	  @Override
	  public void retrieveStore(String sellerId, Handler<AsyncResult<Store>> resultHandler) {
	    JsonObject query = new JsonObject().put("_id", sellerId);
	    client.findOne(COLLECTION, query, null, ar -> {
	      if (ar.succeeded()) {
	        if (ar.result() == null) {
	          resultHandler.handle(Future.succeededFuture());
	        } else {
	          Store store = new Store(ar.result().put("sellerId", ar.result().getString("_id")));
	          resultHandler.handle(Future.succeededFuture(store));
	        }
	      } else {
	        resultHandler.handle(Future.failedFuture(ar.cause()));
	      }
	    });
	  }

	  @Override
	  public void removeStore(String sellerId, Handler<AsyncResult<Void>> resultHandler) {
	    JsonObject query = new JsonObject().put("_id", sellerId);
	    client.removeDocument(COLLECTION, query, ar -> {
	      if (ar.succeeded()) {
	        resultHandler.handle(Future.succeededFuture());
	      } else {
	        resultHandler.handle(Future.failedFuture(ar.cause()));
	      }
	    });
	  } */
}
