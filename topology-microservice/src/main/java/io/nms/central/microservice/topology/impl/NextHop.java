package io.nms.central.microservice.topology.impl;

public class NextHop {
	
	final private int id;
	private int face;
	private int cost;
	
	public NextHop(int id) {
		this.id = id;
	}
	
	public NextHop(int id, int face, int cost) {
		this.id = id;
		this.face = face;
		this.cost = cost;
	}

	public int getId() {
		return id;
	}

	public int getFace() {
		return face;
	}

	public void setFace(int face) {
		this.face = face;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
