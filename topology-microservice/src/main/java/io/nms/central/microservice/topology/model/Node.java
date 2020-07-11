package io.nms.central.microservice.topology.model;

import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Node {
	
	private int id;
	
	public Node() {}
	
	public Node(int id) {
		this.id = id;
	}
	
	public Node(JsonObject json) {
		NodeConverter.fromJson(json, this);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		NodeConverter.toJson(this, json);
		return json;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((Node) obj).id);
	}

	@Override
	public String toString() {
		return this.toJson().encodePrettily();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

}
