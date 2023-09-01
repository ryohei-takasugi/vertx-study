package jp.sample.vertx1.handlers.main.api;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.RoutingContext;
import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import jp.sample.vertx1.models.IResponseRoutingContext;
import jp.sample.vertx1.modules.HandlerLogger;
import jp.sample.vertx1.modules.XmlToJson;
import org.w3c.dom.Document;

/** PUT されたリクエストのBodyからXMLファイルを取得して、Jsonに変換後、Jsonをレスポンスとして返します。 */
public class ConvertHandler
    implements Handler<RoutingContext>, IResponseRoutingContext<JsonObject> {

  /** Logger */
  private static final HandlerLogger LOGGER = HandlerLogger.create(ConvertHandler.class);

  protected ConvertHandler() {}

  @Override
  public void handle(RoutingContext event) {
    try {
      var doc = requestToXmlDocument(event.body());
      var xml = new XmlToJson(doc);
      success(event, xml.toJson());
      return;
    } catch (Throwable th) {
      var message = "cast error. xml file";
      failed(event, FAILED_STATUS_CODE, message, th);
      return;
    }
  }

  private Document requestToXmlDocument(RequestBody body) throws Exception {
    if (body == null) {
      throw new IllegalArgumentException(
          "Processing will be aborted because the request data is empty.");
    }
    var factory = DocumentBuilderFactory.newInstance();
    var builder = factory.newDocumentBuilder();
    var input = new ByteArrayInputStream(body.buffer().getBytes());
    var doc = builder.parse(input);
    input.close();
    return doc;
  }

  @Override
  public void success(RoutingContext event, JsonObject object) {
    LOGGER.info(event.session(), object.encode());
    var response = event.response();
    response.setStatusCode(SUCCESS_STATUS_CODE);
    response.end(object.encode());
  }

  @Override
  public void failed(RoutingContext event, int statusCode, String errorMessage, Throwable th) {
    LOGGER.error(event.session(), errorMessage, th);
    var response = event.response();
    response.setStatusCode(statusCode);
    response.setStatusMessage(errorMessage);
  }
}
