package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallHandler implements Handler<RoutingContext> {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(CallHandler.class);

    /**
     * Create CallHandler class method.
     *
     * @return CallHandler instance.
     */
    public static Handler<RoutingContext> create(Vertx vertx, JsonObject config) {
        return new CallHandler(vertx, config);
    }

    /** Vertx */
    private final Vertx vertx;

    /** config */
    private final JsonObject config;

    /**
     * CallHandler Contractor
     *
     * @param config
     */
    public CallHandler(Vertx vertx, JsonObject config) {
        this.vertx = vertx;
        this.config = config;
    }

    /**
     * main.
     *
     * @param event vert.x RoutingContext data.
     * @return null.
     */
    @Override
    public void handle(RoutingContext event) {
        EventBus eb = vertx.eventBus();
        JsonObject request = new JsonObject();
        JsonObject reply = new JsonObject();

        request.put(
                "uri",
                "https://api.search.nicovideo.jp/api/v2/snapshot/video/contents/search?q=%E5%88%9D%E9%9F%B3%E3%83%9F%E3%82%AF&targets=title&fields=contentId,title,viewCounter&filters[viewCounter][gte]=10000&_sort=-viewCounter&_offset=0&_limit=3&_context=apiguide");
        Future<Message<Object>> fut = eb.request("web-client:GET", request);

        HttpServerResponse responce = event.response();
        responce.setStatusCode(200);

        fut.onSuccess(
                        niconico -> {
                            responce.end(niconico.body().toString());
                        })
                .onFailure(
                        th -> {
                            LOGGER.error("web client error", th);
                        });
    }
}
