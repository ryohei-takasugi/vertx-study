package jp.vertx.starter.controller;

import io.vertx.ext.web.handler.impl.ErrorHandlerImpl;

public class ErrorController extends ErrorHandlerImpl {

  public ErrorController(String errorTemplateName, boolean displayExceptionDetails) {
    super(errorTemplateName, displayExceptionDetails);
  }
}
