package jp.sample.vertx1.models.eventbus;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Session;

public class RequestModel {
  private LocalSession sessionId;
  private String searchWord;

  public static RequestModel create() {
    return new RequestModel();
  }

  public RequestModel setSessionId(Session sessionId) {
    this.sessionId = LocalSession.create(sessionId.id());
    return this;
  }

  public RequestModel setSearchWord(String searchWord) {
    this.searchWord = searchWord;
    return this;
  }

  public JsonObject toJsonObject() {
    if (this.sessionId == null || this.searchWord == null) {
      throw new IllegalArgumentException("Session Id or Search Word is a required field.");
    }
    return new JsonObject()
        .put("sessionId", this.sessionId.id())
        .put("searchWord", this.searchWord);
  }
}
