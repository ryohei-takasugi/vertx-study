package jp.sample.vertx1.MainServices.Handlers.Factories;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.MainServices.Handlers.EchoHandler;

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
