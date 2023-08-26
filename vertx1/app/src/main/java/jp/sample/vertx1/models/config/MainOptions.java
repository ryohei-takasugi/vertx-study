package jp.sample.vertx1.models.config;

import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;

public class MainOptions {
  private final JsonObject config;
  private final HttpServerOptions httpServerOptions;

  public static MainOptions create(JsonObject config) {
    return new MainOptions(config);
  }

  public MainOptions(JsonObject config) {
    if (config == null || config.isEmpty()) {
      throw new IllegalArgumentException("config file is required");
    }
    this.config = config;
    this.httpServerOptions =
        new HttpServerOptions(config.getJsonObject("http-options", new JsonObject()));
  }

  public HttpServerOptions httpServerOptions() {
    return this.httpServerOptions;
  }

  public JsonObject toJson() {
    return this.config;
  }
}
