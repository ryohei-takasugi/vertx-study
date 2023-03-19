package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.RoutingContext;
import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import jp.sample.vertx1.MainServices.models.XmlToJson;
import jp.sample.vertx1.share.MyLogger;
import org.w3c.dom.Document;

/** PUT されたリクエストのBodyからXMLファイルを取得して、Jsonに変換後、Jsonをレスポンスとして返します。 */
public class ConvertHandler implements Handler<RoutingContext> {

  /** Logger */
  private static final MyLogger LOGGER = MyLogger.create(ConvertHandler.class);

  protected ConvertHandler() {}

  @Override
  public void handle(RoutingContext event) {
    try {
      var doc = load(event.body());
      var json = convert(event, doc);
      LOGGER.debug(event.session(), json.encodePrettily());
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

  private JsonObject convert(RoutingContext event, Document doc) throws Exception {
    var xml = new XmlToJson(doc);
    LOGGER.debug(event.session(), xml.toJson().encodePrettily());
    return xml.toJson();
  }
}
