package jp.sample.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import jp.sample.starter.models.config.ConfigModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
      ConfigModel configModel = new ConfigModel(vertx);
      // set Route
      RouteMap routeMap = new RouteMap(vertx);
      Router router = routeMap.getRouter();

      // listen start
      server.requestHandler(router).listen(configModel.getPort());
      LOGGER.info("listen start. port: " + configModel.getPort());

    } catch (Exception e) {
      LOGGER.error(MainVerticle.class.getName() + " start " + e);
      LOGGER.error(MainVerticle.class.getName() + " boot failure");
    }
  }
}
