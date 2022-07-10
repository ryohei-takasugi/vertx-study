package io.vertx.starter.controller;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.starter.service.VertxService;
import java.util.HashMap;
import java.util.Map;

public class BodyHandler implements Handler<RoutingContext> {

  /** ロガー */
  private static final Logger LOGGER = LoggerFactory.getLogger(BodyHandler.class);

  /**
   * Create BodyHandler class method.
   *
   * @return BodyHandler instance.
   */
  public static Handler<RoutingContext> create() {
    return new BodyHandler();
  }

  /**
   * main.
   *
   * @param event vert.x RoutingContext data.
   * @return null.
   */
  @Override
  public void handle(RoutingContext event) {
    LOGGER.info(BodyHandler.class.getName() + " start");

    // try {
    //   // お試し
    //   MySQLClient mySqlClient = new MySQLClient();
    //   mySqlClient.connection();
    // } catch (Exception e) {
    //   // LOGGER.error(null, e.getStackTrace(), e);
    // }
    VertxService vertxService = new VertxService(event);
    try {
      final Map<String, Object> PARAM = getRequestParams(event.request());
      LOGGER.info(BodyHandler.class.getName() + " PARAM: " + PARAM.toString());
      vertxService.sendSuccessResponce(200, PARAM);
    } catch (Throwable e) {
      vertxService.sendErrorResponse(500, e);
    }
    LOGGER.info(BodyHandler.class.getName() + " end BodyHandler.handle");
  }

  /**
   * RequestParameterをMap型で取得する
   *
   * @param event
   * @return requestParam
   */
  private Map<String, Object> getRequestParams(HttpServerRequest request) {
    Map<String, Object> requestParam = new HashMap<String, Object>();
    try {
      // requestParam.put("id", request.getParam("_id", "1"));
      // requestParam.put("name", request.getParam("_name", "sato"));
      requestParam.put("id", 1);
      requestParam.put("name", "sato");
      LOGGER.info(BodyHandler.class.getName() + " request map: " + requestParam.toString());
    } catch (Exception e) {
      LOGGER.info(BodyHandler.class.getName() + " request map: " + requestParam.toString());
    }
    return requestParam;
  }
}
