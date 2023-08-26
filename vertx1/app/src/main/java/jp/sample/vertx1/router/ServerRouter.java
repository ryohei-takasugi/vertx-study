package jp.sample.vertx1.router;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import jp.sample.vertx1.handlers.main.ConvertHandleFactory;
import jp.sample.vertx1.handlers.main.EchoHandleFactory;
import jp.sample.vertx1.handlers.main.FailerHandleFactory;
import jp.sample.vertx1.handlers.main.HomePageHandleFactory;
import jp.sample.vertx1.handlers.main.NicoNicoPageHandleFactory;
import jp.sample.vertx1.models.config.Config;
import jp.sample.vertx1.models.enumeration.ContentType;

public class ServerRouter {
  public static Router create(Vertx vertx, Router router, Config config) {

    /** root page */
    router
        .get("/")
        .produces(ContentType.HTML.getString())
        .handler(HomePageHandleFactory.create())
        .failureHandler(FailerHandleFactory.create(vertx));

    /** config page */
    router
        .get("/echo")
        .produces(ContentType.JSON.getString())
        .handler(EchoHandleFactory.create(config))
        .failureHandler(FailerHandleFactory.create(vertx));

    /** other web api call page */
    router
        .get("/call")
        .produces(ContentType.HTML.getString())
        .handler(NicoNicoPageHandleFactory.create(vertx, config.appOptions().toJson()))
        .failureHandler(FailerHandleFactory.create(vertx));

    /** Create body required for put */
    router.put().handler(BodyHandler.create());

    /** Create body required for post */
    router.post().handler(BodyHandler.create());

    /** Not created */
    router
        .put("/convert")
        .produces(ContentType.JSON.getString())
        .handler(ConvertHandleFactory.create());

    return router;
  }
}
