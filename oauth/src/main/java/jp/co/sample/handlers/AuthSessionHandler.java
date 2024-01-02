package jp.co.sample.handlers;

import io.vertx.core.Vertx;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.SessionStore;
import jp.co.sample.configurations.MainOptions;

public class AuthSessionHandler {

  public static SessionHandler create(final Vertx vertx, final MainOptions options) {
    final var store = SessionStore.create(vertx);
    final var handler = SessionHandler.create(store);

    final var sessionOptions = options.session();

    handler.setCookieHttpOnlyFlag(true);
    handler.setCookieMaxAge(sessionOptions.maxAge());
    handler.setSessionTimeout(sessionOptions.timeout());

    return handler;
  }

}
