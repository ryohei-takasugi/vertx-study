package jp.co.sample.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class SimpleAuthResponceHandler implements Handler<RoutingContext> {

  public static SimpleAuthResponceHandler create() {
    return new SimpleAuthResponceHandler();
  }

  @Override
  public void handle(RoutingContext ctx) {
    if (ctx.user() != null) {
      ctx.response().end(new JsonObject().put("status", "OK").encode());
    } else {
      ctx.fail(401, new IllegalAccessError("Not Auth"));
    }
  }

}
