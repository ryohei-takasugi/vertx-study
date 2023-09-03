package jp.sample.vertx1.handlers.main.page;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;
import jp.sample.vertx1.handlers.api.NicoNicoHandleFactory;
import jp.sample.vertx1.models.api.NicoNicoModel;
import jp.sample.vertx1.models.enumeration.HttpStatus;
import jp.sample.vertx1.models.enumeration.StringEncode;
import jp.sample.vertx1.models.eventbus.RequestModel;
import jp.sample.vertx1.models.handler.IMainHandlerResponse;
import jp.sample.vertx1.modules.HandlerLogger;

public class NicoNicoPageHandler implements Handler<RoutingContext>, IMainHandlerResponse {

  /** Logger */
  private static final HandlerLogger logger = HandlerLogger.create(NicoNicoPageHandler.class);

  /** config */
  protected final JsonObject config;

  /** TemplateEngine */
  private final ThymeleafTemplateEngine engine;

  /** index.html file */
  private static final String INDEX = "templates/index.html";

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
   * @param ctx vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext ctx) {
    var request = RequestModel.create().setSessionId(ctx.session()).setSearchWord("初音ミク");
    // var request = NicoNicoRequest.createRequest(ctx.session());
    var eb = ctx.vertx().eventBus();
    var eventBusName = NicoNicoHandleFactory.GET_ADDRESS;
    var option = NicoNicoHandleFactory.DELIVERY_OPTIONS;
    var fut = eb.request(eventBusName, request.toJsonObject(), option);
    fut.onSuccess(
            res -> {
              if (res == null || !(res.body() instanceof JsonObject)) {
                ctx.fail(new IllegalCallerException());
                return;
              }
              var body = (JsonObject) res.body();
              logger.debug(ctx.session(), "body: " + body.encodePrettily());

              var model = new NicoNicoModel(body);
              if (model.status() != HttpStatus.OK.code()) {
                ctx.fail(new IllegalCallerException(""));
                return;
              }

              var futEng = engine.render(model.entities(), INDEX);
              futEng
                  .onSuccess(
                      html -> {
                        try {
                          response(ctx, html.toString(StringEncode.UTF8.toString()));
                        } catch (Throwable th) {
                          ctx.fail(th);
                        }
                      })
                  .onFailure(
                      th -> {
                        ctx.fail(th);
                      });
            })
        .onFailure(
            th -> {
              ctx.fail(th);
            });
  }

  @Override
  public void response(RoutingContext ctx, String body) {
    logger.info(ctx.session(), "response HTML FILE");
    var response = ctx.response();
    response.setStatusCode(HttpStatus.OK.code());
    response.end(body);
  }
}
