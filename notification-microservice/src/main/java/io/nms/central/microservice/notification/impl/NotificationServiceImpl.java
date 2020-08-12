package io.nms.central.microservice.notification.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.nms.central.microservice.notification.NotificationService;
import io.nms.central.microservice.notification.model.Status;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/**
 *
 */
public class NotificationServiceImpl implements NotificationService {

	// private static final Logger logger = LoggerFactory.getLogger(FaultServiceImpl.class);

	private static final String COLL_STATUS = "status";
	// private static final String COLL_EVENTS = "event";
	// private static final String COLL_FAULTS = "fault";
	
	private Map<Integer, Long> healthTimers;

	private final MongoClient client;
	private final Vertx vertx;

	public NotificationServiceImpl(Vertx vertx, JsonObject config) {
		this.vertx = vertx;
		this.client = MongoClient.create(vertx, config);
		this.healthTimers = new HashMap<Integer, Long>();
	}

	@Override
	public void processStatus(Status status, Handler<AsyncResult<Void>> resultHandler) { 
		saveStatus(status, ar -> {
			if (ar.succeeded()) {
				sendStatusAwaitResult(status).onComplete(res -> {
					if (res.succeeded()) {
						resultHandler.handle(Future.succeededFuture(res.result()));
					} else {
						resultHandler.handle(Future.failedFuture(res.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
		
		// update health timers
		int resId = status.getResId();
		if (healthTimers.containsKey(resId)) {
			vertx.cancelTimer(healthTimers.get(resId));
			healthTimers.remove(resId);
		}
		
		long timerId = vertx.setTimer(TimeUnit.SECONDS.toMillis(60), new Handler<Long>() {
		    @Override
		    public void handle(Long aLong) {
		    	healthTimers.remove(resId);
		    	if (status.getStatus().equals("UP")) {
		    		setNodeStatus(resId, "DISCONN");
		    	} else {
		    		setNodeStatus(resId, "DOWN");
		    	}
		    }
		});
		healthTimers.put(resId, timerId);
	}
	
	private void setNodeStatus(int resId, String s) {
		Status status = new Status();
		status.setResId(resId);
		status.setResType("node");
		status.setStatus(s);
		status.setTimestamp("123456");
		status.setId("ffff");
		sendStatusAwaitResult(status);
		
		if (s.equals("DISCONN")) {
			long timerId = vertx.setTimer(TimeUnit.SECONDS.toMillis(120), new Handler<Long>() {
			    @Override
			    public void handle(Long aLong) {
			    	healthTimers.remove(resId);
			    	setNodeStatus(resId, "DOWN");
			    }
			});
			healthTimers.put(resId, timerId);
		}
	}

	@Override
	public void saveStatus(Status status, Handler<AsyncResult<Void>> resultHandler) {
		client.save(COLL_STATUS, new JsonObject().put("_id", status.getId())
				.put("resType", status.getResType())
				.put("resId", status.getResId())
				.put("status", status.getStatus())
				.put("timestamp", status.getTimestamp()),
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
	public void retrieveStatus(String statusId, Handler<AsyncResult<Status>> resultHandler) {
		JsonObject query = new JsonObject().put("_id", statusId);
		client.findOne(COLL_STATUS, query, null, ar -> {
			if (ar.succeeded()) {
				if (ar.result() == null) {
					resultHandler.handle(Future.succeededFuture());
				} else {
					Status status = new Status(ar.result().put("id", ar.result().getString("_id")));
					resultHandler.handle(Future.succeededFuture(status));
				}
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void removeStatus(String statusId, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject query = new JsonObject().put("_id", statusId);
		client.removeDocument(COLL_STATUS, query, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void removeAllStatus(Handler<AsyncResult<Void>> resultHandler) {
		client.removeDocuments(COLL_STATUS, null, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}




	/* ----------------------------- */
	private Future<Void> sendStatusAwaitResult(Status status) {
		Promise<Void> promise = Promise.promise();
		vertx.eventBus().request(NotificationService.STATUS_ADDRESS, status.toJson(), reply -> {
			if (reply.succeeded()) {
				promise.complete();
			} else {
				promise.fail(reply.cause());
			}
		});
		return promise.future();
	}
}
