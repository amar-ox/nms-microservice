package io.nms.central.microservice.topology.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Vsubnet {
	private String id;
	private String label;
	private String description;
	private String created;
	private String updated;
	private List<Vnode> vnodes = new ArrayList<Vnode>();
	private List<Vlink> vlinks = new ArrayList<Vlink>();
	
	//private List<String> info = new ArrayList<String>();

	public Vsubnet() {}

	public Vsubnet(String id) {
		this.id = id;
	}

	public Vsubnet(JsonObject json) {
		VsubnetConverter.fromJson(json, this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
}

