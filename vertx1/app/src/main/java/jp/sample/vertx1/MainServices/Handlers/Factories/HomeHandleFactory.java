package jp.sample.vertx1.MainServices.Handlers.Factories;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.MainServices.Handlers.HomeHandler;

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
