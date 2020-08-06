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

package io.nms.central.microservice.configuration;

import io.nms.central.microservice.configuration.ConfigurationService;
import io.vertx.core.Vertx;
import io.vertx.core.Handler;
import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import io.vertx.serviceproxy.ServiceBinder;
import io.vertx.serviceproxy.ProxyHandler;
import io.vertx.serviceproxy.ServiceException;
import io.vertx.serviceproxy.ServiceExceptionMessageCodec;
import io.vertx.serviceproxy.HelperUtils;

import io.nms.central.microservice.topology.model.Face;
import io.vertx.core.json.JsonArray;
import java.util.List;
import io.nms.central.microservice.topology.model.Route;
import io.nms.central.microservice.configuration.model.ConfigObj;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.nms.central.microservice.topology.model.Vnode;
/*
  Generated Proxy code - DO NOT EDIT
  @author Roger the Robot
*/

@SuppressWarnings({"unchecked", "rawtypes"})
public class ConfigurationServiceVertxProxyHandler extends ProxyHandler {

  public static final long DEFAULT_CONNECTION_TIMEOUT = 5 * 60; // 5 minutes 
  private final Vertx vertx;
  private final ConfigurationService service;
  private final long timerID;
  private long lastAccessed;
  private final long timeoutSeconds;

  public ConfigurationServiceVertxProxyHandler(Vertx vertx, ConfigurationService service){
    this(vertx, service, DEFAULT_CONNECTION_TIMEOUT);
  }

  public ConfigurationServiceVertxProxyHandler(Vertx vertx, ConfigurationService service, long timeoutInSecond){
    this(vertx, service, true, timeoutInSecond);
  }

  public ConfigurationServiceVertxProxyHandler(Vertx vertx, ConfigurationService service, boolean topLevel, long timeoutSeconds) {
      this.vertx = vertx;
      this.service = service;
      this.timeoutSeconds = timeoutSeconds;
      try {
        this.vertx.eventBus().registerDefaultCodec(ServiceException.class,
            new ServiceExceptionMessageCodec());
      } catch (IllegalStateException ex) {}
      if (timeoutSeconds != -1 && !topLevel) {
        long period = timeoutSeconds * 1000 / 2;
        if (period > 10000) {
          period = 10000;
        }
        this.timerID = vertx.setPeriodic(period, this::checkTimedOut);
      } else {
        this.timerID = -1;
      }
      accessed();
    }


  private void checkTimedOut(long id) {
    long now = System.nanoTime();
    if (now - lastAccessed > timeoutSeconds * 1000000000) {
      close();
    }
  }

    @Override
    public void close() {
      if (timerID != -1) {
        vertx.cancelTimer(timerID);
      }
      super.close();
    }

    private void accessed() {
      this.lastAccessed = System.nanoTime();
    }

  public void handle(Message<JsonObject> msg) {
    try{
      JsonObject json = msg.body();
      String action = msg.headers().get("action");
      if (action == null) throw new IllegalStateException("action not specified");
      accessed();
      switch (action) {
        case "initializePersistence": {
          service.initializePersistence(HelperUtils.createListHandler(msg));
          break;
        }
        case "getCandidateConfig": {
          service.getCandidateConfig(json.getValue("nodeId") == null ? null : (json.getLong("nodeId").intValue()),
                        res -> {
                        if (res.failed()) {
                          if (res.cause() instanceof ServiceException) {
                            msg.reply(res.cause());
                          } else {
                            msg.reply(new ServiceException(-1, res.cause().getMessage()));
                          }
                        } else {
                          msg.reply(res.result() == null ? null : res.result().toJson());
                        }
                     });
          break;
        }
        case "removeAllCandidateConfigs": {
          service.removeAllCandidateConfigs(HelperUtils.createHandler(msg));
          break;
        }
        case "upsertRunningConfig": {
          service.upsertRunningConfig(json.getValue("nodeId") == null ? null : (json.getLong("nodeId").intValue()),
                        json.getJsonObject("config") == null ? null : new io.nms.central.microservice.configuration.model.ConfigObj(json.getJsonObject("config")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "updateRunningConfig": {
          service.updateRunningConfig(json.getValue("nodeId") == null ? null : (json.getLong("nodeId").intValue()),
                        (io.vertx.core.json.JsonArray)json.getValue("patch"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getRunningConfig": {
          service.getRunningConfig(json.getValue("nodeId") == null ? null : (json.getLong("nodeId").intValue()),
                        res -> {
                        if (res.failed()) {
                          if (res.cause() instanceof ServiceException) {
                            msg.reply(res.cause());
                          } else {
                            msg.reply(new ServiceException(-1, res.cause().getMessage()));
                          }
                        } else {
                          msg.reply(res.result() == null ? null : res.result().toJson());
                        }
                     });
          break;
        }
        case "removeAllRunningConfigs": {
          service.removeAllRunningConfigs(HelperUtils.createHandler(msg));
          break;
        }
        case "computeConfigurations": {
          service.computeConfigurations(json.getJsonArray("routes").stream().map(o -> o == null ? null : new io.nms.central.microservice.topology.model.Route((JsonObject)o)).collect(Collectors.toList()),
                        json.getJsonArray("faces").stream().map(o -> o == null ? null : new io.nms.central.microservice.topology.model.Face((JsonObject)o)).collect(Collectors.toList()),
                        json.getJsonArray("nodes").stream().map(o -> o == null ? null : new io.nms.central.microservice.topology.model.Vnode((JsonObject)o)).collect(Collectors.toList()),
                        res -> {
                        if (res.failed()) {
                          if (res.cause() instanceof ServiceException) {
                            msg.reply(res.cause());
                          } else {
                            msg.reply(new ServiceException(-1, res.cause().getMessage()));
                          }
                        } else {
                          msg.reply(new JsonArray(res.result().stream().map(r -> r == null ? null : r.toJson()).collect(Collectors.toList())));
                        }
                     });
          break;
        }
        case "upsertCandidateConfigs": {
          service.upsertCandidateConfigs(json.getJsonArray("configs").stream().map(o -> o == null ? null : new io.nms.central.microservice.configuration.model.ConfigObj((JsonObject)o)).collect(Collectors.toList()),
                        HelperUtils.createHandler(msg));
          break;
        }
        default: throw new IllegalStateException("Invalid action: " + action);
      }
    } catch (Throwable t) {
      msg.reply(new ServiceException(500, t.getMessage()));
      throw t;
    }
  }
}