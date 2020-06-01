package io.nms.central.microservice.topology.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Link extends NmsEntity {
	
	private String sourceNode;
	private String sourceLtp;
	private String destNode;
	private String destLtp;
	private String type;
	private String speed;
	private String status;
	
	public Link() {
		this.id = "";
	}
	public Link(JsonObject json) {
	    LinkConverter.fromJson(json, this);
	}


	public String getSourceNode() {
		return sourceNode;
	}

	public void setSourceNode(String sourceNode) {
		this.sourceNode = sourceNode;
	}

	public String getSourceLtp() {
		return sourceLtp;
	}

	public void setSourceLtp(String sourceLtp) {
		this.sourceLtp = sourceLtp;
	}

	public String getDestNode() {
		return destNode;
	}

	public void setDestNode(String destNode) {
		this.destNode = destNode;
	}

	public String getDestLtp() {
		return destLtp;
	}

	public void setDestLtp(String destLtp) {
		this.destLtp = destLtp;
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

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkForAdding() {
		if (sourceLtp == null || destLtp == null) {
			this.setError("missing source and/or destination LTP");			
			return false;
		}
		
		// LTP format: nodeId.ltpId		
		String source[] = sourceLtp.split("\\.");
		String dest[] = destLtp.split("\\.");
		if (source.length != 2 || dest.length != 2) {
			this.setError("wrong source and/or destination LTP");			
			return false;
		}
		sourceNode = source[0];		
		destNode = dest[0];		
		
		id = sourceLtp + "--" + destLtp;		
		return true;		
	}
}
