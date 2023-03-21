package jp.sample.vertx1.share.model;

import io.vertx.core.json.JsonObject;

public interface IEventBusModel {
  String sessionId();

  JsonObject toJson();
}
