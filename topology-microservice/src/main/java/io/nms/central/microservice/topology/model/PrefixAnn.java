package io.nms.central.microservice.topology.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;


@DataObject(generateConverter = true)
public class PrefixAnn {
	
	// in SQL table		
	private int id;
	private String name;
	private String created;
	private String updated;
		
	private int originId;
	private String expiration;
	
	/*-----------------------------------------------*/
	public PrefixAnn() {}
	
	public PrefixAnn(int id) {
		this.id = id;		
	}
	
	public PrefixAnn(JsonObject json) {
		PrefixAnnConverter.fromJson(json, this);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		PrefixAnnConverter.toJson(this, json);
		return json;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((PrefixAnn) obj).id);
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

	public int getOriginId() {
		return originId;
	}

	public void setOriginId(int originId) {
		this.originId = originId;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
	
}
