package io.nms.central.microservice.telemetry.casper;

import io.nms.central.microservice.telemetry.TelemetryService;
import io.nms.central.microservice.telemetry.model.Capability;
import io.nms.central.microservice.telemetry.model.Interrupt;
import io.nms.central.microservice.telemetry.model.Receipt;
import io.nms.central.microservice.telemetry.model.Result;
import io.nms.central.microservice.telemetry.model.Specification;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * This verticle exposes a HTTP endpoint to process shopping products management with REST APIs.
 *
 * @author Amar Abane
 */
public class CasperTelemetryAPIVerticle extends CasperAPIVerticle {
	
	private static final Logger logger = LoggerFactory.getLogger(CasperTelemetryAPIVerticle.class);
	
	private static final String TOPIC_CAPABILITIES = "/capabilities";
	private static final String TOPIC_SPECIFICATIONS = "/specifications";
	private static final String TOPIC_RESULTS = "/results/";
	
	private final TelemetryService service;

	public CasperTelemetryAPIVerticle(TelemetryService service) {
		this.service = service;
	}
	
	@Override
	public void start(Promise<Void> promise) throws Exception {
		super.start();
		connect(config().getJsonObject("amqp"), done -> {
			if (done.succeeded()) {
				// TODO: re-send existent Specs if any...
				subscribeToCapabilities(TOPIC_CAPABILITIES, promise);
			} else {
				promise.fail(done.cause());
			}
		});	
	}
	
	public void processSpecification(String id, Specification spec, Handler<AsyncResult<Receipt>> resultHandler) {
		String topic = spec.getEndpoint() + TOPIC_SPECIFICATIONS;
		publishSpecAwaitReceipt(spec, topic, ar -> {
			if (ar.succeeded()) {
				Receipt specRct = ar.result();
				if (!specRct.getErrors().isEmpty()) {
					resultHandler.handle(Future.failedFuture(specRct.getErrors().get(0)));
					return;
				}
				String resTopic = specRct.getEndpoint() + TOPIC_RESULTS + specRct.getRole();
				subscribeToResults(resTopic, sub -> {
					if (sub.succeeded()) {
						service.saveSpecification(spec, res -> {
							if (res.succeeded()) {
								service.saveReceipt(ar.result(), done -> {
									if (done.succeeded()) {
										resultHandler.handle(Future.succeededFuture(ar.result()));
									} else {
										resultHandler.handle(Future.failedFuture(done.cause()));
									}
								});
							} else {
								resultHandler.handle(Future.failedFuture(res.cause()));
							}
						});
					} else {
						resultHandler.handle(Future.failedFuture(sub.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	
	public void processInterrupt(String id, Interrupt itr, Handler<AsyncResult<Receipt>> resultHandler) {
		// TODO: check receipt and specification existence...
		String topic = itr.getEndpoint() + TOPIC_SPECIFICATIONS;
		publishItrAwaitReceipt(itr, topic, ar -> {
			if (ar.succeeded()) {
				Receipt itrRct = ar.result();
				if (!itrRct.getErrors().isEmpty()) {
					resultHandler.handle(Future.failedFuture(itrRct.getErrors().get(0)));
					return;
				}
				service.removeSpecification(itrRct.getSchema(), res -> {
					if (res.succeeded()) {
						service.removeReceipt(itrRct.getSchema(), done -> {
							if (done.succeeded()) {
								resultHandler.handle(Future.succeededFuture(ar.result()));
							} else {
								resultHandler.handle(Future.failedFuture(done.cause()));
							}
						});
					} else {
						resultHandler.handle(Future.failedFuture(res.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	
	@Override
	protected void onResult(Result result) {
		service.saveResult(result, done -> {
			if (done.succeeded()) {
				publishUpdateToUI();
				logger.info("result saved"); 
			} else {
				logger.warn("result not saved");
			}
		});
	}

	@Override
	protected void onCapability(Capability capability) {
		service.saveCapability(capability, done -> {
			if (done.succeeded()) {
				publishUpdateToUI();
				logger.info("capability saved"); 
			} else {
				logger.warn("capability not saved");
			}
		});
	}
	
	private void publishUpdateToUI() {
		vertx.eventBus().publish(TelemetryService.UI_ADDRESS, new JsonObject()
				.put("service", TelemetryService.SERVICE_ADDRESS));
	}

}
