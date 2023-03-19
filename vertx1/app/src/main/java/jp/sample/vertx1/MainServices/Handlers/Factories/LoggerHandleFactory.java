package jp.sample.vertx1.MainServices.Handlers.Factories;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.MainServices.Handlers.LoggerHandler;

public class LoggerHandleFactory {
  /**
   * Create LoggerHandler class method.
   *
   * @return LoggerHandler instance.
   */
  public static Handler<RoutingContext> create() {
    return new LoggerHandler();
  }

  private LoggerHandleFactory() {}
}
