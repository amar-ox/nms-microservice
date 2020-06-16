package io.nms.central.microservice.topology.model;

import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Vctp {
		
	private String id;
	private String label;
	private String description;
	
	private String vltpId;
	private String vlinkId;
	
	private String status;
	
	// private Long direction;	
	private String connType; // vlan or wavelength
	private Long connValue;
	
	private String created;
	private String updated;
	
	public Vctp() {}
	
	public Vctp(String id) {
		this.id = id;
	}
	
	public Vctp(JsonObject json) {
	    VctpConverter.fromJson(json, this);
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

	public String getVltpId() {
		return vltpId;
	}

	public void setVltpId(String vltpId) {
		this.vltpId = vltpId;
	}

	public String getVlinkId() {
		return vlinkId;
	}

	public void setVlinkId(String vlinkId) {
		this.vlinkId = vlinkId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getConnType() {
		return connType;
	}

	public void setConnType(String connType) {
		this.connType = connType;
	}

	public Long getConnValue() {
		return connValue;
	}

	public void setConnValue(Long connValue) {
		this.connValue = connValue;
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
		VctpConverter.toJson(this, json);
		return json;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((Vctp) obj).id);
	}

	@Override
	public String toString() {
		return this.toJson().encodePrettily();
	}



	
}
