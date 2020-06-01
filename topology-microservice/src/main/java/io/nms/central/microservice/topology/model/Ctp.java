package io.nms.central.microservice.topology.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Ctp extends NmsEntity {
	
	private String idOnLtp;
	private String ltp;
	private String node;	
	private String status;
	private Face face;
	
	public Ctp() {
		this.id = "";
	}
	
	public Ctp(JsonObject json) {
	    CtpConverter.fromJson(json, this);
	}


	public String getIdOnLtp() {
		return idOnLtp;
	}

	public void setIdOnLtp(String idOnLtp) {
		this.idOnLtp = idOnLtp;
	}

	public String getLtp() {
		return ltp;
	}

	public void setLtp(String ltp) {
		this.ltp = ltp;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Face getFace() {
		return face;
	}

	public void setFace(Face face) {
		this.face = face;
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkForAdding() {
		if (ltp == null || idOnLtp == null) {
			this.setError("missing ltp and/or idOnLtp");			
			return false;
		}		
		String[] nodeLtp = ltp.split("\\.");
		if (nodeLtp.length != 2 || idOnLtp.isEmpty()) {
			this.setError("wrong ltp and/or idOnLtp");			
			return false;
		}
		node = nodeLtp[0];
		id = ltp + "." + idOnLtp;		
		return true;		
	}
}
