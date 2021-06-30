package io.nms.central.microservice.topology.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class UdpConnInfo extends ConnInfo {

	private int srcPort;
	private int destPort;

	/*-----------------------------------------------*/

	public UdpConnInfo() {}
	public UdpConnInfo(JsonObject json) {}

	public JsonObject toJson() {
		return new JsonObject(Json.encode(this));
	}

	/*-----------------------------------------------*/

	public int getDestPort() {
		return destPort;
	}
	public void setDestPort(int destPort) {
		this.destPort = destPort;
	}

	public int getSrcPort() {
		return srcPort;
	}
	public void setSrcPort(int srcPort) {
		this.srcPort = srcPort;
	}
}
