package io.nms.central.microservice.topology;

import java.util.HashMap;
import java.util.Map;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.nms.central.microservice.notification.model.Status;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.servicediscovery.types.MessageSource;

public class StatusHandler extends BaseMicroserviceVerticle {

	private static final Logger logger = LoggerFactory.getLogger(StatusHandler.class);
	
	private final TopologyService topologyService;
	private Map<Integer,Long> statusTimers;

	public StatusHandler(TopologyService topologyService) {
		this.topologyService = topologyService;
		this.statusTimers = new HashMap<Integer,Long>();
	}

	@Override
	public void start(Promise<Void> promise) throws Exception {
		super.start();
		MessageSource.<JsonObject>getConsumer(discovery,
				new JsonObject().put("name", "status-message-source"),
				ar -> {
					if (ar.succeeded()) {
						MessageConsumer<JsonObject> statusConsumer = ar.result();
						statusConsumer.handler(message -> {
							Status status = new Status(message.body());
							initHandleStatus(status, message);
						});
						promise.complete();
					} else {
						promise.fail(ar.cause());
					}
				});
	}

	private void initHandleStatus(Status status, Message<JsonObject> sender) {
		if (!status.getResType().equals("node")) {
			sender.fail(5000, "resource type not supported");
			return;
		}
		int resId = status.getResId();
		if (statusTimers.containsKey(resId)) {
			if (status.getStatus().equals("UP") || status.getStatus().equals("DISCONN")) {
				vertx.cancelTimer(statusTimers.get(resId));
				statusTimers.remove(resId);
				dispatchStatus(status);
			}
		} else {
			if (status.getStatus().equals("UP") || status.getStatus().equals("DISCONN")) {
				dispatchStatus(status);
			} else {
				long timerId = vertx.setTimer(5000, new Handler<Long>() {
				    @Override
				    public void handle(Long aLong) {
				    	dispatchStatus(status);
				    	statusTimers.remove(status.getResId());
				    }
				});
				statusTimers.put(resId, timerId);
			}
		}
		sender.reply(new JsonObject());
	}
	/**
	 * Dispatch the order to the infrastructure layer.
	 * Here we simply save the order to the persistence and modify inventory changes.
	 *
	 * @param order  order data object
	 * @param sender message sender
	 */
	private void dispatchStatus(Status status) {
		String resType = status.getResType();
		int resId = status.getResId();
		String resStatus = status.getStatus();

		if (resType.equals("node")) {       
			topologyService.updateNodeStatus(resId, resStatus, ar -> {
				if (ar.succeeded()) {
					topologyService.generateAllRoutes(res -> {
						if (res.succeeded()) {
							publishUpdateToUI();
							notifyConfigService();
						} else {
							res.cause().printStackTrace();
						}
					});
				} else {
					ar.cause().printStackTrace();
				}
			});
		} 
	}
	
	 private void publishUpdateToUI() {
		    vertx.eventBus().publish(TopologyService.UI_ADDRESS, new JsonObject());
	 }
	 
	 private void notifyConfigService() {
			vertx.eventBus().request(TopologyService.CONFIG_ADDRESS, new JsonObject(), reply -> {
				if (reply.failed()) {
					logger.warn("configuration service replies: ", reply.cause().getMessage());
				}
			});
	}
}
