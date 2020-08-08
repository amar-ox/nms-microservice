package io.nms.central.microservice.common.service;

import java.util.List;
import java.util.Optional;

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
 *
 * @author Eric Zhao
 */
public class JdbcRepositoryWrapper {

	private static final Logger logger = LoggerFactory.getLogger(JdbcRepositoryWrapper.class);
	protected final JDBCClient client;
	private SQLConnection globalConn = null;
	protected enum Entity {
		NONE,
	    NODE,
	    LTP,
	    LINK,
	    LC
	}
	private Entity currentEntity = Entity.NONE;
	

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

	/* protected <R> void execute(JsonArray params, String sql, R ret, Handler<AsyncResult<R>> resultHandler) {
		client.getConnection(connHandler(resultHandler, connection -> {
			connection.updateWithParams(sql, params, r -> {
				if (r.succeeded()) {
					resultHandler.handle(Future.succeededFuture(ret));
				} else {
					resultHandler.handle(Future.failedFuture(r.cause()));
				}
				connection.close();
			});
		}));
	} */
	
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
					Future<Optional<JsonObject>> future = Future.future();
					connection.queryWithParams(sql, new JsonArray().add(param), r -> {
						if (r.succeeded()) {
							List<JsonObject> resList = r.result().getRows();
							if (resList == null || resList.isEmpty()) {
								future.complete(Optional.empty());
							} else {
								future.complete(Optional.of(resList.get(0)));
							}
						} else {
							future.fail(r.cause());
						}
						connection.close();
					});
					return future;
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
			Future<List<JsonObject>> future = Future.future();
			connection.queryWithParams(sql, param, r -> {
				if (r.succeeded()) {
					future.complete(r.result().getRows());					
				} else {
					future.fail(r.cause());
				}
				connection.close();
			});
			return future;
		});
	}

	protected Future<List<JsonObject>> retrieveAll(String sql) {
		return getConnection().compose(connection -> {
			Future<List<JsonObject>> future = Future.future();
			connection.query(sql, r -> {
				if (r.succeeded()) {
					future.complete(r.result().getRows());
					// logger.debug("retrieveAll: "+r.result().getRows().get(0).encodePrettily());
				} else {
					future.fail(r.cause());
				}
				connection.close();
			});
			return future;
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
	
	/* protected Future<SQLConnection> txnBegin() {
		Promise<SQLConnection> promise = Promise.promise();
		client.getConnection(ar -> {
			if (ar.succeeded()) {
				SQLConnection conn = ar.result(); 
				conn.setAutoCommit(false, res -> {
					if (res.succeeded()) {
						promise.complete(conn);
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
	
	protected Future<Integer> txnExecute(SQLConnection conn, String sql, JsonArray params) {
		Promise<Integer> promise = Promise.promise();
		conn.updateWithParams(sql, params, r -> {
			if (r.succeeded()) {
				UpdateResult updateResult = r.result();
				if (updateResult.getUpdated() == 1) {
					promise.complete(updateResult.getKeys().getInteger(0));					 
				} else {
					promise.fail("Not inserted");
				}
			} else {
				promise.fail(r.cause());
			}
		});
		return promise.future();
	}
	
	protected Future<Void> txnExecuteNoResult(SQLConnection conn, String sql, JsonArray params) {
		Promise<Void> promise = Promise.promise();
		conn.updateWithParams(sql, params, r -> {
			if (r.succeeded()) {
				promise.complete();
			} else {
				promise.fail(r.cause());
			}
		});
		return promise.future();
	}
	
	protected Future<Void> txnEnd(SQLConnection conn) {
		Promise<Void> promise = Promise.promise();
		conn.commit(ar -> {
			conn.close();
			if (ar.succeeded()) {				
				promise.complete();
			} else {
				promise.fail(ar.cause());				
			}
		});
		return promise.future();
	} */
	
	
	/* --------------------------------- */
	protected Future<Void> beginTxnAndLock(Entity entity, String sql) {
		Promise<Void> promise = Promise.promise();
		if (!currentEntity.equals(Entity.NONE)) {
			logger.info("TXN already started");
			promise.complete();
		} else {
			logger.info("begin TXN: " + entity);
			client.getConnection(ar -> {
				if (ar.succeeded()) {
					globalConn = ar.result();
					currentEntity = entity;
					globalConn.setAutoCommit(false, res -> {
						if (res.succeeded()) {
							logger.info("lock tables: " + entity);
							globalConn.execute(sql, promise);
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
					promise.fail("Not inserted");
				}					 
			} else {
				promise.fail(ar.cause());
			}
		});
		return promise.future();
	}
	
	protected Future<Void> globalExecute(JsonArray params, String sql) {
		Promise<Void> promise = Promise.promise();
		globalConn.updateWithParams(sql, params, ar -> {
			if (ar.succeeded()) {
				logger.info("sql ok");
				promise.complete();					 
			} else {
				logger.error("sql failed");
				promise.fail(ar.cause());
			}
		});
		return promise.future();
	}
	
	protected Future<List<JsonObject>> globalRetrieveMany(JsonArray params, String sql) { 
		Promise<List<JsonObject>> promise = Promise.promise();
		globalConn.queryWithParams(sql, params, r -> {
				if (r.succeeded()) {
					logger.info("sql ok");
					promise.complete(r.result().getRows());
				} else {
					logger.error("sql failed");
					promise.fail(r.cause());
				}
			});
		return promise.future();
	}
	
	protected Future<Optional<JsonObject>> globalRetrieveOne(JsonArray params, String sql) {
		Promise<Optional<JsonObject>> promise = Promise.promise();
		globalConn.queryWithParams(sql, params, r -> {
			if (r.succeeded()) {
				logger.info("sql ok");
				List<JsonObject> resList = r.result().getRows();
				if (resList == null || resList.isEmpty()) {
					promise.complete(Optional.empty());
				} else {
					promise.complete(Optional.of(resList.get(0)));
				}
			} else {
				logger.error("sql failed");
				promise.fail(r.cause());
			}
		});
		return promise.future();
	}
	
	protected Future<Void> commitTxnAndUnlock(Entity entity) {
		Promise<Void> promise = Promise.promise();
		if (!currentEntity.equals(entity)) {
			logger.info("TXN not ended yet");
			promise.complete();
		} else {
			globalConn.commit(ar -> {
				currentEntity = Entity.NONE;
				if (ar.succeeded()) {
					logger.info("commit TXN ok");
					globalConn.execute("UNLOCK TABLES", done -> {
						globalConn.close();
						globalConn = null;
						if (done.succeeded()) {
							logger.info("tables unlocked");
							promise.complete();
						} else {
							logger.error("tables unlock failed");
							promise.fail(done.cause());
						}
					});
				} else {
					logger.error("commit TXN failed");
					globalConn.close();
					globalConn = null;
					promise.fail(ar.cause());
				}
			});
		}
		return promise.future();
	}

}
