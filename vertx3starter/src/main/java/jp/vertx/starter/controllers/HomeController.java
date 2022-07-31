package jp.vertx.starter.controllers;

import io.vertx.core.Handler;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import jp.vertx.starter.models.api.HomeResponceModel;
import jp.vertx.starter.models.api.RequestEntity;
import jp.vertx.starter.models.api.RequestModel;
import jp.vertx.starter.views.ComonView;
import jp.vertx.starter.views.HomeView;

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
      HomeResponceModel homeResponveModel = new HomeResponceModel(event);
      HomeView homeView = new HomeView(event);
      JsonObject responce = homeResponveModel.get();
      if (responce.getInteger("status").equals(200)) {
        homeView.sendSuccessResponce(ComonView.OK, responce);
      } else {
        event.fail(404);
      }
    } catch (Throwable t) {
      LOGGER.error(HomeController.class.getName() + " handle " + t);
      event.fail(500, t);
    }

    LOGGER.info(HomeController.class.getName() + " end");
  }
}
