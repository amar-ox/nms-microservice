package io.nms.central.microservice.common.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;

/**
 * Helper and wrapper class for JDBC repository services.
 */
public class JdbcRepositoryWrapper {

	private static final Logger logger = LoggerFactory.getLogger(JdbcRepositoryWrapper.class);
	protected final JDBCClient client;
	private SQLConnection globalConn = null;
	protected enum Entity {
		NONE,
	    NODE,
		LTP,
		CTP,
	    LINK,
		LC,
		CONNECTION,
	    ROUTE,
	    PA
	}
	private Entity currEntity = Entity.NONE;
	private UUID currUUID = null;
	private int counter = 0;

	public JdbcRepositoryWrapper(Vertx vertx, JsonObject config) {
		this.client = JDBCClient.create(vertx, config);
	}

	/**
	 * Suitable for `add`, `exists` operation.
	 *
	 * @param params        query params
	 * @param sql           sql
	 * @param resultHandler async result handler
	 */
	protected void executeNoResult(JsonArray params, String sql, Handler<AsyncResult<Void>> resultHandler) {
		client.getConnection(connHandler(resultHandler, connection -> {
			connection.updateWithParams(sql, params, r -> {
				if (r.succeeded()) {
					resultHandler.handle(Future.succeededFuture());
				} else {
					resultHandler.handle(Future.failedFuture(r.cause()));
				}
				connection.close();
			});
		}));
	}
	
	protected void insertAndGetId(JsonArray params, String sql, Handler<AsyncResult<Integer>> resultHandler) {
		client.getConnection(connHandler(resultHandler, connection -> {
			connection.updateWithParams(sql, params, r -> {
				if (r.succeeded()) {
					UpdateResult updateResult = r.result();
					if (updateResult.getUpdated() == 1) {
						resultHandler.handle(Future.succeededFuture(updateResult.getKeys().getInteger(0)));
					} else {
						resultHandler.handle(Future.failedFuture("Not inserted"));
					}
				} else {
					resultHandler.handle(Future.failedFuture(r.cause()));
				}
				connection.close();
			});
		}));
	}
	
	// update or insert ONE row and return Id
	protected void upsert(JsonArray params, String sql, Handler<AsyncResult<Integer>> resultHandler) {
		client.getConnection(connHandler(resultHandler, connection -> {
			connection.updateWithParams(sql, params, r -> {
				if (r.succeeded()) {
					UpdateResult updateResult = r.result();
					Integer id = 0;
					if (updateResult.getUpdated() == 1) {
						id = updateResult.getKeys().getInteger(0);
					}
					resultHandler.handle(Future.succeededFuture(id));
				} else {
					resultHandler.handle(Future.failedFuture(r.cause()));
				}
				connection.close();
			});
		}));
	}
	
	/* execute and return the number of updated elements */
	protected void update(JsonArray params, String sql, Handler<AsyncResult<Integer>> resultHandler) {
		client.getConnection(connHandler(resultHandler, connection -> {
			connection.updateWithParams(sql, params, r -> {
				if (r.succeeded()) {
					UpdateResult updateResult = r.result();
					resultHandler.handle(Future.succeededFuture(updateResult.getUpdated()));
				} else {
					resultHandler.handle(Future.failedFuture(r.cause()));
				}
				connection.close();
			});
		}));
	}

	protected <K> Future<Optional<JsonObject>> retrieveOne(K param, String sql) {
		return getConnection()
				.compose(connection -> {
					Promise<Optional<JsonObject>> promise = Promise.promise();
					connection.queryWithParams(sql, new JsonArray().add(param), r -> {
						if (r.succeeded()) {
							List<JsonObject> resList = r.result().getRows();
							if (resList == null || resList.isEmpty()) {
								promise.complete(Optional.empty());
							} else {
								promise.complete(Optional.of(resList.get(0)));
							}
						} else {
							promise.fail(r.cause());
						}
						connection.close();
					});
					return promise.future();
				});
	}
	
