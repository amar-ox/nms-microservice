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

/** @module nms-central-routing-js/routing_service */
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JRoutingService = Java.type('io.nms.central.microservice.routing.RoutingService');

/**
 A service interface managing products.
 <p>
 This service is an event bus service (aka. service proxy)
 </p>

 @class
*/
var RoutingService = function(j_val) {

  var j_routingService = j_val;
  var that = this;

  var __super_initializePersistence = this.initializePersistence;
  /**

   @public
   @param resultHandler {function} 
   @return {RoutingService}
   */
  this.initializePersistence =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_routingService["initializePersistence(io.vertx.core.Handler)"](function(ar) {
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
  this._jdel = j_routingService;
};

RoutingService._jclass = utils.getJavaClass("io.nms.central.microservice.routing.RoutingService");
RoutingService._jtype = {accept: function(obj) {
    return RoutingService._jclass.isInstance(obj._jdel);
  },wrap: function(jdel) {
    var obj = Object.create(RoutingService.prototype, {});
    RoutingService.apply(obj, arguments);
    return obj;
  },
  unwrap: function(obj) {
    return obj._jdel;
  }
};
RoutingService._create = function(jdel) {var obj = Object.create(RoutingService.prototype, {});
  RoutingService.apply(obj, arguments);
  return obj;
}
RoutingService.SERVICE_NAME = JRoutingService.SERVICE_NAME;
RoutingService.SERVICE_ADDRESS = JRoutingService.SERVICE_ADDRESS;
module.exports = RoutingService;