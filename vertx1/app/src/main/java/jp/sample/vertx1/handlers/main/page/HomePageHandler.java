package jp.sample.vertx1.handlers.main.page;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.models.enumeration.HttpStatus;
import jp.sample.vertx1.models.handler.IMainHandlerResponse;
import jp.sample.vertx1.modules.HandlerLogger;

public class HomePageHandler implements Handler<RoutingContext>, IMainHandlerResponse {

  /** Logger */
  private static final HandlerLogger logger = HandlerLogger.create(HomePageHandler.class);

  protected HomePageHandler() {}

  /**
   * main method.
   *
   * @param ctx vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext ctx) {
    response(ctx, "Hello Vert.x!!");
  }

  @Override
  public void response(RoutingContext ctx, String body) {
    logger.info(ctx.session(), body);
    var response = ctx.response();
    response.setStatusCode(HttpStatus.OK.code());
    response.setStatusMessage(HttpStatus.OK.message());
    response.end(body);
  }
}
