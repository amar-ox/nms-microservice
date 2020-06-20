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

/** @module nms-cnetral-topology-js/topology_service */
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JTopologyService = Java.type('io.nms.central.microservice.topology.TopologyService');
var Vsubnet = Java.type('io.nms.central.microservice.topology.model.Vsubnet');
var Vltp = Java.type('io.nms.central.microservice.topology.model.Vltp');
var Vlink = Java.type('io.nms.central.microservice.topology.model.Vlink');
var VlinkConn = Java.type('io.nms.central.microservice.topology.model.VlinkConn');
var Vnode = Java.type('io.nms.central.microservice.topology.model.Vnode');
var Vctp = Java.type('io.nms.central.microservice.topology.model.Vctp');

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
  var __super_deleteVsubnet = this.deleteVsubnet;
  var __super_getAllVsubnets = this.getAllVsubnets;
  var __super_addVnode = this.addVnode;
  var __super_getVnode = this.getVnode;
  var __super_getAllVnodes = this.getAllVnodes;
  var __super_getVnodesByVsubnet = this.getVnodesByVsubnet;
  var __super_deleteVnode = this.deleteVnode;
  var __super_addVltp = this.addVltp;
  var __super_getVltp = this.getVltp;
  var __super_getAllVltps = this.getAllVltps;
  var __super_deleteVltp = this.deleteVltp;
  var __super_addVctp = this.addVctp;
  var __super_getVctp = this.getVctp;
  var __super_getAllVctps = this.getAllVctps;
  var __super_deleteVctp = this.deleteVctp;
  var __super_addVlink = this.addVlink;
  var __super_getVlink = this.getVlink;
  var __super_getAllVlinks = this.getAllVlinks;
  var __super_getVlinksByVsubnet = this.getVlinksByVsubnet;
  var __super_deleteVlink = this.deleteVlink;
  var __super_addVlinkConn = this.addVlinkConn;
  var __super_getVlinkConn = this.getVlinkConn;
  var __super_getAllVlinkConns = this.getAllVlinkConns;
  var __super_deleteVlinkConn = this.deleteVlinkConn;
  var __super_getVltpsByVnode = this.getVltpsByVnode;
  var __super_getVctpsByVltp = this.getVctpsByVltp;
  var __super_getVctpsByVlink = this.getVctpsByVlink;
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
   Add a subnet to the persistence.

   @public
   @param vsubnet {Object} 
   @param resultHandler {function} the result handler will be called as soon as the subnet has been added. The async result indicates whether the operation was successful or not. 
   @return {TopologyService}
   */
  this.addVsubnet =  function(vsubnet, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addVsubnet(io.nms.central.microservice.topology.model.Vsubnet,io.vertx.core.Handler)"](__args[0]  != null ? new Vsubnet(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
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
   @param vnode {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addVnode =  function(vnode, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addVnode(io.nms.central.microservice.topology.model.Vnode,io.vertx.core.Handler)"](__args[0]  != null ? new Vnode(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
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
   @param vltp {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addVltp =  function(vltp, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addVltp(io.nms.central.microservice.topology.model.Vltp,io.vertx.core.Handler)"](__args[0]  != null ? new Vltp(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
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
   @param vctp {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addVctp =  function(vctp, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addVctp(io.nms.central.microservice.topology.model.Vctp,io.vertx.core.Handler)"](__args[0]  != null ? new Vctp(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
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
   @param vlink {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addVlink =  function(vlink, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addVlink(io.nms.central.microservice.topology.model.Vlink,io.vertx.core.Handler)"](__args[0]  != null ? new Vlink(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
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
   @param vlinkConn {Object} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.addVlinkConn =  function(vlinkConn, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addVlinkConn(io.nms.central.microservice.topology.model.VlinkConn,io.vertx.core.Handler)"](__args[0]  != null ? new VlinkConn(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
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
   @param linkConnId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.deleteVlinkConn =  function(linkConnId, resultHandler) {
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
   @param vlinkId {string} 
   @param resultHandler {function} 
   @return {TopologyService}
   */
  this.getVctpsByVlink =  function(vlinkId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getVctpsByVlink(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_getVctpsByVlink != 'undefined') {
      return __super_getVctpsByVlink.apply(this, __args);
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