package io.nms.central.microservice.crossconnect.impl;

import io.nms.central.microservice.common.service.JdbcRepositoryWrapper;
import io.nms.central.microservice.crossconnect.CrossconnectService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 *
 */
public class CrossconnectServiceImpl extends JdbcRepositoryWrapper implements CrossconnectService {

	private static final Logger logger = LoggerFactory.getLogger(CrossconnectServiceImpl.class);

	public CrossconnectServiceImpl(Vertx vertx, JsonObject config) {
		super(vertx, config);
	}

	@Override
	public CrossconnectService initializePersistence(Handler<AsyncResult<Void>> resultHandler) {
		resultHandler.handle(Future.succeededFuture());
		return this;
	}
}