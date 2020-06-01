package io.nms.central.microservice.topology.model;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Topology extends NmsEntity{
	
	private List<Node> nodes;
	private List<Link> links;
	private List<LinkConn> linkConns;
	
	public Topology() {
		this.id = "";
	}
	
	public Topology(JsonObject json) {
	    TopologyConverter.fromJson(json, this);
	}



	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean checkForAdding() {
		// TODO Auto-generated method stub
		return true;
	}


	public List<Node> getNodes() {
		return nodes;
	}


	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}


	public List<Link> getLinks() {
		return links;
	}


	public void setLinks(List<Link> links) {
		this.links = links;
	}


	public List<LinkConn> getLinkConns() {
		return linkConns;
	}


	public void setLinkConns(List<LinkConn> linkConns) {
		this.linkConns = linkConns;
	}
}
