package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.Vxc}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.Vxc} original class using Vert.x codegen.
 */
public class VxcConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Vxc obj) {
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
        case "destVctpId":
          if (member.getValue() instanceof Number) {
            obj.setDestVctpId(((Number)member.getValue()).intValue());
          }
          break;
        case "dropVctpId":
          if (member.getValue() instanceof Number) {
            obj.setDropVctpId(((Number)member.getValue()).intValue());
          }
          break;
        case "id":
          if (member.getValue() instanceof Number) {
            obj.setId(((Number)member.getValue()).intValue());
          }
          break;
        case "info":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.Object> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Object)
                map.put(entry.getKey(), entry.getValue());
            });
            obj.setInfo(map);
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
        case "srcVctpId":
          if (member.getValue() instanceof Number) {
            obj.setSrcVctpId(((Number)member.getValue()).intValue());
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
        case "updated":
          if (member.getValue() instanceof String) {
            obj.setUpdated((String)member.getValue());
          }
          break;
        case "vnodeId":
          if (member.getValue() instanceof Number) {
            obj.setVnodeId(((Number)member.getValue()).intValue());
          }
          break;
        case "vsubnetId":
          if (member.getValue() instanceof Number) {
            obj.setVsubnetId(((Number)member.getValue()).intValue());
          }
          break;
        case "vtrailId":
          if (member.getValue() instanceof Number) {
            obj.setVtrailId(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(Vxc obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Vxc obj, java.util.Map<String, Object> json) {
    if (obj.getCreated() != null) {
      json.put("created", obj.getCreated());
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    json.put("destVctpId", obj.getDestVctpId());
    json.put("dropVctpId", obj.getDropVctpId());
    json.put("id", obj.getId());
    if (obj.getInfo() != null) {
      JsonObject map = new JsonObject();
      obj.getInfo().forEach((key, value) -> map.put(key, value));
      json.put("info", map);
    }
    if (obj.getLabel() != null) {
      json.put("label", obj.getLabel());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    json.put("srcVctpId", obj.getSrcVctpId());
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getUpdated() != null) {
      json.put("updated", obj.getUpdated());
    }
    json.put("vnodeId", obj.getVnodeId());
    json.put("vsubnetId", obj.getVsubnetId());
    json.put("vtrailId", obj.getVtrailId());
  }
}
