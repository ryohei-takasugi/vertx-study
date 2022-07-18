package jp.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import jp.vertx.starter.controller.HomeController;
import jp.vertx.starter.model.module.ConfigService;

/** main. */
public class MainVerticle extends AbstractVerticle {

  /** logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

  /**
   * vert.x start.
   *
   * @param startPromise vert.x start promise.
   * @throws Exception when this exceptional condition happens.
   * @return null.
   */
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    // start WebApi
    LOGGER.info(MainVerticle.class.getName() + " start");
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);
    ConfigService configService = new ConfigService(Vertx.currentContext().config());

    try {
      // set Route
      normalRoute(router);
      errorRoute(router);

      // listen start
      server.requestHandler(router).listen(configService.getPort());
      LOGGER.info("listen start. port: " + configService.getPort());

    } catch (Exception e) {
      LOGGER.error(MainVerticle.class.getName() + " start " + e);
    }
  }

  private void normalRoute(Router router) {
    // router
    router.get("/").produces("application/json").handler(HomeController.create());
  }

  private void errorRoute(Router router) {
    //   ErrorView eview = new ErrorView();
    //   router.errorHandler(400, eview);
    //   router.errorHandler(401, eview);
    //   router.errorHandler(402, eview);
    //   router.errorHandler(403, eview);
    //   router.errorHandler(500, eview);
    //   router.errorHandler(500, eview);
  }
}
