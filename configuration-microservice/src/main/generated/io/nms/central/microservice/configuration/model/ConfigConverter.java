package io.nms.central.microservice.configuration.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.configuration.model.Config}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.configuration.model.Config} original class using Vert.x codegen.
 */
public class ConfigConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Config obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "faces":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.nms.central.microservice.configuration.model.ConfigFace> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.nms.central.microservice.configuration.model.ConfigFace((JsonObject)item));
            });
            obj.setFaces(list);
          }
          break;
        case "routes":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.nms.central.microservice.configuration.model.ConfigRoute> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.nms.central.microservice.configuration.model.ConfigRoute((JsonObject)item));
            });
            obj.setRoutes(list);
          }
          break;
      }
    }
  }

  public static void toJson(Config obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Config obj, java.util.Map<String, Object> json) {
    if (obj.getFaces() != null) {
      JsonArray array = new JsonArray();
      obj.getFaces().forEach(item -> array.add(item.toJson()));
      json.put("faces", array);
    }
    if (obj.getRoutes() != null) {
      JsonArray array = new JsonArray();
      obj.getRoutes().forEach(item -> array.add(item.toJson()));
      json.put("routes", array);
    }
  }
}
