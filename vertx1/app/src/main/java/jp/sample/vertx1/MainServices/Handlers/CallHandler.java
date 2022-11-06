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
import java.util.Map;
import jp.sample.vertx1.ClientServices.ClientServiceVerticle;
import jp.sample.vertx1.ClientServices.Models.NicoNicoModel;
import jp.sample.vertx1.MainServices.models.CallModel;
import jp.sample.vertx1.share.LogUtils;

public class CallHandler implements Handler<RoutingContext> {

  /** Logger */
  private static final LogUtils LOGGER = new LogUtils(CallHandler.class);

  /**
   * Create CallHandler class method.
   *
   * @return CallHandler instance.
   */

  /**
   * Create CallHandler class method.
   *
   * @param vertx Vert.x of ClientServiceVerticle
   * @param config config of boot parameter
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
  private static final String INDEX = "templates/index.html";

  /**
   * CallHandler Contractor
   *
   * @param vertx Vert.x of ClientServiceVerticle
   * @param config config of boot parameter
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
   */
  @Override
  public void handle(RoutingContext event) {
    EventBus eb = vertx.eventBus();
    Map<String, Object> request = CallModel.createRequest(event.session());
    HttpServerResponse responce = event.response();

    LOGGER.debug(event.session(), request.toString());
    LOGGER.debug(event.session(), config.getString("sample"));

    DeliveryOptions option = new DeliveryOptions().setSendTimeout(3000);
    Future<Message<Object>> fut = eb.request(ClientServiceVerticle.GET_ADDRESS, request, option);
    fut.onSuccess(
            niconico -> {
              JsonObject resbody = (JsonObject) niconico.body();
              LOGGER.debug(event.session(), "resbody: " + resbody.encodePrettily());
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
              LOGGER.error(event.session(), "web client error", th);
              event.fail(th);
            });
  }
}
