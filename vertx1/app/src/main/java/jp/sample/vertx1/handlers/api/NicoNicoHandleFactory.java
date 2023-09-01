package jp.sample.vertx1.handlers.api;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class NicoNicoHandleFactory {
  /** event bus address */
  public static final String GET_ADDRESS = "/my-service/nico-nico/get";

  /**
   * @param vertx Vert.x of ClientServiceVerticle
   * @return NicoNicoHandler instance.
   */
  public static Handler<Message<JsonObject>> create(Vertx vertx) {
    return new NicoNicoHandler(vertx);
  }

  private NicoNicoHandleFactory() {}
}
