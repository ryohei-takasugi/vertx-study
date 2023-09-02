package jp.sample.vertx1.handlers.main.api;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.models.config.Config;
import jp.sample.vertx1.models.enumeration.HttpStatus;
import jp.sample.vertx1.models.handler.IApiHandlerResponse;
import jp.sample.vertx1.modules.HandlerLogger;

public class EchoHandler implements Handler<RoutingContext>, IApiHandlerResponse {

  /** Logger */
  private static final HandlerLogger logger = HandlerLogger.create(EchoHandler.class);

  /** config */
  private final Config config;

  /**
   * EchoHandler Contractor
   *
   * @param config config of boot parameter
   */
  protected EchoHandler(Config config) {
    this.config = config;
  }

  /**
   * main method.
   *
   * @param ctx vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext ctx) {
    response(ctx, config.toJson());
  }

  @Override
  public void response(RoutingContext ctx, JsonObject body) {
    logger.info(ctx.session(), body.encodePrettily());

    var response = ctx.response();
    response.setStatusCode(HttpStatus.OK.code());
    response.setStatusMessage(HttpStatus.OK.message());
    response.end(body.encodePrettily());
  }
}
