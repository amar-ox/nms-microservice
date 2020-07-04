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

  initializePersistence(resultHandler: (err: any, result: Array<number>) => any) : TopologyService;

  addVsubnet(vsubnet: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  getVsubnet(vsubnetId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  getAllVsubnets(resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  deleteVsubnet(vsubnetId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  updateVsubnet(id: string, vsubnet: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  addVnode(vnode: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  getVnode(vnodeId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  getAllVnodes(resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  getVnodesByVsubnet(vsubnetId: string, resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  deleteVnode(vnodeId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  updateVnode(id: string, vnode: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  addVltp(vltp: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  getVltp(vltpId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  getAllVltps(resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  getVltpsByVnode(vnodeId: string, resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  deleteVltp(vltpId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  updateVltp(id: string, vltp: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  addVctp(vctp: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  getVctp(vctpId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  getAllVctps(resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  getVctpsByVltp(vltpId: string, resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  getVctpsByVnode(vnodeId: string, resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  deleteVctp(vctpId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  updateVctp(id: string, vctp: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  addVlink(vlink: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  getVlink(vlinkId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  getAllVlinks(resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  getVlinksByVsubnet(vsubnetId: string, resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  deleteVlink(vlinkId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  updateVlink(id: string, vlink: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  addVlinkConn(vlinkConn: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  getVlinkConn(vlinkConnId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  getAllVlinkConns(resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  getVlinkConnsByVlink(vlinkId: string, resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  getVlinkConnsByVsubnet(vsubnetId: string, resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  deleteVlinkConn(vlinkConnId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  updateVlinkConn(id: string, vlinkConn: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  addVtrail(vtrail: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  getVtrail(vtrailId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  deleteVtrail(vtrailId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  getAllVtrails(resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  getVtrailsByVsubnet(vsubnetId: string, resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  updateVtrail(id: string, vtrail: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  addVxc(vxc: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  getVxc(vxcId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  getAllVxcs(resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  getVxcsByVtrail(vtrailId: string, resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  getVxcsByVnode(vnodeId: string, resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  deleteVxc(vxcId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  updateVxc(id: string, vxc: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  addPrefixAnn(prefixAnn: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  getPrefixAnn(prefixAnnId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  getAllPrefixAnns(resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  deletePrefixAnn(prefixAnnId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  updatePrefixAnn(id: string, prefixAnn: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  addRte(rte: any, resultHandler: (err: any, result: any) => any) : TopologyService;

  getRte(rteId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  getAllRtes(resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  getRtesByNode(nodeId: string, resultHandler: (err: any, result: Array<any>) => any) : TopologyService;

  deleteRte(rteId: string, resultHandler: (err: any, result: any) => any) : TopologyService;

  updateRte(id: string, rte: any, resultHandler: (err: any, result: any) => any) : TopologyService;
}