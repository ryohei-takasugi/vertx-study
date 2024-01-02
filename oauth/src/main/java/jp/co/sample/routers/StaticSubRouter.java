package jp.co.sample.routers;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class StaticSubRouter {

  public static Router create(Router router) {

    router.get().handler(StaticHandler.create());

    return router;
  }
}
