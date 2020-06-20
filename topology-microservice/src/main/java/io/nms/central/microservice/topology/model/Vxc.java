package io.nms.central.microservice.topology.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Vxc {
	
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
		
		// vxc fields
	private String type;
	private int vnodeId;
	private int vtrailId;
	private int dropVctpId;
	private int destVctpId;
	private int srcVctpId;	
	
	// in object only
	private int vsubnetId;

	
	
	/*-----------------------------------------------*/
	public Vxc() {}

	public Vxc(int id) {
		this.id = id;
	}

	public Vxc(JsonObject json) {
		VxcConverter.fromJson(json, this);
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		VxcConverter.toJson(this, json);
		return json;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((Vxc) obj).id);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getVtrailId() {
		return vtrailId;
	}

	public void setVtrailId(int vtrailId) {
		this.vtrailId = vtrailId;
	}

	public int getVnodeId() {
		return vnodeId;
	}

	public void setVnodeId(int vnodeId) {
		this.vnodeId = vnodeId;
	}

	public int getDropVctpId() {
		return dropVctpId;
	}

	public void setDropVctpId(int dropVctpId) {
		this.dropVctpId = dropVctpId;
	}

	public int getSrcVctpId() {
		return srcVctpId;
	}

	public void setSrcVctpId(int srcVctpId) {
		this.srcVctpId = srcVctpId;
	}

	public int getVsubnetId() {
		return vsubnetId;
	}

	public void setVsubnetId(int vsubnetId) {
		this.vsubnetId = vsubnetId;
	}

	public int getDestVctpId() {
		return destVctpId;
	}

	public void setDestVctpId(int destVctpId) {
		this.destVctpId = destVctpId;
	}
}
