package jp.sample.vertx1.MainServices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import jp.sample.vertx1.MainServices.Handlers.CallHandler;
import jp.sample.vertx1.MainServices.Handlers.EchoHandler;
import jp.sample.vertx1.MainServices.Handlers.HomeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainServiceVerticle extends AbstractVerticle {
    /** logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MainServiceVerticle.class);

    private static final String CONTENT_TYPE = "application/json";

    /**
     * vert.x start.
     *
     * @param startPromise vert.x start promise.
     * @return null.
     */
    @Override
    public void start(Promise<Void> startPromise) {
        JsonObject config = config();
        Router router = getRouter(config);

        final HttpServerOptions httpOptions = new HttpServerOptions(config.getJsonObject("http"));

        HttpServer server = vertx.createHttpServer(httpOptions);
        server.requestHandler(router).listen();

        LOGGER.info("listen start. port: " + httpOptions.getPort());
    }

    /**
     * Create Router
     *
     * @param config
     * @return router
     */
    private Router getRouter(JsonObject config) {
        Router router = Router.router(vertx);

        router.get("/").produces(CONTENT_TYPE).handler(HomeHandler.create());
        router.get("/echo").produces(CONTENT_TYPE).handler(EchoHandler.create(config));
        router.get("/call").produces(CONTENT_TYPE).handler(CallHandler.create(vertx, config));

        return router;
    }
}
