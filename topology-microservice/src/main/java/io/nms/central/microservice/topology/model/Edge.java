package io.nms.central.microservice.topology.model;

import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Edge {
	
	private int id;
	private int srcNodeId;
	private int destNodeId;
	private int srcFaceId;
	private int destFaceId;
	// private int cost;
	
	public Edge() {}
	
	public Edge(int id) {
		this.id = id;
	}
	
	public Edge(JsonObject json) {
		EdgeConverter.fromJson(json, this);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		EdgeConverter.toJson(this, json);
		return json;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((Edge) obj).id);
	}

	@Override
	public String toString() {
		return this.toJson().encodePrettily();
	}
	
	public int getDestNodeId() {
		return destNodeId;
	}

	public void setDestNodeId(int destNodeId) {
		this.destNodeId = destNodeId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSrcFaceId() {
		return srcFaceId;
	}

	public void setSrcFaceId(int srcFaceId) {
		this.srcFaceId = srcFaceId;
	}

	public int getDestFaceId() {
		return destFaceId;
	}

	public void setDestFaceId(int destFaceId) {
		this.destFaceId = destFaceId;
	}

	public int getSrcNodeId() {
		return srcNodeId;
	}

	public void setSrcNodeId(int srcNodeId) {
		this.srcNodeId = srcNodeId;
	}

}
