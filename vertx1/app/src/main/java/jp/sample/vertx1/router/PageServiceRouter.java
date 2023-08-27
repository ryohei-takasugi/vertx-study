package jp.sample.vertx1.router;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import jp.sample.vertx1.handlers.main.page.FailerPageHandleFactory;
import jp.sample.vertx1.handlers.main.page.HomePageHandleFactory;
import jp.sample.vertx1.handlers.main.page.NicoNicoPageHandleFactory;
import jp.sample.vertx1.models.config.Config;
import jp.sample.vertx1.models.enumeration.ContentType;

public class PageServiceRouter {
  public static Router create(Vertx vertx, Router router, Config config) {

    /** root page */
    router
        .get("/")
        .produces(ContentType.HTML.getString())
        .handler(HomePageHandleFactory.create())
        .failureHandler(FailerPageHandleFactory.create(vertx));

    /** other web api call page */
    router
        .get("/call")
        .produces(ContentType.HTML.getString())
        .handler(NicoNicoPageHandleFactory.create(vertx, config.appOptions().toJson()))
        .failureHandler(FailerPageHandleFactory.create(vertx));

    return router;
  }
}
