package io.nms.central.microservice.notification.model;

import java.time.OffsetDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Fault {
	
	private String id;
	
	private Integer origin;
	private Integer code;
	private String msg;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private OffsetDateTime timestamp; 
	
	public Fault() {}
	
	public Fault(String id) {
		this.id = id;
	}
	
	public Fault(JsonObject json) {
		FaultConverter.fromJson(json, this);
	}
	
	public JsonObject toJson() {
		/* JsonObject json = new JsonObject();
		FaultConverter.toJson(this, json);
		return json; */
		return new JsonObject(Json.encode(this));
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((Fault) obj).id);
	}

	@Override
	public String toString() {
		return Json.encode(this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
