package io.nms.central.microservice.telemetry.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import io.nms.central.microservice.telemetry.TelemetryService;
import io.nms.central.microservice.telemetry.model.Capability;
import io.nms.central.microservice.telemetry.model.Receipt;
import io.nms.central.microservice.telemetry.model.Result;
import io.nms.central.microservice.telemetry.model.Specification;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;

/**
 *  This verticle implements the telemetry service.
 *
 * @author Amar Abane
 */
public class TelemetryServiceImpl implements TelemetryService {
	
	private static final Logger logger = LoggerFactory.getLogger(TelemetryServiceImpl.class);

	private static final String CAPABILITIES = "capabilities";
	private static final String SPECIFICATIONS = "specifications";
	private static final String RECEIPTS = "receipts";
	private static final String RESULTS = "results";
	
	private final MongoClient client;
	private final Vertx vertx;

	public TelemetryServiceImpl(Vertx vertx, JsonObject config) {
		this.vertx = vertx;
		this.client = MongoClient.create(vertx, config);
		
		/* this.client.removeDocuments(SPECIFICATIONS, new JsonObject(), ar -> {
			return;
		});
		this.client.removeDocuments(RECEIPTS, new JsonObject(), ar -> {
			return;
		});
		this.client.removeDocuments(RESULTS, new JsonObject(), ar -> {
			return;
		}); */
	}

