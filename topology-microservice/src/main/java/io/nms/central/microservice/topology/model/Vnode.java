package io.nms.central.microservice.topology.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Vnode {
	
	private String id;
	private String label;
	private String description;	
	
	private String location;
	private Integer posx;
	private Integer posy;
	
	private String  vsubnetId;
	
	private String type;
	private boolean managed;	
	private String status;	
	
	private String created;
	private String updated;
	
	private List<Vltp> vltps = new ArrayList<Vltp>();
	//private List<Vxc> vxcs = new ArrayList<>();
	
	public Vnode() {}
	
	public Vnode(String id) {
		this.id = id;
	}
	
	public Vnode(JsonObject json) {
	    VnodeConverter.fromJson(json, this);
		//Json.decodeValue(json.encode(), Node.class);
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

	public boolean getManaged() {
		return managed;
	}

	public void setManaged(boolean managed) {
		this.managed = managed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVsubnetId() {
		return vsubnetId;
	}

	public void setVsubnetId(String vsubnetId) {
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
	
	public void addVltp(Vltp vltp) {
		vltps.add(vltp);
	}
	
	public void removeVltp(Vltp vltp) {
		vltps.remove(vltp);
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
	
}
