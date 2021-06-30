package io.nms.central.microservice.topology.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.nms.central.microservice.common.functional.JSONUtils;
import io.nms.central.microservice.notification.model.Status.StatusEnum;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class VlinkConn {

		// common fields
	private int id;
	private String name;
	private String label;
	private String description;
	private String created;
	private String updated;
	private StatusEnum status;
	private Map<String, Object> info = new HashMap<String, Object>();
	
		// vlinkConn fields
	private int srcVctpId;
	private int destVctpId;	
	private int vlinkId;
	
	// in object only
	private int srcVltpId;	
	private int destVltpId;
	private int srcVnodeId;	
	private int destVnodeId;	
		
	/*-----------------------------------------------*/

	public VlinkConn() {}
	public VlinkConn(int id) {
		this.id = id;
	}
	public VlinkConn(JsonObject json) {}
	
	/*-----------------------------------------------*/

	public JsonObject toJson() {
		return new JsonObject(JSONUtils.pojo2Json(this, false));
	}
	@Override
	public String toString() {
		return JSONUtils.pojo2Json(this, false);
	}
	@Override
	public boolean equals(Object obj) {
		return Objects.equals(toString(), ((VlinkConn) obj).toString());
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
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

	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
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

	public int getVlinkId() {
		return vlinkId;
	}
	public void setVlinkId(int vlinkId) {
		this.vlinkId = vlinkId;
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
}
