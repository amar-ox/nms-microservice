package io.nms.central.microservice.account;

import static io.nms.central.microservice.account.AccountService.SERVICE_ADDRESS;
import static io.nms.central.microservice.account.AccountService.SERVICE_NAME;

import io.nms.central.microservice.account.api.RestAccountAPIVerticle;
import io.nms.central.microservice.account.impl.AccountServiceImpl;
import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ProxyHelper;


/**
 * A verticle publishing the product service.
 *
 * @author Eric Zhao
 */
public class AccountVerticle extends BaseMicroserviceVerticle {

	@Override
	public void start(Future<Void> future) throws Exception {
		super.start();
		// create the service instance
		AccountService accService = new AccountServiceImpl(vertx, config());
		// register the service proxy on event bus
		ProxyHelper.registerService(AccountService.class, vertx, accService, SERVICE_ADDRESS);

		initAuthDatabase(accService)
			.compose(databaseOkay -> publishEventBusService(SERVICE_NAME, SERVICE_ADDRESS, AccountService.class))
			.compose(servicePublished -> deployRestVerticle(accService))
			.onComplete(future);
	}

	private Future<Void> initAuthDatabase(AccountService service) {
		Promise<Void> initPromise = Promise.promise();
		service.initializePersistence(initPromise);
		return initPromise.future();
	}

	private Future<Void> deployRestVerticle(AccountService service) {
		Promise<String> promise = Promise.promise();
		vertx.deployVerticle(new RestAccountAPIVerticle(service),
				new DeploymentOptions().setConfig(config()), promise);
		return promise.future().map(r -> null);
	}

}