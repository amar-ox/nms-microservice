package io.nms.central.microservice.telemetry;

import java.util.List;

import io.nms.central.microservice.telemetry.model.Capability;
import io.nms.central.microservice.telemetry.model.Receipt;
import io.nms.central.microservice.telemetry.model.Result;
import io.nms.central.microservice.telemetry.model.Specification;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * A service interface managing telemetry operations.
 * <p>
 * This service is an event bus service (aka. service proxy)
 * </p>
 */
public interface TelemetryService {

  /**
   * The name of the event bus service.
   */
  String SERVICE_NAME = "telemetry-eb-service";

  /**
   * The address on which the service is published.
   */
  String SERVICE_ADDRESS = "service.telemetry";
  
  String UI_ADDRESS = "nms.to.ui";
  
  void saveCapability(Capability cap, Handler<AsyncResult<Void>> resultHandler);
  void getCapabilitiesByRole(String role, Handler<AsyncResult<List<Capability>>> resultHandler);
  
  void saveSpecification(Specification spec, Handler<AsyncResult<Void>> resultHandler);
  void getAllSpecifications(Handler<AsyncResult<List<Specification>>> resultHandler);
  void removeSpecification(String id, Handler<AsyncResult<Void>> resultHandler);
  
  void saveReceipt(Receipt rct, Handler<AsyncResult<Void>> resultHandler);
  void getAllReceipts(Handler<AsyncResult<List<Receipt>>> resultHandler);
  void removeReceipt(String id, Handler<AsyncResult<Void>> resultHandler);
  
  void saveResult(Result res, Handler<AsyncResult<Void>> resultHandler);
  void getResult(String id, Handler<AsyncResult<Result>> resultHandler);
  // void getAllResults(Handler<AsyncResult<List<Result>>> resultHandler);
  // void removeResult(String id, Handler<AsyncResult<Void>> resultHandler);
  // void removeAllResults(Handler<AsyncResult<Void>> resultHandler);
  
  void getAllResultOperations(String type, Handler<AsyncResult<JsonArray>> resultHandler);
  void getResultsByOperation(String op, Handler<AsyncResult<List<Result>>> resultHandler);
  void removeResultsByOperation(String op, Handler<AsyncResult<Void>> resultHandler);

}
