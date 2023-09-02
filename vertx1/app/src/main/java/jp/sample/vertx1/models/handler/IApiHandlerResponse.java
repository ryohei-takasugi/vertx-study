package jp.sample.vertx1.models.handler;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public interface IApiHandlerResponse {
  void response(RoutingContext ctx, JsonObject body);
}
