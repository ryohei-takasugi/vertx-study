package jp.sample.vertx1.ClientServices.Handlers;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class NicoNicoHandleFactory {

  /**
   * @param vertx Vert.x of ClientServiceVerticle
   * @return NicoNicoHandler instance.
   */
  public static Handler<Message<JsonObject>> create(Vertx vertx) {
    return new NicoNicoHandler(vertx);
  }

  private NicoNicoHandleFactory() {}
}
