package io.nms.central.microservice.topology.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Vltp {
	private String id;
	private String label;
	private String description;
	
	private String vnodeId;
	private String status;		
	private Boolean busy;
	
	private String created;
	private String updated;
	
	// private List<String> hwinfo = new ArrayList<>();
	private List<Vctp> vctps = new ArrayList<>();
	
	public Vltp() {}
	
	public Vltp(String id) {
		this.id = id;		
	}
	
	public Vltp(JsonObject json) {
	    VltpConverter.fromJson(json, this);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVnodeId() {
		return vnodeId;
	}

	public void setVnodeId(String vnodeId) {
		this.vnodeId = vnodeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Boolean isBusy() {
		return busy;
	}

	public void setBusy(Boolean busy) {
		this.busy = busy;
	}

	public List<Vctp> getVctps() {
		return vctps;
	}

	public void setVctps(List<Vctp> vctps) {
		this.vctps = vctps;
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
	
	public void addVctp(Vctp vctp) {
		vctps.add(vctp);
	}
	
	public void removeVctp(Vctp vctp) {
		vctps.remove(vctp);
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		VltpConverter.toJson(this, json);
		return json;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((Vltp) obj).id);
	}

	
	@Override
	public String toString() {
		return this.toJson().encodePrettily();
	}
}
