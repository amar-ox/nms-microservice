package io.nms.central.microservice.topology.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class EtherConnInfo extends ConnInfo {

	private String address;
	private int vlanId;
	private boolean isNative = true;

	/*-----------------------------------------------*/
	
	public EtherConnInfo() {}
	public EtherConnInfo(JsonObject json) {}
	
	public JsonObject toJson() {
		return new JsonObject(Json.encode(this));
	}

	/*-----------------------------------------------*/

	public boolean getIsNative() {
		return isNative;
	}
	public void setNative(boolean isNative) {
		this.isNative = isNative;
	}

	public int getVlanId() {
		return vlanId;
	}
	public void setVlanId(int vlanId) {
		this.vlanId = vlanId;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
