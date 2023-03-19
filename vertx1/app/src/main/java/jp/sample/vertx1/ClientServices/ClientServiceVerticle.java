package jp.sample.vertx1.ClientServices;

import java.util.Map;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import jp.sample.vertx1.ClientServices.Handlers.NicoNicoHandler;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

public class ClientServiceVerticle extends AbstractVerticle {

  /** logger. */
  // private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceVerticle.class);

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
    MessageConsumer<Map<String, Object>> nico = eb.consumer(GET_ADDRESS, NicoNicoHandler.create(vertx));
    nico.completionHandler(ar -> {
      if (ar.failed()) {
        startPromise.fail(ar.cause());
      }
      startPromise.complete();
    });
  }
}
