package jp.sample.vertx1.MainServices.Modules;

import io.vertx.ext.web.RoutingContext;

public interface IResponse<E> {

  static final int SUCCESS_STATUS_CODE = 200;
  static final int FAILED_STATUS_CODE = 500;
  static final int NOT_FOUND_STATUS_CODE = 404;

  void success(RoutingContext event, E object);

  void failed(RoutingContext event, int statusCode, String errorMessage, Throwable th);
}
