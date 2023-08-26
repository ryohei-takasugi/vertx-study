package jp.sample.vertx1.handlers.main;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import jp.sample.vertx1.models.IResponseRoutingContext;
import jp.sample.vertx1.modules.MyLogger;

public class FailerHandler implements Handler<RoutingContext>, IResponseRoutingContext<String> {

  /** Logger */
  private static final MyLogger LOGGER = MyLogger.create(FailerHandler.class);

  private static final String File_404_PATH = "error/404.html";
  private static final String File_500_PATH = "error/500.html";

  // private String PAGE_404 = "";

  protected FailerHandler(Vertx v) {
    // final var fileSystem = v.fileSystem();
    // var fut404 = fileSystem.readFile(File_404_PATH);
    // fut404
    //   .onSuccess(
    //       buffer -> {
    //         PAGE_404 = buffer.toString("UTF-8");
    //       })
    //   .onFailure(
    //       th -> {
    //         throw new IllegalArgumentException(th);
    //       });
  }

  /**
   * main method.
   *
   * @param event vert.x RoutingContext data.
   */
  @Override
  public void handle(RoutingContext event) {
    final var fileSystem = event.vertx().fileSystem();
    switch (event.statusCode()) {
      case 404:
        final var exists404File = fileSystem.exists(File_404_PATH);
        exists404File
            .onSuccess(
                ex -> {
                  failed(event, NOT_FOUND_STATUS_CODE, File_404_PATH);
                })
            .onFailure(
                th -> {
                  failedMessage(event, NOT_FOUND_STATUS_CODE, "404 not found", th);
                });
        break;
      default:
        final var exists500File = fileSystem.exists(File_500_PATH);
        exists500File
            .onSuccess(
                ex -> {
                  failed(event, FAILED_STATUS_CODE, File_500_PATH);
                })
            .onFailure(
                th -> {
                  failedMessage(event, FAILED_STATUS_CODE, "500 server error", th);
                });
        break;
    }
  }

  @Override
  public void success(RoutingContext event, String object) {
    // not used
  }

  @Override
  public void failed(RoutingContext event, int statusCode, String filePath, Throwable th) {
    LOGGER.error(event.session(), filePath, th);
    var response = event.response();
    response.setStatusCode(statusCode);
    response.sendFile(filePath);
  }

  public void failed(RoutingContext event, int statusCode, String message) {
    failed(event, statusCode, message, null);
  }

  public void failedMessage(
      RoutingContext event, int statusCode, String errorMessage, Throwable th) {
    LOGGER.error(event.session(), errorMessage, th);
    var response = event.response();
    response.setStatusCode(statusCode);
    response.setStatusMessage(errorMessage);
    response.end(errorMessage);
  }
}
