package jp.sample.vertx1.handlers.main.page;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.models.IResponseRoutingContext;
import jp.sample.vertx1.modules.MyLogger;

public class HomePageHandler implements Handler<RoutingContext>, IResponseRoutingContext<String> {

  /** Logger */
  private static final MyLogger LOGGER = MyLogger.create(HomePageHandler.class);

  protected HomePageHandler() {}

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
