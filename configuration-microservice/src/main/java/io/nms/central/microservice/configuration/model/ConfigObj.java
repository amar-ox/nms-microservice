package io.nms.central.microservice.configuration.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class ConfigObj {
	
	private int nodeId;
	// private String timestamp;
	private Config config = new Config();
	
	public ConfigObj() {}
	
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
		return Objects.hash(nodeId + getConfig().hashCode());
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

	/* public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	} */

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

}
