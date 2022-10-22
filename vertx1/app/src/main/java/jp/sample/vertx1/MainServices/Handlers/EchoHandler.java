package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoHandler implements Handler<RoutingContext> {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(EchoHandler.class);

    /** config */
    private final JsonObject config;

    /**
     * EchoHandler Contractor
     *
     * @param config
     */
    public EchoHandler(JsonObject config) {
        this.config = config;
    }

    /**
     * Create EchoHandler class method.
     *
     * @return EchoHandler instance.
     */
    public static Handler<RoutingContext> create(JsonObject config) {
        return new EchoHandler(config);
    }

    /**
     * main.
     *
     * @param event vert.x RoutingContext data.
     * @return null.
     */
    @Override
    public void handle(RoutingContext event) {
        HttpServerResponse responce = event.response();
        responce.setStatusCode(200);
        responce.end(config.encode());
    }
}