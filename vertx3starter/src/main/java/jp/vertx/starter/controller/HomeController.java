package jp.vertx.starter.controller;

import io.vertx.core.Handler;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import jp.vertx.starter.model.api.HomeResponceModel;
import jp.vertx.starter.model.api.RequestModel;
import jp.vertx.starter.view.ComonView;
import jp.vertx.starter.view.HomeView;

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
      RequestModel requestModel = new RequestModel(event);
      if (requestModel.getException()) {
        throw new IllegalAccessError();
      }
      HomeResponceModel homeResponveModel = new HomeResponceModel(requestModel);
      HomeView homeView = new HomeView(event);
      homeView.sendSuccessResponce(ComonView.OK, homeResponveModel.get());
    } catch (Throwable t) {
      LOGGER.error(HomeController.class.getName() + " handle " + t);
      event.fail(500, t);
    }

    LOGGER.info(HomeController.class.getName() + " end");
  }
}
