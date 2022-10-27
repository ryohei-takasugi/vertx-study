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
    WebClient client = WebClient.create(vertx);
    JsonObject clientCofig = event.body();
    RequestOptions option = new RequestOptions(clientCofig);
    HttpMethod method = HttpMethod.valueOf(clientCofig.getString("method"));
    HttpRequest<Buffer> request = client.request(method, option);
    JsonObject reply = new JsonObject();
    Future<HttpResponse<Buffer>> fut = request.send();
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
