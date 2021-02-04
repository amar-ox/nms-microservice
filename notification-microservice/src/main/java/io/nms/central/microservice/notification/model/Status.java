package io.nms.central.microservice.notification.model;

import java.time.OffsetDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

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

	public enum ResTypeEnum {
		NODE("NODE"),
		LTP("LTP"),
		CTP("CTP"),
		LINK("LINK"),
		LC("LC"),
		CONNECTION("CONECTION");

		private String value;
		private ResTypeEnum(String value) { this.value = value; }
		public String getValue() { return this.value; }
	};

	private String id;
	
	private StatusEnum status;
	
	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private OffsetDateTime timestamp;
	
	private Integer resId;
	private ResTypeEnum resType;

	public Status() {}

	public Status(String id) {
		this.id = id;
	}

	public Status(JsonObject json) {
		StatusConverter.fromJson(json, this);
	}

	public JsonObject toJson() {
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

	public ResTypeEnum getResType() {
		return resType;
	}

	public void setResType(ResTypeEnum resType) {
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
