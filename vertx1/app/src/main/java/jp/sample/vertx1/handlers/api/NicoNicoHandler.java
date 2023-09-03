package jp.sample.vertx1.handlers.api;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import jp.sample.vertx1.models.api.NicoNicoRequest;
import jp.sample.vertx1.models.api.NicoNicoResponse;
import jp.sample.vertx1.models.enumeration.HttpStatus;
import jp.sample.vertx1.models.eventbus.IEventBusResponse;
import jp.sample.vertx1.modules.HandlerLogger;

public class NicoNicoHandler implements Handler<Message<JsonObject>>, IEventBusResponse {

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

    var model = NicoNicoRequest.create(message.body());
    var option = new RequestOptions(model.toJson());

    logger.debug(model.sessionId(), model.toJson().encodePrettily());

    var client = WebClient.create(vertx);
    var request = client.request(model.method(), option);
    var fut = request.send();
    fut.onSuccess(
            res -> {
              logger.info(model.sessionId(), res.bodyAsJsonObject().encodePrettily());
              var response =
                  NicoNicoResponse.create()
                      .setStasu(res.statusCode())
                      .setBody(res.bodyAsJsonObject());
              response(message, response);
            })
        .onFailure(
            th -> {
              logger.error(model.sessionId(), HttpStatus.INTERNAL_SERVER_ERROR.message(), th);
              var response =
                  NicoNicoResponse.create()
                      .setStasu(HttpStatus.INTERNAL_SERVER_ERROR.code())
                      .setBody(th);
              response(message, response);
            });
  }

  @Override
  public void response(Message<JsonObject> message, NicoNicoResponse res) {
    var options = NicoNicoHandleFactory.DELIVERY_OPTIONS;
    message.reply(res.toJsonObject(), options);
  }
}
