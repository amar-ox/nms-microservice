package io.nms.central.microservice.dss;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.dss.Product}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.dss.Product} original class using Vert.x codegen.
 */
public class ProductConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Product obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "illustration":
          if (member.getValue() instanceof String) {
            obj.setIllustration((String)member.getValue());
          }
          break;
        case "name":
          if (member.getValue() instanceof String) {
            obj.setName((String)member.getValue());
          }
          break;
        case "price":
          if (member.getValue() instanceof Number) {
            obj.setPrice(((Number)member.getValue()).doubleValue());
          }
          break;
        case "productId":
          if (member.getValue() instanceof String) {
            obj.setProductId((String)member.getValue());
          }
          break;
        case "sellerId":
          if (member.getValue() instanceof String) {
            obj.setSellerId((String)member.getValue());
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

  public static void toJson(Product obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Product obj, java.util.Map<String, Object> json) {
    if (obj.getIllustration() != null) {
      json.put("illustration", obj.getIllustration());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    json.put("price", obj.getPrice());
    if (obj.getProductId() != null) {
      json.put("productId", obj.getProductId());
    }
    if (obj.getSellerId() != null) {
      json.put("sellerId", obj.getSellerId());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
  }
}
