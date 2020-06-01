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
var Ctp = Java.type('io.nms.central.microservice.topology.model.Ctp');
var LinkConn = Java.type('io.nms.central.microservice.topology.model.LinkConn');
var Ltp = Java.type('io.nms.central.microservice.topology.model.Ltp');
var Node = Java.type('io.nms.central.microservice.topology.model.Node');
var Topology = Java.type('io.nms.central.microservice.topology.model.Topology');
var Link = Java.type('io.nms.central.microservice.topology.model.Link');

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

  var __super_addNode = this.addNode;
  var __super_getNode = this.getNode;
  var __super_getNodes = this.getNodes;
  var __super_getAllNodes = this.getAllNodes;
  var __super_deleteNode = this.deleteNode;
  var __super_addLtp = this.addLtp;
  var __super_getLtp = this.getLtp;
  var __super_deleteLtp = this.deleteLtp;
  var __super_addCtp = this.addCtp;
  var __super_getCtp = this.getCtp;
  var __super_deleteCtp = this.deleteCtp;
  var __super_addLink = this.addLink;
  var __super_getLink = this.getLink;
  var __super_getAllLinks = this.getAllLinks;
  var __super_deleteLink = this.deleteLink;
  var __super_addLinkConn = this.addLinkConn;
  var __super_getLinkConn = this.getLinkConn;
  var __super_getAllLinkConns = this.getAllLinkConns;
  var __super_deleteLinkConn = this.deleteLinkConn;
  var __super_getTopology = this.getTopology;
  /**
   Add a node to the persistence.

   @public
   @param node {Object} a node entity that we want to add 
   @param resultHandler {function} the result handler will be called as soon as the product has been added. The async result indicates whether the operation was successful or not. 
   */
  this.addNode =  function(node, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addNode(io.nms.central.microservice.topology.model.Node,io.vertx.core.Handler)"](__args[0]  != null ? new Node(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_addNode != 'undefined') {
      return __super_addNode.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param nodeId {string} 
   @param resultHandler {function} 
   */
  this.getNode =  function(nodeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getNode(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_getNode != 'undefined') {
      return __super_getNode.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param nodeIds {Array.<string>} 
   @param resultHandler {function} 
   */
  this.getNodes =  function(nodeIds, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_topologyService["getNodes(java.util.List,io.vertx.core.Handler)"](utils.convParamListBasicOther(__args[0]), function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_getNodes != 'undefined') {
      return __super_getNodes.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   */
  this.getAllNodes =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["getAllNodes(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      });
    } else if (typeof __super_getAllNodes != 'undefined') {
      return __super_getAllNodes.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param nodeId {string} 
   @param resultHandler {function} 
   */
  this.deleteNode =  function(nodeId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteNode(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_deleteNode != 'undefined') {
      return __super_deleteNode.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param ltp {Object} 
   @param resultHandler {function} 
   */
  this.addLtp =  function(ltp, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addLtp(io.nms.central.microservice.topology.model.Ltp,io.vertx.core.Handler)"](__args[0]  != null ? new Ltp(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_addLtp != 'undefined') {
      return __super_addLtp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param ltpId {string} 
   @param resultHandler {function} 
   */
  this.getLtp =  function(ltpId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getLtp(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_getLtp != 'undefined') {
      return __super_getLtp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param ltpId {string} 
   @param resultHandler {function} 
   */
  this.deleteLtp =  function(ltpId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteLtp(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_deleteLtp != 'undefined') {
      return __super_deleteLtp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param ctp {Object} 
   @param resultHandler {function} 
   */
  this.addCtp =  function(ctp, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addCtp(io.nms.central.microservice.topology.model.Ctp,io.vertx.core.Handler)"](__args[0]  != null ? new Ctp(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_addCtp != 'undefined') {
      return __super_addCtp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param ctpId {string} 
   @param resultHandler {function} 
   */
  this.getCtp =  function(ctpId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getCtp(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_getCtp != 'undefined') {
      return __super_getCtp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param ctpId {string} 
   @param resultHandler {function} 
   */
  this.deleteCtp =  function(ctpId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteCtp(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_deleteCtp != 'undefined') {
      return __super_deleteCtp.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param linke {Object} 
   @param resultHandler {function} 
   */
  this.addLink =  function(linke, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addLink(io.nms.central.microservice.topology.model.Link,io.vertx.core.Handler)"](__args[0]  != null ? new Link(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_addLink != 'undefined') {
      return __super_addLink.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param linkId {string} 
   @param resultHandler {function} 
   */
  this.getLink =  function(linkId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getLink(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_getLink != 'undefined') {
      return __super_getLink.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   */
  this.getAllLinks =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["getAllLinks(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      });
    } else if (typeof __super_getAllLinks != 'undefined') {
      return __super_getAllLinks.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param linkId {string} 
   @param resultHandler {function} 
   */
  this.deleteLink =  function(linkId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteLink(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_deleteLink != 'undefined') {
      return __super_deleteLink.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param linkConn {Object} 
   @param resultHandler {function} 
   */
  this.addLinkConn =  function(linkConn, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_topologyService["addLinkConn(io.nms.central.microservice.topology.model.LinkConn,io.vertx.core.Handler)"](__args[0]  != null ? new LinkConn(new JsonObject(Java.asJSONCompatible(__args[0]))) : null, function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_addLinkConn != 'undefined') {
      return __super_addLinkConn.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param linkConnId {string} 
   @param resultHandler {function} 
   */
  this.getLinkConn =  function(linkConnId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["getLinkConn(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_getLinkConn != 'undefined') {
      return __super_getLinkConn.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   */
  this.getAllLinkConns =  function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_topologyService["getAllLinkConns(io.vertx.core.Handler)"](function(ar) {
        if (ar.succeeded()) {
          __args[0](utils.convReturnListSetDataObject(ar.result()), null);
        } else {
          __args[0](null, ar.cause());
        }
      });
    } else if (typeof __super_getAllLinkConns != 'undefined') {
      return __super_getAllLinkConns.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param linkConnId {string} 
   @param resultHandler {function} 
   */
  this.deleteLinkConn =  function(linkConnId, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_topologyService["deleteLinkConn(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](null, null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_deleteLinkConn != 'undefined') {
      return __super_deleteLinkConn.apply(this, __args);
    }
    else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param level {number} 
   @param resultHandler {function} 
   */
  this.getTopology =  function(level, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] ==='number' && typeof __args[1] === 'function') {
      j_topologyService["getTopology(int,io.vertx.core.Handler)"](__args[0], function(ar) {
        if (ar.succeeded()) {
          __args[1](utils.convReturnDataObject(ar.result()), null);
        } else {
          __args[1](null, ar.cause());
        }
      });
    } else if (typeof __super_getTopology != 'undefined') {
      return __super_getTopology.apply(this, __args);
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