package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.RoutingContext;
import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilderFactory;

import jp.sample.vertx1.share.CastXML;
import jp.sample.vertx1.share.LogUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/** PUT されたリクエストのBodyからXMLファイルを取得して、Jsonに変換後、Jsonをレスポンスとして返します。 */
public class ConvertHandler implements Handler<RoutingContext> {

  /** Logger */
  private static final LogUtils LOGGER = new LogUtils(ConvertHandler.class);

  public static ConvertHandler create() {
    return new ConvertHandler();
  }

  @Override
  public void handle(RoutingContext event) {
    try {
      var doc = load(event.body());
      var json = convert(event, doc);
      LOGGER.debug(event.session().id(), json.encodePrettily());
      event.response().setStatusCode(200);
      event.response().end(json.encodePrettily());
    } catch (Throwable t) {
      t.printStackTrace();
      event.response().setStatusMessage("cast error. xml file");
      event.response().setStatusCode(500);
      event.fail(t);
    }
  }

  private Document load(RequestBody body) throws Exception {
    var factory = DocumentBuilderFactory.newInstance();
    var builder = factory.newDocumentBuilder();
    var input = new ByteArrayInputStream(body.buffer().getBytes());
    var doc = builder.parse(input);
    input.close();
    return doc;
  }

  private JsonArray convert(RoutingContext event, Document doc) throws Exception {
    var array = new JsonArray();
    var element = doc.getDocumentElement();
    var xml = new CastXML(event, doc);
    var nodeList = element.getElementsByTagName("Book");

    for (int i = 0; i < nodeList.getLength(); i++) {
      var detail = new JsonObject();
      var node = (Element) nodeList.item(i);
      detail.put("isbn", node.getAttribute("isbn"));
      detail.put("author", node.getAttribute("author"));
      detail.put("title", node.getAttribute("title"));
      detail.put("content", node.getTextContent());
      array.add(detail);
    }
    return array;
  }
}
