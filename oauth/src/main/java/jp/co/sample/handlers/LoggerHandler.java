package jp.co.sample.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class LoggerHandler implements Handler<RoutingContext> {

  /** logger. */
  private static final Logger logger = LoggerFactory.getLogger(LoggerHandler.class);

  public static LoggerHandler create() {
    return new LoggerHandler();
  }

  @Override
  public void handle(RoutingContext ctx) {
    final var request = ctx.request();

    final var requestId = ctx.session().id();
    // var requestId = UUID.randomUUID().toString().replace("-", "");
    ctx.put("RequestId", requestId);

    final var start = System.currentTimeMillis();
    final var method = request.method();
    final var absoluteURI = request.absoluteURI();
    final var remoteAddress = request.remoteAddress();
    final var version = request.version();

    logger.info("[{}] {} {} {} {}",
        requestId,
        method,
        absoluteURI,
        remoteAddress,
        version);

    ctx.response().endHandler(v -> {
      logger.info("[{}] {} {} {} {} byte {} ms",
          requestId,
          ctx.response().getStatusCode(),
          method,
          absoluteURI,
          ctx.response().bytesWritten(),
          System.currentTimeMillis() - start);
    });

    ctx.next();
  }

}
