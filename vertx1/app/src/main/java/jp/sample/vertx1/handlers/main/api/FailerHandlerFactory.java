package jp.sample.vertx1.handlers.main.api;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.models.config.Config;

public class FailerHandlerFactory {
  /**
   * Create FailerHandler class method.
   *
   * @param config config of boot parameter
   * @return FailerHandler instance.
   */
  public static Handler<RoutingContext> create(Config config) {
    return new FailerHandler(config);
  }

  private FailerHandlerFactory() {}
}
