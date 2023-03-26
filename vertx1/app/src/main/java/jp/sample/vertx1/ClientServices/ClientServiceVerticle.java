package jp.sample.vertx1.ClientServices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import jp.sample.vertx1.ClientServices.Handlers.NicoNicoHandleFactory;
import jp.sample.vertx1.share.MyLogger;

public class ClientServiceVerticle extends AbstractVerticle {

  /** logger. */
  private static final MyLogger LOGGER = MyLogger.create(ClientServiceVerticle.class);

  /** event bus address */
  public static final String GET_ADDRESS = "web-client:GET";

  /**
   * vert.x start.
   *
   * @param startPromise vert.x start promise.
   */
  @Override
  public void start(Promise<Void> startPromise) {
    EventBus eb = vertx.eventBus();
    MessageConsumer<JsonObject> nico =
        eb.consumer(GET_ADDRESS, NicoNicoHandleFactory.create(vertx));
    nico.completionHandler(
        ar -> {
          if (ar.failed()) {
            LOGGER.info("common", "success consumer");
            startPromise.fail(ar.cause());
          }
          startPromise.complete();
        });
  }
}
