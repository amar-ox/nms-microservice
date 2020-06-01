package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.LinkConn}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.LinkConn} original class using Vert.x codegen.
 */
public class LinkConnConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, LinkConn obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "adminCost":
          if (member.getValue() instanceof String) {
            obj.setAdminCost((String)member.getValue());
          }
          break;
        case "destCtp":
          if (member.getValue() instanceof String) {
            obj.setDestCtp((String)member.getValue());
          }
          break;
        case "destLtp":
          if (member.getValue() instanceof String) {
            obj.setDestLtp((String)member.getValue());
          }
          break;
        case "destNode":
          if (member.getValue() instanceof String) {
            obj.setDestNode((String)member.getValue());
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
        case "sourceCtp":
          if (member.getValue() instanceof String) {
            obj.setSourceCtp((String)member.getValue());
          }
          break;
        case "sourceLtp":
          if (member.getValue() instanceof String) {
            obj.setSourceLtp((String)member.getValue());
          }
          break;
        case "sourceNode":
          if (member.getValue() instanceof String) {
            obj.setSourceNode((String)member.getValue());
          }
          break;
        case "status":
          if (member.getValue() instanceof String) {
            obj.setStatus((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(LinkConn obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(LinkConn obj, java.util.Map<String, Object> json) {
    if (obj.getAdminCost() != null) {
      json.put("adminCost", obj.getAdminCost());
    }
    if (obj.getDestCtp() != null) {
      json.put("destCtp", obj.getDestCtp());
    }
    if (obj.getDestLtp() != null) {
      json.put("destLtp", obj.getDestLtp());
    }
    if (obj.getDestNode() != null) {
      json.put("destNode", obj.getDestNode());
    }
    if (obj.getError() != null) {
      json.put("error", obj.getError());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getSourceCtp() != null) {
      json.put("sourceCtp", obj.getSourceCtp());
    }
    if (obj.getSourceLtp() != null) {
      json.put("sourceLtp", obj.getSourceLtp());
    }
    if (obj.getSourceNode() != null) {
      json.put("sourceNode", obj.getSourceNode());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
  }
}
