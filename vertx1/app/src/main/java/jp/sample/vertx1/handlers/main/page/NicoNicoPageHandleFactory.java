package jp.sample.vertx1.handlers.main.page;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class NicoNicoPageHandleFactory {

  /**
   * Create NicoNicoPage class method.
   *
   * @param vertx Vert.x of MainServiceVerticle
   * @param config config of boot parameter
   * @return NicoNicoPage instance.
   */
  public static Handler<RoutingContext> create(Vertx vertx, JsonObject config) {
    return new NicoNicoPageHandler(vertx, config);
  }

  private NicoNicoPageHandleFactory() {}
}
