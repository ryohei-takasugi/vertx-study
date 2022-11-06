package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class FailerHandler implements Handler<RoutingContext> {

  /** Logger */
  // private static final LogUtils LOGGER = new LogUtils(FailerHandler.class);

  /**
   * Create FailerHandler class method.
   *
   * @return FailerHandler instance.
   */
  public static Handler<RoutingContext> create() {
    return new FailerHandler();
  }

  /**
   * main method.
   *
   * @param event vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext event) {
    if (event.statusCode() == 404) {
      event.response().sendFile("error/404.html");
    } else {
      event.response().sendFile("error/500.html");
    }
  }
}
