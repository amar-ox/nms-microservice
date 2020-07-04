package io.nms.central.microservice.telemetry;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * A service interface managing products.
 * <p>
 * This service is an event bus service (aka. service proxy)
 * </p>
 *
 * @author Eric Zhao
 */
@VertxGen
@ProxyGen
public interface TelemetryService {

  /**
   * The name of the event bus service.
   */
  String SERVICE_NAME = "telemetry-eb-service";

  /**
   * The address on which the service is published.
   */
  String SERVICE_ADDRESS = "service.telemetry";

  /**
   * A static method that creates a product service.
   *
   * @param config a json object for configuration
   * @return initialized product service
   */
  // static ProductService createService(Vertx vertx, JsonObject config)

  /**
   * Initialize the persistence.
   *
   * @param resultHandler the result handler will be called as soon as the initialization has been accomplished. The async result indicates
   *                      whether the operation was successful or not.
   */
  @Fluent
  TelemetryService initializePersistence(Handler<AsyncResult<Void>> resultHandler);
  
}
