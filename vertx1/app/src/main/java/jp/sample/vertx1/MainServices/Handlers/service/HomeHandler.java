package jp.sample.vertx1.MainServices.Handlers.service;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.MainServices.Modules.IResponseRoutingContext;
import jp.sample.vertx1.share.MyLogger;

public class HomeHandler implements Handler<RoutingContext>, IResponseRoutingContext<String> {

  /** Logger */
  private static final MyLogger LOGGER = MyLogger.create(HomeHandler.class);

  protected HomeHandler() {}

  /**
   * main method.
   *
   * @param event vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext event) {
    success(event, "Hello Vert.x!!");
  }

  @Override
  public void success(RoutingContext event, String object) {
    LOGGER.info(event.session(), object);
    var response = event.response();
    response.setStatusCode(SUCCESS_STATUS_CODE);
    response.end(object);
  }

  @Override
  public void failed(RoutingContext event, int statusCode, String errorMessage, Throwable th) {
    // not used
  }
}
