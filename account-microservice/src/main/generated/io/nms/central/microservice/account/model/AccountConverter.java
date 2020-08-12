package io.nms.central.microservice.account.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.account.model.Account}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.account.model.Account} original class using Vert.x codegen.
 */
public class AccountConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Account obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "nodeId":
          if (member.getValue() instanceof Number) {
            obj.setNodeId(((Number)member.getValue()).intValue());
          }
          break;
        case "role":
          if (member.getValue() instanceof String) {
            obj.setRole((String)member.getValue());
          }
          break;
        case "username":
          if (member.getValue() instanceof String) {
            obj.setUsername((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(Account obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Account obj, java.util.Map<String, Object> json) {
    json.put("nodeId", obj.getNodeId());
    if (obj.getRole() != null) {
      json.put("role", obj.getRole());
    }
    if (obj.getUsername() != null) {
      json.put("username", obj.getUsername());
    }
  }
}
