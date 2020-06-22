package io.nms.central.microservice.topology.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Vsubnet {
	
	// in SQL table
		// common fields (to all entities)
	private int id;
	private String name;
	private String label;
	private String description;
	private String created;
	private String updated;
	private String status;
	private Map<String, Object> info = new HashMap<String, Object>();
	
		// specific Vsubnet fields
		// TODO?
	
	// in object only (obtained through SQL JOINS)
	private List<Vnode> vnodes = new ArrayList<Vnode>();
	private List<Vlink> vlinks = new ArrayList<Vlink>();
	
	
	
	/*-----------------------------------------------*/
	public Vsubnet() {}

	public Vsubnet(int id) {
		this.id = id;
	}

	public Vsubnet(JsonObject json) {
		VsubnetConverter.fromJson(json, this);
	}
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		VsubnetConverter.toJson(this, json);
		return json;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((Vsubnet) obj).id);
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<Vnode> getVnodes() {
		return vnodes;
	}

	public void setVnodes(List<Vnode> vnodes) {
		this.vnodes = vnodes;
	}

	public List<Vlink> getVlinks() {
		return vlinks;
	}

	public void setVlinks(List<Vlink> vlinks) {
		this.vlinks = vlinks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

