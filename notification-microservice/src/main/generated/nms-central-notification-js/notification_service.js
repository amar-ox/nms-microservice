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

  var __super_processReport = this.processReport;
  /**

   @public
   @param report {Object} 
   @param resultHandler {function} 
   */
  this.processReport =  function(report, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_notificationService["processReport(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(report), function(ar) {
        if (ar.succeeded()) {
          resultHandler(utils.convReturnJson(ar.result()), null);
        } else {
          resultHandler(null, ar.cause());
        }
      });
    } else if (typeof __super_processReport != 'undefined') {
      return __super_processReport.apply(this, __args);
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
NotificationService.REPORTS_ADDRESS = JNotificationService.REPORTS_ADDRESS;
NotificationService.UPDATE_ADDRESS = JNotificationService.UPDATE_ADDRESS;
module.exports = NotificationService;