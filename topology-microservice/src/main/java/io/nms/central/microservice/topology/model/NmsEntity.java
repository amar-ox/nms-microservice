package io.nms.central.microservice.topology.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class NmsEntity {
	
//	@JsonProperty("_id")
	protected String id;	
	private String _error;
	
	public abstract boolean check();
	public abstract boolean checkForAdding();
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonIgnore
	public String getError() {
		return _error;
	}
	public void setError(String error) {
		this._error = error;
	}
	
	public JsonObject toJson() {
		return (JsonObject) Json.decodeValue(Json.encode(this)); 
	}
	
	@Override
	public String toString() {
		 return this.toJson().encodePrettily();
	}

}
