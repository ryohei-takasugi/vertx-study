package jp.sample.vertx1.MainServices.Handlers.service;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.MainServices.Modules.IResponseRoutingContext;
import jp.sample.vertx1.share.MyLogger;

public class EchoHandler implements Handler<RoutingContext>, IResponseRoutingContext<String> {

  /** Logger */
  private static final MyLogger LOGGER = MyLogger.create(EchoHandler.class);

  /** config */
  private final JsonObject config;

  /**
   * EchoHandler Contractor
   *
   * @param config config of boot parameter
   */
  protected EchoHandler(JsonObject config) {
    this.config = config;
  }

  /**
   * main method.
   *
   * @param event vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext event) {
    success(event, config.encodePrettily());
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
