package jp.sample.vertx1.router;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import jp.sample.vertx1.handlers.main.api.ConvertHandleFactory;
import jp.sample.vertx1.handlers.main.api.EchoHandleFactory;
import jp.sample.vertx1.handlers.main.api.FailerHandlerFactory;
import jp.sample.vertx1.models.config.Config;
import jp.sample.vertx1.models.enumeration.ContentType;

public class ApiServiceRouter {
  public static Router create(Vertx vertx, Router router, Config config) {

    /** config page */
    router
        .route()
        .produces(ContentType.JSON.getString())
        .failureHandler(FailerHandlerFactory.create(config));

    /** config page */
    router.get("/echo").handler(EchoHandleFactory.create(config));

    /** Create body required for put */
    router.put().handler(BodyHandler.create());

    /** Create body required for post */
    router.post().handler(BodyHandler.create());

    /** Not created */
    router.post("/convert").handler(ConvertHandleFactory.create());

    return router;
  }
}
