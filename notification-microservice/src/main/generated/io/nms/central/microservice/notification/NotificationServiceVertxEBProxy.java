/*
* Copyright 2014 Red Hat, Inc.
*
* Red Hat licenses this file to you under the Apache License, version 2.0
* (the "License"); you may not use this file except in compliance with the
* License. You may obtain a copy of the License at:
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations
* under the License.
*/

package io.nms.central.microservice.notification;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.Vertx;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.function.Function;
import io.vertx.serviceproxy.ServiceProxyBuilder;
import io.vertx.serviceproxy.ServiceException;
import io.vertx.serviceproxy.ServiceExceptionMessageCodec;
import io.vertx.serviceproxy.ProxyUtils;

import java.util.List;
import io.nms.central.microservice.notification.model.Status;
import io.nms.central.microservice.notification.model.Event;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.nms.central.microservice.notification.model.Fault;
/*
  Generated Proxy code - DO NOT EDIT
  @author Roger the Robot
*/

@SuppressWarnings({"unchecked", "rawtypes"})
public class NotificationServiceVertxEBProxy implements NotificationService {
  private Vertx _vertx;
  private String _address;
  private DeliveryOptions _options;
  private boolean closed;

  public NotificationServiceVertxEBProxy(Vertx vertx, String address) {
    this(vertx, address, null);
  }

  public NotificationServiceVertxEBProxy(Vertx vertx, String address, DeliveryOptions options) {
    this._vertx = vertx;
    this._address = address;
    this._options = options;
    try{
      this._vertx.eventBus().registerDefaultCodec(ServiceException.class, new ServiceExceptionMessageCodec());
    } catch (IllegalStateException ex) {}
  }

