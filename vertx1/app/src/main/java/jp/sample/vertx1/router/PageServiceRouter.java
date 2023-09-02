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

    /** common */
    router
        .route()
        .produces(ContentType.HTML.toString())
        .failureHandler(FailerPageHandleFactory.create(vertx));

    /** root page */
    router.get("/").handler(HomePageHandleFactory.create());

    /** other web api nico page */
    router
        .get("/nico")
        .handler(NicoNicoPageHandleFactory.create(vertx, config.appOptions().toJson()));

    return router;
  }
}
