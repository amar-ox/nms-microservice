package io.nms.central.microservice.configuration.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.configuration.model.ConfigObj}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.configuration.model.ConfigObj} original class using Vert.x codegen.
 */
public class ConfigObjConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, ConfigObj obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "config":
          if (member.getValue() instanceof JsonObject) {
            obj.setConfig(new io.nms.central.microservice.configuration.model.Config((JsonObject)member.getValue()));
          }
          break;
        case "nodeId":
          if (member.getValue() instanceof Number) {
            obj.setNodeId(((Number)member.getValue()).intValue());
          }
          break;
        case "version":
          if (member.getValue() instanceof Number) {
            obj.setVersion(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(ConfigObj obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(ConfigObj obj, java.util.Map<String, Object> json) {
    if (obj.getConfig() != null) {
      json.put("config", obj.getConfig().toJson());
    }
    json.put("nodeId", obj.getNodeId());
    json.put("version", obj.getVersion());
  }
}
