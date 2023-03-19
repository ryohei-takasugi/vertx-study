package jp.sample.vertx1.ClientServices.Handlers;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
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
  public NicoNicoHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  /**
   * @param vertx Vert.x of ClientServiceVerticle
   * @return NicoNicoHandler instance.
   */
  public static Handler<Message<Map<String, Object>>> create(Vertx vertx) {
    return new NicoNicoHandler(vertx);
  }

  @Override
  public void handle(Message<Map<String, Object>> requestEvent) {

    CallModel model = new CallModel(requestEvent.body());
    HttpMethod method = model.method();
    RequestOptions option = new RequestOptions(model.toJson());

    LOGGER.debug(model.sessionId(), model.toJson().encodePrettily());

    WebClient client = WebClient.create(vertx);
    HttpRequest<Buffer> request = client.request(method, option);

    Future<HttpResponse<Buffer>> fut = request.send();
    JsonObject reply = new JsonObject();
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
