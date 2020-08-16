package io.nms.central.microservice.notification.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.notification.model.Fault}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.notification.model.Fault} original class using Vert.x codegen.
 */
public class FaultConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Fault obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "code":
          if (member.getValue() instanceof Number) {
            obj.setCode(((Number)member.getValue()).intValue());
          }
          break;
        case "id":
          if (member.getValue() instanceof String) {
            obj.setId((String)member.getValue());
          }
          break;
        case "msg":
          if (member.getValue() instanceof String) {
            obj.setMsg((String)member.getValue());
          }
          break;
        case "origin":
          if (member.getValue() instanceof Number) {
            obj.setOrigin(((Number)member.getValue()).intValue());
          }
          break;
        case "timestamp":
          if (member.getValue() instanceof String) {
            obj.setTimestamp((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(Fault obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Fault obj, java.util.Map<String, Object> json) {
    json.put("code", obj.getCode());
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getMsg() != null) {
      json.put("msg", obj.getMsg());
    }
    json.put("origin", obj.getOrigin());
    if (obj.getTimestamp() != null) {
      json.put("timestamp", obj.getTimestamp());
    }
  }
}
