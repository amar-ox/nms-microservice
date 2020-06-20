package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.Vltp}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.Vltp} original class using Vert.x codegen.
 */
public class VltpConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Vltp obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "busy":
          if (member.getValue() instanceof Boolean) {
            obj.setBusy((Boolean)member.getValue());
          }
          break;
        case "created":
          if (member.getValue() instanceof String) {
            obj.setCreated((String)member.getValue());
          }
          break;
        case "description":
          if (member.getValue() instanceof String) {
            obj.setDescription((String)member.getValue());
          }
          break;
        case "id":
          if (member.getValue() instanceof Number) {
            obj.setId(((Number)member.getValue()).intValue());
          }
          break;
        case "info":
          if (member.getValue() instanceof JsonObject) {
            obj.setInfo(((JsonObject)member.getValue()).copy());
          }
          break;
        case "label":
          if (member.getValue() instanceof String) {
            obj.setLabel((String)member.getValue());
          }
          break;
        case "name":
          if (member.getValue() instanceof String) {
            obj.setName((String)member.getValue());
          }
          break;
        case "status":
          if (member.getValue() instanceof String) {
            obj.setStatus((String)member.getValue());
          }
          break;
        case "updated":
          if (member.getValue() instanceof String) {
            obj.setUpdated((String)member.getValue());
          }
          break;
        case "vctps":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.nms.central.microservice.topology.model.Vctp> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.nms.central.microservice.topology.model.Vctp((JsonObject)item));
            });
            obj.setVctps(list);
          }
          break;
        case "vnodeId":
          if (member.getValue() instanceof Number) {
            obj.setVnodeId(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(Vltp obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Vltp obj, java.util.Map<String, Object> json) {
    if (obj.isBusy() != null) {
      json.put("busy", obj.isBusy());
    }
    if (obj.getCreated() != null) {
      json.put("created", obj.getCreated());
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    json.put("id", obj.getId());
    if (obj.getInfo() != null) {
      json.put("info", obj.getInfo());
    }
    if (obj.getLabel() != null) {
      json.put("label", obj.getLabel());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getUpdated() != null) {
      json.put("updated", obj.getUpdated());
    }
    if (obj.getVctps() != null) {
      JsonArray array = new JsonArray();
      obj.getVctps().forEach(item -> array.add(item.toJson()));
      json.put("vctps", array);
    }
    json.put("vnodeId", obj.getVnodeId());
  }
}
