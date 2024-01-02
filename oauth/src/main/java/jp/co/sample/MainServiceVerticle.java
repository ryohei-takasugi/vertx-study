package jp.co.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import jp.co.sample.configurations.MainOptions;
import jp.co.sample.routers.MainRouter;

public class MainServiceVerticle extends AbstractVerticle {

  /** logger. */
  private static final Logger logger = LoggerFactory.getLogger(MainServiceVerticle.class);

  /**
   * vert.x start.
   *
   * @param startPromise vert.x start promise.
   */
  @Override
  public void start(Promise<Void> startPromise) {
    logger.debug("input config: {}", config());
    final var config = MainOptions.create(config());
    // logger.debug("load config: {}", config.toJsonObject().encodePrettily());

    final var router = MainRouter.create(vertx, config);
    final var httpOptions = config.httpServer();

    final var server = vertx.createHttpServer(httpOptions);
    final var fut = server.requestHandler(router).listen();
    fut.onSuccess(srv -> {
      logger.info("listen start. port: " + httpOptions.getPort());
      startPromise.complete();
    }).onFailure(th -> {
      logger.error("failed server ", th);
      startPromise.fail(th);
    });
  }
}
