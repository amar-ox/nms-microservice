package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.Vlink}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.Vlink} original class using Vert.x codegen.
 */
public class VlinkConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Vlink obj) {
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
        case "destVltpId":
          if (member.getValue() instanceof Number) {
            obj.setDestVltpId(((Number)member.getValue()).intValue());
          }
          break;
        case "destVnodeId":
          if (member.getValue() instanceof Number) {
            obj.setDestVnodeId(((Number)member.getValue()).intValue());
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
        case "speed":
          if (member.getValue() instanceof String) {
            obj.setSpeed((String)member.getValue());
          }
          break;
        case "srcVltpId":
          if (member.getValue() instanceof Number) {
            obj.setSrcVltpId(((Number)member.getValue()).intValue());
          }
          break;
        case "srcVnodeId":
          if (member.getValue() instanceof Number) {
            obj.setSrcVnodeId(((Number)member.getValue()).intValue());
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
        case "vlinkConns":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.nms.central.microservice.topology.model.VlinkConn> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.nms.central.microservice.topology.model.VlinkConn((JsonObject)item));
            });
            obj.setVlinkConns(list);
          }
          break;
        case "vsubnetId":
          if (member.getValue() instanceof Number) {
            obj.setVsubnetId(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(Vlink obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Vlink obj, java.util.Map<String, Object> json) {
    if (obj.getCreated() != null) {
      json.put("created", obj.getCreated());
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    json.put("destVltpId", obj.getDestVltpId());
    json.put("destVnodeId", obj.getDestVnodeId());
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
    if (obj.getSpeed() != null) {
      json.put("speed", obj.getSpeed());
    }
    json.put("srcVltpId", obj.getSrcVltpId());
    json.put("srcVnodeId", obj.getSrcVnodeId());
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getUpdated() != null) {
      json.put("updated", obj.getUpdated());
    }
    if (obj.getVlinkConns() != null) {
      JsonArray array = new JsonArray();
      obj.getVlinkConns().forEach(item -> array.add(item.toJson()));
      json.put("vlinkConns", array);
    }
    json.put("vsubnetId", obj.getVsubnetId());
  }
}
