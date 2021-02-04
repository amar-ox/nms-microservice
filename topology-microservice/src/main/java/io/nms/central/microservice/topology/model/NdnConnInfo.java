package io.nms.central.microservice.topology.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class NdnConnInfo extends ConnInfo {

	public enum SchemeEnum {
		ether("ether");

		private String value;
		private SchemeEnum(String value) { this.value = value; }
		public String getValue() { return this.value; }
	};

	private String local;
	private String remote;
	private SchemeEnum scheme;
	private int vlan;

	/*-----------------------------------------------*/
	
	public NdnConnInfo() {}
	public NdnConnInfo(JsonObject json) {}
	
	public JsonObject toJson() {
		return new JsonObject(Json.encode(this));
	}
	
	/*-----------------------------------------------*/
	
	public int getVlan() {
		return vlan;
	}
	public void setVlan(int vlan) {
		this.vlan = vlan;
	}

	public SchemeEnum getScheme() {
		return scheme;
	}
	public void setScheme(SchemeEnum scheme) {
		this.scheme = scheme;
	}

	public String getRemote() {
		return remote;
	}
	public void setRemote(String remote) {
		this.remote = remote;
	}

	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
}
