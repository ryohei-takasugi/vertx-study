package jp.sample.vertx1.handlers.main.api;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.models.config.Config;
import jp.sample.vertx1.models.enumeration.HttpStatus;
import jp.sample.vertx1.modules.HandlerLogger;

public class FailerHandler implements Handler<RoutingContext> {

  /** Logger */
  private static final HandlerLogger logger = HandlerLogger.create(FailerHandler.class);

  protected FailerHandler(Config config) {}

  /**
   * main method.
   *
   * @param ctx vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext ctx) {
    var status = ctx.statusCode();
    var response = ctx.response();
    switch (status) {
      case 404:
        response
            .setStatusCode(HttpStatus.NOT_FOUND.code())
            .setStatusMessage(HttpStatus.NOT_FOUND.message());
        break;
      case 500:
        response
            .setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.code())
            .setStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.message());
        break;
      default:
        response
            .setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.code())
            .setStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.message());
    }
    response.end();
  }
}
