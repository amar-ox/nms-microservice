package io.nms.central.microservice.topology.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Vlink {
	
	// in SQL table
		// common fields
	private int id;
	private String name;
	private String label;
	private String description;
	private String created;
	private String updated;
	private String status;
	private Map<String, Object> info = new HashMap<String, Object>();	
		
		// vlink fields
	private String type; 		// internal/external to subnet
	private int srcVltpId;	
	private int destVltpId;
	
	// in object only
	private int srcVnodeId;
	private int destVnodeId;	
	private int vsubnetId;
	private List<VlinkConn> vlinkConns = new ArrayList<VlinkConn>();
	
	
	
	/*-----------------------------------------------*/
	public Vlink() {}
	
	public Vlink(int id) {
		this.id = id;
	}
	public Vlink(JsonObject json) {
	    VlinkConverter.fromJson(json, this);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		VlinkConverter.toJson(this, json);
		return json;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((Vlink) obj).id);
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

	public int getSrcVltpId() {
		return srcVltpId;
	}

	public void setSrcVltpId(int srcVltpId) {
		this.srcVltpId = srcVltpId;
	}

	public int getDestVltpId() {
		return destVltpId;
	}

	public void setDestVltpId(int destVltpId) {
		this.destVltpId = destVltpId;
	}	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<VlinkConn> getVlinkConns() {
		return vlinkConns;
	}

	public void setVlinkConns(List<VlinkConn> vlinkConns) {
		this.vlinkConns = vlinkConns;
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
	
	public int getSrcVnodeId() {
		return srcVnodeId;
	}

	public void setSrcVnodeId(int srcVnodeId) {
		this.srcVnodeId = srcVnodeId;
	}

	public int getDestVnodeId() {
		return destVnodeId;
	}

	public void setDestVnodeId(int destVnodeId) {
		this.destVnodeId = destVnodeId;
	}
	
	public void addVlinkConn(VlinkConn vlinkConn) {
		vlinkConns.add(vlinkConn);
	}
	
	public void removeVlinkConn(VlinkConn vlinkConn) {
		vlinkConns.remove(vlinkConn);
	}

	public int getVsubnetId() {
		return vsubnetId;
	}

	public void setVsubnetId(int vsubnetId) {
		this.vsubnetId = vsubnetId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
