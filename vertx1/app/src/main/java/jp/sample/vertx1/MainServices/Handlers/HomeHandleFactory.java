package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class HomeHandleFactory {

  /**
   * Create BodyHandler class method.
   *
   * @return BodyHandler instance.
   */
  public static Handler<RoutingContext> create() {
    return new HomeHandler();
  }

  private HomeHandleFactory() {}
}
