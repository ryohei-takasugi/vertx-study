package jp.sample.starter.controllers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import jp.sample.starter.models.request.RequestModel;
import jp.sample.starter.models.responce.HomeResponceModel;
import jp.sample.starter.views.ComonView;
import jp.sample.starter.views.HomeView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeController implements Handler<RoutingContext> {

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

  /**
   * Create BodyHandler class method.
   *
   * @return BodyHandler instance.
   */
  public static Handler<RoutingContext> create() {
    return new HomeController();
  }

  /**
   * main.
   *
   * @param event vert.x RoutingContext data.
   * @return null.
   */
  @Override
  public void handle(RoutingContext event) {
    LOGGER.info(HomeController.class.getName() + " begin");

    try {
      RequestModel request = new RequestModel(event);
      if (!("success".equals(request.getModelStatus()))) {
        event.fail(500);
      }
      HomeResponceModel responce = new HomeResponceModel(request);
      if (!("success".equals(responce.getModelStatus()))) {
        event.fail(500);
      }
      HomeView homeView = new HomeView(event);
      homeView.sendSuccessResponce(ComonView.OK, responce.toJsonObject());
    } catch (Throwable t) {
      LOGGER.error(HomeController.class.getName() + " handle " + t);
      event.fail(500, t);
    }

    LOGGER.info(HomeController.class.getName() + " end");
  }
}
