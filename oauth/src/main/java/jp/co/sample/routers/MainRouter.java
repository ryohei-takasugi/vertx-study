package jp.co.sample.routers;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import jp.co.sample.configurations.MainOptions;
import jp.co.sample.handlers.AuthSessionHandler;
import jp.co.sample.handlers.LoggerHandler;

public class MainRouter {

  public static Router create(Vertx vertx, MainOptions options) {

    Router router = Router.router(vertx);

    /** session */
    router.route().handler(AuthSessionHandler.create(vertx, options));

    /** request logger */
    router.route().handler(LoggerHandler.create());

    /** 静的コンテンツ */
    router.route("/static/*").subRouter(StaticSubRouter.create(router));

    /** 認証・認可 */
    router.route("/auth/*").subRouter(OAuthSubRouter.create(vertx, router, options));

    /** error handler */
    router.route().failureHandler(ErrorHandler.create(vertx, "webroot/html/error.html"));

    SockJSBridgeOptions sjsb = new SockJSBridgeOptions();

    return router;
  }

}