	protected Future<Optional<JsonObject>> retrieveOne(JsonArray params, String sql) {
		return getConnection()
				.compose(connection -> {
					Promise<Optional<JsonObject>> promise = Promise.promise();
					connection.queryWithParams(sql, params, r -> {
						if (r.succeeded()) {
							List<JsonObject> resList = r.result().getRows();
							if (resList == null || resList.isEmpty()) {
								promise.complete(Optional.empty());
							} else {
								promise.complete(Optional.of(resList.get(0)));
							}
						} else {
							promise.fail(r.cause());
						}
						connection.close();
					});
					return promise.future();
				});
	}
	
	protected <K> Future<Optional<List<JsonObject>>> retrieveOneNested(K param, String sql) {
		return getConnection().compose(connection -> {
			Promise<Optional<List<JsonObject>>> promise = Promise.promise();
			connection.queryWithParams(sql, new JsonArray().add(param), r -> {
				if (r.succeeded()) {
					List<JsonObject> resList = r.result().getRows();
					if (resList == null || resList.isEmpty()) {
						promise.complete(Optional.empty());
					} else {
						promise.complete(Optional.of(resList));
					}					
				} else {
					promise.fail(r.cause());
				}
				connection.close();
			});
			return promise.future();
		});
	}

	
	protected Future<List<JsonObject>> retrieveMany(JsonArray param, String sql) {
		return getConnection().compose(connection -> {
			Promise<List<JsonObject>> promise = Promise.promise();
			connection.queryWithParams(sql, param, r -> {
				if (r.succeeded()) {
					promise.complete(r.result().getRows());
				} else {
					promise.fail(r.cause());
				}
				connection.close();
			});
			return promise.future();
		});
	}

	protected Future<List<JsonObject>> retrieveAll(String sql) {
		return getConnection().compose(connection -> {
			Promise<List<JsonObject>> promise = Promise.promise();
			connection.query(sql, r -> {
				if (r.succeeded()) {
					promise.complete(r.result().getRows());
				} else {
					promise.fail(r.cause());
				}
				connection.close();
			});
			return promise.future();
		});
	}

	protected <K> void removeOne(K id, String sql, Handler<AsyncResult<Void>> resultHandler) {
		client.getConnection(connHandler(resultHandler, connection -> {
			JsonArray params = new JsonArray().add(id);
			connection.updateWithParams(sql, params, r -> {
				if (r.succeeded()) {
					resultHandler.handle(Future.succeededFuture());
				} else {
					resultHandler.handle(Future.failedFuture(r.cause()));
				}
				connection.close();
			});
		}));
	}

	protected void removeAll(String sql, Handler<AsyncResult<Void>> resultHandler) {
		client.getConnection(connHandler(resultHandler, connection -> {
			connection.update(sql, r -> {
				if (r.succeeded()) {
					resultHandler.handle(Future.succeededFuture());
				} else {
					resultHandler.handle(Future.failedFuture(r.cause()));
				}
				connection.close();
			});
		}));
	}

	/**
	 * A helper methods that generates async handler for SQLConnection
	 *
	 * @return generated handler
	 */
	protected <R> Handler<AsyncResult<SQLConnection>> connHandler(Handler<AsyncResult<R>> h1, Handler<SQLConnection> h2) {
		return conn -> {
			if (conn.succeeded()) {
				final SQLConnection connection = conn.result();
				h2.handle(connection);
			} else {
				h1.handle(Future.failedFuture(conn.cause()));
			}
		};
	}

	protected Future<SQLConnection> getConnection() {
		Promise<SQLConnection> promise = Promise.promise();
		client.getConnection(promise);
		return promise.future();
	}
	
	/* --------------------------------- */
	protected Future<Void> beginTxnAndLock(Entity entity, UUID uuid, String sql) {
		Promise<Void> promise = Promise.promise();
		if ((currUUID != null) && uuid.equals(currUUID)) {
			counter++;
			promise.complete();
		} else {
			client.getConnection(ar -> {
				if (ar.succeeded()) {
					ar.result().setAutoCommit(false, res -> {
						if (res.succeeded()) {
							ar.result().execute(sql, p -> {
								if (p.succeeded()) {
									globalConn = ar.result();
									currUUID = uuid;
									currEntity = entity;
									counter = 0;
									promise.complete();
								} else {
									promise.fail(p.cause());
								}
							});
						} else {
							promise.fail(res.cause());
						}
					});
				} else {
					promise.fail(ar.cause());
				}
			});
		}
		return promise.future();
	}
	
