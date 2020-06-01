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


/**
 A service interface managing products.
 <p>
 This service is an event bus service (aka. service proxy)
 </p>

 @class
*/
export default class TopologyService {

  constructor (eb: any, address: string);

  addNode(node: any, resultHandler: (err: any, result: any) => any) : void;

  getNode(nodeId: string, resultHandler: (err: any, result: any) => any) : void;

  getNodes(nodeIds: Array<string>, resultHandler: (err: any, result: Array<any>) => any) : void;

  getAllNodes(resultHandler: (err: any, result: Array<any>) => any) : void;

  deleteNode(nodeId: string, resultHandler: (err: any, result: any) => any) : void;

  addLtp(ltp: any, resultHandler: (err: any, result: any) => any) : void;

  getLtp(ltpId: string, resultHandler: (err: any, result: any) => any) : void;

  deleteLtp(ltpId: string, resultHandler: (err: any, result: any) => any) : void;

  addCtp(ctp: any, resultHandler: (err: any, result: any) => any) : void;

  getCtp(ctpId: string, resultHandler: (err: any, result: any) => any) : void;

  deleteCtp(ctpId: string, resultHandler: (err: any, result: any) => any) : void;

  addLink(linke: any, resultHandler: (err: any, result: any) => any) : void;

  getLink(linkId: string, resultHandler: (err: any, result: any) => any) : void;

  getAllLinks(resultHandler: (err: any, result: Array<any>) => any) : void;

  deleteLink(linkId: string, resultHandler: (err: any, result: any) => any) : void;

  addLinkConn(linkConn: any, resultHandler: (err: any, result: any) => any) : void;

  getLinkConn(linkConnId: string, resultHandler: (err: any, result: any) => any) : void;

  getAllLinkConns(resultHandler: (err: any, result: Array<any>) => any) : void;

  deleteLinkConn(linkConnId: string, resultHandler: (err: any, result: any) => any) : void;

  getTopology(level: number, resultHandler: (err: any, result: any) => any) : void;
}