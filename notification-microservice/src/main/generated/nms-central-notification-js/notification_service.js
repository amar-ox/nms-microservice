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
var Event = Java.type('io.nms.central.microservice.notification.model.Event');
var Fault = Java.type('io.nms.central.microservice.notification.model.Fault');

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
  var __super_retrieveAllStatus = this.retrieveAllStatus;
  var __super_removeStatus = this.removeStatus;
  var __super_saveEvent = this.saveEvent;
  var __super_retrieveAllEvents = this.retrieveAllEvents;
  var __super_removeEvent = this.removeEvent;
  var __super_saveFault = this.saveFault;
  var __super_retrieveAllFaults = this.retrieveAllFaults;
  var __super_removeFault = this.removeFault;
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
   @param resultHandler {function} 
   */
  this.retrieveAllStatus =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_notificationService["retrieveAllStatus(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      });
    } else if (typeof __super_retrieveAllStatus != 'undefined') {
      return __super_retrieveAllStatus.apply(this, __args);
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
   @param event {Object} 
   @param resultHandler {function} 
   */
  this.saveEvent =  function(event, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_notificationService["saveEvent(io.nms.central.microservice.notification.model.Event,io.vertx.core.Handler)"](__args[0]  != null ? new Event(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_saveEvent != 'undefined') {
      return __super_saveEvent.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   */
  this.retrieveAllEvents =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_notificationService["retrieveAllEvents(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      });
    } else if (typeof __super_retrieveAllEvents != 'undefined') {
      return __super_retrieveAllEvents.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param id {string} 
   @param resultHandler {function} 
   */
  this.removeEvent =  function(id, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_notificationService["removeEvent(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_removeEvent != 'undefined') {
      return __super_removeEvent.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param fault {Object} 
   @param resultHandler {function} 
   */
  this.saveFault =  function(fault, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_notificationService["saveFault(io.nms.central.microservice.notification.model.Fault,io.vertx.core.Handler)"](__args[0]  != null ? new Fault(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_saveFault != 'undefined') {
      return __super_saveFault.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   */
  this.retrieveAllFaults =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_notificationService["retrieveAllFaults(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      });
    } else if (typeof __super_retrieveAllFaults != 'undefined') {
      return __super_retrieveAllFaults.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param id {string} 
   @param resultHandler {function} 
   */
  this.removeFault =  function(id, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_notificationService["removeFault(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_removeFault != 'undefined') {
      return __super_removeFault.apply(this, __args);
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