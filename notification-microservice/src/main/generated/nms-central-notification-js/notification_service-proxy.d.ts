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
 A service interface managing notifications.
 <p>
 This service is an event bus service (aka. service proxy)
 </p>

 @class
*/
export default class NotificationService {

  constructor (eb: any, address: string);

  processStatus(status: any, resultHandler: (err: any, result: any) => any) : void;

  saveStatus(status: any, resultHandler: (err: any, result: any) => any) : void;

  retrieveAllStatus(resultHandler: (err: any, result: Array<any>) => any) : void;

  removeStatus(id: string, resultHandler: (err: any, result: any) => any) : void;

  saveEvent(event: any, resultHandler: (err: any, result: any) => any) : void;

  retrieveAllEvents(resultHandler: (err: any, result: Array<any>) => any) : void;

  removeEvent(id: string, resultHandler: (err: any, result: any) => any) : void;

  saveFault(fault: any, resultHandler: (err: any, result: any) => any) : void;

  retrieveAllFaults(resultHandler: (err: any, result: Array<any>) => any) : void;

  removeFault(id: string, resultHandler: (err: any, result: any) => any) : void;
}