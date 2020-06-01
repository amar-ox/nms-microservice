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

package io.nms.central.microservice.topology;

import io.nms.central.microservice.topology.TopologyService;
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

import java.util.List;
import io.nms.central.microservice.topology.model.Ctp;
import io.nms.central.microservice.topology.model.LinkConn;
import io.nms.central.microservice.topology.model.Ltp;
import io.nms.central.microservice.topology.model.Node;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.nms.central.microservice.topology.model.Topology;
import io.nms.central.microservice.topology.model.Link;
/*
  Generated Proxy code - DO NOT EDIT
  @author Roger the Robot
*/

@SuppressWarnings({"unchecked", "rawtypes"})
public class TopologyServiceVertxProxyHandler extends ProxyHandler {

  public static final long DEFAULT_CONNECTION_TIMEOUT = 5 * 60; // 5 minutes 
  private final Vertx vertx;
  private final TopologyService service;
  private final long timerID;
  private long lastAccessed;
  private final long timeoutSeconds;

  public TopologyServiceVertxProxyHandler(Vertx vertx, TopologyService service){
    this(vertx, service, DEFAULT_CONNECTION_TIMEOUT);
  }

  public TopologyServiceVertxProxyHandler(Vertx vertx, TopologyService service, long timeoutInSecond){
    this(vertx, service, true, timeoutInSecond);
  }

  public TopologyServiceVertxProxyHandler(Vertx vertx, TopologyService service, boolean topLevel, long timeoutSeconds) {
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
        case "addNode": {
          service.addNode(json.getJsonObject("node") == null ? null : new io.nms.central.microservice.topology.model.Node(json.getJsonObject("node")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getNode": {
          service.getNode((java.lang.String)json.getValue("nodeId"),
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
        case "getNodes": {
          service.getNodes(HelperUtils.convertList(json.getJsonArray("nodeIds").getList()),
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
        case "getAllNodes": {
          service.getAllNodes(res -> {
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
        case "deleteNode": {
          service.deleteNode((java.lang.String)json.getValue("nodeId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "addLtp": {
          service.addLtp(json.getJsonObject("ltp") == null ? null : new io.nms.central.microservice.topology.model.Ltp(json.getJsonObject("ltp")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getLtp": {
          service.getLtp((java.lang.String)json.getValue("ltpId"),
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
        case "deleteLtp": {
          service.deleteLtp((java.lang.String)json.getValue("ltpId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "addCtp": {
          service.addCtp(json.getJsonObject("ctp") == null ? null : new io.nms.central.microservice.topology.model.Ctp(json.getJsonObject("ctp")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getCtp": {
          service.getCtp((java.lang.String)json.getValue("ctpId"),
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
        case "deleteCtp": {
          service.deleteCtp((java.lang.String)json.getValue("ctpId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "addLink": {
          service.addLink(json.getJsonObject("linke") == null ? null : new io.nms.central.microservice.topology.model.Link(json.getJsonObject("linke")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getLink": {
          service.getLink((java.lang.String)json.getValue("linkId"),
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
        case "getAllLinks": {
          service.getAllLinks(res -> {
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
        case "deleteLink": {
          service.deleteLink((java.lang.String)json.getValue("linkId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "addLinkConn": {
          service.addLinkConn(json.getJsonObject("linkConn") == null ? null : new io.nms.central.microservice.topology.model.LinkConn(json.getJsonObject("linkConn")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getLinkConn": {
          service.getLinkConn((java.lang.String)json.getValue("linkConnId"),
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
        case "getAllLinkConns": {
          service.getAllLinkConns(res -> {
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
        case "deleteLinkConn": {
          service.deleteLinkConn((java.lang.String)json.getValue("linkConnId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getTopology": {
          service.getTopology(json.getValue("level") == null ? null : (json.getLong("level").intValue()),
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
        default: throw new IllegalStateException("Invalid action: " + action);
      }
    } catch (Throwable t) {
      msg.reply(new ServiceException(500, t.getMessage()));
      throw t;
    }
  }
}