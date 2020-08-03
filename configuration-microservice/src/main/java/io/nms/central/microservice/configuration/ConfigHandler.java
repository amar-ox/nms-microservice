package io.nms.central.microservice.configuration;

import java.util.List;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.nms.central.microservice.topology.TopologyService;
import io.nms.central.microservice.topology.model.Face;
import io.nms.central.microservice.topology.model.Route;
import io.nms.central.microservice.topology.model.Vnode;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.impl.CompositeFutureImpl;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.types.EventBusService;
import io.vertx.servicediscovery.types.MessageSource;

public class ConfigHandler extends BaseMicroserviceVerticle {

	private final ConfigurationService configService;

	public ConfigHandler(ConfigurationService configService) {
		this.configService = configService;
	}

	@Override
	public void start(Promise<Void> promise) throws Exception {
		super.start();
		MessageSource.<JsonObject>getConsumer(discovery,
				new JsonObject().put("name", "config-message-source"),
				ar -> {
					if (ar.succeeded() && ar.result() != null) {
						MessageConsumer<JsonObject> eventConsumer = ar.result();
						eventConsumer.handler(message -> {
							generateConfig(message);
						});
						promise.complete();
					} else {
						promise.fail(ar.cause());
					}
				});
	}

	private void generateConfig(Message<JsonObject> sender) {
		EventBusService.getProxy(discovery, TopologyService.class, ar -> {
			if (ar.succeeded()) {
				TopologyService service = ar.result();
				Promise<List<Route>> pRoute = Promise.promise();
				Promise<List<Face>> pFace = Promise.promise();
				Promise<List<Vnode>> pNode = Promise.promise();
				service.getAllRoutes(pRoute);
				service.getAllFaces(pFace);
				service.getAllVnodes(pNode);
				CompositeFutureImpl.all(pRoute.future(), pFace.future(), pNode.future()).onComplete( res -> {
					if (res.succeeded()) {
						List<Route> routes = pRoute.future().result();
						List<Face> faces = pFace.future().result();
						List<Vnode> nodes = pNode.future().result();
						configService.computeConfigurations(routes,faces, nodes, done -> {
							if (done.succeeded()) {
								configService.upsertCandidateConfigs(done.result(), r -> {
									if (r.succeeded()) {
										sender.reply(new JsonObject());
										publishUpdateToUI();
									} else {
										sender.fail(5000, ar.cause().getMessage());
									}
								});
							} else {
								sender.fail(5000, ar.cause().getMessage());
							}
						});
					} else {
						sender.fail(5000, ar.cause().getMessage());
					}
				});
			} else {
				sender.fail(5000, ar.cause().getMessage());
			}
		});
	}

	private void publishUpdateToUI() {
		vertx.eventBus().publish(ConfigurationService.UI_ADDRESS, new JsonObject());
	}
}
