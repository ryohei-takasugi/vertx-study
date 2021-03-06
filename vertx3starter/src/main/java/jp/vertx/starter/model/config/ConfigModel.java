package jp.vertx.starter.model.config;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;

public class ConfigModel {

  /** logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigModel.class);

  private final JsonObject contextConfig;

  /**
   * contractor
   *
   * @param config
   */
  public ConfigModel(JsonObject jsonObject) {
    if (jsonObject.isEmpty()) {
      this.contextConfig = defaultConfig();
    } else {
      this.contextConfig = jsonObject;
    }
    LOGGER.debug(ConfigModel.class.getName() + " " + this.contextConfig);
  }

  /**
   * default config
   *
   * @return config
   */
  private JsonObject defaultConfig() {
    JsonObject jsonObjectLv1 = new JsonObject();
    jsonObjectLv1.put("port", 8080);
    JsonObject jsonObjectLv2 = new JsonObject();
    jsonObjectLv2.put("id", 1);
    jsonObjectLv2.put("name", "sato");
    return jsonObjectLv1.put("default", jsonObjectLv2);
  }

  /**
   * http listen port number.
   *
   * @return port number
   */
  public int getPort() {
    int port = 0;
    try {
      port = this.contextConfig.getInteger("port");
    } catch (Exception e) {
      LOGGER.error(e.fillInStackTrace().getMessage());
    }
    return port;
  }

  /**
   * request default id
   *
   * @return id number
   */
  public int getDefaultId() {
    int id = 0;
    try {
      id = this.contextConfig.getJsonObject("default").getInteger("id");
    } catch (Exception e) {
      LOGGER.error(e.fillInStackTrace().getMessage());
    }
    return id;
  }

  /**
   * request default name
   *
   * @return name
   */
  public String getDefaultName() {
    String name = "";
    try {
      name = this.contextConfig.getJsonObject("default").getString("name");
    } catch (Exception e) {
      LOGGER.error(e.fillInStackTrace().getMessage());
    }
    return name;
  }
}
