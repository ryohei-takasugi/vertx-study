package jp.sample.vertx1.ClientServices.Handlers;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NicoNicoHandler implements Handler<Message<JsonObject>> {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(NicoNicoHandler.class);

    /** vertx */
    private final Vertx vertx;

    /**
     * NicoNicoHandler Contractor
     *
     * @param vertx
     */
    public NicoNicoHandler(Vertx vertx) {
        this.vertx = vertx;
    }

    /**
     * Create NicoNicoHandler class method.
     *
     * @return NicoNicoHandler instance.
     */
    public static Handler<Message<JsonObject>> create(Vertx vertx) {
        return new NicoNicoHandler(vertx);
    }

    @Override
    public void handle(Message<JsonObject> event) {
        // TODO Auto-generated method stub
        JsonObject message = event.body();
        event.reply(message);
    }
}
