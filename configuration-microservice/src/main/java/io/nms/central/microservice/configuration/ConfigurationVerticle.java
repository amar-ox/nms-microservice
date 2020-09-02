package io.nms.central.microservice.configuration;

import static io.nms.central.microservice.configuration.ConfigurationService.SERVICE_ADDRESS;
import static io.nms.central.microservice.configuration.ConfigurationService.SERVICE_NAME;

import java.util.List;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.nms.central.microservice.configuration.api.RestConfigurationAPIVerticle;
import io.nms.central.microservice.configuration.impl.ConfigurationServiceImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ProxyHelper;


/**
 * A verticle publishing the configuration service.
 *
 */
public class ConfigurationVerticle extends BaseMicroserviceVerticle {

	@Override
	public void start(Future<Void> future) throws Exception {
		super.start();
		// create the service instance
		ConfigurationService configService = new ConfigurationServiceImpl(vertx, config());
		// register the service proxy on event bus
		ProxyHelper.registerService(ConfigurationService.class, vertx, configService, SERVICE_ADDRESS);

		initConfigDatabase(configService)
			.compose(databaseOkay -> publishEventBusService(SERVICE_NAME, SERVICE_ADDRESS, ConfigurationService.class))
			.compose(servicePublished -> deployHandler(configService))
			.compose(handlerPrepared -> deployRestVerticle(configService))
			.onComplete(future);
	}

	private Future<List<Integer>> initConfigDatabase(ConfigurationService service) {
		Promise<List<Integer>> initPromise = Promise.promise();
		service.initializePersistence(initPromise);
		return initPromise.future();
	}

	private Future<Void> deployHandler(ConfigurationService service) {
		Promise<String> promise = Promise.promise();
		vertx.deployVerticle(new ConfigHandler(service),
				new DeploymentOptions().setConfig(config()), promise);
		return promise.future().map(r -> null);
	}

	private Future<Void> deployRestVerticle(ConfigurationService service) {
		Promise<String> promise = Promise.promise();
		vertx.deployVerticle(new RestConfigurationAPIVerticle(service),
				new DeploymentOptions().setConfig(config()), promise);
		return promise.future().map(r -> null);
	}

}