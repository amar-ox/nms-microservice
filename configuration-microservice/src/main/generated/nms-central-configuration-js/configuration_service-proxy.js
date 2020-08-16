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

/// <reference path="./configuration_service-proxy.d.ts" />

/** @module nms-central-configuration-js/configuration_service */
!function (factory) {
  if (typeof require === 'function' && typeof module !== 'undefined') {
    factory();
  } else if (typeof define === 'function' && define.amd) {
    // AMD loader
    define('nms-central-configuration-js/configuration_service-proxy', [], factory);
  } else {
    // plain old include
    ConfigurationService = factory();
  }
}(function () {

  /**
   A service interface managing products.
   <p>
   This service is an event bus service (aka. service proxy)
   </p>

   @class
  */
  var ConfigurationService = function(eb, address) {
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
     */
    this.initializePersistence =  function(resultHandler) {
      var __args = arguments;
      if (__args.length === 1 && typeof __args[0] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {}, {"action":"initializePersistence"}, function(err, result) { __args[0](err, result && result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param nodeId {number} 
     @param resultHandler {function} 
     */
    this.getCandidateConfig =  function(nodeId, resultHandler) {
      var __args = arguments;
      if (__args.length === 2 && typeof __args[0] ==='number' && typeof __args[1] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"nodeId":__args[0]}, {"action":"getCandidateConfig"}, function(err, result) { __args[1](err, result && result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param nodeId {number} 
     @param resultHandler {function} 
     */
    this.removeCandidateConfig =  function(nodeId, resultHandler) {
      var __args = arguments;
      if (__args.length === 2 && typeof __args[0] ==='number' && typeof __args[1] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"nodeId":__args[0]}, {"action":"removeCandidateConfig"}, function(err, result) { __args[1](err, result && result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param nodeId {number} 
     @param config {Object} 
     @param resultHandler {function} 
     */
    this.upsertRunningConfig =  function(nodeId, config, resultHandler) {
      var __args = arguments;
      if (__args.length === 3 && typeof __args[0] ==='number' && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"nodeId":__args[0], "config":__args[1]}, {"action":"upsertRunningConfig"}, function(err, result) { __args[2](err, result && result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param nodeId {number} 
     @param patch {Array} 
     @param resultHandler {function} 
     */
    this.updateRunningConfig =  function(nodeId, patch, resultHandler) {
      var __args = arguments;
      if (__args.length === 3 && typeof __args[0] ==='number' && typeof __args[1] === 'object' && __args[1] instanceof Array && typeof __args[2] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"nodeId":__args[0], "patch":__args[1]}, {"action":"updateRunningConfig"}, function(err, result) { __args[2](err, result && result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param nodeId {number} 
     @param resultHandler {function} 
     */
    this.getRunningConfig =  function(nodeId, resultHandler) {
      var __args = arguments;
      if (__args.length === 2 && typeof __args[0] ==='number' && typeof __args[1] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"nodeId":__args[0]}, {"action":"getRunningConfig"}, function(err, result) { __args[1](err, result && result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param nodeId {number} 
     @param resultHandler {function} 
     */
    this.removeRunningConfig =  function(nodeId, resultHandler) {
      var __args = arguments;
      if (__args.length === 2 && typeof __args[0] ==='number' && typeof __args[1] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"nodeId":__args[0]}, {"action":"removeRunningConfig"}, function(err, result) { __args[1](err, result && result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param routes {Array.<Object>} 
     @param faces {Array.<Object>} 
     @param nodes {Array.<Object>} 
     @param resultHandler {function} 
     */
    this.computeConfigurations =  function(routes, faces, nodes, resultHandler) {
      var __args = arguments;
      if (__args.length === 4 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'object' && __args[1] instanceof Array && typeof __args[2] === 'object' && __args[2] instanceof Array && typeof __args[3] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"routes":__args[0], "faces":__args[1], "nodes":__args[2]}, {"action":"computeConfigurations"}, function(err, result) { __args[3](err, result && result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param configs {Array.<Object>} 
     @param resultHandler {function} 
     */
    this.upsertCandidateConfigs =  function(configs, resultHandler) {
      var __args = arguments;
      if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"configs":__args[0]}, {"action":"upsertCandidateConfigs"}, function(err, result) { __args[1](err, result && result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

  };

  if (typeof exports !== 'undefined') {
    if (typeof module !== 'undefined' && module.exports) {
      exports = module.exports = ConfigurationService;
    } else {
      exports.ConfigurationService = ConfigurationService;
    }
  } else {
    return ConfigurationService;
  }
});