package io.nms.central.microservice.configuration.model;

import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class ConfigRoute {
	
	private String prefix;
	private Integer faceId;
	private Integer cost;
	private Integer origin;
	
	public ConfigRoute() {}
	
	public ConfigRoute(JsonObject json) {
		ConfigRouteConverter.fromJson(json, this);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		ConfigRouteConverter.toJson(this, json);
		return json;
	}

	@Override
	public int hashCode() {
		return Objects.hash(prefix + "" + faceId + "" + origin);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(this.toJson(), ((ConfigRoute) obj).toJson());
	}

	@Override
	public String toString() {
		return this.toJson().encodePrettily();
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getFaceId() {
		return faceId;
	}

	public void setFaceId(Integer faceId) {
		this.faceId = faceId;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

}
