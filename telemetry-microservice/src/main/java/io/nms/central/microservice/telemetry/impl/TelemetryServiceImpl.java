package io.nms.central.microservice.telemetry.impl;

import io.nms.central.microservice.common.service.JdbcRepositoryWrapper;
import io.nms.central.microservice.telemetry.TelemetryService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * JDBC implementation of {@link io.vertx.blueprint.microservice.product.ProductService}.
 *
 * @author Eric Zhao
 */
public class TelemetryServiceImpl extends JdbcRepositoryWrapper implements TelemetryService {

  private static final int PAGE_LIMIT = 10;

  public TelemetryServiceImpl(Vertx vertx, JsonObject config) {
    super(vertx, config);
  }

  @Override
  public TelemetryService initializePersistence(Handler<AsyncResult<Void>> resultHandler) {
    client.getConnection(connHandler(resultHandler, connection -> {
      connection.execute(CREATE_STATEMENT, r -> {
        resultHandler.handle(r);
        connection.close();
      });
    }));
    return this;
  }

  // SQL statements

  private static final String CREATE_STATEMENT = "CREATE TABLE IF NOT EXISTS `product` (\n" +
    "  `productId` VARCHAR(60) NOT NULL,\n" +
    "  `sellerId` varchar(30) NOT NULL,\n" +
    "  `name` varchar(255) NOT NULL,\n" +
    "  `price` double NOT NULL,\n" +
    "  `illustration` MEDIUMTEXT NOT NULL,\n" +
    "  `type` varchar(45) NOT NULL,\n" +
    "  PRIMARY KEY (`productId`),\n" +
    "  KEY `index_seller` (`sellerId`) )";
  private static final String INSERT_STATEMENT = "INSERT INTO product (`productId`, `sellerId`, `name`, `price`, `illustration`, `type`) VALUES (?, ?, ?, ?, ?, ?)";
  private static final String FETCH_STATEMENT = "SELECT * FROM product WHERE productId = ?";
  private static final String FETCH_ALL_STATEMENT = "SELECT * FROM product";
  private static final String FETCH_WITH_PAGE_STATEMENT = "SELECT * FROM product LIMIT ?, ?";
  private static final String DELETE_STATEMENT = "DELETE FROM product WHERE productId = ?";
  private static final String DELETE_ALL_STATEMENT = "DELETE FROM product";
}
