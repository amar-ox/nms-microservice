package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.Edge}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.Edge} original class using Vert.x codegen.
 */
public class EdgeConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Edge obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "destFaceId":
          if (member.getValue() instanceof Number) {
            obj.setDestFaceId(((Number)member.getValue()).intValue());
          }
          break;
        case "destNodeId":
          if (member.getValue() instanceof Number) {
            obj.setDestNodeId(((Number)member.getValue()).intValue());
          }
          break;
        case "id":
          if (member.getValue() instanceof Number) {
            obj.setId(((Number)member.getValue()).intValue());
          }
          break;
        case "srcFaceId":
          if (member.getValue() instanceof Number) {
            obj.setSrcFaceId(((Number)member.getValue()).intValue());
          }
          break;
        case "srcNodeId":
          if (member.getValue() instanceof Number) {
            obj.setSrcNodeId(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(Edge obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Edge obj, java.util.Map<String, Object> json) {
    json.put("destFaceId", obj.getDestFaceId());
    json.put("destNodeId", obj.getDestNodeId());
    json.put("id", obj.getId());
    json.put("srcFaceId", obj.getSrcFaceId());
    json.put("srcNodeId", obj.getSrcNodeId());
  }
}
