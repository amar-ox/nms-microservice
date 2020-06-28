package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.VlinkConn}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.VlinkConn} original class using Vert.x codegen.
 */
public class VlinkConnConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, VlinkConn obj) {
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
        case "vlinkId":
          if (member.getValue() instanceof Number) {
            obj.setVlinkId(((Number)member.getValue()).intValue());
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

  public static void toJson(VlinkConn obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(VlinkConn obj, java.util.Map<String, Object> json) {
    if (obj.getCreated() != null) {
      json.put("created", obj.getCreated());
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    json.put("destVctpId", obj.getDestVctpId());
    json.put("destVltpId", obj.getDestVltpId());
    json.put("destVnodeId", obj.getDestVnodeId());
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
    json.put("srcVltpId", obj.getSrcVltpId());
    json.put("srcVnodeId", obj.getSrcVnodeId());
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getUpdated() != null) {
      json.put("updated", obj.getUpdated());
    }
    json.put("vlinkId", obj.getVlinkId());
    json.put("vsubnetId", obj.getVsubnetId());
  }
}
