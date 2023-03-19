package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class CallHandleFactory {

  /**
   * Create CallHandler class method.
   *
   * @param vertx Vert.x of MainServiceVerticle
   * @param config config of boot parameter
   * @return CallHandler instance.
   */
  public static Handler<RoutingContext> create(Vertx vertx, JsonObject config) {
    return new CallHandler(vertx, config);
  }

  private CallHandleFactory() {}
}