	/* ------------ capability ------------ */
	@Override
	public void saveCapability(Capability cap, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject doc = cap.toJson();
		if (doc == null) {
			resultHandler.handle(Future.failedFuture("error while encoding capability"));
			return;
		}

		doc.put("_id", cap.getSchema());			

		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		String nowAsISO = df.format(new Date());
		doc.put("_ts", nowAsISO);
		
		client.save(CAPABILITIES, doc, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void getCapabilitiesByRole(String role, Handler<AsyncResult<List<Capability>>> resultHandler) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		
		JsonObject query = new JsonObject()
			.put("role", role)
			.put("_ts", new JsonObject().put("$gte", df.format(new Date().getTime() - 60000)));
		
		JsonObject fields = new JsonObject().put("_id", 0).put("_ts", 0);
		
		client.findWithOptions(CAPABILITIES, query, new FindOptions().setFields(fields), res -> {
			if (res.succeeded()) {
				List<Capability> result = res.result().stream()
						.map(raw -> {
							Capability cap = Json.decodeValue(raw.encode(), Capability.class);
							return cap;
						})
						.collect(Collectors.toList());
				resultHandler.handle(Future.succeededFuture(result));
			} else {
				resultHandler.handle(Future.failedFuture(res.cause()));
			}
		});
	}

	/* ------------ specification ------------ */
	@Override
	public void saveSpecification(Specification spec, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject doc = spec.toJson();
		if (doc == null) {
			resultHandler.handle(Future.failedFuture("error while encoding specification"));
			return;
		}

		doc.put("_id", spec.getSchema());			

		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		String nowAsISO = df.format(new Date());
		doc.put("_ts", nowAsISO);
		client.save(SPECIFICATIONS, doc, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void getAllSpecifications(Handler<AsyncResult<List<Specification>>> resultHandler) {
		JsonObject fields = new JsonObject().put("_id", 0).put("_ts", 0);
		client.findWithOptions(SPECIFICATIONS, new JsonObject(), new FindOptions().setFields(fields), res -> {
			if (res.succeeded()) {
				List<Specification> result = res.result().stream()
							.map(raw -> {
								Specification spec = Json.decodeValue(raw.encode(), Specification.class);
								return spec;
							})
						.collect(Collectors.toList());
					resultHandler.handle(Future.succeededFuture(result));
			} else {
				resultHandler.handle(Future.failedFuture(res.cause()));
			}
		});
	}

	@Override
	public void removeSpecification(String id, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject query = new JsonObject().put("_id", id);
		client.removeDocument(SPECIFICATIONS, query, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	
	/* ------------ receipt ------------ */
	@Override
	public void saveReceipt(Receipt rct, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject doc = rct.toJson();
		if (doc == null) {
			resultHandler.handle(Future.failedFuture("error while encoding receipt"));
			return;
		}

		doc.put("_id", rct.getSchema());			

		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		String nowAsISO = df.format(new Date());
		doc.put("_ts", nowAsISO);
		client.save(RECEIPTS, doc, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void getAllReceipts(Handler<AsyncResult<List<Receipt>>> resultHandler) {
		JsonObject fields = new JsonObject().put("_id", 0).put("_ts", 0);
		client.findWithOptions(RECEIPTS, new JsonObject(), new FindOptions().setFields(fields), res -> {
			if (res.succeeded()) {
				List<Receipt> result = res.result().stream()
							.map(raw -> {
								Receipt rct = Json.decodeValue(raw.encode(), Receipt.class);
								return rct;
							})
						.collect(Collectors.toList());
				resultHandler.handle(Future.succeededFuture(result));
			} else {
				resultHandler.handle(Future.failedFuture(res.cause()));
			}
		});
	}

	@Override
	public void removeReceipt(String id, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject query = new JsonObject().put("_id", id);
		client.removeDocument(RECEIPTS, query, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	
	/* ------------ result ------------ */
	@Override
	public void saveResult(Result res, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject doc = res.toJson();
		if (doc == null) {
			resultHandler.handle(Future.failedFuture("error while encoding result"));
			return;
		}

		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		String nowAsISO = df.format(new Date());
		doc.put("_ts", nowAsISO);
		client.save(RESULTS, doc, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	
	@Override
	public void getResult(String id, Handler<AsyncResult<Result>> resultHandler) {
		JsonObject query = new JsonObject().put("_id", id);
		JsonObject fields = new JsonObject().put("_id", 0).put("_ts", 0);
		client.findOne(RESULTS, query, fields, res -> {
			if (res.succeeded()) {
				if (res.result() == null) {
					resultHandler.handle(Future.succeededFuture());
				} else {
					JsonObject raw = res.result();
					raw.put("id", id);
					Result rs = Json.decodeValue(res.result().encode(), Result.class);
					resultHandler.handle(Future.succeededFuture(rs));
				}
			} else {
				resultHandler.handle(Future.failedFuture(res.cause()));
			}
		});
	}

	@Override
	public void getAllResultOperations(String type, Handler<AsyncResult<JsonArray>> resultHandler) {
		JsonObject match = new JsonObject()
				.put("result", type);
			JsonObject group = new JsonObject()
				.put("_id", "$schema")
				.put("endpoint", new JsonObject().put("$first", "$endpoint"))
				.put("name", new JsonObject().put("$first", "$name"))
				.put("begins", new JsonObject().put("$min", "$timestamp"))
				.put("ends", new JsonObject().put("$max", "$timestamp"))
				.put("total", new JsonObject().put("$sum", 1));
			
			JsonObject command = new JsonObject()
				.put("aggregate", RESULTS)
				.put("pipeline", new JsonArray()
						.add(new JsonObject().put("$match", match))
						.add(new JsonObject().put("$group", group)))
				.put("cursor", new JsonObject());
							
			client.runCommand("aggregate", command, res -> {
				if (res.succeeded()) {				
					if (res.result().getInteger("ok") == 1) {
						JsonArray results = res.result().getJsonObject("cursor").getJsonArray("firstBatch");
						resultHandler.handle(Future.succeededFuture(results));
					} else {
						resultHandler.handle(Future.succeededFuture());
					}
				} else {
					resultHandler.handle(Future.failedFuture(res.cause()));
				}
			});
	}

	@Override
	public void getResultsByOperation(String op, Handler<AsyncResult<List<Result>>> resultHandler) {
		JsonObject query = new JsonObject().put("schema", op);
		client.find(RESULTS, query, res -> {
			if (res.succeeded()) {
				List<Result> result = res.result().stream()
						.map(raw -> {
							Result rs = Json.decodeValue(raw.encode(), Result.class);
							rs.setId(raw.getString("_id"));
							return rs;
						})
					.collect(Collectors.toList());
				resultHandler.handle(Future.succeededFuture(result));
			} else {
				resultHandler.handle(Future.failedFuture(res.cause()));
			}
		});
	}

	@Override
	public void removeResultsByOperation(String op, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject query = new JsonObject().put("schema", op);
		client.removeDocuments(RESULTS, query, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
}
