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
        case "location":
          if (member.getValue() instanceof String) {
            obj.setLocation((String)member.getValue());
          }
          break;
        case "ltps":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.nms.central.microservice.topology.model.Ltp> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.nms.central.microservice.topology.model.Ltp((JsonObject)item));
            });
            obj.setLtps(list);
          }
          break;
        case "managed":
          if (member.getValue() instanceof Boolean) {
            obj.setManaged((Boolean)member.getValue());
          }
          break;
        case "status":
          if (member.getValue() instanceof String) {
            obj.setStatus((String)member.getValue());
          }
          break;
        case "type":
          if (member.getValue() instanceof String) {
            obj.setType((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(Node obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Node obj, java.util.Map<String, Object> json) {
    if (obj.getError() != null) {
      json.put("error", obj.getError());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getLocation() != null) {
      json.put("location", obj.getLocation());
    }
    if (obj.getLtps() != null) {
      JsonArray array = new JsonArray();
      obj.getLtps().forEach(item -> array.add(item.toJson()));
      json.put("ltps", array);
    }
    json.put("managed", obj.isManaged());
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
  }
}
