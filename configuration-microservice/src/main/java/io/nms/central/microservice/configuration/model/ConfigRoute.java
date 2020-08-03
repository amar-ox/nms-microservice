package io.nms.central.microservice.configuration.model;

import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class ConfigRoute {
	
	private String prefix;
	private int nextHop;
	private int cost;
	private String origin;
	
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
		return Objects.hash(prefix + nextHop + origin);
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

	public int getNextHop() {
		return nextHop;
	}

	public void setNextHop(int nextHop) {
		this.nextHop = nextHop;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

}
