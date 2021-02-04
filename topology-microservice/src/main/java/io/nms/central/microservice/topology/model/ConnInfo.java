package io.nms.central.microservice.topology.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.core.json.JsonObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ConnInfo {
    public abstract JsonObject toJson();
    protected ConnInfo() {}
}
