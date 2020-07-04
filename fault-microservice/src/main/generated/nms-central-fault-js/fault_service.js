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

/** @module nms-central-fault-js/fault_service */
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JFaultService = Java.type('io.nms.central.microservice.fault.FaultService');

/**
 A service interface managing products.
 <p>
 This service is an event bus service (aka. service proxy)
 </p>

 @class
*/
var FaultService = function(j_val) {

  var j_faultService = j_val;
  var that = this;

  var __super_initializePersistence = this.initializePersistence;
  /**

   @public
   @param resultHandler {function} 
   @return {FaultService}
   */
  this.initializePersistence =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_faultService["initializePersistence(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          resultHandler(ar.result(), null);
        } else {
          resultHandler(null, ar.cause());
        }
      }) ;
      return that;
    } else if (typeof __super_initializePersistence != 'undefined') {
      return __super_initializePersistence.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_faultService;
};

FaultService._jclass = utils.getJavaClass("io.nms.central.microservice.fault.FaultService");
FaultService._jtype = {accept: function(obj) {
    return FaultService._jclass.isInstance(obj._jdel);
  },wrap: function(jdel) {
    var obj = Object.create(FaultService.prototype, {});
    FaultService.apply(obj, arguments);
    return obj;
  },
  unwrap: function(obj) {
    return obj._jdel;
  }
};
FaultService._create = function(jdel) {var obj = Object.create(FaultService.prototype, {});
  FaultService.apply(obj, arguments);
  return obj;
}
FaultService.SERVICE_NAME = JFaultService.SERVICE_NAME;
FaultService.SERVICE_ADDRESS = JFaultService.SERVICE_ADDRESS;
module.exports = FaultService;