package jp.sample.vertx1.handlers.main.api;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.models.enumeration.HttpStatus;
import jp.sample.vertx1.models.handler.IApiHandlerResponse;
import jp.sample.vertx1.modules.HandlerLogger;
import jp.sample.vertx1.modules.XmlToJson;
import org.xml.sax.SAXException;

/** PUT されたリクエストのBodyからXMLファイルを取得して、Jsonに変換後、Jsonをレスポンスとして返します。 */
public class ConvertHandler implements Handler<RoutingContext>, IApiHandlerResponse {

  /** Logger */
  private static final HandlerLogger logger = HandlerLogger.create(ConvertHandler.class);

  protected ConvertHandler() {}

  @Override
  public void handle(RoutingContext ctx) {
    try {
      var body = ctx.body();
      if (body == null) {
        throw new IllegalArgumentException(
            "Processing will be aborted because the request data is empty.");
      }
      var xml = XmlToJson.create(body.buffer().getBytes());
      response(ctx, xml.toJson());
    } catch (Throwable th) {
      if (th instanceof SAXException || th instanceof IllegalArgumentException) {
        ctx.fail(HttpStatus.BAD_REQUEST.code(), th);
      } else {
        ctx.fail(th);
      }
    }
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
