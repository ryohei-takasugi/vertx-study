package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.starter.handle.BodyHandler;

/** main. */
public class MainVerticle extends AbstractVerticle {

  /** logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

  /** http port number. */
  private static final int PORT = 8080;

  /**
   * vert.x start.
   *
   * @param startPromise vert.x start promise.
   * @throws Exception when this exceptional condition happens.
   * @return null.
   */
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOGGER.info("app start");
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);
    router.get("/").produces("application/json").handler(BodyHandler.create());
    server.requestHandler(router).listen(PORT);
    LOGGER.info("listen start. port: " + PORT);
  }
}
