package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.starter.controller.BodyHandler;
import io.vertx.starter.service.ConfigService;

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
    LOGGER.info(MainVerticle.class.getName() + " start");
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);
    ConfigService configService = new ConfigService(Vertx.currentContext().config());
    router.get("/").produces("application/json").handler(BodyHandler.create());
    server.requestHandler(router).listen(configService.getPort());
    LOGGER.info("listen start. port: " + configService.getPort());
  }
}