	protected Future<Integer> globalInsert(JsonArray params, String sql) {
		Promise<Integer> promise = Promise.promise();
		globalConn.updateWithParams(sql, params, ar -> {
			if (ar.succeeded()) {
				UpdateResult updateResult = ar.result();
				if (updateResult.getUpdated() == 1) {
					promise.complete(updateResult.getKeys().getInteger(0));
				} else {
					rollbackAndUnlock();
					promise.fail("Not inserted");
				}					 
			} else {
				rollbackAndUnlock();
				promise.fail(ar.cause());
			}
		});
		return promise.future();
	}
	
	protected Future<Void> globalExecute(JsonArray params, String sql) {
		Promise<Void> promise = Promise.promise();
		globalConn.updateWithParams(sql, params, ar -> {
			if (ar.succeeded()) {
				promise.complete();					 
			} else {
				rollbackAndUnlock();
				promise.fail(ar.cause());
			}
		});
		return promise.future();
	}
	
	protected Future<Void> globalExecute(String sql) {
		Promise<Void> promise = Promise.promise();
		globalConn.update(sql, ar -> {
			if (ar.succeeded()) {
				promise.complete();					 
			} else {
				rollbackAndUnlock();
				promise.fail(ar.cause());
			}
		});
		return promise.future();
	}
	
	protected Future<List<JsonObject>> globalRetrieveAll(String sql) { 
		Promise<List<JsonObject>> promise = Promise.promise();
		globalConn.query(sql, r -> {
				if (r.succeeded()) {
					promise.complete(r.result().getRows());
				} else {
					rollbackAndUnlock();
					promise.fail(r.cause());
				}
			});
		return promise.future();
	}
	
	protected Future<List<JsonObject>> globalRetrieveMany(JsonArray params, String sql) { 
		Promise<List<JsonObject>> promise = Promise.promise();
		globalConn.queryWithParams(sql, params, r -> {
				if (r.succeeded()) {
					promise.complete(r.result().getRows());
				} else {
					rollbackAndUnlock();
					promise.fail(r.cause());
				}
			});
		return promise.future();
	}
	
	protected Future<Optional<JsonObject>> globalRetrieveOne(JsonArray params, String sql) {
		Promise<Optional<JsonObject>> promise = Promise.promise();
		globalConn.queryWithParams(sql, params, r -> {
			if (r.succeeded()) {
				List<JsonObject> resList = r.result().getRows();
				if (resList == null || resList.isEmpty()) {
					promise.complete(Optional.empty());
				} else {
					promise.complete(Optional.of(resList.get(0)));
				}
			} else {
				rollbackAndUnlock();
				promise.fail(r.cause());
			}
		});
		return promise.future();
	}
	
	protected Future<Void> commitTxnAndUnlock(Entity entity, UUID uuid) {
		Promise<Void> promise = Promise.promise();
		if (currUUID.equals(uuid) && currEntity.equals(entity) && (counter == 0)) {
			globalConn.commit(ar -> {
				if (ar.succeeded()) {
					globalConn.execute("UNLOCK TABLES", done -> {
						currEntity = Entity.NONE;
						currUUID = null;
						globalConn.close();
						globalConn = null;
						if (done.succeeded()) {
							promise.complete();
						} else {
							promise.fail(done.cause());
						}
					});
				} else {
					globalConn.close();
					globalConn = null;
					promise.fail(ar.cause());
				}
			});
		} else {
			counter--;
			promise.complete();
		}
		return promise.future();
	}
	
	protected void rollbackAndUnlock() {
		if (!currEntity.equals(Entity.NONE)) {
			globalConn.rollback(ar -> {
				globalConn.execute("UNLOCK TABLES", done -> {
					currEntity = Entity.NONE;
					currUUID = null;
				});
			});
		}
	}

}
