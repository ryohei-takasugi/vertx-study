package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

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
