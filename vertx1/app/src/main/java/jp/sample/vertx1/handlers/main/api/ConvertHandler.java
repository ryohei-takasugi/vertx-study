package jp.sample.vertx1.handlers.main.api;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.RoutingContext;
import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import jp.sample.vertx1.models.enumeration.HttpStatus;
import jp.sample.vertx1.models.handler.IApiHandlerResponse;
import jp.sample.vertx1.modules.HandlerLogger;
import jp.sample.vertx1.modules.XmlToJson;
import org.w3c.dom.Document;

/** PUT されたリクエストのBodyからXMLファイルを取得して、Jsonに変換後、Jsonをレスポンスとして返します。 */
public class ConvertHandler implements Handler<RoutingContext>, IApiHandlerResponse {

  /** Logger */
  private static final HandlerLogger logger = HandlerLogger.create(ConvertHandler.class);

  protected ConvertHandler() {}

  @Override
  public void handle(RoutingContext ctx) {
    try {
      var doc = requestToXmlDocument(ctx.body());
      var xml = new XmlToJson(doc);
      response(ctx, xml.toJson());
    } catch (Throwable th) {
      ctx.fail(400, th);
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
  public void response(RoutingContext ctx, JsonObject res) {
    logger.info(ctx.session(), res.encode());
    var response = ctx.response();
    response.setStatusCode(HttpStatus.OK.code());
    response.setStatusMessage(HttpStatus.OK.message());
    response.end(res.encode());
  }
}
