package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.Route}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.Route} original class using Vert.x codegen.
 */
public class RouteConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Route obj) {
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
        case "faceId":
          if (member.getValue() instanceof Number) {
            obj.setFaceId(((Number)member.getValue()).intValue());
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
        case "nodeId":
          if (member.getValue() instanceof Number) {
            obj.setNodeId(((Number)member.getValue()).intValue());
          }
          break;
        case "origin":
          if (member.getValue() instanceof String) {
            obj.setOrigin((String)member.getValue());
          }
          break;
        case "paId":
          if (member.getValue() instanceof Number) {
            obj.setPaId(((Number)member.getValue()).intValue());
          }
          break;
        case "prefix":
          if (member.getValue() instanceof String) {
            obj.setPrefix((String)member.getValue());
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

  public static void toJson(Route obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Route obj, java.util.Map<String, Object> json) {
    json.put("cost", obj.getCost());
    if (obj.getCreated() != null) {
      json.put("created", obj.getCreated());
    }
    json.put("faceId", obj.getFaceId());
    json.put("id", obj.getId());
    json.put("nextHopId", obj.getNextHopId());
    json.put("nodeId", obj.getNodeId());
    if (obj.getOrigin() != null) {
      json.put("origin", obj.getOrigin());
    }
    json.put("paId", obj.getPaId());
    if (obj.getPrefix() != null) {
      json.put("prefix", obj.getPrefix());
    }
    if (obj.getUpdated() != null) {
      json.put("updated", obj.getUpdated());
    }
  }
}
