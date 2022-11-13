package jp.sample.vertx1.share;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class CastXML {

  private final RoutingContext event;
  private final Document doc;

  public CastXML(RoutingContext event, Document doc) {
    this.event = event;
    this.doc = doc;
  }

  public JsonObject toJson() {
    var json = new JsonObject();
    var element = doc.getDocumentElement();
    // if (element.hasChildNodes()) {
    //   var array = new JsonArray();
    //   var childs = element.getChildNodes();
    //   for (int i = 0; i < childs.getLength(); i++) {
    //     if (childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
    //       var name = childs.item(i).getNodeName();
    //       array.add(cast(childs.item(i)));
    //     }
    //   }
    //   json.put(element.getNodeName(), array);
    // } else {
    //   var d = new JsonObject();
    //   d.put("attributes", attributes(element));
    //   d.put("content", element.getTextContent());
    //   json.put(element.getNodeName(), d);
    // }
    // return json;
    return cast(element);
  }

  private JsonObject cast(Node childs) {
    var json = new JsonObject();
    if (hasChildNodes(childs)) {
      var array = new JsonArray();
      var grandChilds = childs.getChildNodes();
      for (int i = 0; i < grandChilds.getLength(); i++) {
        if (grandChilds.item(i).getNodeType() == Node.ELEMENT_NODE) {
          array.add(cast(grandChilds.item(i)));
        }
      }
      json.put(childs.getNodeName(), array);
    } else {
      var d = new JsonObject();
      d.put("attributes", attributes(childs));
      d.put("content", childs.getTextContent());
      json.put(childs.getNodeName(), d);
    }
    return json;
  }

  private JsonObject attributes(Node node) {
    var json = new JsonObject();
    if (node.hasAttributes()) {
      var attributes = node.getAttributes();
      for (int i = 0; i < attributes.getLength(); i++) {
        json.put(attributes.item(i).getNodeName(), attributes.item(i).getNodeValue());
      }
    }
    return json;
  }

  private boolean hasChildNodes(Node target) {
    var array = new JsonArray();
    if (target.hasChildNodes()) {
      var childs = target.getChildNodes();
      for (int i = 0; i < childs.getLength(); i++) {
        if (childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
          array.add(childs.item(i));
        }
      }
    }
    return !array.isEmpty();
  }

  private JsonArray getChildNodes(Node target) {
    var array = new JsonArray();
    if (target.hasChildNodes()) {
      var childs = target.getChildNodes();
      for (int i = 0; i < childs.getLength(); i++) {
        if (childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
          array.add(childs.item(i));
        }
      }
    }
    var result = new JsonArray();
    array.forEach(
        child -> {
          if (!result.contains(child)) {
            result.add(child);
          }
        });
    return result;
  }
}
