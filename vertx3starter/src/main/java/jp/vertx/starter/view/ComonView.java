package jp.vertx.starter.view;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ComonView {

  /** ロガー */
  private static final Logger LOGGER = LoggerFactory.getLogger(ComonView.class);

  /** Vert.x Routing Context event data */
  private final RoutingContext event;

  public static final int CONTINUE = 100;
  public static final int Switching_Protocols = 101;
  public static final int Early_Hints = 103;
  public static final int OK = 200;
  public static final int Created = 201;
  public static final int Accepted = 202;
  public static final int Non_Authoritative_Information = 203;
  public static final int No_Content = 204;

  /**
   * contractor
   *
   * @param event
   * @return null
   */
  public ComonView(RoutingContext event) {
    this.event = event;
  }

  /**
   * success responce.
   *
   * @param statusCode
   * @param dataLv2
   * @return null
   */
  public void sendSuccessResponce(int statusCode, JsonObject responce) {
    try {
      responce.put("status", statusCode);
      sendResponce(statusCode, responce);
    } catch (Throwable t) {
      LOGGER.error(
          ComonView.class.getName() + " sendSuccessResponce " + t.fillInStackTrace().getMessage());
      event.fail(t);
    }
  }

  /**
   * error responce.
   *
   * @param statusCode
   * @param e
   * @return null
   */
  public void sendErrorResponse(int statusCode, Throwable e) {
    try {
      JsonObject dataLv2 = new JsonObject();
      dataLv2.put("message", "error message"); // TODO: switch error code & error message
      dataLv2.put("ditail", e.fillInStackTrace().getMessage());
      JsonObject dataLv1 = new JsonObject();
      dataLv1.put("status", statusCode);
      dataLv1.put("data", dataLv2);
      sendResponce(statusCode, dataLv1);
    } catch (Throwable t) {
      LOGGER.error(
          ComonView.class.getName() + " sendErrorResponse " + t.fillInStackTrace().getMessage());
      event.fail(t);
    }
  }

  /**
   * responce.
   *
   * @param event
   * @param statusCode
   * @param data
   * @return null
   */
  private void sendResponce(int statusCode, JsonObject responce) {
    try {
      LOGGER.debug(ComonView.class.getName() + " " + responce.toString());
      event.response().setStatusCode(statusCode);
      event.response().putHeader("content-type", event.getAcceptableContentType());
      event.response().end(responce.toString());
    } catch (Throwable t) {
      LOGGER.error(
          ComonView.class.getName() + " sendResponce " + t.fillInStackTrace().getMessage());
      event.fail(t);
    }
  }
}
