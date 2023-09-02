package jp.sample.vertx1.models.eventbus;

public class LocalSession {
  private final String id;

  public static LocalSession create(String id) {
    return new LocalSession(id);
  }

  private LocalSession(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("Session id is Empty");
    }
    this.id = id;
  }

  public String id() {
    return id;
  }
}