  @Override
  public  void processStatus(Status status, Handler<AsyncResult<Void>> resultHandler){
    if (closed) {
      resultHandler.handle(Future.failedFuture(new IllegalStateException("Proxy is closed")));
      return;
    }
    JsonObject _json = new JsonObject();
    _json.put("status", status == null ? null : status.toJson());

    DeliveryOptions _deliveryOptions = (_options != null) ? new DeliveryOptions(_options) : new DeliveryOptions();
    _deliveryOptions.addHeader("action", "processStatus");
    _vertx.eventBus().<Void>request(_address, _json, _deliveryOptions, res -> {
      if (res.failed()) {
        resultHandler.handle(Future.failedFuture(res.cause()));
      } else {
        resultHandler.handle(Future.succeededFuture(res.result().body()));
      }
    });
  }
  @Override
  public  void saveStatus(Status status, Handler<AsyncResult<Void>> resultHandler){
    if (closed) {
      resultHandler.handle(Future.failedFuture(new IllegalStateException("Proxy is closed")));
      return;
    }
    JsonObject _json = new JsonObject();
    _json.put("status", status == null ? null : status.toJson());

    DeliveryOptions _deliveryOptions = (_options != null) ? new DeliveryOptions(_options) : new DeliveryOptions();
    _deliveryOptions.addHeader("action", "saveStatus");
    _vertx.eventBus().<Void>request(_address, _json, _deliveryOptions, res -> {
      if (res.failed()) {
        resultHandler.handle(Future.failedFuture(res.cause()));
      } else {
        resultHandler.handle(Future.succeededFuture(res.result().body()));
      }
    });
  }
  @Override
  public  void retrieveAllStatus(Handler<AsyncResult<List<Status>>> resultHandler){
    if (closed) {
      resultHandler.handle(Future.failedFuture(new IllegalStateException("Proxy is closed")));
      return;
    }
    JsonObject _json = new JsonObject();

    DeliveryOptions _deliveryOptions = (_options != null) ? new DeliveryOptions(_options) : new DeliveryOptions();
    _deliveryOptions.addHeader("action", "retrieveAllStatus");
    _vertx.eventBus().<JsonArray>request(_address, _json, _deliveryOptions, res -> {
      if (res.failed()) {
        resultHandler.handle(Future.failedFuture(res.cause()));
      } else {
        resultHandler.handle(Future.succeededFuture(res.result().body().stream()
          .map(o -> { if (o == null) return null;
              return o instanceof Map ? new Status(new JsonObject((Map) o)) : new Status((JsonObject) o);
            })
          .collect(Collectors.toList())));
      }
    });
  }
  @Override
  public  void removeStatus(String id, Handler<AsyncResult<Void>> resultHandler){
    if (closed) {
      resultHandler.handle(Future.failedFuture(new IllegalStateException("Proxy is closed")));
      return;
    }
    JsonObject _json = new JsonObject();
    _json.put("id", id);

    DeliveryOptions _deliveryOptions = (_options != null) ? new DeliveryOptions(_options) : new DeliveryOptions();
    _deliveryOptions.addHeader("action", "removeStatus");
    _vertx.eventBus().<Void>request(_address, _json, _deliveryOptions, res -> {
      if (res.failed()) {
        resultHandler.handle(Future.failedFuture(res.cause()));
      } else {
        resultHandler.handle(Future.succeededFuture(res.result().body()));
      }
    });
  }
  @Override
  public  void saveEvent(Event event, Handler<AsyncResult<Void>> resultHandler){
    if (closed) {
      resultHandler.handle(Future.failedFuture(new IllegalStateException("Proxy is closed")));
      return;
    }
    JsonObject _json = new JsonObject();
    _json.put("event", event == null ? null : event.toJson());

    DeliveryOptions _deliveryOptions = (_options != null) ? new DeliveryOptions(_options) : new DeliveryOptions();
    _deliveryOptions.addHeader("action", "saveEvent");
    _vertx.eventBus().<Void>request(_address, _json, _deliveryOptions, res -> {
      if (res.failed()) {
        resultHandler.handle(Future.failedFuture(res.cause()));
      } else {
        resultHandler.handle(Future.succeededFuture(res.result().body()));
      }
    });
  }
  @Override
  public  void retrieveAllEvents(Handler<AsyncResult<List<Event>>> resultHandler){
    if (closed) {
      resultHandler.handle(Future.failedFuture(new IllegalStateException("Proxy is closed")));
      return;
    }
    JsonObject _json = new JsonObject();

    DeliveryOptions _deliveryOptions = (_options != null) ? new DeliveryOptions(_options) : new DeliveryOptions();
    _deliveryOptions.addHeader("action", "retrieveAllEvents");
    _vertx.eventBus().<JsonArray>request(_address, _json, _deliveryOptions, res -> {
      if (res.failed()) {
        resultHandler.handle(Future.failedFuture(res.cause()));
      } else {
        resultHandler.handle(Future.succeededFuture(res.result().body().stream()
          .map(o -> { if (o == null) return null;
              return o instanceof Map ? new Event(new JsonObject((Map) o)) : new Event((JsonObject) o);
            })
          .collect(Collectors.toList())));
      }
    });
  }
  @Override
  public  void removeEvent(String id, Handler<AsyncResult<Void>> resultHandler){
    if (closed) {
      resultHandler.handle(Future.failedFuture(new IllegalStateException("Proxy is closed")));
      return;
    }
    JsonObject _json = new JsonObject();
    _json.put("id", id);

    DeliveryOptions _deliveryOptions = (_options != null) ? new DeliveryOptions(_options) : new DeliveryOptions();
    _deliveryOptions.addHeader("action", "removeEvent");
    _vertx.eventBus().<Void>request(_address, _json, _deliveryOptions, res -> {
      if (res.failed()) {
        resultHandler.handle(Future.failedFuture(res.cause()));
      } else {
        resultHandler.handle(Future.succeededFuture(res.result().body()));
      }
    });
  }
  @Override
  public  void saveFault(Fault fault, Handler<AsyncResult<Void>> resultHandler){
    if (closed) {
      resultHandler.handle(Future.failedFuture(new IllegalStateException("Proxy is closed")));
      return;
    }
    JsonObject _json = new JsonObject();
    _json.put("fault", fault == null ? null : fault.toJson());

    DeliveryOptions _deliveryOptions = (_options != null) ? new DeliveryOptions(_options) : new DeliveryOptions();
    _deliveryOptions.addHeader("action", "saveFault");
    _vertx.eventBus().<Void>request(_address, _json, _deliveryOptions, res -> {
      if (res.failed()) {
        resultHandler.handle(Future.failedFuture(res.cause()));
      } else {
        resultHandler.handle(Future.succeededFuture(res.result().body()));
      }
    });
  }
  @Override
  public  void retrieveAllFaults(Handler<AsyncResult<List<Fault>>> resultHandler){
    if (closed) {
      resultHandler.handle(Future.failedFuture(new IllegalStateException("Proxy is closed")));
      return;
    }
    JsonObject _json = new JsonObject();

    DeliveryOptions _deliveryOptions = (_options != null) ? new DeliveryOptions(_options) : new DeliveryOptions();
    _deliveryOptions.addHeader("action", "retrieveAllFaults");
    _vertx.eventBus().<JsonArray>request(_address, _json, _deliveryOptions, res -> {
      if (res.failed()) {
        resultHandler.handle(Future.failedFuture(res.cause()));
      } else {
        resultHandler.handle(Future.succeededFuture(res.result().body().stream()
          .map(o -> { if (o == null) return null;
              return o instanceof Map ? new Fault(new JsonObject((Map) o)) : new Fault((JsonObject) o);
            })
          .collect(Collectors.toList())));
      }
    });
  }
  @Override
  public  void removeFault(String id, Handler<AsyncResult<Void>> resultHandler){
    if (closed) {
      resultHandler.handle(Future.failedFuture(new IllegalStateException("Proxy is closed")));
      return;
    }
    JsonObject _json = new JsonObject();
    _json.put("id", id);

    DeliveryOptions _deliveryOptions = (_options != null) ? new DeliveryOptions(_options) : new DeliveryOptions();
    _deliveryOptions.addHeader("action", "removeFault");
    _vertx.eventBus().<Void>request(_address, _json, _deliveryOptions, res -> {
      if (res.failed()) {
        resultHandler.handle(Future.failedFuture(res.cause()));
      } else {
        resultHandler.handle(Future.succeededFuture(res.result().body()));
      }
    });
  }
}
