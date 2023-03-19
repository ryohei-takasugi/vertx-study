package jp.sample.vertx1.ClientServices.Handlers.Factories;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import java.util.Map;
import jp.sample.vertx1.ClientServices.Handlers.NicoNicoHandler;

public class NicoNicoHandleFactory {

  /**
   * @param vertx Vert.x of ClientServiceVerticle
   * @return NicoNicoHandler instance.
   */
  public static Handler<Message<Map<String, Object>>> create(Vertx vertx) {
    return new NicoNicoHandler(vertx);
  }

  private NicoNicoHandleFactory() {}
}
