package io.nms.central.microservice.dss;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.dss.ProductTuple}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.dss.ProductTuple} original class using Vert.x codegen.
 */
public class ProductTupleConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, ProductTuple obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "amount":
          if (member.getValue() instanceof Number) {
            obj.setAmount(((Number)member.getValue()).intValue());
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
      }
    }
  }

  public static void toJson(ProductTuple obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(ProductTuple obj, java.util.Map<String, Object> json) {
    if (obj.getAmount() != null) {
      json.put("amount", obj.getAmount());
    }
    if (obj.getPrice() != null) {
      json.put("price", obj.getPrice());
    }
    if (obj.getProductId() != null) {
      json.put("productId", obj.getProductId());
    }
    if (obj.getSellerId() != null) {
      json.put("sellerId", obj.getSellerId());
    }
  }
}
