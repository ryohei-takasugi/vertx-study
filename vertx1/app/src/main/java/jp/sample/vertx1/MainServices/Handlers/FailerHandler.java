package jp.sample.vertx1.MainServices.Handlers;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.file.FileSystem;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.share.MyLogger;

public class FailerHandler implements Handler<RoutingContext> {

  /** Logger */
  private static final MyLogger LOGGER = MyLogger.create(FailerHandler.class);

  private static final String File_404_PATH = "error/404.html";

  private static final String File_500_PATH = "error/500.html";

  /**
   * main method.
   *
   * @param event vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext event) {
    final FileSystem fileSystem = event.vertx().fileSystem();
    switch (event.statusCode()) {
      case 404:
        final Future<Boolean> exists404File = fileSystem.exists(File_404_PATH);
        exists404File
            .onSuccess(
                ex -> {
                  event.response().setStatusCode(404).sendFile(File_404_PATH);
                })
            .onFailure(
                th -> {
                  LOGGER.error(event.session(), "", new IllegalAccessError("404 not found"));
                  event.response().setStatusCode(404).end("404 not found");
                });
        break;
      default:
        final Future<Boolean> exists500File = fileSystem.exists(File_500_PATH);
        exists500File
            .onSuccess(
                ex -> {
                  event.response().setStatusCode(500).sendFile(File_500_PATH);
                })
            .onFailure(
                th -> {
                  LOGGER.error(event.session(), "", new IllegalAccessError("500 server error"));
                  event.response().setStatusCode(500).end("500 server error");
                });
        break;
    }
  }
}
