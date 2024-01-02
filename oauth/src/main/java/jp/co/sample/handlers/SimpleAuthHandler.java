package jp.co.sample.handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.SimpleAuthenticationHandler;
import jp.co.sample.functions.Authentication;

public class SimpleAuthHandler implements Handler<RoutingContext> {

  public static SimpleAuthHandler create() {
    return new SimpleAuthHandler();
  }

  @Override
  public void handle(RoutingContext ctx) {
    var auth = SimpleAuthenticationHandler.create().authenticate(Authentication.create());
    auth.handle(ctx);
    ctx.next();
  }
}
