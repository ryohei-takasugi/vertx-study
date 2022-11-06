package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.share.LogUtils;

public class HomeHandler implements Handler<RoutingContext> {

  /** Logger */
  private static final LogUtils LOGGER = new LogUtils(HomeHandler.class);

  /**
   * Create BodyHandler class method.
   *
   * @return BodyHandler instance.
   */
  public static Handler<RoutingContext> create() {
    return new HomeHandler();
  }

  /**
   * main method.
   *
   * @param event vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext event) {
    LOGGER.info(event.session(), "home");
    HttpServerResponse responce = event.response();
    responce.setStatusCode(200);
    responce.end("Hello Vert.x!!");
  }
}
