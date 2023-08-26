package jp.sample.vertx1.models.config;

import io.vertx.core.json.JsonObject;

public class Config {

  private final JsonObject config;
  private final ApiOptions appOptions;
  private final MainOptions mainOptions;

  public static Config create(JsonObject config) {
    return new Config(config);
  }

  public Config(JsonObject config) {
    if (config == null || config.isEmpty()) {
      throw new IllegalArgumentException("config file is required");
    }
    this.config = config;
    this.appOptions = ApiOptions.create(config.getJsonObject("api-verticle", new JsonObject()));
    this.mainOptions = MainOptions.create(config.getJsonObject("main-verticle", new JsonObject()));
  }

  public ApiOptions appOptions() {
    return this.appOptions;
  }

  public MainOptions mainOptions() {
    return this.mainOptions;
  }

  public JsonObject toJson() {
    return this.config;
  }
}
