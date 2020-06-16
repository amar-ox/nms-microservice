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
          if (member.getValue() instanceof String) {
            obj.setDestVctpId((String)member.getValue());
          }
          break;
        case "destVnode":
          if (member.getValue() instanceof String) {
            obj.setDestVnode((String)member.getValue());
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
        case "srcVctpId":
          if (member.getValue() instanceof String) {
            obj.setSrcVctpId((String)member.getValue());
          }
          break;
        case "srcVnode":
          if (member.getValue() instanceof String) {
            obj.setSrcVnode((String)member.getValue());
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
    if (obj.getDestVctpId() != null) {
      json.put("destVctpId", obj.getDestVctpId());
    }
    if (obj.getDestVnode() != null) {
      json.put("destVnode", obj.getDestVnode());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getLabel() != null) {
      json.put("label", obj.getLabel());
    }
    if (obj.getSrcVctpId() != null) {
      json.put("srcVctpId", obj.getSrcVctpId());
    }
    if (obj.getSrcVnode() != null) {
      json.put("srcVnode", obj.getSrcVnode());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getUpdated() != null) {
      json.put("updated", obj.getUpdated());
    }
  }
}
