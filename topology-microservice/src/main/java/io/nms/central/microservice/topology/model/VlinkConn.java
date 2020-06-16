package io.nms.central.microservice.topology.model;

import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class VlinkConn {
	
	private String id;
	private String label;
	private String description;
	private String created;
	private String updated;	
	
	private String srcVctpId;
	private String destVctpId;
	
	private String status;
	
	private String srcVnode;	
	private String destVnode;	
	// private String vsubnetId;

	public String getSrcVctpId() {
		return srcVctpId;
	}

	public void setSrcVctpId(String srcVctpId) {
		this.srcVctpId = srcVctpId;
	}

	public String getDestVctpId() {
		return destVctpId;
	}

	public void setDestVctpId(String destVctpId) {
		this.destVctpId = destVctpId;
	}

	public String getSrcVnode() {
		return srcVnode;
	}

	public void setSrcVnode(String srcVnode) {
		this.srcVnode = srcVnode;
	}

	public String getDestVnode() {
		return destVnode;
	}

	public void setDestVnode(String destVnode) {
		this.destVnode = destVnode;
	}
	
	public VlinkConn() {}
	
	public VlinkConn(String id) {
		this.id = id;
	}
	
	public VlinkConn(JsonObject json) {
	    VlinkConnConverter.fromJson(json, this);
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		VlinkConnConverter.toJson(this, json);
		return json;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((VlinkConn) obj).id);
	}

	@Override
	public String toString() {
		return this.toJson().encodePrettily();
	}

	
}
