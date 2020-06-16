package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.Vsubnet}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.Vsubnet} original class using Vert.x codegen.
 */
public class VsubnetConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Vsubnet obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
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
          if (member.getValue() instanceof String) {
            obj.setId((String)member.getValue());
          }
          break;
        case "label":
          if (member.getValue() instanceof String) {
            obj.setLabel((String)member.getValue());
          }
          break;
        case "updated":
          if (member.getValue() instanceof String) {
            obj.setUpdated((String)member.getValue());
          }
          break;
        case "vlinks":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.nms.central.microservice.topology.model.Vlink> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.nms.central.microservice.topology.model.Vlink((JsonObject)item));
            });
            obj.setVlinks(list);
          }
          break;
        case "vnodes":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.nms.central.microservice.topology.model.Vnode> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.nms.central.microservice.topology.model.Vnode((JsonObject)item));
            });
            obj.setVnodes(list);
          }
          break;
      }
    }
  }

  public static void toJson(Vsubnet obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Vsubnet obj, java.util.Map<String, Object> json) {
    if (obj.getCreated() != null) {
      json.put("created", obj.getCreated());
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getLabel() != null) {
      json.put("label", obj.getLabel());
    }
    if (obj.getUpdated() != null) {
      json.put("updated", obj.getUpdated());
    }
    if (obj.getVlinks() != null) {
      JsonArray array = new JsonArray();
      obj.getVlinks().forEach(item -> array.add(item.toJson()));
      json.put("vlinks", array);
    }
    if (obj.getVnodes() != null) {
      JsonArray array = new JsonArray();
      obj.getVnodes().forEach(item -> array.add(item.toJson()));
      json.put("vnodes", array);
    }
  }
}
