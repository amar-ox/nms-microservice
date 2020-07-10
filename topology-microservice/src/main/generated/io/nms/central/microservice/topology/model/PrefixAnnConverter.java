package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.PrefixAnn}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.PrefixAnn} original class using Vert.x codegen.
 */
public class PrefixAnnConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, PrefixAnn obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "created":
          if (member.getValue() instanceof String) {
            obj.setCreated((String)member.getValue());
          }
          break;
        case "expiration":
          if (member.getValue() instanceof String) {
            obj.setExpiration((String)member.getValue());
          }
          break;
        case "id":
          if (member.getValue() instanceof Number) {
            obj.setId(((Number)member.getValue()).intValue());
          }
          break;
        case "name":
          if (member.getValue() instanceof String) {
            obj.setName((String)member.getValue());
          }
          break;
        case "originId":
          if (member.getValue() instanceof Number) {
            obj.setOriginId(((Number)member.getValue()).intValue());
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

  public static void toJson(PrefixAnn obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(PrefixAnn obj, java.util.Map<String, Object> json) {
    if (obj.getCreated() != null) {
      json.put("created", obj.getCreated());
    }
    if (obj.getExpiration() != null) {
      json.put("expiration", obj.getExpiration());
    }
    json.put("id", obj.getId());
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    json.put("originId", obj.getOriginId());
    if (obj.getUpdated() != null) {
      json.put("updated", obj.getUpdated());
    }
  }
}
