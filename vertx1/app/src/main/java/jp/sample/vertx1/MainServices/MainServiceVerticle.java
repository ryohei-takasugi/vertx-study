package jp.sample.vertx1.MainServices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.ResponseContentTypeHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.SessionStore;
import jp.sample.vertx1.MainServices.Handlers.CallHandler;
import jp.sample.vertx1.MainServices.Handlers.ConvertHandler;
import jp.sample.vertx1.MainServices.Handlers.EchoHandler;
import jp.sample.vertx1.MainServices.Handlers.FailerHandler;
import jp.sample.vertx1.MainServices.Handlers.HomeHandler;
import jp.sample.vertx1.MainServices.Handlers.LoggerHandler;
import jp.sample.vertx1.share.ContentType;
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
    JsonObject config = config();
    Router router = getRouter(config);

    final HttpServerOptions httpOptions = new HttpServerOptions(config.getJsonObject("http"));

    HttpServer server = vertx.createHttpServer(httpOptions);
    Future<HttpServer> listen = server.requestHandler(router).listen();
    listen
        .onSuccess(
            srv -> {
              LOGGER.info("listen start. port: " + httpOptions.getPort());
              startPromise.complete();
            })
        .onFailure(
            th -> {
              startPromise.fail(th);
            });
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

    /** Auto Content-TYpe */
    router.route().handler(ResponseContentTypeHandler.create());

    /** session */
    SessionStore store = SessionStore.create(vertx);
    store.createSession(10000);
    router.route().handler(SessionHandler.create(store));

    /** acsess logger */
    router.route().handler(LoggerHandler.create());

    /** default 404 error page */
    router.errorHandler(
        404,
        ctx -> {
          ctx.response().sendFile("error/404.html");
        });

    /** default 500 error page */
    router.errorHandler(
        500,
        ctx -> {
          ctx.response().sendFile("error/500.html");
        });

    /** root page */
    router
        .get("/")
        .produces(ContentType.HTML.getString())
        .handler(HomeHandler.create())
        .failureHandler(FailerHandler.create());

    /** config page */
    router
        .get("/echo")
        .produces(ContentType.JSON.getString())
        .handler(EchoHandler.create(config))
        .failureHandler(FailerHandler.create());

    /** other web api call page */
    router
        .get("/call")
        .produces(ContentType.HTML.getString())
        .handler(CallHandler.create(vertx, config.getJsonObject("app")))
        .failureHandler(FailerHandler.create());

    /** Create body required for put or post */
    router.route().handler(BodyHandler.create());

    /** Not created */
    router.put("/convert").produces(ContentType.JSON.getString()).handler(ConvertHandler.create());

    return router;
  }
}
