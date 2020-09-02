package io.nms.central.microservice.notification.impl;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.nms.central.microservice.notification.NotificationService;
import io.nms.central.microservice.notification.model.Event;
import io.nms.central.microservice.notification.model.Fault;
import io.nms.central.microservice.notification.model.Status;
import io.nms.central.microservice.notification.model.Status.StatusEnum;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;

/**
 *
 */
public class NotificationServiceImpl implements NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	private static final String COLL_STATUS = "status";
	private static final String COLL_EVENT = "event";
	private static final String COLL_FAULT = "fault";
	
	private static final int TIMER_TO_DISCONN_SEC = 2 * 60;
	private static final int TIMER_TO_DOWN_SEC = 5 * 60;
	
	private Map<Integer, Long> healthTimers;

	private final MongoClient client;
	private final Vertx vertx;

	public NotificationServiceImpl(Vertx vertx, JsonObject config) {
		this.vertx = vertx;
		this.client = MongoClient.create(vertx, config);
		this.healthTimers = new HashMap<Integer, Long>();
	}
	
	/* -------------- Status --------------- */
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
		
		long timerId = vertx.setTimer(TimeUnit.SECONDS.toMillis(TIMER_TO_DISCONN_SEC), new Handler<Long>() {
		    @Override
		    public void handle(Long aLong) {
		    	healthTimers.remove(resId);
		    	if (status.getStatus().equals(StatusEnum.UP)) {
		    		setNodeStatus(resId, StatusEnum.DISCONN);
		    	} else {
		    		setNodeStatus(resId, StatusEnum.DOWN);
		    	}
		    }
		});
		healthTimers.put(resId, timerId);
	}
	
	@Override
	public void saveStatus(Status status, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject jStatus = new JsonObject()
				.put("_id", status.getId())
				.put("resType", status.getResType())
				.put("resId", status.getResId())
				.put("status", status.getStatus().getValue())
				.put("timestamp", status.getTimestamp().toString());
		client.save(COLL_STATUS, jStatus, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void retrieveAllStatus(Handler<AsyncResult<List<Status>>> resultHandler) {
		client.find(COLL_STATUS, new JsonObject(), ar -> {
			if (ar.succeeded()) {
				if (ar.result() == null) {
					resultHandler.handle(Future.succeededFuture());
				} else {
					List<Status> result = ar.result().stream()
							.map(raw -> {
								raw.put("id", raw.getString("_id"));
								raw.remove("_id");
								Status status = Json.decodeValue(raw.encode(), Status.class);
								return status;
							})
						.collect(Collectors.toList());
					resultHandler.handle(Future.succeededFuture(result));
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

	/* ----------------------------- */
	private void setNodeStatus(int resId, StatusEnum se) {
		Status status = new Status();
		status.setResId(resId);
		status.setResType("node");
		status.setStatus(se);
		status.setTimestamp(OffsetDateTime.now());
		status.setId(String.valueOf(status.hashCode()));
		sendStatusAwaitResult(status);
		
		if (se.equals(StatusEnum.DISCONN)) {
			long timerId = vertx.setTimer(TimeUnit.SECONDS.toMillis(TIMER_TO_DOWN_SEC), new Handler<Long>() {
			    @Override
			    public void handle(Long aLong) {
			    	healthTimers.remove(resId);
			    	setNodeStatus(resId, StatusEnum.DOWN);
			    }
			});
			healthTimers.put(resId, timerId);
		}
	}
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
	
	
	
	/* -------------- Event --------------- */
	@Override
	public void saveEvent(Event event, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject jEvent = new JsonObject()
				.put("_id", event.getId())
				.put("origin", event.getOrigin())
				.put("severity", event.getSeverity().getValue())
				.put("code", event.getCode())
				.put("msg", event.getMsg())
				.put("timestamp", event.getTimestamp().toString());
		client.save(COLL_EVENT, jEvent, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void retrieveAllEvents(Handler<AsyncResult<List<Event>>> resultHandler) {
		client.find(COLL_EVENT, new JsonObject(), ar -> {
			if (ar.succeeded()) {
				if (ar.result() == null) {
					resultHandler.handle(Future.succeededFuture());
				} else {
					List<Event> result = ar.result().stream()
							.map(raw -> {
								raw.put("id", raw.getString("_id"));
								raw.remove("_id");
								Event event = Json.decodeValue(raw.encode(), Event.class);
								return event;
							})
						.collect(Collectors.toList());
					resultHandler.handle(Future.succeededFuture(result));
				}
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void removeEvent(String id, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject query = new JsonObject().put("_id", id);
		client.removeDocument(COLL_EVENT, query, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	/* -------------- Event --------------- */
	@Override
	public void saveFault(Fault fault, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject jFault = new JsonObject()
				.put("_id", fault.getId())
				.put("origin", fault.getOrigin())
				.put("code", fault.getCode())
				.put("msg", fault.getMsg())
				.put("timestamp", fault.getTimestamp().toString());
		client.save(COLL_FAULT, jFault, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void retrieveAllFaults(Handler<AsyncResult<List<Fault>>> resultHandler) {
		client.find(COLL_FAULT, new JsonObject(), ar -> {
			if (ar.succeeded()) {
				if (ar.result() == null) {
					resultHandler.handle(Future.succeededFuture());
				} else {
					List<Fault> result = ar.result().stream()
							.map(raw -> {
								raw.put("id", raw.getString("_id"));
								raw.remove("_id");
								Fault fault = Json.decodeValue(raw.encode(), Fault.class);
								return fault;
							})
						.collect(Collectors.toList());
					resultHandler.handle(Future.succeededFuture(result));
				}
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void removeFault(String id, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject query = new JsonObject().put("_id", id);
		client.removeDocument(COLL_FAULT, query, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
}
