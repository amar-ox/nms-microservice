package io.nms.central.microservice.configuration.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.configuration.model.ConfigFace}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.configuration.model.ConfigFace} original class using Vert.x codegen.
 */
public class ConfigFaceConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, ConfigFace obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "id":
          if (member.getValue() instanceof Number) {
            obj.setId(((Number)member.getValue()).intValue());
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
            obj.setScheme(io.nms.central.microservice.topology.model.Face.SchemeEnum.valueOf((String)member.getValue()));
          }
          break;
      }
    }
  }

  public static void toJson(ConfigFace obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(ConfigFace obj, java.util.Map<String, Object> json) {
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
      json.put("scheme", obj.getScheme().name());
    }
  }
}
