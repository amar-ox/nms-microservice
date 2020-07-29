package io.nms.central.microservice.topology;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.types.MessageSource;

public class ReportHandler extends BaseMicroserviceVerticle {

	private final TopologyService topologyService;

	public ReportHandler(TopologyService topologyService) {
		this.topologyService = topologyService;
	}

	@Override
	public void start(Promise<Void> promise) throws Exception {
		super.start();
		MessageSource.<JsonObject>getConsumer(discovery,
				new JsonObject().put("name", "reports-message-source"),
				ar -> {
					if (ar.succeeded()) {
						MessageConsumer<JsonObject> reportConsumer = ar.result();
						reportConsumer.handler(message -> {
							JsonObject report = wrapRawReport(message.body());
							dispatchReport(report, message);
						});
						promise.complete();
					} else {
						promise.fail(ar.cause());
					}
				});
	}

	/**
	 * Wrap raw order and generate new order.
	 *
	 * @return the new order.
	 */
	private JsonObject wrapRawReport(JsonObject rawReport) {
		// TODO: return Report obj
		return rawReport;
	}

	/**
	 * Dispatch the order to the infrastructure layer.
	 * Here we simply save the order to the persistence and modify inventory changes.
	 *
	 * @param order  order data object
	 * @param sender message sender
	 */
	private void dispatchReport(JsonObject report, Message<JsonObject> sender) {
		String type = report.getString("type");
		int id = report.getInteger("id", 0);
		String name = report.getString("name", "");
		String status = report.getString("status");

		if (type.equals("node")) {       
			topologyService.updateNodeStatus(id, name, status, ar -> {
				if (ar.succeeded()) {
					topologyService.generateAllRoutes(res -> {
						if (res.succeeded()) {
							JsonObject result = new JsonObject()
									.put("report_processed", report);
							sender.reply(result);
						} else {
							sender.fail(5000, res.cause().getMessage());
							res.cause().printStackTrace();
						}
					});
				} else {
					sender.fail(5000, ar.cause().getMessage());
					ar.cause().printStackTrace();					
				}
			});
		} else {
			sender.fail(5000, "type not supported");
		}
	}

}
