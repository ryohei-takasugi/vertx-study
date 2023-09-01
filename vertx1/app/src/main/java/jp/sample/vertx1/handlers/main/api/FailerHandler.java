package jp.sample.vertx1.handlers.main.api;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.models.config.Config;
import jp.sample.vertx1.modules.HandlerLogger;

public class FailerHandler implements Handler<RoutingContext> {

  /** Logger */
  private static final HandlerLogger LOGGER = HandlerLogger.create(FailerHandler.class);

  protected FailerHandler(Config config) {}

  /**
   * main method.
   *
   * @param event vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext event) {
    int status = event.statusCode();
    switch (status) {
      case 404:
        event.response().end(new JsonObject().put("mesasge", "NOT FOUND").encode());
        break;
      case 500:
        event.response().end(new JsonObject().put("mesasge", "INTERNAL SERVER ERROR").encode());
        break;
      default:
        event.response().end(new JsonObject().put("mesasge", "INTERNAL SERVER ERROR").encode());
    }
  }
}
