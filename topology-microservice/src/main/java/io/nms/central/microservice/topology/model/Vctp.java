package io.nms.central.microservice.topology.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonSubTypes;

import io.nms.central.microservice.common.functional.JSONUtils;
import io.nms.central.microservice.notification.model.Status.StatusEnum;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Vctp {

	public enum ConnTypeEnum {
		Ether("Ether"), VXEther("VXEther"), 
		NDN("NDN"), IPv4("IPv4"), IPv6("IPv6"), 
		UDP("UDP"), TCP("TCP"),
        VXLAN("VXLAN");

        private String value;

        private ConnTypeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    };

	// common fields
	private int id;
	private String name;
	private String label;
	private String description;
	private StatusEnum status;
	private String created;
	private String updated;
	private Map<String, Object> info = new HashMap<String, Object>();

	// vctp fields
	private ConnTypeEnum connType;

	@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "connType")
	@JsonSubTypes({@Type(value = EtherConnInfo.class, name = "Ether"),
        @Type(value = EtherConnInfo.class, name = "VXEther"), @Type(value = Ipv4ConnInfo.class, name = "IPv4"),
        @Type(value = Ipv6ConnInfo.class, name = "IPv6"), @Type(value = NdnConnInfo.class, name = "NDN"),
        @Type(value = UdpConnInfo.class, name = "UDP"), @Type(value = TcpConnInfo.class, name = "TCP"),
        @Type(value = VxlanConnInfo.class, name = "VXLAN") })
	private ConnInfo connInfo;

	@JsonProperty("parentId")
	@JsonAlias({"vltpId", "vctpId"})
	private int parentId;
	private int vnodeId;

	/*-----------------------------------------------*/

	public Vctp() {}
	public Vctp(int id) {
		this.id = id;		
	}
	public Vctp(JsonObject json) {
		JSONUtils.fromJson(json, this, Vctp.class);
	}
	
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
		return Objects.equals(toString(), ((Vctp) obj).toString());
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	/*-----------------------------------------------*/
	
	public ConnTypeEnum getConnType() {
        return connType;
    }
    public void setConnType(ConnTypeEnum connType) {
        this.connType = connType;
    }
	
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	public ConnInfo getConnInfo() {
		return connInfo;
	}
	public void setConnInfo(ConnInfo connInfo) {
		this.connInfo = connInfo;
	}

	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
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

	public int getVnodeId() {
		return vnodeId;
	}
	public void setVnodeId(int vnodeId) {
		this.vnodeId = vnodeId;
	}

	public Map<String, Object> getInfo() {
		return info;
	}
	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}
}
