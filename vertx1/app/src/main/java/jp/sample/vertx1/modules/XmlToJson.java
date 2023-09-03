package jp.sample.vertx1.modules;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XmlToJson {

  private final Element element;

  public static XmlToJson create(byte[] requestBody) {
    return new XmlToJson(requestBody);
  }

  private XmlToJson(byte[] requestBody) {
    Document doc = castByteToDocument(requestBody);
    if (doc == null) {
      throw new IllegalArgumentException(
          "Processing will be aborted because the request document is empty.");
    }
    this.element = doc.getDocumentElement();
  }

  private Document castByteToDocument(byte[] requestBody) {
    try {
      var factory = DocumentBuilderFactory.newInstance();
      var builder = factory.newDocumentBuilder();
      var input = new ByteArrayInputStream(requestBody);
      var doc = builder.parse(input);
      input.close();
      return doc;
    } catch (Throwable th) {
      throw new IllegalArgumentException(
          "Processing will be aborted because the request buffer is empty.");
    }
  }

  public JsonObject toJson() {
    return cast(this.element);
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
}
