package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.Node}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.Node} original class using Vert.x codegen.
 */
public class NodeConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Node obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "id":
          if (member.getValue() instanceof Number) {
            obj.setId(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(Node obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Node obj, java.util.Map<String, Object> json) {
    json.put("id", obj.getId());
  }
}
