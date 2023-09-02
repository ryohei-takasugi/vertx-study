package jp.sample.vertx1.models.handler;

import io.vertx.ext.web.RoutingContext;

public interface IMainHandlerResponse {
  void response(RoutingContext ctx, String body);
}
