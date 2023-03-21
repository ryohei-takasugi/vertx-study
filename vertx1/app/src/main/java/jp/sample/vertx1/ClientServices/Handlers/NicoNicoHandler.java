package jp.sample.vertx1.ClientServices.Handlers;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import java.util.Map;
import jp.sample.vertx1.share.MyLogger;
import jp.sample.vertx1.share.model.CallModel;

public class NicoNicoHandler implements Handler<Message<Map<String, Object>>> {

  /** Logger */
  private static final MyLogger LOGGER = MyLogger.create(NicoNicoHandler.class);

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
  public void handle(Message<Map<String, Object>> requestEvent) {

    var model = new CallModel(requestEvent.body());
    var method = model.method();
    var option = new RequestOptions(model.toJson());

    LOGGER.debug(model.sessionId(), model.toJson().encodePrettily());

    var client = WebClient.create(vertx);
    var request = client.request(method, option);

    var fut = request.send();
    var reply = new JsonObject();
    fut.onSuccess(
            response -> {
              LOGGER.info(model.sessionId(), Integer.toString(response.statusCode()));
              reply.put("status", response.statusCode());
              reply.put("body", response.bodyAsJsonObject());
              requestEvent.reply(reply);
            })
        .onFailure(
            th -> {
              LOGGER.error(model.sessionId(), "web client error ", th);
              reply.put("status", 500);
              reply.put("body", th.getMessage());
              requestEvent.reply(reply);
            });
  }
}
