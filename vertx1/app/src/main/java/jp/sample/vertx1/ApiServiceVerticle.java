package jp.sample.vertx1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import jp.sample.vertx1.handlers.api.NicoNicoHandleFactory;

public class ApiServiceVerticle extends AbstractVerticle {

  /** logger. */
  private static final Logger logger = LoggerFactory.getLogger(ApiServiceVerticle.class);

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
            logger.error("Failed api service verticle. ", ar.cause());
            startPromise.fail(ar.cause());
          }
          startPromise.complete();
        });
  }
}
