package jp.sample.vertx1.MainServices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.StaticHandler;
import jp.sample.vertx1.MainServices.Handlers.CallHandler;
import jp.sample.vertx1.MainServices.Handlers.EchoHandler;
import jp.sample.vertx1.MainServices.Handlers.FailerHandler;
import jp.sample.vertx1.MainServices.Handlers.HomeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainServiceVerticle extends AbstractVerticle {
  /** logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(MainServiceVerticle.class);

  private static final String CONTENT_TYPE = "application/json";

  /**
   * vert.x start.
   *
   * @param startPromise vert.x start promise.
   * @return null.
   */
  @Override
  public void start(Promise<Void> startPromise) {
    JsonObject config = config();
    Router router = getRouter(config);

    final HttpServerOptions httpOptions = new HttpServerOptions(config.getJsonObject("http"));

    HttpServer server = vertx.createHttpServer(httpOptions);
    server.requestHandler(router).listen();

    LOGGER.info("listen start. port: " + httpOptions.getPort());
  }

  /**
   * Create Router
   *
   * @param config
   * @return router
   */
  private Router getRouter(JsonObject config) {
    Router router = Router.router(vertx);

    /** static page */
    router.route().handler(StaticHandler.create());

    /** acsess logger */
    router.route().handler(LoggerHandler.create());

    /** default 404 error page */
    router.errorHandler(
        404,
        ctx -> {
          LOGGER.error("NOT FOUND");
          ctx.response().sendFile("error/404.html");
        });

    /** default 500 error page */
    router.errorHandler(
        500,
        ctx -> {
          LOGGER.error("Internal Server Error");
          ctx.response().sendFile("error/500.html");
        });

    /** root page */
    router
        .get("/")
        .produces(CONTENT_TYPE)
        .handler(HomeHandler.create())
        .failureHandler(FailerHandler.create());

    /** config page */
    router
        .get("/echo")
        .produces(CONTENT_TYPE)
        .handler(EchoHandler.create(config))
        .failureHandler(FailerHandler.create());

    /** other web api call page */
    router
        .get("/call")
        .produces(CONTENT_TYPE)
        .handler(CallHandler.create(vertx, config))
        .failureHandler(FailerHandler.create());

    return router;
  }
}
