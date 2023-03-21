package jp.sample.vertx1.MainServices.Modules;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import jp.sample.vertx1.share.model.IEventBusModel;

public interface IResponseEventBus {

  static final int SUCCESS_STATUS_CODE = 200;
  static final int FAILED_STATUS_CODE = 500;
  static final int NOT_FOUND_STATUS_CODE = 404;

  void success(Message<JsonObject> requestEvent, IEventBusModel model, HttpResponse<Buffer> object);

  void failed(
      Message<JsonObject> requestEvent,
      IEventBusModel model,
      int statusCode,
      String errorMessage,
      Throwable th);
}
