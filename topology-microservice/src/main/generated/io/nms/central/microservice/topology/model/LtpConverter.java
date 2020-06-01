package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.Ltp}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.Ltp} original class using Vert.x codegen.
 */
public class LtpConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Ltp obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "ctps":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.nms.central.microservice.topology.model.Ctp> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.nms.central.microservice.topology.model.Ctp((JsonObject)item));
            });
            obj.setCtps(list);
          }
          break;
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
        case "idOnNode":
          if (member.getValue() instanceof String) {
            obj.setIdOnNode((String)member.getValue());
          }
          break;
        case "model":
          if (member.getValue() instanceof String) {
            obj.setModel((String)member.getValue());
          }
          break;
        case "node":
          if (member.getValue() instanceof String) {
            obj.setNode((String)member.getValue());
          }
          break;
        case "speed":
          if (member.getValue() instanceof String) {
            obj.setSpeed((String)member.getValue());
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

  public static void toJson(Ltp obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Ltp obj, java.util.Map<String, Object> json) {
    if (obj.getCtps() != null) {
      JsonArray array = new JsonArray();
      obj.getCtps().forEach(item -> array.add(item.toJson()));
      json.put("ctps", array);
    }
    if (obj.getError() != null) {
      json.put("error", obj.getError());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getIdOnNode() != null) {
      json.put("idOnNode", obj.getIdOnNode());
    }
    if (obj.getModel() != null) {
      json.put("model", obj.getModel());
    }
    if (obj.getNode() != null) {
      json.put("node", obj.getNode());
    }
    if (obj.getSpeed() != null) {
      json.put("speed", obj.getSpeed());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
  }
}
