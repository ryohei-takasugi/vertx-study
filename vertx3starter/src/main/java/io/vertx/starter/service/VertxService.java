package io.vertx.starter.service;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.util.HashMap;
import java.util.Map;

public class VertxService {

  /** ロガー */
  private static final Logger LOGGER = LoggerFactory.getLogger(VertxService.class);

  /** Vert.x Routing Context event data */
  private final RoutingContext event;

  /**
   * contractor
   *
   * @param event
   * @return null
   */
  public VertxService(RoutingContext event) {
    this.event = event;
  }

  /**
   * success responce.
   *
   * @param statusCode
   * @param dataLv2
   * @return null
   */
  public void sendSuccessResponce(int statusCode, Map<String, Object> dataLv2) {
    Map<String, Object> dataLv1 = new HashMap<>();
    dataLv1.put("status", statusCode);
    dataLv1.put("data", dataLv2);
    sendResponce(statusCode, dataLv1);
  }

  /**
   * error responce.
   *
   * @param statusCode
   * @param e
   * @return null
   */
  public void sendErrorResponse(int statusCode, Throwable e) {
    Map<String, Object> dataLv2 = new HashMap<>();
    dataLv2.put("message", "error message"); // TODO: switch error code & error message
    dataLv2.put("ditail", e.fillInStackTrace().getMessage());
    Map<String, Object> dataLv1 = new HashMap<>();
    dataLv1.put("status", statusCode);
    dataLv1.put("data", dataLv2);
    sendResponce(statusCode, dataLv1);
  }

  /**
   * responce.
   *
   * @param event
   * @param statusCode
   * @param data
   * @return null
   */
  private void sendResponce(int statusCode, Map<String, Object> data) {
    LOGGER.debug(VertxService.class.getName() + " " + new JsonObject(data).toString());
    event.response().setStatusCode(statusCode);
    event.response().putHeader("content-type", event.getAcceptableContentType());
    event.response().end(new JsonObject(data).toString());
  }
}
