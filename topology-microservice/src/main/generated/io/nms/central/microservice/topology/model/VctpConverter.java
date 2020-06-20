package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.Vctp}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.Vctp} original class using Vert.x codegen.
 */
public class VctpConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Vctp obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "connType":
          if (member.getValue() instanceof String) {
            obj.setConnType((String)member.getValue());
          }
          break;
        case "connValue":
          if (member.getValue() instanceof Number) {
            obj.setConnValue(((Number)member.getValue()).longValue());
          }
          break;
        case "created":
          if (member.getValue() instanceof String) {
            obj.setCreated((String)member.getValue());
          }
          break;
        case "description":
          if (member.getValue() instanceof String) {
            obj.setDescription((String)member.getValue());
          }
          break;
        case "id":
          if (member.getValue() instanceof Number) {
            obj.setId(((Number)member.getValue()).intValue());
          }
          break;
        case "info":
          if (member.getValue() instanceof JsonObject) {
            obj.setInfo(((JsonObject)member.getValue()).copy());
          }
          break;
        case "label":
          if (member.getValue() instanceof String) {
            obj.setLabel((String)member.getValue());
          }
          break;
        case "name":
          if (member.getValue() instanceof String) {
            obj.setName((String)member.getValue());
          }
          break;
        case "status":
          if (member.getValue() instanceof String) {
            obj.setStatus((String)member.getValue());
          }
          break;
        case "updated":
          if (member.getValue() instanceof String) {
            obj.setUpdated((String)member.getValue());
          }
          break;
        case "vlinkId":
          if (member.getValue() instanceof Number) {
            obj.setVlinkId(((Number)member.getValue()).intValue());
          }
          break;
        case "vltpId":
          if (member.getValue() instanceof Number) {
            obj.setVltpId(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(Vctp obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Vctp obj, java.util.Map<String, Object> json) {
    if (obj.getConnType() != null) {
      json.put("connType", obj.getConnType());
    }
    if (obj.getConnValue() != null) {
      json.put("connValue", obj.getConnValue());
    }
    if (obj.getCreated() != null) {
      json.put("created", obj.getCreated());
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    json.put("id", obj.getId());
    if (obj.getInfo() != null) {
      json.put("info", obj.getInfo());
    }
    if (obj.getLabel() != null) {
      json.put("label", obj.getLabel());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getUpdated() != null) {
      json.put("updated", obj.getUpdated());
    }
    json.put("vlinkId", obj.getVlinkId());
    json.put("vltpId", obj.getVltpId());
  }
}
