package jp.sample.vertx1.models.api;

import io.vertx.core.json.JsonObject;

public class NicoNicoResponse {
  private int status;
  private JsonObject jsonBody;
  private Throwable throwBody;

  public static NicoNicoResponse create() {
    return new NicoNicoResponse();
  }

  public NicoNicoResponse setStasu(int status) {
    this.status = status;
    return this;
  }

  public NicoNicoResponse setBody(JsonObject body) {
    this.jsonBody = body;
    return this;
  }

  public NicoNicoResponse setBody(Throwable th) {
    this.throwBody = th;
    return this;
  }

  public JsonObject toJsonObject() {
    var response = new JsonObject().put("status", this.status);
    if (this.throwBody != null) {
      return response.put("body", this.throwBody.getMessage());
    } else {
      return response.put("body", this.jsonBody);
    }
  }
}
