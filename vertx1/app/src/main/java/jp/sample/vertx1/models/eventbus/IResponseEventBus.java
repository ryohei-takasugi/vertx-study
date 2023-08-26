package jp.sample.vertx1.models.eventbus;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;

public interface IResponseEventBus {

  static final int SUCCESS_STATUS_CODE = 200;
  static final int FAILED_STATUS_CODE = 500;
  static final int NOT_FOUND_STATUS_CODE = 404;

  void success(Message<JsonObject> requestEvent, IEventBus model, HttpResponse<Buffer> object);

  void failed(
      Message<JsonObject> requestEvent,
      IEventBus model,
      int statusCode,
      String errorMessage,
      Throwable th);
}
