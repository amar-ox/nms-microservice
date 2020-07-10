package io.nms.central.microservice.topology.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Route {
	
	// in SQL table		
	private int id;
	private String created;
	private String updated;	
	
	private int paId;
	private int nodeId;
	private int nextHopId;
	private int faceId;
	private int cost;
	private String origin;
	
	// in object only
	private String prefix;
	
	
	/*-----------------------------------------------*/
	public Route() {}
	
	public Route(int id) {
		this.id = id;		
	}
	
	public Route(JsonObject json) {
		RouteConverter.fromJson(json, this);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		RouteConverter.toJson(this, json);
		return json;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((Route) obj).id);
	}

	
	@Override
	public String toString() {
		return this.toJson().encodePrettily();
	}

	
	
	/*-----------------------------------------------*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public int getNextHopId() {
		return nextHopId;
	}

	public void setNextHopId(int nextHopId) {
		this.nextHopId = nextHopId;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getPaId() {
		return paId;
	}

	public void setPaId(int paId) {
		this.paId = paId;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public int getFaceId() {
		return faceId;
	}

	public void setFaceId(int faceId) {
		this.faceId = faceId;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
}
