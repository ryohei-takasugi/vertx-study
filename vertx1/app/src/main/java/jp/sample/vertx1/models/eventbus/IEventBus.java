package jp.sample.vertx1.models.eventbus;

import io.vertx.core.json.JsonObject;

public interface IEventBus {
  LocalSession sessionId();

  JsonObject toJson();

  public class LocalSession {
    private final String id;

    public static LocalSession create(String id) {
      return new LocalSession(id);
    }

    private LocalSession(String id) {
      this.id = id;
    }

    public String id() {
      return id;
    }
  }
}
