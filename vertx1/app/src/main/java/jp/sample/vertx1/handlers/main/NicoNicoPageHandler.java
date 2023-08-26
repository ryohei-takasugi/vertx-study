package jp.sample.vertx1.handlers.main;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;
import jp.sample.vertx1.handlers.api.NicoNicoHandleFactory;
import jp.sample.vertx1.models.IResponseRoutingContext;
import jp.sample.vertx1.models.api.NicoNicoModel;
import jp.sample.vertx1.models.eventbus.NicoNicoRequest;
import jp.sample.vertx1.modules.MyLogger;

public class NicoNicoPageHandler
    implements Handler<RoutingContext>, IResponseRoutingContext<Buffer> {

  /** Logger */
  private static final MyLogger LOGGER = MyLogger.create(NicoNicoPageHandler.class);

  /** config */
  protected final JsonObject config;

  /** TemplateEngine */
  private final ThymeleafTemplateEngine engine;

  /** index.html file */
  private static final String INDEX = "templates/index.html";

  /** Request Option to send to the event bus */
  private static final DeliveryOptions OPTIONS = new DeliveryOptions().setSendTimeout(3000);

  /**
   * NicoNicoPage Contractor
   *
   * @param vertx Vert.x of ClientServiceVerticle
   * @param config config of boot parameter
   */
  protected NicoNicoPageHandler(Vertx vertx, JsonObject config) {
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
    var request = NicoNicoRequest.createRequest(event.session());
    var eb = event.vertx().eventBus();
    var fut = eb.request(NicoNicoHandleFactory.GET_ADDRESS, request, OPTIONS);
    fut.onSuccess(
            clientResponse -> {
              if (clientResponse == null || !(clientResponse.body() instanceof JsonObject)) {
                var message = "Could not get information from Niconico Douga";
                failed(event, FAILED_STATUS_CODE, message);
                return;
              }

              var responseBody = (JsonObject) clientResponse.body();
              LOGGER.debug(event.session(), "responseBody: " + responseBody.encodePrettily());

              var model = new NicoNicoModel(responseBody);
              if (model.status() != 200) {
                var message = "Could not get information from Niconico Douga";
                failed(event, FAILED_STATUS_CODE, message);
                return;
              }
              var futEng = engine.render(model.entities(), INDEX);
              futEng
                  .onSuccess(
                      html -> {
                        success(event, html);
                        return;
                      })
                  .onFailure(
                      th -> {
                        var message = "Web client error";
                        failed(event, FAILED_STATUS_CODE, message, th);
                        return;
                      });
            })
        .onFailure(
            th -> {
              var message = "Web client error";
              failed(event, FAILED_STATUS_CODE, message, th);
              return;
            });
  }

  @Override
  public void success(RoutingContext event, Buffer html) {
    LOGGER.info(event.session(), "response HTML FILE");
    var response = event.response();
    response.setStatusCode(SUCCESS_STATUS_CODE);
    response.end(html);
  }

  @Override
  public void failed(RoutingContext event, int statusCode, String errorMessage, Throwable th) {
    LOGGER.error(event.session(), errorMessage, th);
    var response = event.response();
    response.setStatusCode(statusCode);
    response.setStatusMessage(errorMessage);
    response.end();
  }

  public void failed(RoutingContext event, int statusCode, String message) {
    failed(event, statusCode, message, null);
  }
}
