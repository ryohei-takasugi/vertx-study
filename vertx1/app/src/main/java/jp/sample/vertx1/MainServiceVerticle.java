package jp.sample.vertx1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import jp.sample.vertx1.models.config.Config;
import jp.sample.vertx1.router.MainRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    final Config config = Config.create(config());
    final Router router = MainRouter.create(vertx, config);
    final HttpServerOptions httpOptions = config.mainOptions().httpServerOptions();

    final HttpServer server = vertx.createHttpServer(httpOptions);
    final Future<HttpServer> listen = server.requestHandler(router).listen();
    listen
        .onSuccess(
            srv -> {
              logger.info("listen start. port: " + httpOptions.getPort());
              startPromise.complete();
            })
        .onFailure(
            th -> {
              logger.error("failed server ", th);
              startPromise.fail(th);
            });
  }
}
