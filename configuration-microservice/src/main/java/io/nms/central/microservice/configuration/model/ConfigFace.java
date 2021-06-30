package io.nms.central.microservice.configuration.model;

import java.util.Objects;

import io.nms.central.microservice.topology.model.NdnConnInfo.SchemeEnum;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class ConfigFace {
	
	private Integer id;
	private String local;
	private String remote;
	private SchemeEnum scheme;
	
	
	public ConfigFace() {}
	
	public ConfigFace(JsonObject json) {
		ConfigFaceConverter.fromJson(json, this);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		ConfigFaceConverter.toJson(this, json);
		return json;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getRemote() {
		return remote;
	}

	public void setRemote(String remote) {
		this.remote = remote;
	}

	public SchemeEnum getScheme() {
		return scheme;
	}

	public void setScheme(SchemeEnum scheme) {
		this.scheme = scheme;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(id, ((ConfigFace) obj).id);
	}

	@Override
	public String toString() {
		return this.toJson().encodePrettily();
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

}
