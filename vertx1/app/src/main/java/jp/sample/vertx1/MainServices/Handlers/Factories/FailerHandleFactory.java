package jp.sample.vertx1.MainServices.Handlers.Factories;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.MainServices.Handlers.FailerHandler;

public class FailerHandleFactory {

  /**
   * Create FailerHandler class method.
   *
   * @return FailerHandler instance.
   */
  public static Handler<RoutingContext> create() {
    return new FailerHandler();
  }

  private FailerHandleFactory() {}
}
