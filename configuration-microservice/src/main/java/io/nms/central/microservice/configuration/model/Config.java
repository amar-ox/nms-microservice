package io.nms.central.microservice.configuration.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Config {
	
	private List<ConfigFace> faces = new ArrayList<ConfigFace>();
	private List<ConfigRoute> routes = new ArrayList<ConfigRoute>();
	
	public Config() {}
	
	public Config(JsonObject json) {
		ConfigConverter.fromJson(json, this);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		ConfigConverter.toJson(this, json);
		return json;
	}

	@Override
	public int hashCode() {
		return Objects.hash(toJson());
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(toJson(), ((Config) obj).toJson());
	}

	@Override
	public String toString() {
		return this.toJson().encodePrettily();
	}
	
	public List<ConfigFace> getFaces() {
		return faces;
	}

	public void setFaces(List<ConfigFace> faces) {
		this.faces = faces;
	}

	public List<ConfigRoute> getRoutes() {
		return routes;
	}

	public void setRoutes(List<ConfigRoute> routes) {
		this.routes = routes;
	}
	
	public void addFace(ConfigFace face) {
		faces.add(face);
	}
	
	public void addRoute(ConfigRoute route) {
		routes.add(route);
	}

}
