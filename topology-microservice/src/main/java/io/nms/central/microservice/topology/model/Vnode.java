package io.nms.central.microservice.topology.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Vnode {
	
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
	
		// vnode fields
	private String location;
	private Integer posx;
	private Integer posy;
	private int vsubnetId;
	private String type;
	
	// in object only
	private List<Vltp> vltps;
	private List<Vxc> vxcs;
	
	
	
	/*-----------------------------------------------*/
	public Vnode() {}
	
	public Vnode(int id) {
		this.id = id;
	}
	
	public Vnode(JsonObject json) {
	    VnodeConverter.fromJson(json, this);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		VnodeConverter.toJson(this, json);
		return json;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((Vnode) obj).id);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getPosx() {
		return posx;
	}

	public void setPosx(Integer posx) {
		this.posx = posx;
	}

	public Integer getPosy() {
		return posy;
	}

	public void setPosy(Integer posy) {
		this.posy = posy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getVsubnetId() {
		return vsubnetId;
	}

	public void setVsubnetId(int vsubnetId) {
		this.vsubnetId = vsubnetId;
	}

	public List<Vltp> getVltps() {
		return vltps;
	}

	public void setVltps(List<Vltp> vltps) {
		this.vltps = vltps;
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
	
	
	public void addVltp(Vltp vltp) {
		vltps.add(vltp);
	}
	
	public void removeVltp(Vltp vltp) {
		vltps.remove(vltp);
	}
}
