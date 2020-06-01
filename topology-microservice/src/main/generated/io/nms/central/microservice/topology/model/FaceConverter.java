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
        case "error":
          if (member.getValue() instanceof String) {
            obj.setError((String)member.getValue());
          }
          break;
        case "id":
          if (member.getValue() instanceof String) {
            obj.setId((String)member.getValue());
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
        case "status":
          if (member.getValue() instanceof String) {
            obj.setStatus((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(Face obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Face obj, java.util.Map<String, Object> json) {
    if (obj.getError() != null) {
      json.put("error", obj.getError());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
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
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
  }
}
