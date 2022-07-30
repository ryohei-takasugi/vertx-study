package jp.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import jp.vertx.starter.models.config.ConfigModel;

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
    try {
      // start WebApi
      LOGGER.info(MainVerticle.class.getName() + " start");
      HttpServer server = vertx.createHttpServer();
      Router defaultRouter = Router.router(vertx);
      ConfigModel configService = new ConfigModel(Vertx.currentContext().config());

      // set Route
      RouteMap routeMap = new RouteMap(defaultRouter, vertx);
      Router router = routeMap.getRouter();

      // listen start
      server.requestHandler(router).listen(configService.getPort());
      LOGGER.info("listen start. port: " + configService.getPort());

    } catch (Exception e) {
      LOGGER.error(MainVerticle.class.getName() + " start " + e);
      LOGGER.error(MainVerticle.class.getName() + " boot failure");
    }
  }
}
