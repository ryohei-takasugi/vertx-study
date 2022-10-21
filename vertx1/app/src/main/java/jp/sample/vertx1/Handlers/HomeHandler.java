package jp.sample.vertx1.Handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeHandler implements Handler<RoutingContext> {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeHandler.class);

    /**
     * Create BodyHandler class method.
     *
     * @return BodyHandler instance.
     */
    public static Handler<RoutingContext> create() {
        return new HomeHandler();
    }

    /**
     * main.
     *
     * @param event vert.x RoutingContext data.
     * @return null.
     */
    @Override
    public void handle(RoutingContext event) {
        // TODO Auto-generated method stub

    }
}
