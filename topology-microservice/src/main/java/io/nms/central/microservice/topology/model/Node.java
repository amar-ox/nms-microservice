package io.nms.central.microservice.topology.model;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Node extends NmsEntity {
	
	private String location;
	private String type;
	private boolean managed;	
	private List<Ltp> ltps;
	private String status;
	
	public Node() {
		this.id = "";
	}
	
	public Node(JsonObject json) {
	    NodeConverter.fromJson(json, this);
		//Json.decodeValue(json.encode(), Node.class);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isManaged() {
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

	public List<Ltp> getLtps() {
		return ltps;
	}

	public void setLtps(List<Ltp> ltps) {
		this.ltps = ltps;
	}

	@Override
	public boolean checkForAdding() {
		if (id == null) {
			this.setError("missing node Id");			
			return false;
		}
		if (this.id.isEmpty()) {
			this.setError("wrong node Id");			
			return false;
		}
		if (this.ltps == null) {
			return true;
		} else {
			this.setError("node can not embed LTPs objects");			
			return false;
		}		
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
	    NodeConverter.toJson(this, json);
	    return json;
	}*/
}
