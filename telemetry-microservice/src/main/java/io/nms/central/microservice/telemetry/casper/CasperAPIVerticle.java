package io.nms.central.microservice.telemetry.casper;

import java.util.Random;

import io.nms.central.microservice.telemetry.model.Capability;
import io.nms.central.microservice.telemetry.model.Interrupt;
import io.nms.central.microservice.telemetry.model.Message;
import io.nms.central.microservice.telemetry.model.Receipt;
import io.nms.central.microservice.telemetry.model.Result;
import io.nms.central.microservice.telemetry.model.Specification;
import io.vertx.amqp.AmqpClient;
import io.vertx.amqp.AmqpClientOptions;
import io.vertx.amqp.AmqpConnection;
import io.vertx.amqp.AmqpMessage;
import io.vertx.amqp.AmqpReceiver;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public abstract class CasperAPIVerticle extends AbstractVerticle {
	
	protected abstract void onCapability(Capability cap);	
	protected abstract void onResult(Result res);	
	
	private static final Logger logger = LoggerFactory.getLogger(CasperAPIVerticle.class);
	
	private AmqpConnection connection = null;
	private Random rand = new Random();
	
	protected void connect(JsonObject config, Handler<AsyncResult<Void>> resultHandler) {
		if (config == null) {
			resultHandler.handle(Future.failedFuture("AMQP config object is missing"));
			return;
		}
		
		String host = config.getString("host");
		Integer port = config.getInteger("port");	
		if (host == null || port == null) {
			resultHandler.handle(Future.failedFuture("AMQP parameters missing"));
			return;
		}
		
		AmqpClientOptions options = new AmqpClientOptions()
				.setHost(host)
				.setPort(port);
		AmqpClient client = AmqpClient.create(options);
		client.connect(ar -> {
			if (ar.succeeded()) {
				connection = ar.result();
				logger.info("Connected to the messaging platform.");
				resultHandler.handle(Future.succeededFuture());
			} else {
				logger.error("Failed to connect to the messaging platform", ar.cause());
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	
	protected void subscribeToCapabilities(String topic, Handler<AsyncResult<Void>> resultHandler) {
		if (connection == null) {
			resultHandler.handle(Future.failedFuture("not connected to the messaging platform"));
			return;
		}
		connection.createReceiver(topic, ar -> {
			if (ar.succeeded()) {
				AmqpReceiver receiver = ar.result();
				receiver
						.exceptionHandler(t -> {})
				        .handler(msg -> {
				        	onCapability(Message.fromJsonString(msg.bodyAsString(), Capability.class));				  
				        });
				resultHandler.handle(Future.succeededFuture());
				logger.info("subscribed to" + topic + "topic");
			} else {
				logger.error("failed to subscribe to " + topic, ar.cause());
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
	
	protected void publishSpecAwaitReceipt(Specification spec, String topic, Handler<AsyncResult<Receipt>> resultHandler) {
		if (connection == null) {
			resultHandler.handle(Future.failedFuture("not connected to the messaging platform"));
			return;
		}
		connection.createDynamicReceiver(replyReceiver -> {
			if (replyReceiver.succeeded()) {
				String replyToAddress = replyReceiver.result().address();
				replyReceiver.result().handler(msg -> {
					Receipt rct = Message.fromJsonString(msg.bodyAsString(), Receipt.class);									
					resultHandler.handle(Future.succeededFuture(rct));
				});
				connection.createSender(topic, sender -> {
					if (sender.succeeded()) {
						sender.result().send(AmqpMessage.create()
								.replyTo(replyToAddress)
								.id(String.valueOf(rand.nextInt(10000)))
								.withBody(spec.toString()).build());
					} else {
						resultHandler.handle(Future.failedFuture(sender.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(replyReceiver.cause()));
			}
		});
	}
	
	protected void publishItrAwaitReceipt(Interrupt itr, String topic, Handler<AsyncResult<Receipt>> resultHandler) {
		if (connection == null) {
			resultHandler.handle(Future.failedFuture("not connected to the messaging platform"));
			return;
		}
		connection.createDynamicReceiver(replyReceiver -> {
			if (replyReceiver.succeeded()) {
				String replyToAddress = replyReceiver.result().address();
				replyReceiver.result().handler(msg -> {
					Receipt rct = Message.fromJsonString(msg.bodyAsString(), Receipt.class);									
					resultHandler.handle(Future.succeededFuture(rct));
				});
				connection.createSender(topic, sender -> {
					if (sender.succeeded()) {
						sender.result().send(AmqpMessage.create()
								.replyTo(replyToAddress)
								.id(String.valueOf(rand.nextInt(10000)))
								.withBody(itr.toString()).build());
					} else {
						resultHandler.handle(Future.failedFuture(sender.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(replyReceiver.cause()));
			}
		});
	}
	
	protected void subscribeToResults(String topic, Handler<AsyncResult<Void>> resultHandler) {
		if (connection == null) {
			resultHandler.handle(Future.failedFuture("not connected to the messaging platform"));
			return;
		}
		connection.createReceiver(topic, ar -> {
			if (ar.succeeded()) {
				AmqpReceiver receiver = ar.result();
				receiver
						.exceptionHandler(t -> {})
				        .handler(msg -> {
				        	onResult(Message.fromJsonString(msg.bodyAsString(), Result.class));				  
				        });
				resultHandler.handle(Future.succeededFuture());
				logger.info("subscribed to" + topic + "topic");
			} else {
				logger.error("failed to subscribe to " + topic, ar.cause());
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
}
