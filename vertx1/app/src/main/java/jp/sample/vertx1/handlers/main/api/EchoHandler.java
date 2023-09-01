package jp.sample.vertx1.handlers.main.api;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.models.IResponseRoutingContext;
import jp.sample.vertx1.models.config.Config;
import jp.sample.vertx1.modules.HandlerLogger;

public class EchoHandler implements Handler<RoutingContext>, IResponseRoutingContext<String> {

  /** Logger */
  private static final HandlerLogger LOGGER = HandlerLogger.create(EchoHandler.class);

  /** config */
  private final Config config;

  /**
   * EchoHandler Contractor
   *
   * @param config config of boot parameter
   */
  protected EchoHandler(Config config) {
    this.config = config;
  }

  /**
   * main method.
   *
   * @param event vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext event) {
    success(event, config.toJson().encodePrettily());
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
