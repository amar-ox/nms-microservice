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

/** @module nms-central-configuration-js/configuration_service */
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JConfigurationService = Java.type('io.nms.central.microservice.configuration.ConfigurationService');
var Face = Java.type('io.nms.central.microservice.topology.model.Face');
var Route = Java.type('io.nms.central.microservice.topology.model.Route');
var ConfigObj = Java.type('io.nms.central.microservice.configuration.model.ConfigObj');
var Vnode = Java.type('io.nms.central.microservice.topology.model.Vnode');

/**
 A service interface managing products.
 <p>
 This service is an event bus service (aka. service proxy)
 </p>

 @class
*/
var ConfigurationService = function(j_val) {

  var j_configurationService = j_val;
  var that = this;

  var __super_initializePersistence = this.initializePersistence;
  var __super_getCandidateConfig = this.getCandidateConfig;
  var __super_removeCandidateConfig = this.removeCandidateConfig;
  var __super_upsertRunningConfig = this.upsertRunningConfig;
  var __super_updateRunningConfig = this.updateRunningConfig;
  var __super_getRunningConfig = this.getRunningConfig;
  var __super_removeRunningConfig = this.removeRunningConfig;
  var __super_computeConfigurations = this.computeConfigurations;
  var __super_upsertCandidateConfigs = this.upsertCandidateConfigs;
  /**

   @public
   @param resultHandler {function} 
   */
  this.initializePersistence =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_configurationService["initializePersistence(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](ar.result(), null);
        } else {
          __args[0](null, ar.cause());
        }
      });
    } else if (typeof __super_initializePersistence != 'undefined') {
      return __super_initializePersistence.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param nodeId {number} 
   @param resultHandler {function} 
   */
  this.getCandidateConfig =  function(nodeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] ==='number' && typeof __args[1] === 'function') {
      j_configurationService["getCandidateConfig(int,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_getCandidateConfig != 'undefined') {
      return __super_getCandidateConfig.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param nodeId {number} 
   @param resultHandler {function} 
   */
  this.removeCandidateConfig =  function(nodeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] ==='number' && typeof __args[1] === 'function') {
      j_configurationService["removeCandidateConfig(int,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_removeCandidateConfig != 'undefined') {
      return __super_removeCandidateConfig.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
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
      j_configurationService["upsertRunningConfig(int,io.nms.central.microservice.configuration.model.ConfigObj,io.vertx.core.Handler)"](__args[0], __args[1]  != null ? new ConfigObj(new JsonObject(Java.asJSONCompatible(__args[1]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[2](null, null);
        } else {
          __args[2](null, ar.cause());
        }
      });
    } else if (typeof __super_upsertRunningConfig != 'undefined') {
      return __super_upsertRunningConfig.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
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
      j_configurationService["updateRunningConfig(int,io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](__args[0], utils.convParamJsonArray(__args[1]), function(ar) {
        if (ar.succeeded()) {
          __args[2](null, null);
        } else {
          __args[2](null, ar.cause());
        }
      });
    } else if (typeof __super_updateRunningConfig != 'undefined') {
      return __super_updateRunningConfig.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param nodeId {number} 
   @param resultHandler {function} 
   */
  this.getRunningConfig =  function(nodeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] ==='number' && typeof __args[1] === 'function') {
      j_configurationService["getRunningConfig(int,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_getRunningConfig != 'undefined') {
      return __super_getRunningConfig.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param nodeId {number} 
   @param resultHandler {function} 
   */
  this.removeRunningConfig =  function(nodeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] ==='number' && typeof __args[1] === 'function') {
      j_configurationService["removeRunningConfig(int,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_removeRunningConfig != 'undefined') {
      return __super_removeRunningConfig.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
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
      j_configurationService["computeConfigurations(java.util.List,java.util.List,java.util.List,io.vertx.core.Handler)"](utils.convParamListDataObject(__args[0], function(json) { return new Route(json); }), utils.convParamListDataObject(__args[1], function(json) { return new Face(json); }), utils.convParamListDataObject(__args[2], function(json) { return new Vnode(json); }), function(ar) {
        if (ar.succeeded()) {
          __args[3](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[3](null, ar.cause());
        }
      });
    } else if (typeof __super_computeConfigurations != 'undefined') {
      return __super_computeConfigurations.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param configs {Array.<Object>} 
   @param resultHandler {function} 
   */
  this.upsertCandidateConfigs =  function(configs, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_configurationService["upsertCandidateConfigs(java.util.List,io.vertx.core.Handler)"](utils.convParamListDataObject(__args[0], function(json) { return new ConfigObj(json); }), function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_upsertCandidateConfigs != 'undefined') {
      return __super_upsertCandidateConfigs.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_configurationService;
};

ConfigurationService._jclass = utils.getJavaClass("io.nms.central.microservice.configuration.ConfigurationService");
ConfigurationService._jtype = {accept: function(obj) {
    return ConfigurationService._jclass.isInstance(obj._jdel);
  },wrap: function(jdel) {
    var obj = Object.create(ConfigurationService.prototype, {});
    ConfigurationService.apply(obj, arguments);
    return obj;
  },
  unwrap: function(obj) {
    return obj._jdel;
  }
};
ConfigurationService._create = function(jdel) {var obj = Object.create(ConfigurationService.prototype, {});
  ConfigurationService.apply(obj, arguments);
  return obj;
}
ConfigurationService.SERVICE_NAME = JConfigurationService.SERVICE_NAME;
ConfigurationService.SERVICE_ADDRESS = JConfigurationService.SERVICE_ADDRESS;
ConfigurationService.UI_ADDRESS = JConfigurationService.UI_ADDRESS;
module.exports = ConfigurationService;