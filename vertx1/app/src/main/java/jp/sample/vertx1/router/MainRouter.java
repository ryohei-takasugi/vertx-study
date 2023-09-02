package jp.sample.vertx1.router;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ResponseContentTypeHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.SessionStore;
import jp.sample.vertx1.handlers.main.LoggerHandleFactory;
import jp.sample.vertx1.handlers.main.page.FailerPageHandleFactory;
import jp.sample.vertx1.models.config.Config;
import jp.sample.vertx1.models.enumeration.StringEncode;

/** Create Router */
public class MainRouter {

  private final Router router;

  private final Vertx vertx;

  private final Config config;

  public static Router create(Vertx v, Config c) {
    MainRouter mainRouter = new MainRouter(v, c);
    return mainRouter.createRouter();
  }

  public MainRouter(Vertx v, Config c) {
    this.router = Router.router(v);
    this.vertx = v;
    this.config = c;
  }

  private Router createRouter() {

    /** static page (default DEFAULT_WEB_ROOT = "webroot") */
    router
        .route()
        .handler(StaticHandler.create().setDefaultContentEncoding(StringEncode.UTF8.toString()));

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
    router.errorHandler(404, FailerPageHandleFactory.create(vertx));

    /** default 500 error page */
    router.errorHandler(500, FailerPageHandleFactory.create(vertx));

    /** api Service Router */
    router.route().path("/prefix/api/*").subRouter(ApiServiceRouter.create(vertx, router, config));

    /** page Service Router */
    router.route().path("/prefix/*").subRouter(PageServiceRouter.create(vertx, router, config));

    return router;
  }
}
