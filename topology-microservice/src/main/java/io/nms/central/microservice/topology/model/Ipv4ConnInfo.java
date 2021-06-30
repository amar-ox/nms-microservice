package io.nms.central.microservice.topology.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Ipv4ConnInfo extends ConnInfo {

	private String address;
	private String netmask;

	/*-----------------------------------------------*/

	public Ipv4ConnInfo() {}
	public Ipv4ConnInfo(JsonObject json) {}

	public JsonObject toJson() {
		return new JsonObject(Json.encode(this));
	}

	/*-----------------------------------------------*/

	public String getNetmask() {
		return netmask;
	}
	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
