package jp.sample.vertx1.MainServices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainServiceVerticle extends AbstractVerticle {

  /** logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(MainServiceVerticle.class);

  /**
   * vert.x start.
   *
   * @param startPromise vert.x start promise.
   */
  @Override
  public void start(Promise<Void> startPromise) {
    final JsonObject config = config();
    final Router router = MainRouter.create(vertx, config);
    final HttpServerOptions httpOptions = new HttpServerOptions(config.getJsonObject("http"));

    final HttpServer server = vertx.createHttpServer(httpOptions);
    final Future<HttpServer> listen = server.requestHandler(router).listen();
    listen
        .onSuccess(
            srv -> {
              LOGGER.info("listen start. port: " + httpOptions.getPort());
              startPromise.complete();
            })
        .onFailure(
            th -> {
              LOGGER.error("failed server ", th);
              startPromise.fail(th);
            });
  }
}
