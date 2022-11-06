package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class EchoHandler implements Handler<RoutingContext> {

  /** Logger */
  // private static final LogUtils LOGGER = new LogUtils(EchoHandler.class);

  /** config */
  private final JsonObject config;

  /**
   * EchoHandler Contractor
   *
   * @param config config of boot parameter
   */
  public EchoHandler(JsonObject config) {
    this.config = config;
  }

  /**
   * Create EchoHandler class method.
   *
   * @param config config of boot parameter
   * @return EchoHandler instance.
   */
  public static Handler<RoutingContext> create(JsonObject config) {
    return new EchoHandler(config);
  }

  /**
   * main method.
   *
   * @param event vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext event) {
    HttpServerResponse responce = event.response();
    responce.setStatusCode(200);
    responce.end(config.encodePrettily());
  }
}
