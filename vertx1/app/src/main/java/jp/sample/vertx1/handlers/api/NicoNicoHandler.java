package jp.sample.vertx1.handlers.api;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import jp.sample.vertx1.models.eventbus.IEventBus;
import jp.sample.vertx1.models.eventbus.IResponseEventBus;
import jp.sample.vertx1.models.eventbus.NicoNicoRequest;
import jp.sample.vertx1.modules.HandlerLogger;

public class NicoNicoHandler implements Handler<Message<JsonObject>>, IResponseEventBus {

  /** Logger */
  private static final HandlerLogger logger = HandlerLogger.create(NicoNicoHandler.class);

  /** vertx */
  private final Vertx vertx;

  /**
   * NicoNicoHandler Contractor
   *
   * @param vertx Vert.x of ClientServiceVerticle
   */
  protected NicoNicoHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public void handle(Message<JsonObject> message) {

    var model = new NicoNicoRequest(message.body());
    var option = new RequestOptions(model.toJson());

    logger.debug(model.sessionId(), model.toJson().encodePrettily());

    var client = WebClient.create(vertx);
    var request = client.request(model.method(), option);
    var fut = request.send();
    fut.onSuccess(
            response -> {
              success(message, model, response);
            })
        .onFailure(
            th -> {
              failed(message, model, FAILED_STATUS_CODE, "web client error ", th);
            });
  }

  @Override
  public void failed(
      Message<JsonObject> message,
      IEventBus model,
      int statusCode,
      String errorMessage,
      Throwable th) {
    var reply = new JsonObject();
    logger.error(model.sessionId(), errorMessage, th);
    reply.put("status", statusCode);
    reply.put("body", th.getMessage());
    message.reply(reply);
  }

  @Override
  public void success(Message<JsonObject> message, IEventBus model, HttpResponse<Buffer> object) {
    var reply = new JsonObject();
    logger.info(model.sessionId(), object.statusMessage());
    reply.put("status", object.statusCode());
    reply.put("body", object.bodyAsJsonObject());
    message.reply(reply);
  }
}
