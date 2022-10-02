package jp.sample.starter;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ErrorHandler;
import jp.sample.starter.controllers.HomeController;

public class RouteMap {

  private final Vertx v;
  private final Router r;
  private static final String CONTENT_TYPE = "application/json";

  public RouteMap(Vertx vertx) {
    this.v = vertx;
    this.r = Router.router(vertx);
    createRoute();
  }

  public Router getRouter() {
    return this.r;
  }

  private void createRoute() {
    r.get("/").produces(CONTENT_TYPE).handler(HomeController.create());
    r.errorHandler(500, ErrorHandler.create());
  }
}
