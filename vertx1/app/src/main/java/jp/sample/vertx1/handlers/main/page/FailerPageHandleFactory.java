package jp.sample.vertx1.handlers.main.page;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

public class FailerPageHandleFactory {

  /**
   * Create FailerHandler class method.
   *
   * @return FailerHandler instance.
   */
  public static Handler<RoutingContext> create(Vertx v) {
    return new FailerPageHandler(v);
  }

  private FailerPageHandleFactory() {}
}
