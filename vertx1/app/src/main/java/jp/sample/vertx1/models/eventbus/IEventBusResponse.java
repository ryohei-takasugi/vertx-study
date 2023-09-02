package jp.sample.vertx1.models.eventbus;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import jp.sample.vertx1.models.api.NicoNicoResponse;

public interface IEventBusResponse {
  void response(Message<JsonObject> ctx, NicoNicoResponse res);
}
