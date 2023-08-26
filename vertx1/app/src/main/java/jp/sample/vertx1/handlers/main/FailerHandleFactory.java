package jp.sample.vertx1.handlers.main;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

public class FailerHandleFactory {

  /**
   * Create FailerHandler class method.
   *
   * @return FailerHandler instance.
   */
  public static Handler<RoutingContext> create(Vertx v) {
    return new FailerHandler(v);
  }

  private FailerHandleFactory() {}
}
