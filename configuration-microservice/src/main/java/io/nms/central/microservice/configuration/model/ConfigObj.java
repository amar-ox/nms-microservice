package io.nms.central.microservice.configuration.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class ConfigObj {
	
	private int nodeId;
	private int version;
	private Config config = new Config();
	
	public ConfigObj() {
		version = 0;
	}
	
	public ConfigObj(JsonObject json) {
		ConfigObjConverter.fromJson(json, this);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		ConfigObjConverter.toJson(this, json);
		return json;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nodeId + version);
	}

	@Override
	public boolean equals(Object obj) {
		// return Objects.equals(toJson().encode(), ((ConfigObj) obj).toJson().encode());
		return getConfig().equals(((ConfigObj) obj).getConfig());
	}

	@Override
	public String toString() {
		return this.toJson().encodePrettily();
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

}
