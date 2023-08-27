package jp.sample.vertx1.handlers.main.api;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.models.config.Config;

public class EchoHandleFactory {

  /**
   * Create EchoHandler class method.
   *
   * @param config config of boot parameter
   * @return EchoHandler instance.
   */
  public static Handler<RoutingContext> create(Config config) {
    return new EchoHandler(config);
  }

  private EchoHandleFactory() {}
}
