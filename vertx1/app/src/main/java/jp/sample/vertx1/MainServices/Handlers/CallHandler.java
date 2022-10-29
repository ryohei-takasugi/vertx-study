package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;
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

  /** TemplateEngine */
  private final ThymeleafTemplateEngine engine;

  /** index.html file */
  private final static String INDEX = "templates/index.html";

  /** event bus address */
  private final static String GET_ADDRESS = "web-client:GET";

  /**
   * CallHandler Contractor
   *
   * @param config
   */
  public CallHandler(Vertx vertx, JsonObject config) {
    this.vertx = vertx;
    this.config = config;
    this.engine = ThymeleafTemplateEngine.create(vertx);
  }

  /**
   * main method.
   *
   * @param event vert.x RoutingContext data.
   * @return null.
   */
  @Override
  public void handle(RoutingContext event) {
    EventBus eb = vertx.eventBus();
    JsonObject request = new JsonObject();

    request.put("method", "GET");
    request.put("host", "api.search.nicovideo.jp");
    request.put("port", 443);
    request.put("ssl", true);
    request.put(
        "uri",
        "/api/v2/snapshot/video/contents/search?q=%E5%88%9D%E9%9F%B3%E3%83%9F%E3%82%AF&targets=title&fields=contentId,title,viewCounter&filters[viewCounter][gte]=10000&_sort=-viewCounter&_offset=0&_limit=3&_context=apiguide");
    LOGGER.debug(request.encodePrettily());
    LOGGER.debug(config.getString("sample"));

    HttpServerResponse responce = event.response();

    DeliveryOptions option = new DeliveryOptions().setSendTimeout(3000);
    Future<Message<Object>> fut = eb.request(GET_ADDRESS, request, option);
    fut.onSuccess(
            niconico -> {
              JsonObject resbody = (JsonObject) niconico.body();
              LOGGER.debug("resbody: {}", resbody.encodePrettily());
              NicoNicoModel model = new NicoNicoModel(resbody);
              if (model.status() == 200) {
                Future<Buffer> futEng = engine.render(model.entities(), INDEX);
                futEng
                    .onSuccess(
                        html -> {
                          responce.setStatusCode(200);
                          responce.end(html);
                        })
                    .onFailure(
                        th -> {
                          event.fail(th);
                        });
              } else {
                responce.setStatusCode(500);
                responce.setStatusMessage("Could not get information from Niconico Douga");
                event.failed();
              }
            })
        .onFailure(
            th -> {
              responce.setStatusCode(500);
              responce.setStatusMessage("Web client error");
              LOGGER.error("web client error", th);
              event.fail(th);
            });
  }
}
