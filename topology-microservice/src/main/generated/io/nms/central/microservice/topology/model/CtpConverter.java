package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.Ctp}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.Ctp} original class using Vert.x codegen.
 */
public class CtpConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Ctp obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "error":
          if (member.getValue() instanceof String) {
            obj.setError((String)member.getValue());
          }
          break;
        case "face":
          if (member.getValue() instanceof JsonObject) {
            obj.setFace(new io.nms.central.microservice.topology.model.Face((JsonObject)member.getValue()));
          }
          break;
        case "id":
          if (member.getValue() instanceof String) {
            obj.setId((String)member.getValue());
          }
          break;
        case "idOnLtp":
          if (member.getValue() instanceof String) {
            obj.setIdOnLtp((String)member.getValue());
          }
          break;
        case "ltp":
          if (member.getValue() instanceof String) {
            obj.setLtp((String)member.getValue());
          }
          break;
        case "node":
          if (member.getValue() instanceof String) {
            obj.setNode((String)member.getValue());
          }
          break;
        case "status":
          if (member.getValue() instanceof String) {
            obj.setStatus((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(Ctp obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Ctp obj, java.util.Map<String, Object> json) {
    if (obj.getError() != null) {
      json.put("error", obj.getError());
    }
    if (obj.getFace() != null) {
      json.put("face", obj.getFace().toJson());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getIdOnLtp() != null) {
      json.put("idOnLtp", obj.getIdOnLtp());
    }
    if (obj.getLtp() != null) {
      json.put("ltp", obj.getLtp());
    }
    if (obj.getNode() != null) {
      json.put("node", obj.getNode());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
  }
}
