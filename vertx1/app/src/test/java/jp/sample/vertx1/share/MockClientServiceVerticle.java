package jp.sample.vertx1.share;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import jp.sample.vertx1.handlers.api.NicoNicoHandleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockClientServiceVerticle extends AbstractVerticle {

  /** logger. */
  private static final Logger logger = LoggerFactory.getLogger(MockClientServiceVerticle.class);

  /**
   * vert.x start.
   *
   * @param startPromise vert.x start promise.
   */
  @Override
  public void start(Promise<Void> startPromise) {
    EventBus eb = vertx.eventBus();
    eb.consumer(
            NicoNicoHandleFactory.GET_ADDRESS,
            m -> {
              JsonObject reply = new JsonObject();
              JsonObject body = config().getJsonObject("returnJsonData");
              reply.put("status", 200);
              reply.put("body", body);
              logger.info(reply.encodePrettily());
              m.reply(reply);
            })
        .completionHandler(startPromise);
  }
}
