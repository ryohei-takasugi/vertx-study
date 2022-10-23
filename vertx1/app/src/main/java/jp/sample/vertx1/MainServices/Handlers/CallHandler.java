package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.ClientServices.Models.NicoNicoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallHandler implements Handler<RoutingContext> {

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(CallHandler.class);

  /**
   * Create CallHandler class method.
   *
   * @return CallHandler instance.
   */
  public static Handler<RoutingContext> create(Vertx vertx, JsonObject config) {
    return new CallHandler(vertx, config);
  }

  /** Vertx */
  private final Vertx vertx;

  /** config */
  private final JsonObject config;

  /**
   * CallHandler Contractor
   *
   * @param config
   */
  public CallHandler(Vertx vertx, JsonObject config) {
    this.vertx = vertx;
    this.config = config;
  }

  /**
   * main.
   *
   * @param event vert.x RoutingContext data.
   * @return null.
   */
  @Override
  public void handle(RoutingContext event) {
    EventBus eb = vertx.eventBus();
    JsonObject request = new JsonObject();
    JsonObject query = new JsonObject();

    request.put("host", "api.search.nicovideo.jp");
    request.put("port", 443);
    request.put("isSSL", true);
    request.put("url", "/api/v2/snapshot/video/contents/search");
    query.put("q", "初音ミク");
    query.put("targets", "title");
    query.put("fields", "contentId,title,viewCounter");
    query.put("filters[viewCounter][gte]", "10000");
    query.put("_sort", "-viewCounter");
    query.put("_offset", "0");
    query.put("_limit", "3");
    query.put("_context", "apiguide");
    request.put("queries", query);
    LOGGER.debug(request.encodePrettily());

    Future<Message<Object>> fut = eb.request("web-client:GET", request);

    HttpServerResponse responce = event.response();
    responce.setStatusCode(200);

    fut.onSuccess(
            niconico -> {
              JsonObject body = (JsonObject) niconico.body();
              NicoNicoModel model = new NicoNicoModel(body.getJsonObject("body"));
              LOGGER.debug(body.encodePrettily());
              if (body.getInteger("status") != 500) {
                responce.end(body.encodePrettily(), "SJIS");
              } else {
                responce.end("web client error");
              }
            })
        .onFailure(
            th -> {
              LOGGER.error("web client error", th);
              responce.end("web client error");
            });
  }
}
