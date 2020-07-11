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

import io.nms.central.microservice.topology.TopologyService;
import io.nms.central.microservice.topology.model.Vltp;
import io.nms.central.microservice.topology.model.Vlink;
import io.nms.central.microservice.topology.model.Vxc;
import io.nms.central.microservice.topology.model.Vsubnet;
import io.nms.central.microservice.topology.model.Face;
import java.util.List;
import io.nms.central.microservice.topology.model.Route;
import io.nms.central.microservice.topology.model.VlinkConn;
import io.nms.central.microservice.topology.model.Vtrail;
import io.vertx.core.AsyncResult;
import io.nms.central.microservice.topology.model.PrefixAnn;
import io.vertx.core.Handler;
import io.nms.central.microservice.topology.model.Vnode;
import io.nms.central.microservice.topology.model.Vctp;
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
        case "initializePersistence": {
          service.initializePersistence(HelperUtils.createListHandler(msg));
          break;
        }
        case "addVsubnet": {
          service.addVsubnet(json.getJsonObject("vsubnet") == null ? null : new io.nms.central.microservice.topology.model.Vsubnet(json.getJsonObject("vsubnet")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getVsubnet": {
          service.getVsubnet((java.lang.String)json.getValue("vsubnetId"),
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
        case "getAllVsubnets": {
          service.getAllVsubnets(res -> {
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
        case "deleteVsubnet": {
          service.deleteVsubnet((java.lang.String)json.getValue("vsubnetId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "updateVsubnet": {
          service.updateVsubnet((java.lang.String)json.getValue("id"),
                        json.getJsonObject("vsubnet") == null ? null : new io.nms.central.microservice.topology.model.Vsubnet(json.getJsonObject("vsubnet")),
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
        case "addVnode": {
          service.addVnode(json.getJsonObject("vnode") == null ? null : new io.nms.central.microservice.topology.model.Vnode(json.getJsonObject("vnode")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getVnode": {
          service.getVnode((java.lang.String)json.getValue("vnodeId"),
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
        case "getAllVnodes": {
          service.getAllVnodes(res -> {
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
        case "getVnodesByVsubnet": {
          service.getVnodesByVsubnet((java.lang.String)json.getValue("vsubnetId"),
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
        case "deleteVnode": {
          service.deleteVnode((java.lang.String)json.getValue("vnodeId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "updateVnode": {
          service.updateVnode((java.lang.String)json.getValue("id"),
                        json.getJsonObject("vnode") == null ? null : new io.nms.central.microservice.topology.model.Vnode(json.getJsonObject("vnode")),
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
        case "addVltp": {
          service.addVltp(json.getJsonObject("vltp") == null ? null : new io.nms.central.microservice.topology.model.Vltp(json.getJsonObject("vltp")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getVltp": {
          service.getVltp((java.lang.String)json.getValue("vltpId"),
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
        case "getAllVltps": {
          service.getAllVltps(res -> {
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
        case "getVltpsByVnode": {
          service.getVltpsByVnode((java.lang.String)json.getValue("vnodeId"),
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
        case "deleteVltp": {
          service.deleteVltp((java.lang.String)json.getValue("vltpId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "updateVltp": {
          service.updateVltp((java.lang.String)json.getValue("id"),
                        json.getJsonObject("vltp") == null ? null : new io.nms.central.microservice.topology.model.Vltp(json.getJsonObject("vltp")),
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
        case "addVctp": {
          service.addVctp(json.getJsonObject("vctp") == null ? null : new io.nms.central.microservice.topology.model.Vctp(json.getJsonObject("vctp")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getVctp": {
          service.getVctp((java.lang.String)json.getValue("vctpId"),
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
        case "getAllVctps": {
          service.getAllVctps(res -> {
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
        case "getVctpsByVltp": {
          service.getVctpsByVltp((java.lang.String)json.getValue("vltpId"),
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
        case "getVctpsByVnode": {
          service.getVctpsByVnode((java.lang.String)json.getValue("vnodeId"),
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
        case "getVctpsByVlink": {
          service.getVctpsByVlink((java.lang.String)json.getValue("vlinkId"),
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
        case "deleteVctp": {
          service.deleteVctp((java.lang.String)json.getValue("vctpId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "updateVctp": {
          service.updateVctp((java.lang.String)json.getValue("id"),
                        json.getJsonObject("vctp") == null ? null : new io.nms.central.microservice.topology.model.Vctp(json.getJsonObject("vctp")),
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
        case "addVlink": {
          service.addVlink(json.getJsonObject("vlink") == null ? null : new io.nms.central.microservice.topology.model.Vlink(json.getJsonObject("vlink")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getVlink": {
          service.getVlink((java.lang.String)json.getValue("vlinkId"),
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
        case "getAllVlinks": {
          service.getAllVlinks(res -> {
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
        case "getVlinksByVsubnet": {
          service.getVlinksByVsubnet((java.lang.String)json.getValue("vsubnetId"),
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
        case "deleteVlink": {
          service.deleteVlink((java.lang.String)json.getValue("vlinkId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "updateVlink": {
          service.updateVlink((java.lang.String)json.getValue("id"),
                        json.getJsonObject("vlink") == null ? null : new io.nms.central.microservice.topology.model.Vlink(json.getJsonObject("vlink")),
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
        case "addVlinkConn": {
          service.addVlinkConn(json.getJsonObject("vlinkConn") == null ? null : new io.nms.central.microservice.topology.model.VlinkConn(json.getJsonObject("vlinkConn")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getVlinkConn": {
          service.getVlinkConn((java.lang.String)json.getValue("vlinkConnId"),
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
        case "getAllVlinkConns": {
          service.getAllVlinkConns(res -> {
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
        case "getVlinkConnsByVlink": {
          service.getVlinkConnsByVlink((java.lang.String)json.getValue("vlinkId"),
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
        case "getVlinkConnsByVsubnet": {
          service.getVlinkConnsByVsubnet((java.lang.String)json.getValue("vsubnetId"),
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
        case "deleteVlinkConn": {
          service.deleteVlinkConn((java.lang.String)json.getValue("vlinkConnId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "updateVlinkConn": {
          service.updateVlinkConn((java.lang.String)json.getValue("id"),
                        json.getJsonObject("vlinkConn") == null ? null : new io.nms.central.microservice.topology.model.VlinkConn(json.getJsonObject("vlinkConn")),
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
        case "addVtrail": {
          service.addVtrail(json.getJsonObject("vtrail") == null ? null : new io.nms.central.microservice.topology.model.Vtrail(json.getJsonObject("vtrail")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getVtrail": {
          service.getVtrail((java.lang.String)json.getValue("vtrailId"),
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
        case "deleteVtrail": {
          service.deleteVtrail((java.lang.String)json.getValue("vtrailId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getAllVtrails": {
          service.getAllVtrails(res -> {
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
        case "getVtrailsByVsubnet": {
          service.getVtrailsByVsubnet((java.lang.String)json.getValue("vsubnetId"),
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
        case "updateVtrail": {
          service.updateVtrail((java.lang.String)json.getValue("id"),
                        json.getJsonObject("vtrail") == null ? null : new io.nms.central.microservice.topology.model.Vtrail(json.getJsonObject("vtrail")),
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
        case "addVxc": {
          service.addVxc(json.getJsonObject("vxc") == null ? null : new io.nms.central.microservice.topology.model.Vxc(json.getJsonObject("vxc")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getVxc": {
          service.getVxc((java.lang.String)json.getValue("vxcId"),
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
        case "getAllVxcs": {
          service.getAllVxcs(res -> {
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
        case "getVxcsByVtrail": {
          service.getVxcsByVtrail((java.lang.String)json.getValue("vtrailId"),
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
        case "getVxcsByVnode": {
          service.getVxcsByVnode((java.lang.String)json.getValue("vnodeId"),
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
        case "deleteVxc": {
          service.deleteVxc((java.lang.String)json.getValue("vxcId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "updateVxc": {
          service.updateVxc((java.lang.String)json.getValue("id"),
                        json.getJsonObject("vxc") == null ? null : new io.nms.central.microservice.topology.model.Vxc(json.getJsonObject("vxc")),
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
        case "addPrefixAnn": {
          service.addPrefixAnn(json.getJsonObject("prefixAnn") == null ? null : new io.nms.central.microservice.topology.model.PrefixAnn(json.getJsonObject("prefixAnn")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getPrefixAnn": {
          service.getPrefixAnn((java.lang.String)json.getValue("prefixAnnId"),
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
        case "getAllPrefixAnns": {
          service.getAllPrefixAnns(res -> {
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
        case "deletePrefixAnn": {
          service.deletePrefixAnn((java.lang.String)json.getValue("prefixAnnId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "updatePrefixAnn": {
          service.updatePrefixAnn((java.lang.String)json.getValue("id"),
                        json.getJsonObject("prefixAnn") == null ? null : new io.nms.central.microservice.topology.model.PrefixAnn(json.getJsonObject("prefixAnn")),
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
        case "addRoute": {
          service.addRoute(json.getJsonObject("route") == null ? null : new io.nms.central.microservice.topology.model.Route(json.getJsonObject("route")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getRoute": {
          service.getRoute((java.lang.String)json.getValue("routeId"),
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
        case "getAllRoutes": {
          service.getAllRoutes(res -> {
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
        case "getRoutesByNode": {
          service.getRoutesByNode((java.lang.String)json.getValue("nodeId"),
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
        case "deleteRoute": {
          service.deleteRoute((java.lang.String)json.getValue("routeId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "addFace": {
          service.addFace(json.getJsonObject("face") == null ? null : new io.nms.central.microservice.topology.model.Face(json.getJsonObject("face")),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "generateFacesForLc": {
          service.generateFacesForLc((java.lang.String)json.getValue("linkConnId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "getFace": {
          service.getFace((java.lang.String)json.getValue("faceId"),
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
        case "getAllFaces": {
          service.getAllFaces(res -> {
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
        case "getFacesByNode": {
          service.getFacesByNode((java.lang.String)json.getValue("nodeId"),
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
        case "deleteFace": {
          service.deleteFace((java.lang.String)json.getValue("faceId"),
                        HelperUtils.createHandler(msg));
          break;
        }
        case "generateRoutesToPrefix": {
          service.generateRoutesToPrefix((java.lang.String)json.getValue("name"),
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
        default: throw new IllegalStateException("Invalid action: " + action);
      }
    } catch (Throwable t) {
      msg.reply(new ServiceException(500, t.getMessage()));
      throw t;
    }
  }
}