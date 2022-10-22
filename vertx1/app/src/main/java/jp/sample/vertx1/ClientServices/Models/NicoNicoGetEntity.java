package jp.sample.vertx1.ClientServices.Models;

import io.vertx.core.json.JsonObject;

public class NicoNicoGetEntity {

  private final String contentId;
  private final String title;
  private final Integer viewCounter;

  public NicoNicoGetEntity(JsonObject recode) {
    if (recode != null && !recode.isEmpty()) {
      this.contentId = recode.getString("contentId");
      this.title = recode.getString("title");
      this.viewCounter = recode.getInteger("viewCounter");
    } else {
      this.contentId = "";
      this.title = "";
      this.viewCounter = 0;
    }
  }

  public String contentId() {
    return contentId;
  }

  public String title() {
    return title;
  }

  public Integer viewCounter() {
    return viewCounter;
  }
}
