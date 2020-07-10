package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.Face}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.Face} original class using Vert.x codegen.
 */
public class FaceConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Face obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "created":
          if (member.getValue() instanceof String) {
            obj.setCreated((String)member.getValue());
          }
          break;
        case "id":
          if (member.getValue() instanceof Number) {
            obj.setId(((Number)member.getValue()).intValue());
          }
          break;
        case "label":
          if (member.getValue() instanceof String) {
            obj.setLabel((String)member.getValue());
          }
          break;
        case "local":
          if (member.getValue() instanceof String) {
            obj.setLocal((String)member.getValue());
          }
          break;
        case "remote":
          if (member.getValue() instanceof String) {
            obj.setRemote((String)member.getValue());
          }
          break;
        case "scheme":
          if (member.getValue() instanceof String) {
            obj.setScheme((String)member.getValue());
          }
          break;
        case "updated":
          if (member.getValue() instanceof String) {
            obj.setUpdated((String)member.getValue());
          }
          break;
        case "vctpId":
          if (member.getValue() instanceof Number) {
            obj.setVctpId(((Number)member.getValue()).intValue());
          }
          break;
        case "vlinkConnId":
          if (member.getValue() instanceof Number) {
            obj.setVlinkConnId(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(Face obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Face obj, java.util.Map<String, Object> json) {
    if (obj.getCreated() != null) {
      json.put("created", obj.getCreated());
    }
    json.put("id", obj.getId());
    if (obj.getLabel() != null) {
      json.put("label", obj.getLabel());
    }
    if (obj.getLocal() != null) {
      json.put("local", obj.getLocal());
    }
    if (obj.getRemote() != null) {
      json.put("remote", obj.getRemote());
    }
    if (obj.getScheme() != null) {
      json.put("scheme", obj.getScheme());
    }
    if (obj.getUpdated() != null) {
      json.put("updated", obj.getUpdated());
    }
    json.put("vctpId", obj.getVctpId());
    json.put("vlinkConnId", obj.getVlinkConnId());
  }
}
