package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.*;
import io.vertx.ext.web.*;
import io.vertx.starter.handle.BodyHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

  /**
   * logger
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

  /**
   * http port number
   */
  private final static int PORT = 8080;

  /**
   * メイン処理
   * @param startPromise
   * @return null
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
