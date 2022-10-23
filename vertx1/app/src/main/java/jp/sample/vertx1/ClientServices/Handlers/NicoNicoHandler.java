package jp.sample.vertx1.ClientServices.Handlers;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NicoNicoHandler implements Handler<Message<JsonObject>> {

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(NicoNicoHandler.class);

  /** vertx */
  private final Vertx vertx;

  /**
   * NicoNicoHandler Contractor
   *
   * @param vertx
   */
  public NicoNicoHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  /**
   * Create NicoNicoHandler class method.
   *
   * @return NicoNicoHandler instance.
   */
  public static Handler<Message<JsonObject>> create(Vertx vertx) {
    return new NicoNicoHandler(vertx);
  }

  @Override
  public void handle(Message<JsonObject> event) {
    // TODO Auto-generated method stub
    JsonObject message = event.body();
    JsonObject reply = new JsonObject();

    WebClient client = WebClient.create(vertx);
    Future<HttpResponse<Buffer>> fut =
        client
            .get(443, "api.search.nicovideo.jp", "/api/v2/snapshot/video/contents/search")
            .ssl(true)
            .addQueryParam("q", "初音ミク")
            .addQueryParam("targets", "title")
            .addQueryParam("fields", "contentId,title,viewCounter")
            .addQueryParam("filters[viewCounter][gte]", "10000")
            .addQueryParam("_sort", "-viewCounter")
            .addQueryParam("_offset", "0")
            .addQueryParam("_limit", "3")
            .addQueryParam("_context", "apiguide")
            .send();

    fut.onSuccess(
            responce -> {
              reply.put("status", responce.statusCode());
              reply.put("body", responce.bodyAsJsonObject());
              event.reply(reply);
            })
        .onFailure(
            th -> {
              LOGGER.error("web client erro ", th);
              reply.put("status", 500);
              reply.put("body", th.getMessage());
              event.reply(reply);
            });
  }
}
