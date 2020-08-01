package io.nms.central.microservice.common;

import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Status {
	
	private String id;
	
	private Integer resId;
	private String resType;
	private String status;
	private String timestamp; 
	
	public Status() {}
	
	public Status(String id) {
		this.id = id;
	}
	
	public Status(JsonObject json) {
		StatusConverter.fromJson(json, this);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		StatusConverter.toJson(this, json);
		return json;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((Status) obj).id);
	}

	@Override
	public String toString() {
		return this.toJson().encodePrettily();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getResId() {
		return resId;
	}

	public void setResId(Integer resId) {
		this.resId = resId;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	
}
