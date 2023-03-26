package jp.sample.vertx1.MainServices;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.ResponseContentTypeHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.SessionStore;
import jp.sample.vertx1.MainServices.Handlers.FailerHandleFactory;
import jp.sample.vertx1.MainServices.Handlers.LoggerHandleFactory;
import jp.sample.vertx1.MainServices.Handlers.service.CallHandleFactory;
import jp.sample.vertx1.MainServices.Handlers.service.ConvertHandleFactory;
import jp.sample.vertx1.MainServices.Handlers.service.EchoHandleFactory;
import jp.sample.vertx1.MainServices.Handlers.service.HomeHandleFactory;
import jp.sample.vertx1.MainServices.models.ContentType;

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
    this.router = Router.router(v);
    this.vertx = v;
    this.config = c;
  }

  private Router createRouter() {

    /** static page */
    router.route().handler(StaticHandler.create());

    /** Auto Content-TYpe */
    router.route().handler(ResponseContentTypeHandler.create());

    /** session */
    // final LocalSessionStore store = LocalSessionStore.create(vertx);
    // store.createSession(10000);
    router
        .route()
        .handler(
            SessionHandler.create(SessionStore.create(vertx))
                .setCookieHttpOnlyFlag(true)
                .setCookieMaxAge(60)
                .setSessionTimeout(60));

    /** access logger */
    router.route().handler(LoggerHandleFactory.create());

    /** default 404 error page */
    router.errorHandler(404, FailerHandleFactory.create());

    /** default 500 error page */
    router.errorHandler(500, FailerHandleFactory.create());

    /** root page */
    router
        .get("/")
        .produces(ContentType.HTML.getString())
        .handler(HomeHandleFactory.create())
        .failureHandler(FailerHandleFactory.create());

    /** config page */
    router
        .get("/echo")
        .produces(ContentType.JSON.getString())
        .handler(EchoHandleFactory.create(config))
        .failureHandler(FailerHandleFactory.create());

    /** other web api call page */
    router
        .get("/call")
        .produces(ContentType.HTML.getString())
        .handler(CallHandleFactory.create(vertx, config.getJsonObject("app")))
        .failureHandler(FailerHandleFactory.create());

    /** Create body required for put or post */
    router.route().handler(BodyHandler.create());

    /** Not created */
    router
        .put("/convert")
        .produces(ContentType.JSON.getString())
        .handler(ConvertHandleFactory.create());

    return router;
  }
}
