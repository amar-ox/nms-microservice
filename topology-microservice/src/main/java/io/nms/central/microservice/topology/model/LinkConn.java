package io.nms.central.microservice.topology.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class LinkConn extends NmsEntity {
	
	private String sourceNode;
	private String sourceLtp;
	private String sourceCtp;
	private String destNode;
	private String destLtp;
	private String destCtp;	
	private String adminCost;
	private String status;
	
	public LinkConn() {
		this.id = "";
	}
	
	public LinkConn(JsonObject json) {
	    LinkConnConverter.fromJson(json, this);
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

	public String getSourceCtp() {
		return sourceCtp;
	}

	public void setSourceCtp(String sourceCtp) {
		this.sourceCtp = sourceCtp;
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

	public String getDestCtp() {
		return destCtp;
	}

	public void setDestCtp(String destCtp) {
		this.destCtp = destCtp;
	}

	public String getAdminCost() {
		return adminCost;
	}

	public void setAdminCost(String adminCost) {
		this.adminCost = adminCost;
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
		if (sourceCtp == null || destCtp == null) {
			this.setError("missing source and/or destination CTP");			
			return false;
		}
		
		// CTP format: nodeId.ltpId.ctpId		
		String source[] = sourceCtp.split("\\.");
		String dest[] = destCtp.split("\\.");
		if (source.length != 3 || dest.length != 3) {
			this.setError("wrong source and/or destination CTP");			
			return false;
		}
		sourceNode = source[0];
		sourceLtp = sourceNode + "." + source[1];
		destNode = dest[0];
		destLtp = destNode + "." + dest[1];
		
		id = sourceCtp + "--" + destCtp;		
		return true;
	}
}
