package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class EchoHandleFactory {

  /**
   * Create EchoHandler class method.
   *
   * @param config config of boot parameter
   * @return EchoHandler instance.
   */
  public static Handler<RoutingContext> create(JsonObject config) {
    return new EchoHandler(config);
  }

  private EchoHandleFactory() {}
}
