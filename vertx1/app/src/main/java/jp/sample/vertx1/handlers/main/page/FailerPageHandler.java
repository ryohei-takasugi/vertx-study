package jp.sample.vertx1.handlers.main.page;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.models.enumeration.HttpStatus;
import jp.sample.vertx1.modules.HandlerLogger;

public class FailerPageHandler implements Handler<RoutingContext> {

  /** Logger */
  private static final HandlerLogger logger = HandlerLogger.create(FailerPageHandler.class);

  private static final String File_404_PATH = "error/404.html";
  private static final String File_500_PATH = "error/500.html";

  private String page404;
  private String page500;

  protected FailerPageHandler(Vertx v) {
    final var fileSystem = v.fileSystem();

    var exist404 = fileSystem.existsBlocking(File_404_PATH);
    if (exist404) {
      var fut404 = fileSystem.readFile(File_404_PATH);
      fut404
          .onSuccess(
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

    var exist500 = fileSystem.existsBlocking(File_500_PATH);
    if (exist500) {
      var fut500 = fileSystem.readFile(File_500_PATH);
      fut500
          .onSuccess(
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

  /**
   * main method.
   *
   * @param ctx vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext ctx) {
    final var fileSystem = ctx.vertx().fileSystem();
    final var response = ctx.response();
    switch (ctx.statusCode()) {
      case 404:
        response.setStatusCode(HttpStatus.NOT_FOUND.code());
        response.setStatusMessage(HttpStatus.NOT_FOUND.message());
        response.end(page404);
        break;
      default:
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.code());
        response.setStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.message());
        response.end(page500);
        break;
    }
  }
}
