package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.share.MyLogger;

public class HomeHandler implements Handler<RoutingContext> {

  /** Logger */
  private static final MyLogger LOGGER = MyLogger.create(HomeHandler.class);

  /**
   * main method.
   *
   * @param event vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext event) {
    LOGGER.info(event.session(), "home");
    HttpServerResponse response = event.response();
    response.setStatusCode(200);
    response.end("Hello Vert.x!!");
  }
}
