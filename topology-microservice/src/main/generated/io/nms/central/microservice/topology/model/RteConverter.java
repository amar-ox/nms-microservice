package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.Rte}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.Rte} original class using Vert.x codegen.
 */
public class RteConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Rte obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "cost":
          if (member.getValue() instanceof Number) {
            obj.setCost(((Number)member.getValue()).intValue());
          }
          break;
        case "created":
          if (member.getValue() instanceof String) {
            obj.setCreated((String)member.getValue());
          }
          break;
        case "ctpId":
          if (member.getValue() instanceof Number) {
            obj.setCtpId(((Number)member.getValue()).intValue());
          }
          break;
        case "fromNodeId":
          if (member.getValue() instanceof Number) {
            obj.setFromNodeId(((Number)member.getValue()).intValue());
          }
          break;
        case "id":
          if (member.getValue() instanceof Number) {
            obj.setId(((Number)member.getValue()).intValue());
          }
          break;
        case "nextHopId":
          if (member.getValue() instanceof Number) {
            obj.setNextHopId(((Number)member.getValue()).intValue());
          }
          break;
        case "prefixId":
          if (member.getValue() instanceof Number) {
            obj.setPrefixId(((Number)member.getValue()).intValue());
          }
          break;
        case "prefixName":
          if (member.getValue() instanceof String) {
            obj.setPrefixName((String)member.getValue());
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
      }
    }
  }

  public static void toJson(Rte obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Rte obj, java.util.Map<String, Object> json) {
    json.put("cost", obj.getCost());
    if (obj.getCreated() != null) {
      json.put("created", obj.getCreated());
    }
    json.put("ctpId", obj.getCtpId());
    json.put("fromNodeId", obj.getFromNodeId());
    json.put("id", obj.getId());
    json.put("nextHopId", obj.getNextHopId());
    json.put("prefixId", obj.getPrefixId());
    if (obj.getPrefixName() != null) {
      json.put("prefixName", obj.getPrefixName());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getUpdated() != null) {
      json.put("updated", obj.getUpdated());
    }
  }
}
