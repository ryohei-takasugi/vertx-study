package jp.vertx.starter.controller;

import io.vertx.core.Handler;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import jp.vertx.starter.model.HomeModel;
import jp.vertx.starter.view.ComonView;

public class HomeController implements Handler<RoutingContext> {

  /** ロガー */
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
      HomeModel homeModel = new HomeModel(event);
      ComonView homeView = new ComonView(event);
      homeView.sendSuccessResponce(ComonView.OK, homeModel.get());
    } catch (Throwable t) {
      LOGGER.error(HomeController.class.getName() + " handle " + t);
      event.fail(t);
    }

    LOGGER.info(HomeController.class.getName() + " end");
  }
}
