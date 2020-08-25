package io.nms.central.microservice.telemetry;

import static io.nms.central.microservice.telemetry.TelemetryService.SERVICE_ADDRESS;
import static io.nms.central.microservice.telemetry.TelemetryService.SERVICE_NAME;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.nms.central.microservice.telemetry.api.RestTelemetryAPIVerticle;
import io.nms.central.microservice.telemetry.casper.CasperTelemetryAPIVerticle;
import io.nms.central.microservice.telemetry.impl.TelemetryServiceImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;


/**
 * A verticle publishing the product service.
 *
 * @author Amar Abane
 */
public class TelemetryVerticle extends BaseMicroserviceVerticle {

	private TelemetryService telemetryService;
	private CasperTelemetryAPIVerticle casperVerticle;


	@Override
	public void start(Future<Void> future) throws Exception {
		super.start();

		// create the service instance
		telemetryService = new TelemetryServiceImpl(vertx, config());
		
		// create the casper verticle
		casperVerticle = new CasperTelemetryAPIVerticle(telemetryService);

		// register the service proxy on event bus
		/* ServiceBinder binder = new ServiceBinder(vertx);
		binder.setAddress(SERVICE_ADDRESS);
		binder.register(TelemetryService.class, telemetryService);

		// publish the service in the discovery infrastructure    
		publishEventBusService(SERVICE_NAME, SERVICE_ADDRESS, TelemetryService.class)
				.compose(servicePublished -> deployCasperVerticle())
				.compose(capserDeployed -> deployRestVerticle())
				.onComplete(future); */
		
		deployCasperVerticle()
			.compose(capserDeployed -> deployRestVerticle())
			.onComplete(future);
	}

	private Future<Void> deployCasperVerticle() {
		Promise<String> promise = Promise.promise();
		vertx.deployVerticle(casperVerticle, 
				new DeploymentOptions().setConfig(config()), promise);
		return promise.future().map(r -> null);
	}

	private Future<Void> deployRestVerticle() {
		Promise<String> promise = Promise.promise();
		vertx.deployVerticle(new RestTelemetryAPIVerticle(telemetryService, casperVerticle),
				new DeploymentOptions().setConfig(config()), promise);
		return promise.future().map(r -> null);
	}

}
