package jp.sample.vertx1.ClientServices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import jp.sample.vertx1.ClientServices.Handlers.NicoNicoHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientServiceVerticle extends AbstractVerticle {

  /** logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceVerticle.class);

  /**
   * vert.x start.
   *
   * @param startPromise vert.x start promise.
   * @return null.
   */
  @Override
  public void start(Promise<Void> startPromise) {
    // WebClient client = WebClient.create(vertx);
    EventBus eb = vertx.eventBus();
    eb.consumer("web-client:GET", NicoNicoHandler.create(vertx));
  }
}
