package jp.co.sample.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

public class FailerPageHandler implements Handler<RoutingContext> {

  /** Logger */
  private static final Logger logger = LoggerFactory.getLogger(FailerPageHandler.class);

  private static final String File_404_PATH = "error/404.html";
  private static final String File_500_PATH = "error/500.html";

  private String page404;
  private String page500;

  public static FailerPageHandler create(Vertx v) {
    return new FailerPageHandler(v);
  }

  public FailerPageHandler(Vertx v) {
    final var fileSystem = v.fileSystem();

    if (fileSystem.existsBlocking(File_404_PATH)) {
      final var fut = fileSystem.readFile(File_404_PATH);
      fut.onSuccess(
          buffer -> {
            page404 = buffer.toString("UTF-8");
          })
          .onFailure(
              th -> {
                throw new IllegalArgumentException("NOT FOUND error/404.html", th);
              });
    } else {
      throw new IllegalArgumentException("NOT FOUND error/404.html");
    }

    if (fileSystem.existsBlocking(File_500_PATH)) {
      final var fut = fileSystem.readFile(File_500_PATH);
      fut.onSuccess(
          buffer -> {
            page500 = buffer.toString("UTF-8");
          })
          .onFailure(
              th -> {
                throw new IllegalArgumentException("NOT FOUND error/500.html", th);
              });
    } else {
      throw new IllegalArgumentException("NOT FOUND error/500.html");
    }
  }

  @Override
  public void handle(RoutingContext ctx) {
    final var response = ctx.response();
    logger.error("[{}] {} {}", ctx.session().id(), response.getStatusCode(), response.getStatusMessage());
    switch (ctx.statusCode()) {
      case 404:
        response.setStatusCode(404);
        response.end(page404);
        break;
      default:
        response.setStatusCode(500);
        response.end(page500);
        break;
    }
  }
}
