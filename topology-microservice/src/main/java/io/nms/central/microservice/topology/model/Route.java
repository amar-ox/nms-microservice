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
	
	private Integer paId;
	private Integer nodeId;
	private Integer nextHopId;
	private Integer faceId;
	private Integer cost;
	private Integer origin;
	
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

	public Integer getNextHopId() {
		return nextHopId;
	}

	public void setNextHopId(Integer nextHopId) {
		this.nextHopId = nextHopId;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getPaId() {
		return paId;
	}

	public void setPaId(Integer paId) {
		this.paId = paId;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getFaceId() {
		return faceId;
	}

	public void setFaceId(Integer faceId) {
		this.faceId = faceId;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
}
