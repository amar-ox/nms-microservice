package io.nms.central.microservice.notification.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;

@DataObject(generateConverter = true)
public class Status {

	public enum StatusEnum {
		UP("UP"),
		DOWN("DOWN"),
		DISCONN("DISCONN");

		private String value;
		private StatusEnum(String value) { this.value = value; }
		public String getValue() { return this.value; }
	};

	private String id;
	
	private StatusEnum status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private OffsetDateTime timestamp;
	
	private Integer resId;
	private String resType;

	public Status() {}

	public Status(String id) {
		this.id = id;
	}

	public Status(JsonObject json) {
		StatusConverter.fromJson(json, this);
	}

	public JsonObject toJson() {
		/* JsonObject json = new JsonObject();
		StatusConverter.toJson(this, json);
		return json; */
		return new JsonObject(Json.encode(this));
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
		// return this.toJson().encodePrettily();
		return Json.encode(this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}


}
