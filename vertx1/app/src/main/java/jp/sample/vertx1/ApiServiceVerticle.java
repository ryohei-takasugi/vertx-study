package jp.sample.vertx1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import jp.sample.vertx1.handlers.api.NicoNicoHandleFactory;
import jp.sample.vertx1.modules.MyLogger;

public class ApiServiceVerticle extends AbstractVerticle {

  /** logger. */
  private static final MyLogger LOGGER = MyLogger.create(ApiServiceVerticle.class);

  /**
   * vert.x start.
   *
   * @param startPromise vert.x start promise.
   */
  @Override
  public void start(Promise<Void> startPromise) {
    EventBus eb = vertx.eventBus();
    var nico = eb.consumer(NicoNicoHandleFactory.GET_ADDRESS, NicoNicoHandleFactory.create(vertx));
    // CompositeFuture.all()
    nico.completionHandler(
        ar -> {
          if (ar.failed()) {
            LOGGER.error("common", "consumer", ar.cause());
            startPromise.fail(ar.cause());
          }
          startPromise.complete();
        });
  }
}
