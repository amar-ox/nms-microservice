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

/// <reference path="./topology_service-proxy.d.ts" />

/** @module nms-central-topology-js/topology_service */
!function (factory) {
  if (typeof require === 'function' && typeof module !== 'undefined') {
    factory();
  } else if (typeof define === 'function' && define.amd) {
    // AMD loader
    define('nms-central-topology-js/topology_service-proxy', [], factory);
  } else {
    // plain old include
    TopologyService = factory();
  }
}(function () {

  /**
   A service interface managing products.
   <p>
   This service is an event bus service (aka. service proxy)
   </p>

   @class
  */
  var TopologyService = function(eb, address) {
    var j_eb = eb;
    var j_address = address;
    var closed = false;
    var that = this;
    var convCharCollection = function(coll) {
      var ret = [];
      for (var i = 0;i < coll.length;i++) {
        ret.push(String.fromCharCode(coll[i]));
      }
      return ret;
    };

    /**

     @public
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.initializePersistence =  function(resultHandler) {
      var __args = arguments;
      if (__args.length === 1 && typeof __args[0] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {}, {"action":"initializePersistence"}, function(err, result) { __args[0](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vsubnet":__args[0]}, {"action":"addVsubnet"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vsubnetId":__args[0]}, {"action":"getVsubnet"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.getAllVsubnets =  function(resultHandler) {
      var __args = arguments;
      if (__args.length === 1 && typeof __args[0] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {}, {"action":"getAllVsubnets"}, function(err, result) { __args[0](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vsubnetId":__args[0]}, {"action":"deleteVsubnet"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"id":__args[0], "vsubnet":__args[1]}, {"action":"updateVsubnet"}, function(err, result) { __args[2](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vnode":__args[0]}, {"action":"addVnode"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vnodeId":__args[0]}, {"action":"getVnode"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.getAllVnodes =  function(resultHandler) {
      var __args = arguments;
      if (__args.length === 1 && typeof __args[0] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {}, {"action":"getAllVnodes"}, function(err, result) { __args[0](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vsubnetId":__args[0]}, {"action":"getVnodesByVsubnet"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vnodeId":__args[0]}, {"action":"deleteVnode"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"id":__args[0], "vnode":__args[1]}, {"action":"updateVnode"}, function(err, result) { __args[2](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vltp":__args[0]}, {"action":"addVltp"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vltpId":__args[0]}, {"action":"getVltp"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.getAllVltps =  function(resultHandler) {
      var __args = arguments;
      if (__args.length === 1 && typeof __args[0] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {}, {"action":"getAllVltps"}, function(err, result) { __args[0](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vnodeId":__args[0]}, {"action":"getVltpsByVnode"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vltpId":__args[0]}, {"action":"deleteVltp"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"id":__args[0], "vltp":__args[1]}, {"action":"updateVltp"}, function(err, result) { __args[2](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vctp":__args[0]}, {"action":"addVctp"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vctpId":__args[0]}, {"action":"getVctp"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.getAllVctps =  function(resultHandler) {
      var __args = arguments;
      if (__args.length === 1 && typeof __args[0] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {}, {"action":"getAllVctps"}, function(err, result) { __args[0](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vltpId":__args[0]}, {"action":"getVctpsByVltp"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vnodeId":__args[0]}, {"action":"getVctpsByVnode"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vlinkId":__args[0]}, {"action":"getVctpsByVlink"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vctpId":__args[0]}, {"action":"deleteVctp"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"id":__args[0], "vctp":__args[1]}, {"action":"updateVctp"}, function(err, result) { __args[2](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vlink":__args[0]}, {"action":"addVlink"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vlinkId":__args[0]}, {"action":"getVlink"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.getAllVlinks =  function(resultHandler) {
      var __args = arguments;
      if (__args.length === 1 && typeof __args[0] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {}, {"action":"getAllVlinks"}, function(err, result) { __args[0](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vsubnetId":__args[0]}, {"action":"getVlinksByVsubnet"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vlinkId":__args[0]}, {"action":"deleteVlink"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"id":__args[0], "vlink":__args[1]}, {"action":"updateVlink"}, function(err, result) { __args[2](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vlinkConn":__args[0]}, {"action":"addVlinkConn"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vlinkConnId":__args[0]}, {"action":"getVlinkConn"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.getAllVlinkConns =  function(resultHandler) {
      var __args = arguments;
      if (__args.length === 1 && typeof __args[0] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {}, {"action":"getAllVlinkConns"}, function(err, result) { __args[0](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vlinkId":__args[0]}, {"action":"getVlinkConnsByVlink"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vsubnetId":__args[0]}, {"action":"getVlinkConnsByVsubnet"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vlinkConnId":__args[0]}, {"action":"deleteVlinkConn"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"id":__args[0], "vlinkConn":__args[1]}, {"action":"updateVlinkConn"}, function(err, result) { __args[2](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vtrail":__args[0]}, {"action":"addVtrail"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vtrailId":__args[0]}, {"action":"getVtrail"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vtrailId":__args[0]}, {"action":"deleteVtrail"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.getAllVtrails =  function(resultHandler) {
      var __args = arguments;
      if (__args.length === 1 && typeof __args[0] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {}, {"action":"getAllVtrails"}, function(err, result) { __args[0](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vsubnetId":__args[0]}, {"action":"getVtrailsByVsubnet"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"id":__args[0], "vtrail":__args[1]}, {"action":"updateVtrail"}, function(err, result) { __args[2](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vxc":__args[0]}, {"action":"addVxc"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vxcId":__args[0]}, {"action":"getVxc"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.getAllVxcs =  function(resultHandler) {
      var __args = arguments;
      if (__args.length === 1 && typeof __args[0] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {}, {"action":"getAllVxcs"}, function(err, result) { __args[0](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vtrailId":__args[0]}, {"action":"getVxcsByVtrail"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vnodeId":__args[0]}, {"action":"getVxcsByVnode"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"vxcId":__args[0]}, {"action":"deleteVxc"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"id":__args[0], "vxc":__args[1]}, {"action":"updateVxc"}, function(err, result) { __args[2](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"prefixAnn":__args[0]}, {"action":"addPrefixAnn"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"prefixAnnId":__args[0]}, {"action":"getPrefixAnn"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.getAllPrefixAnns =  function(resultHandler) {
      var __args = arguments;
      if (__args.length === 1 && typeof __args[0] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {}, {"action":"getAllPrefixAnns"}, function(err, result) { __args[0](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"prefixAnnId":__args[0]}, {"action":"deletePrefixAnn"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param id {string} 
     @param prefixAnn {Object} 
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.updatePrefixAnn =  function(id, prefixAnn, resultHandler) {
      var __args = arguments;
      if (__args.length === 3 && typeof __args[0] === 'string' && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"id":__args[0], "prefixAnn":__args[1]}, {"action":"updatePrefixAnn"}, function(err, result) { __args[2](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param rte {Object} 
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.addRte =  function(rte, resultHandler) {
      var __args = arguments;
      if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"rte":__args[0]}, {"action":"addRte"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param rteId {string} 
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.getRte =  function(rteId, resultHandler) {
      var __args = arguments;
      if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"rteId":__args[0]}, {"action":"getRte"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.getAllRtes =  function(resultHandler) {
      var __args = arguments;
      if (__args.length === 1 && typeof __args[0] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {}, {"action":"getAllRtes"}, function(err, result) { __args[0](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param nodeId {string} 
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.getRtesByNode =  function(nodeId, resultHandler) {
      var __args = arguments;
      if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"nodeId":__args[0]}, {"action":"getRtesByNode"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param rteId {string} 
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.deleteRte =  function(rteId, resultHandler) {
      var __args = arguments;
      if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"rteId":__args[0]}, {"action":"deleteRte"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param id {string} 
     @param rte {Object} 
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.updateRte =  function(id, rte, resultHandler) {
      var __args = arguments;
      if (__args.length === 3 && typeof __args[0] === 'string' && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"id":__args[0], "rte":__args[1]}, {"action":"updateRte"}, function(err, result) { __args[2](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"face":__args[0]}, {"action":"addFace"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param linkConnId {string} 
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.generateFacesForLc =  function(linkConnId, resultHandler) {
      var __args = arguments;
      if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"linkConnId":__args[0]}, {"action":"generateFacesForLc"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"faceId":__args[0]}, {"action":"getFace"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param resultHandler {function} 
     @return {TopologyService}
     */
    this.getAllFaces =  function(resultHandler) {
      var __args = arguments;
      if (__args.length === 1 && typeof __args[0] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {}, {"action":"getAllFaces"}, function(err, result) { __args[0](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"nodeId":__args[0]}, {"action":"getFacesByNode"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
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
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"faceId":__args[0]}, {"action":"deleteFace"}, function(err, result) { __args[1](err, result && result.body); });
        return that;
      } else throw new TypeError('function invoked with invalid arguments');
    };

  };

  if (typeof exports !== 'undefined') {
    if (typeof module !== 'undefined' && module.exports) {
      exports = module.exports = TopologyService;
    } else {
      exports.TopologyService = TopologyService;
    }
  } else {
    return TopologyService;
  }
});