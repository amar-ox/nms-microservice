package io.nms.central.microservice.topology.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Vtrail {
	
	// in SQL table
		// common fields
	private int id;
	private String name;
	private String label;
	private String description;
	private String created;
	private String updated;
	private String status;
	private Map<String, Object> info;	
	
		// vtrail fields
	private int srcVctpId;
	private int destVctpId;

	// in object only
	private List<Vxc> vxcs;

	
	
	/*-----------------------------------------------*/
	public Vtrail() {}
	
	public Vtrail(int id) {
		this.id = id;
	}
	
	public Vtrail(JsonObject json) {
	    VtrailConverter.fromJson(json, this);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		VtrailConverter.toJson(this, json);
		return json;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((Vtrail) obj).id);
	}

	@Override
	public String toString() {
		return this.toJson().encodePrettily();
	}
	
	
	
	/*-----------------------------------------------*/
	public int getSrcVctpId() {
		return srcVctpId;
	}

	public void setSrcVctpId(int srcVctpId) {
		this.srcVctpId = srcVctpId;
	}

	public int getDestVctpId() {
		return destVctpId;
	}

	public void setDestVctpId(int destVctpId) {
		this.destVctpId = destVctpId;
	}
	
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

	public List<Vxc> getVxcs() {
		return vxcs;
	}

	public void setVxcs(List<Vxc> vxcs) {
		this.vxcs = vxcs;
	}
	
	
	public void addVxc(Vxc vxc) {
		vxcs.add(vxc);
	}
	
	public void removeVxc(Vxc vxc) {
		vxcs.remove(vxc);
	}
}
