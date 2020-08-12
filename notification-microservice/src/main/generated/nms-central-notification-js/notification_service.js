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

/** @module nms-central-notification-js/notification_service */
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JNotificationService = Java.type('io.nms.central.microservice.notification.NotificationService');
var Status = Java.type('io.nms.central.microservice.notification.model.Status');

/**
 A service interface managing products.
 <p>
 This service is an event bus service (aka. service proxy)
 </p>

 @class
*/
var NotificationService = function(j_val) {

  var j_notificationService = j_val;
  var that = this;

  var __super_processStatus = this.processStatus;
  var __super_saveStatus = this.saveStatus;
  var __super_retrieveStatus = this.retrieveStatus;
  var __super_removeStatus = this.removeStatus;
  var __super_removeAllStatus = this.removeAllStatus;
  /**

   @public
   @param status {Object} 
   @param resultHandler {function} 
   */
  this.processStatus =  function(status, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_notificationService["processStatus(io.nms.central.microservice.notification.model.Status,io.vertx.core.Handler)"](__args[0]  != null ? new Status(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_processStatus != 'undefined') {
      return __super_processStatus.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param status {Object} 
   @param resultHandler {function} 
   */
  this.saveStatus =  function(status, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_notificationService["saveStatus(io.nms.central.microservice.notification.model.Status,io.vertx.core.Handler)"](__args[0]  != null ? new Status(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_saveStatus != 'undefined') {
      return __super_saveStatus.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param id {string} 
   @param resultHandler {function} 
   */
  this.retrieveStatus =  function(id, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_notificationService["retrieveStatus(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_retrieveStatus != 'undefined') {
      return __super_retrieveStatus.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param id {string} 
   @param resultHandler {function} 
   */
  this.removeStatus =  function(id, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_notificationService["removeStatus(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_removeStatus != 'undefined') {
      return __super_removeStatus.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   */
  this.removeAllStatus =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_notificationService["removeAllStatus(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](null, null);
        } else {
          __args[0](null, ar.cause());
        }
      });
    } else if (typeof __super_removeAllStatus != 'undefined') {
      return __super_removeAllStatus.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_notificationService;
};

NotificationService._jclass = utils.getJavaClass("io.nms.central.microservice.notification.NotificationService");
NotificationService._jtype = {accept: function(obj) {
    return NotificationService._jclass.isInstance(obj._jdel);
  },wrap: function(jdel) {
    var obj = Object.create(NotificationService.prototype, {});
    NotificationService.apply(obj, arguments);
    return obj;
  },
  unwrap: function(obj) {
    return obj._jdel;
  }
};
NotificationService._create = function(jdel) {var obj = Object.create(NotificationService.prototype, {});
  NotificationService.apply(obj, arguments);
  return obj;
}
NotificationService.SERVICE_NAME = JNotificationService.SERVICE_NAME;
NotificationService.SERVICE_ADDRESS = JNotificationService.SERVICE_ADDRESS;
NotificationService.STATUS_ADDRESS = JNotificationService.STATUS_ADDRESS;
module.exports = NotificationService;