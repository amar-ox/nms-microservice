package io.nms.central.microservice.topology.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Rte {
	
	// in SQL table		
	private int id;
	private String created;
	private String updated;
	private String status = "DOWN";	
	
	private int prefixId;
	private int fromNodeId;
	private int nextHopId;
	private int ctpId;
	private int cost;
	
	// in object only
	private String prefixName; 	// prefix name
	
	
	/*-----------------------------------------------*/
	public Rte() {}
	
	public Rte(int id) {
		this.id = id;		
	}
	
	public Rte(JsonObject json) {
		RteConverter.fromJson(json, this);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		RteConverter.toJson(this, json);
		return json;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((Rte) obj).id);
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPrefixId() {
		return prefixId;
	}

	public void setPrefixId(int prefixId) {
		this.prefixId = prefixId;
	}

	public int getFromNodeId() {
		return fromNodeId;
	}

	public void setFromNodeId(int fromNodeId) {
		this.fromNodeId = fromNodeId;
	}

	public int getNextHopId() {
		return nextHopId;
	}

	public void setNextHopId(int nextHopId) {
		this.nextHopId = nextHopId;
	}

	public int getCtpId() {
		return ctpId;
	}

	public void setCtpId(int ctpId) {
		this.ctpId = ctpId;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getPrefixName() {
		return prefixName;
	}

	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}

}
