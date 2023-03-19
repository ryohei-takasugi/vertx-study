package jp.sample.vertx1.MainServices;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.ResponseContentTypeHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.SessionStore;
import jp.sample.vertx1.MainServices.Handlers.CallHandler;
import jp.sample.vertx1.MainServices.Handlers.ConvertHandler;
import jp.sample.vertx1.MainServices.Handlers.EchoHandler;
import jp.sample.vertx1.MainServices.Handlers.FailerHandler;
import jp.sample.vertx1.MainServices.Handlers.HomeHandler;
import jp.sample.vertx1.share.ContentType;

/** Create Router */
public class MainRouter {

  private final Router router;

  private final Vertx vertx;

  private final JsonObject config;

  public static Router create(Vertx v, JsonObject c) {
    MainRouter mainRouter = new MainRouter(v, c);
    return mainRouter.createRouter();
  }

  public MainRouter(Vertx v, JsonObject c) {
    router = Router.router(v);
    vertx = v;
    config = c;
  }

  private Router createRouter() {

    /** static page */
    router.route().handler(StaticHandler.create());

    /** Auto Content-TYpe */
    router.route().handler(ResponseContentTypeHandler.create());

    /** session */
    final SessionStore store = SessionStore.create(vertx);
    store.createSession(10000);
    router.route().handler(SessionHandler.create(store));

    /** access logger */
    router.route().handler(LoggerHandler.create());

    /** default 404 error page */
    router.errorHandler(404, FailerHandler.create());

    /** default 500 error page */
    router.errorHandler(500, FailerHandler.create());

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
