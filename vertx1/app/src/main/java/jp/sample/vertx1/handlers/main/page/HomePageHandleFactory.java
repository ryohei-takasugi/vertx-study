package jp.sample.vertx1.handlers.main.page;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class HomePageHandleFactory {

  /**
   * Create BodyHandler class method.
   *
   * @return BodyHandler instance.
   */
  public static Handler<RoutingContext> create() {
    return new HomePageHandler();
  }

  private HomePageHandleFactory() {}
}
