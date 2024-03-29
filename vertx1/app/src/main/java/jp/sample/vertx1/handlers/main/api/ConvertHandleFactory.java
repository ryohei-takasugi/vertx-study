package jp.sample.vertx1.handlers.main.api;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class ConvertHandleFactory {

  /**
   * Create ConvertHandler class method.
   *
   * @return ConvertHandler instance.
   */
  public static Handler<RoutingContext> create() {
    return new ConvertHandler();
  }

  private ConvertHandleFactory() {}
}
