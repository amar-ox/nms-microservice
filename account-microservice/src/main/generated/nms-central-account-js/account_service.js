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

/** @module nms-central-account-js/account_service */
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JAccountService = Java.type('io.nms.central.microservice.account.AccountService');
var Account = Java.type('io.nms.central.microservice.account.model.Account');
var Agent = Java.type('io.nms.central.microservice.account.model.Agent');
var User = Java.type('io.nms.central.microservice.account.model.User');

/**
 A service interface managing products.
 <p>
 This service is an event bus service (aka. service proxy)
 </p>

 @class
*/
var AccountService = function(j_val) {

  var j_accountService = j_val;
  var that = this;

  var __super_initializePersistence = this.initializePersistence;
  var __super_saveUser = this.saveUser;
  var __super_retrieveUser = this.retrieveUser;
  var __super_retrieveAllUsers = this.retrieveAllUsers;
  var __super_retrieveUsersByRole = this.retrieveUsersByRole;
  var __super_removeUser = this.removeUser;
  var __super_authenticateUser = this.authenticateUser;
  var __super_saveAgent = this.saveAgent;
  var __super_retrieveAgent = this.retrieveAgent;
  var __super_retrieveAllAgents = this.retrieveAllAgents;
  var __super_removeAgent = this.removeAgent;
  var __super_authenticateAgent = this.authenticateAgent;
  /**

   @public
   @param resultHandler {function} 
   */
  this.initializePersistence =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_accountService["initializePersistence(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](null, null);
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
   @param user {Object} 
   @param resultHandler {function} 
   */
  this.saveUser =  function(user, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_accountService["saveUser(io.nms.central.microservice.account.model.User,io.vertx.core.Handler)"](__args[0]  != null ? new User(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_saveUser != 'undefined') {
      return __super_saveUser.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param username {string} 
   @param resultHandler {function} 
   */
  this.retrieveUser =  function(username, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_accountService["retrieveUser(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_retrieveUser != 'undefined') {
      return __super_retrieveUser.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   */
  this.retrieveAllUsers =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_accountService["retrieveAllUsers(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      });
    } else if (typeof __super_retrieveAllUsers != 'undefined') {
      return __super_retrieveAllUsers.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param role {string} 
   @param resultHandler {function} 
   */
  this.retrieveUsersByRole =  function(role, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_accountService["retrieveUsersByRole(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_retrieveUsersByRole != 'undefined') {
      return __super_retrieveUsersByRole.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param username {string} 
   @param resultHandler {function} 
   */
  this.removeUser =  function(username, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_accountService["removeUser(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_removeUser != 'undefined') {
      return __super_removeUser.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param username {string} 
   @param password {string} 
   @param resultHandler {function} 
   */
  this.authenticateUser =  function(username, password, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'string' && typeof __args[1] === 'string' && typeof __args[2] === 'function') {
      j_accountService["authenticateUser(java.lang.String,java.lang.String,io.vertx.core.Handler)"](__args[0], __args[1], function(ar) {
        if (ar.succeeded()) {
          __args[2](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[2](null, ar.cause());
        }
      });
    } else if (typeof __super_authenticateUser != 'undefined') {
      return __super_authenticateUser.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param agent {Object} 
   @param resultHandler {function} 
   */
  this.saveAgent =  function(agent, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_accountService["saveAgent(io.nms.central.microservice.account.model.Agent,io.vertx.core.Handler)"](__args[0]  != null ? new Agent(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_saveAgent != 'undefined') {
      return __super_saveAgent.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param username {string} 
   @param resultHandler {function} 
   */
  this.retrieveAgent =  function(username, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_accountService["retrieveAgent(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_retrieveAgent != 'undefined') {
      return __super_retrieveAgent.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   */
  this.retrieveAllAgents =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_accountService["retrieveAllAgents(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      });
    } else if (typeof __super_retrieveAllAgents != 'undefined') {
      return __super_retrieveAllAgents.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param username {string} 
   @param resultHandler {function} 
   */
  this.removeAgent =  function(username, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_accountService["removeAgent(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_removeAgent != 'undefined') {
      return __super_removeAgent.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param username {string} 
   @param password {string} 
   @param resultHandler {function} 
   */
  this.authenticateAgent =  function(username, password, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'string' && typeof __args[1] === 'string' && typeof __args[2] === 'function') {
      j_accountService["authenticateAgent(java.lang.String,java.lang.String,io.vertx.core.Handler)"](__args[0], __args[1], function(ar) {
        if (ar.succeeded()) {
          __args[2](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[2](null, ar.cause());
        }
      });
    } else if (typeof __super_authenticateAgent != 'undefined') {
      return __super_authenticateAgent.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_accountService;
};

AccountService._jclass = utils.getJavaClass("io.nms.central.microservice.account.AccountService");
AccountService._jtype = {accept: function(obj) {
    return AccountService._jclass.isInstance(obj._jdel);
  },wrap: function(jdel) {
    var obj = Object.create(AccountService.prototype, {});
    AccountService.apply(obj, arguments);
    return obj;
  },
  unwrap: function(obj) {
    return obj._jdel;
  }
};
AccountService._create = function(jdel) {var obj = Object.create(AccountService.prototype, {});
  AccountService.apply(obj, arguments);
  return obj;
}
AccountService.SERVICE_NAME = JAccountService.SERVICE_NAME;
AccountService.SERVICE_ADDRESS = JAccountService.SERVICE_ADDRESS;
AccountService.UI_ADDRESS = JAccountService.UI_ADDRESS;
module.exports = AccountService;