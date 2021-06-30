package io.nms.central.microservice.topology.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Ipv6ConnInfo extends ConnInfo {

	private String address;

	/*-----------------------------------------------*/

	public Ipv6ConnInfo() {}
	public Ipv6ConnInfo(JsonObject json) {}

	public JsonObject toJson() {
		return new JsonObject(Json.encode(this));
	}

	/*-----------------------------------------------*/

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
