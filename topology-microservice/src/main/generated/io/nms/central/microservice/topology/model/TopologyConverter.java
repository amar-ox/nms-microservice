package io.nms.central.microservice.topology.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.nms.central.microservice.topology.model.Topology}.
 * NOTE: This class has been automatically generated from the {@link io.nms.central.microservice.topology.model.Topology} original class using Vert.x codegen.
 */
public class TopologyConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Topology obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
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
        case "linkConns":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.nms.central.microservice.topology.model.LinkConn> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.nms.central.microservice.topology.model.LinkConn((JsonObject)item));
            });
            obj.setLinkConns(list);
          }
          break;
        case "links":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.nms.central.microservice.topology.model.Link> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.nms.central.microservice.topology.model.Link((JsonObject)item));
            });
            obj.setLinks(list);
          }
          break;
        case "nodes":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.nms.central.microservice.topology.model.Node> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.nms.central.microservice.topology.model.Node((JsonObject)item));
            });
            obj.setNodes(list);
          }
          break;
      }
    }
  }

  public static void toJson(Topology obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Topology obj, java.util.Map<String, Object> json) {
    if (obj.getError() != null) {
      json.put("error", obj.getError());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getLinkConns() != null) {
      JsonArray array = new JsonArray();
      obj.getLinkConns().forEach(item -> array.add(item.toJson()));
      json.put("linkConns", array);
    }
    if (obj.getLinks() != null) {
      JsonArray array = new JsonArray();
      obj.getLinks().forEach(item -> array.add(item.toJson()));
      json.put("links", array);
    }
    if (obj.getNodes() != null) {
      JsonArray array = new JsonArray();
      obj.getNodes().forEach(item -> array.add(item.toJson()));
      json.put("nodes", array);
    }
  }
}
