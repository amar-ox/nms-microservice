package io.nms.central.microservice.notification.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.notification.model.Status}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.notification.model.Status} original class using Vert.x codegen.
 */
public class StatusConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Status obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "id":
          if (member.getValue() instanceof String) {
            obj.setId((String)member.getValue());
          }
          break;
        case "resId":
          if (member.getValue() instanceof Number) {
            obj.setResId(((Number)member.getValue()).intValue());
          }
          break;
        case "resType":
          if (member.getValue() instanceof String) {
            obj.setResType((String)member.getValue());
          }
          break;
        case "status":
          if (member.getValue() instanceof String) {
            obj.setStatus((String)member.getValue());
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

  public static void toJson(Status obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Status obj, java.util.Map<String, Object> json) {
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    json.put("resId", obj.getResId());
    if (obj.getResType() != null) {
      json.put("resType", obj.getResType());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getTimestamp() != null) {
      json.put("timestamp", obj.getTimestamp());
    }
  }
}
