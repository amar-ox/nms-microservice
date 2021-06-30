package io.nms.central.microservice.topology.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class VxlanConnInfo extends ConnInfo {

	private int vni;

	/*-----------------------------------------------*/

	public VxlanConnInfo() {}
	public VxlanConnInfo(JsonObject json) {}

	public JsonObject toJson() {
		return new JsonObject(Json.encode(this));
	}

	/*-----------------------------------------------*/

	public int getVni() {
		return vni;
	}
	public void setVni(int vni) {
		this.vni = vni;
	}
}
