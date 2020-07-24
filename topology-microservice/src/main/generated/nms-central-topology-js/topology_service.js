/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

/** @module nms-central-topology-js/topology_service */
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JTopologyService = Java.type('io.nms.central.microservice.topology.TopologyService');
var Vsubnet = Java.type('io.nms.central.microservice.topology.model.Vsubnet');
var Face = Java.type('io.nms.central.microservice.topology.model.Face');
var Vltp = Java.type('io.nms.central.microservice.topology.model.Vltp');
var Route = Java.type('io.nms.central.microservice.topology.model.Route');
var Vlink = Java.type('io.nms.central.microservice.topology.model.Vlink');
var VlinkConn = Java.type('io.nms.central.microservice.topology.model.VlinkConn');
var Vtrail = Java.type('io.nms.central.microservice.topology.model.Vtrail');
var PrefixAnn = Java.type('io.nms.central.microservice.topology.model.PrefixAnn');
var Vnode = Java.type('io.nms.central.microservice.topology.model.Vnode');
var Vctp = Java.type('io.nms.central.microservice.topology.model.Vctp');
var Vxc = Java.type('io.nms.central.microservice.topology.model.Vxc');

/**
 A service interface managing products.
 <p>
 This service is an event bus service (aka. service proxy)
 </p>

 @class
*/
var TopologyService = function(j_val) {

  var j_topologyService = j_val;
  var that = this;

  var __super_initializePersistence = this.initializePersistence;
  var __super_addVsubnet = this.addVsubnet;
  var __super_getVsubnet = this.getVsubnet;
  var __super_getAllVsubnets = this.getAllVsubnets;
  var __super_deleteVsubnet = this.deleteVsubnet;
  var __super_updateVsubnet = this.updateVsubnet;
  var __super_addVnode = this.addVnode;
  var __super_getVnode = this.getVnode;
  var __super_getAllVnodes = this.getAllVnodes;
  var __super_getVnodesByVsubnet = this.getVnodesByVsubnet;
  var __super_deleteVnode = this.deleteVnode;
  var __super_updateVnode = this.updateVnode;
  var __super_addVltp = this.addVltp;
  var __super_getVltp = this.getVltp;
  var __super_getAllVltps = this.getAllVltps;
  var __super_getVltpsByVnode = this.getVltpsByVnode;
  var __super_deleteVltp = this.deleteVltp;
  var __super_updateVltp = this.updateVltp;
  var __super_addVctp = this.addVctp;
  var __super_getVctp = this.getVctp;
  var __super_getAllVctps = this.getAllVctps;
  var __super_getVctpsByVltp = this.getVctpsByVltp;
  var __super_getVctpsByVnode = this.getVctpsByVnode;
  var __super_deleteVctp = this.deleteVctp;
  var __super_updateVctp = this.updateVctp;
  var __super_addVlink = this.addVlink;
  var __super_getVlink = this.getVlink;
  var __super_getAllVlinks = this.getAllVlinks;
  var __super_getVlinksByVsubnet = this.getVlinksByVsubnet;
  var __super_deleteVlink = this.deleteVlink;
  var __super_updateVlink = this.updateVlink;
  var __super_addVlinkConn = this.addVlinkConn;
  var __super_getVlinkConn = this.getVlinkConn;
  var __super_getAllVlinkConns = this.getAllVlinkConns;
  var __super_getVlinkConnsByVlink = this.getVlinkConnsByVlink;
  var __super_getVlinkConnsByVsubnet = this.getVlinkConnsByVsubnet;
  var __super_deleteVlinkConn = this.deleteVlinkConn;
  var __super_updateVlinkConn = this.updateVlinkConn;
  var __super_addVtrail = this.addVtrail;
  var __super_getVtrail = this.getVtrail;
  var __super_deleteVtrail = this.deleteVtrail;
  var __super_getAllVtrails = this.getAllVtrails;
  var __super_getVtrailsByVsubnet = this.getVtrailsByVsubnet;
  var __super_updateVtrail = this.updateVtrail;
  var __super_addVxc = this.addVxc;
  var __super_getVxc = this.getVxc;
  var __super_getAllVxcs = this.getAllVxcs;
  var __super_getVxcsByVtrail = this.getVxcsByVtrail;
  var __super_getVxcsByVnode = this.getVxcsByVnode;
  var __super_deleteVxc = this.deleteVxc;
  var __super_updateVxc = this.updateVxc;
  var __super_addPrefixAnn = this.addPrefixAnn;
  var __super_getPrefixAnn = this.getPrefixAnn;
  var __super_getAllPrefixAnns = this.getAllPrefixAnns;
  var __super_getPrefixAnnsByVsubnet = this.getPrefixAnnsByVsubnet;
  var __super_getPrefixAnnsByVnode = this.getPrefixAnnsByVnode;
  var __super_deletePrefixAnn = this.deletePrefixAnn;
  var __super_addRoute = this.addRoute;
  var __super_getRoute = this.getRoute;
  var __super_getAllRoutes = this.getAllRoutes;
  var __super_getRoutesByVsubnet = this.getRoutesByVsubnet;
  var __super_getRoutesByNode = this.getRoutesByNode;
  var __super_deleteRoute = this.deleteRoute;
  var __super_addFace = this.addFace;
  var __super_getFace = this.getFace;
  var __super_getAllFaces = this.getAllFaces;
  var __super_getFacesByVsubnet = this.getFacesByVsubnet;
  var __super_getFacesByNode = this.getFacesByNode;
  var __super_deleteFace = this.deleteFace;
  var __super_reportDispatcher = this.reportDispatcher;
  /**

   @public
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.initializePersistence =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["initializePersistence(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](ar.result(), null);
        } else {
          __args[0](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_initializePersistence != 'undefined') {
      return __super_initializePersistence.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vsubnet {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addVsubnet =  function(vsubnet, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addVsubnet(io.nms.central.microservice.topology.model.Vsubnet,io.vertx.core.Handler)"](__args[0]  != null ? new Vsubnet(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](ar.result(), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_addVsubnet != 'undefined') {
      return __super_addVsubnet.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vsubnetId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVsubnet =  function(vsubnetId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVsubnet(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVsubnet != 'undefined') {
      return __super_getVsubnet.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getAllVsubnets =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["getAllVsubnets(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getAllVsubnets != 'undefined') {
      return __super_getAllVsubnets.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vsubnetId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.deleteVsubnet =  function(vsubnetId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteVsubnet(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_deleteVsubnet != 'undefined') {
      return __super_deleteVsubnet.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param id {string} 
   @param vsubnet {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.updateVsubnet =  function(id, vsubnet, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'string' && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
      j_topologyService["updateVsubnet(java.lang.String,io.nms.central.microservice.topology.model.Vsubnet,io.vertx.core.Handler)"](__args[0], __args[1]  != null ? new Vsubnet(new JsonObject(Java.asJSONCompatible(__args[1]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[2](ar.result(), null);
        } else {
          __args[2](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_updateVsubnet != 'undefined') {
      return __super_updateVsubnet.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vnode {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addVnode =  function(vnode, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addVnode(io.nms.central.microservice.topology.model.Vnode,io.vertx.core.Handler)"](__args[0]  != null ? new Vnode(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](ar.result(), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_addVnode != 'undefined') {
      return __super_addVnode.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vnodeId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVnode =  function(vnodeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVnode(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVnode != 'undefined') {
      return __super_getVnode.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getAllVnodes =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["getAllVnodes(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getAllVnodes != 'undefined') {
      return __super_getAllVnodes.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vsubnetId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVnodesByVsubnet =  function(vsubnetId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVnodesByVsubnet(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVnodesByVsubnet != 'undefined') {
      return __super_getVnodesByVsubnet.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vnodeId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.deleteVnode =  function(vnodeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteVnode(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_deleteVnode != 'undefined') {
      return __super_deleteVnode.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param id {string} 
   @param vnode {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.updateVnode =  function(id, vnode, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'string' && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
      j_topologyService["updateVnode(java.lang.String,io.nms.central.microservice.topology.model.Vnode,io.vertx.core.Handler)"](__args[0], __args[1]  != null ? new Vnode(new JsonObject(Java.asJSONCompatible(__args[1]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[2](ar.result(), null);
        } else {
          __args[2](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_updateVnode != 'undefined') {
      return __super_updateVnode.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vltp {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addVltp =  function(vltp, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addVltp(io.nms.central.microservice.topology.model.Vltp,io.vertx.core.Handler)"](__args[0]  != null ? new Vltp(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](ar.result(), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_addVltp != 'undefined') {
      return __super_addVltp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vltpId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVltp =  function(vltpId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVltp(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVltp != 'undefined') {
      return __super_getVltp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getAllVltps =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["getAllVltps(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getAllVltps != 'undefined') {
      return __super_getAllVltps.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vnodeId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVltpsByVnode =  function(vnodeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVltpsByVnode(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVltpsByVnode != 'undefined') {
      return __super_getVltpsByVnode.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vltpId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.deleteVltp =  function(vltpId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteVltp(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_deleteVltp != 'undefined') {
      return __super_deleteVltp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param id {string} 
   @param vltp {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.updateVltp =  function(id, vltp, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'string' && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
      j_topologyService["updateVltp(java.lang.String,io.nms.central.microservice.topology.model.Vltp,io.vertx.core.Handler)"](__args[0], __args[1]  != null ? new Vltp(new JsonObject(Java.asJSONCompatible(__args[1]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[2](ar.result(), null);
        } else {
          __args[2](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_updateVltp != 'undefined') {
      return __super_updateVltp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vctp {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addVctp =  function(vctp, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addVctp(io.nms.central.microservice.topology.model.Vctp,io.vertx.core.Handler)"](__args[0]  != null ? new Vctp(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](ar.result(), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_addVctp != 'undefined') {
      return __super_addVctp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vctpId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVctp =  function(vctpId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVctp(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVctp != 'undefined') {
      return __super_getVctp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getAllVctps =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["getAllVctps(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getAllVctps != 'undefined') {
      return __super_getAllVctps.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vltpId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVctpsByVltp =  function(vltpId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVctpsByVltp(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVctpsByVltp != 'undefined') {
      return __super_getVctpsByVltp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vnodeId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVctpsByVnode =  function(vnodeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVctpsByVnode(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVctpsByVnode != 'undefined') {
      return __super_getVctpsByVnode.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vctpId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.deleteVctp =  function(vctpId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteVctp(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_deleteVctp != 'undefined') {
      return __super_deleteVctp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param id {string} 
   @param vctp {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.updateVctp =  function(id, vctp, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'string' && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
      j_topologyService["updateVctp(java.lang.String,io.nms.central.microservice.topology.model.Vctp,io.vertx.core.Handler)"](__args[0], __args[1]  != null ? new Vctp(new JsonObject(Java.asJSONCompatible(__args[1]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[2](ar.result(), null);
        } else {
          __args[2](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_updateVctp != 'undefined') {
      return __super_updateVctp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vlink {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addVlink =  function(vlink, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addVlink(io.nms.central.microservice.topology.model.Vlink,io.vertx.core.Handler)"](__args[0]  != null ? new Vlink(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](ar.result(), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_addVlink != 'undefined') {
      return __super_addVlink.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vlinkId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVlink =  function(vlinkId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVlink(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVlink != 'undefined') {
      return __super_getVlink.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getAllVlinks =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["getAllVlinks(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getAllVlinks != 'undefined') {
      return __super_getAllVlinks.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vsubnetId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVlinksByVsubnet =  function(vsubnetId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVlinksByVsubnet(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVlinksByVsubnet != 'undefined') {
      return __super_getVlinksByVsubnet.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vlinkId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.deleteVlink =  function(vlinkId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteVlink(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_deleteVlink != 'undefined') {
      return __super_deleteVlink.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param id {string} 
   @param vlink {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.updateVlink =  function(id, vlink, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'string' && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
      j_topologyService["updateVlink(java.lang.String,io.nms.central.microservice.topology.model.Vlink,io.vertx.core.Handler)"](__args[0], __args[1]  != null ? new Vlink(new JsonObject(Java.asJSONCompatible(__args[1]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[2](ar.result(), null);
        } else {
          __args[2](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_updateVlink != 'undefined') {
      return __super_updateVlink.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vlinkConn {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addVlinkConn =  function(vlinkConn, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addVlinkConn(io.nms.central.microservice.topology.model.VlinkConn,io.vertx.core.Handler)"](__args[0]  != null ? new VlinkConn(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](ar.result(), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_addVlinkConn != 'undefined') {
      return __super_addVlinkConn.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vlinkConnId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVlinkConn =  function(vlinkConnId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVlinkConn(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVlinkConn != 'undefined') {
      return __super_getVlinkConn.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getAllVlinkConns =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["getAllVlinkConns(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getAllVlinkConns != 'undefined') {
      return __super_getAllVlinkConns.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vlinkId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVlinkConnsByVlink =  function(vlinkId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVlinkConnsByVlink(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVlinkConnsByVlink != 'undefined') {
      return __super_getVlinkConnsByVlink.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vsubnetId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVlinkConnsByVsubnet =  function(vsubnetId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVlinkConnsByVsubnet(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVlinkConnsByVsubnet != 'undefined') {
      return __super_getVlinkConnsByVsubnet.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vlinkConnId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.deleteVlinkConn =  function(vlinkConnId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteVlinkConn(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_deleteVlinkConn != 'undefined') {
      return __super_deleteVlinkConn.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param id {string} 
   @param vlinkConn {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.updateVlinkConn =  function(id, vlinkConn, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'string' && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
      j_topologyService["updateVlinkConn(java.lang.String,io.nms.central.microservice.topology.model.VlinkConn,io.vertx.core.Handler)"](__args[0], __args[1]  != null ? new VlinkConn(new JsonObject(Java.asJSONCompatible(__args[1]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[2](ar.result(), null);
        } else {
          __args[2](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_updateVlinkConn != 'undefined') {
      return __super_updateVlinkConn.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vtrail {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addVtrail =  function(vtrail, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addVtrail(io.nms.central.microservice.topology.model.Vtrail,io.vertx.core.Handler)"](__args[0]  != null ? new Vtrail(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](ar.result(), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_addVtrail != 'undefined') {
      return __super_addVtrail.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vtrailId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVtrail =  function(vtrailId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVtrail(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVtrail != 'undefined') {
      return __super_getVtrail.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vtrailId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.deleteVtrail =  function(vtrailId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteVtrail(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_deleteVtrail != 'undefined') {
      return __super_deleteVtrail.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getAllVtrails =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["getAllVtrails(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getAllVtrails != 'undefined') {
      return __super_getAllVtrails.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vsubnetId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVtrailsByVsubnet =  function(vsubnetId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVtrailsByVsubnet(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVtrailsByVsubnet != 'undefined') {
      return __super_getVtrailsByVsubnet.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param id {string} 
   @param vtrail {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.updateVtrail =  function(id, vtrail, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'string' && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
      j_topologyService["updateVtrail(java.lang.String,io.nms.central.microservice.topology.model.Vtrail,io.vertx.core.Handler)"](__args[0], __args[1]  != null ? new Vtrail(new JsonObject(Java.asJSONCompatible(__args[1]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[2](ar.result(), null);
        } else {
          __args[2](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_updateVtrail != 'undefined') {
      return __super_updateVtrail.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vxc {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addVxc =  function(vxc, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addVxc(io.nms.central.microservice.topology.model.Vxc,io.vertx.core.Handler)"](__args[0]  != null ? new Vxc(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](ar.result(), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_addVxc != 'undefined') {
      return __super_addVxc.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vxcId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVxc =  function(vxcId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVxc(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVxc != 'undefined') {
      return __super_getVxc.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getAllVxcs =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["getAllVxcs(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getAllVxcs != 'undefined') {
      return __super_getAllVxcs.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vtrailId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVxcsByVtrail =  function(vtrailId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVxcsByVtrail(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVxcsByVtrail != 'undefined') {
      return __super_getVxcsByVtrail.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vnodeId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVxcsByVnode =  function(vnodeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVxcsByVnode(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVxcsByVnode != 'undefined') {
      return __super_getVxcsByVnode.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vxcId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.deleteVxc =  function(vxcId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteVxc(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_deleteVxc != 'undefined') {
      return __super_deleteVxc.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param id {string} 
   @param vxc {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.updateVxc =  function(id, vxc, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'string' && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
      j_topologyService["updateVxc(java.lang.String,io.nms.central.microservice.topology.model.Vxc,io.vertx.core.Handler)"](__args[0], __args[1]  != null ? new Vxc(new JsonObject(Java.asJSONCompatible(__args[1]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[2](ar.result(), null);
        } else {
          __args[2](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_updateVxc != 'undefined') {
      return __super_updateVxc.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param prefixAnn {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addPrefixAnn =  function(prefixAnn, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addPrefixAnn(io.nms.central.microservice.topology.model.PrefixAnn,io.vertx.core.Handler)"](__args[0]  != null ? new PrefixAnn(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](ar.result(), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_addPrefixAnn != 'undefined') {
      return __super_addPrefixAnn.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param prefixAnnId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getPrefixAnn =  function(prefixAnnId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getPrefixAnn(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getPrefixAnn != 'undefined') {
      return __super_getPrefixAnn.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getAllPrefixAnns =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["getAllPrefixAnns(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getAllPrefixAnns != 'undefined') {
      return __super_getAllPrefixAnns.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vsubnetId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getPrefixAnnsByVsubnet =  function(vsubnetId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getPrefixAnnsByVsubnet(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getPrefixAnnsByVsubnet != 'undefined') {
      return __super_getPrefixAnnsByVsubnet.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param nodeId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getPrefixAnnsByVnode =  function(nodeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getPrefixAnnsByVnode(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getPrefixAnnsByVnode != 'undefined') {
      return __super_getPrefixAnnsByVnode.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param prefixAnnId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.deletePrefixAnn =  function(prefixAnnId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deletePrefixAnn(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_deletePrefixAnn != 'undefined') {
      return __super_deletePrefixAnn.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param route {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addRoute =  function(route, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addRoute(io.nms.central.microservice.topology.model.Route,io.vertx.core.Handler)"](__args[0]  != null ? new Route(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](ar.result(), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_addRoute != 'undefined') {
      return __super_addRoute.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param routeId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getRoute =  function(routeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getRoute(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getRoute != 'undefined') {
      return __super_getRoute.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getAllRoutes =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["getAllRoutes(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getAllRoutes != 'undefined') {
      return __super_getAllRoutes.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vsubnetId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getRoutesByVsubnet =  function(vsubnetId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getRoutesByVsubnet(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getRoutesByVsubnet != 'undefined') {
      return __super_getRoutesByVsubnet.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param nodeId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getRoutesByNode =  function(nodeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getRoutesByNode(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getRoutesByNode != 'undefined') {
      return __super_getRoutesByNode.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param routeId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.deleteRoute =  function(routeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteRoute(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_deleteRoute != 'undefined') {
      return __super_deleteRoute.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param face {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addFace =  function(face, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addFace(io.nms.central.microservice.topology.model.Face,io.vertx.core.Handler)"](__args[0]  != null ? new Face(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](ar.result(), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_addFace != 'undefined') {
      return __super_addFace.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param faceId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getFace =  function(faceId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getFace(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getFace != 'undefined') {
      return __super_getFace.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getAllFaces =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["getAllFaces(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getAllFaces != 'undefined') {
      return __super_getAllFaces.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param vsubnetId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getFacesByVsubnet =  function(vsubnetId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getFacesByVsubnet(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getFacesByVsubnet != 'undefined') {
      return __super_getFacesByVsubnet.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param nodeId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getFacesByNode =  function(nodeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getFacesByNode(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getFacesByNode != 'undefined') {
      return __super_getFacesByNode.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param faceId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.deleteFace =  function(faceId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteFace(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_deleteFace != 'undefined') {
      return __super_deleteFace.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param report {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.reportDispatcher =  function(report, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["reportDispatcher(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(__args[0]), function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_reportDispatcher != 'undefined') {
      return __super_reportDispatcher.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_topologyService;
};

TopologyService._jclass = utils.getJavaClass("io.nms.central.microservice.topology.TopologyService");
TopologyService._jtype = {accept: function(obj) {
    return TopologyService._jclass.isInstance(obj._jdel);
  },wrap: function(jdel) {
    var obj = Object.create(TopologyService.prototype, {});
    TopologyService.apply(obj, arguments);
    return obj;
  },
  unwrap: function(obj) {
    return obj._jdel;
  }
};
TopologyService._create = function(jdel) {var obj = Object.create(TopologyService.prototype, {});
  TopologyService.apply(obj, arguments);
  return obj;
}
TopologyService.SERVICE_NAME = JTopologyService.SERVICE_NAME;
TopologyService.SERVICE_ADDRESS = JTopologyService.SERVICE_ADDRESS;
module.exports = TopologyService;