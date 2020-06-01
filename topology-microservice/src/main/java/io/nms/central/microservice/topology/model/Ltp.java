package io.nms.central.microservice.topology.model;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Ltp extends NmsEntity {
	
	private String idOnNode;
	private String node;
	private String model;
	private String type;
	private String speed;
	private List<Ctp> ctps;
	private String status;
	
	public Ltp() {
		this.id = "";		
	}
	
	public Ltp(JsonObject json) {
	    LtpConverter.fromJson(json, this);
	}

	public String getIdOnNode() {
		return idOnNode;
	}

	public void setIdOnNode(String idOnNode) {
		this.idOnNode = idOnNode;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public List<Ctp> getCtps() {
		return ctps;
	}

	public void setCtps(List<Ctp> ctps) {
		this.ctps = ctps;
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkForAdding() {
		if (node == null || idOnNode == null) {
			this.setError("missing node and/or idOnNode");			
			return false;
		}
		if (idOnNode.isEmpty() || node.isEmpty()) {
			this.setError("wrong node and/or idOnNode");			
			return false;
		}
		id = node + "." + idOnNode;
		if (this.ctps == null) {
			return true;
		} else {
			this.setError("LTP can not embed CTPs objects");			
			return false;
		}		
	}
	
	
	
	

}
