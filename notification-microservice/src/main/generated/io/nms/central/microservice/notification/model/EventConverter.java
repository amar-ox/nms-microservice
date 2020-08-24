package io.nms.central.microservice.notification.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.notification.model.Event}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.notification.model.Event} original class using Vert.x codegen.
 */
public class EventConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Event obj) {
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
        case "severity":
          if (member.getValue() instanceof String) {
            obj.setSeverity(io.nms.central.microservice.notification.model.Event.SeverityEnum.valueOf((String)member.getValue()));
          }
          break;
      }
    }
  }

  public static void toJson(Event obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Event obj, java.util.Map<String, Object> json) {
    if (obj.getCode() != null) {
      json.put("code", obj.getCode());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getMsg() != null) {
      json.put("msg", obj.getMsg());
    }
    if (obj.getOrigin() != null) {
      json.put("origin", obj.getOrigin());
    }
    if (obj.getSeverity() != null) {
      json.put("severity", obj.getSeverity().name());
    }
  }
}
