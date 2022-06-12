package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
  private final static Logger logger = LoggerFactory.getLogger(MainVerticle.class);
  private final static int port = 8080;
  @Override
  public void start() {
    vertx.createHttpServer()
        .requestHandler(req -> {
          logger.trace("");
          logger.debug("");
          logger.info("");
          logger.warn("");
          logger.error("");
          req.response().end("Hello Vert.x!" + port);
        })
        .listen(port);
  }

}
