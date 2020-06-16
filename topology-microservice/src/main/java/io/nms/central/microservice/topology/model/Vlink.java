package io.nms.central.microservice.topology.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Vlink {
	
	private String id;
	private String label;
	private String description;
		
	private String srcVltpId;	
	private String destVltpId;
	
	private String srcVnodeId;	
	private String destVnodeId;	
	// private String vsubnetId;
	
	private String speed;
	private String status;	
	
	private String created;
	private String updated;
	
	private List<VlinkConn> vlinkConns = new ArrayList<>();
	
	public Vlink() {}
	
	public Vlink(String id) {
		this.id = id;
	}
	public Vlink(JsonObject json) {
	    VlinkConverter.fromJson(json, this);
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

	public String getSrcVltpId() {
		return srcVltpId;
	}

	public void setSrcVltpId(String srcVltpId) {
		this.srcVltpId = srcVltpId;
	}

	public String getDestVltpId() {
		return destVltpId;
	}

	public void setDestVltpId(String destVltpId) {
		this.destVltpId = destVltpId;
	}	

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
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
	
	public String getSrcVnodeId() {
		return srcVnodeId;
	}

	public void setSrcVnodeId(String srcVnodeId) {
		this.srcVnodeId = srcVnodeId;
	}

	public String getDestVnodeId() {
		return destVnodeId;
	}

	public void setDestVnodeId(String destVnodeId) {
		this.destVnodeId = destVnodeId;
	}
	
	public void addVlinkConn(VlinkConn vlinkConn) {
		vlinkConns.add(vlinkConn);
	}
	
	public void removeVlinkConn(VlinkConn vlinkConn) {
		vlinkConns.remove(vlinkConn);
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
	
}
