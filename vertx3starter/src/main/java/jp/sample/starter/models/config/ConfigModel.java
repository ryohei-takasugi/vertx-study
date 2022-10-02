package jp.sample.starter.models.config;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigModel extends ConfigEntity {

  /** logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigModel.class);

  /** config */
  private final JsonObject contextConfig;

  /**
   * contractor
   *
   * @param config
   */
  public ConfigModel(Vertx vertx) {
    JsonObject jsonObject = readConfigFile(vertx);
    if (jsonObject.isEmpty()) {
      this.contextConfig = defaultConfig();
    } else {
      this.contextConfig = jsonObject;
    }
    setEntity();
    LOGGER.debug(ConfigModel.class.getName() + " " + this.contextConfig);
  }

  private JsonObject readConfigFile(Vertx vertx) {
    try {
      Buffer buf = vertx.fileSystem().readFileBlocking("config/config.json");
      LOGGER.debug(ConfigModel.class.getName() + " readConfigFile " + buf.toString());
      return new JsonObject(buf.toString());
    } catch (Throwable t) {
      LOGGER.error(ConfigModel.class.getName() + " readConfigFile ", t);
      return new JsonObject();
    }
  }

  // private JsonObject readConfigFile(Vertx vertx) {
  //   try {
  //     ConfigStoreOptions fileStore =
  //         new ConfigStoreOptions()
  //             .setType("file")
  //             .setOptional(true)
  //             .setConfig(new JsonObject().put("path", "config/config.json"));
  //     ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore(fileStore);
  //     ConfigRetriever retriever = ConfigRetriever.create(vertx, options);
  //     JsonObject result = new JsonObject();
  //     retriever.getConfig(
  //         ar -> {
  //           if (ar.failed()) {
  //             LOGGER.error("message");
  //           } else {
  //             result.mergeIn(ar.result());
  //           }
  //         });
  //     LOGGER.debug("@@@ config" + result);
  //     return result;
  //   } catch (Throwable t) {
  //     LOGGER.error("message", t);
  //     return new JsonObject();
  //   }
  // }

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

  private void setEntity() {
    try {
      setPort(this.contextConfig.getInteger("port"));
      setDefaultId(this.contextConfig.getJsonObject("default").getInteger("id"));
      setDefaultName(this.contextConfig.getJsonObject("default").getString("name"));
    } catch (Exception e) {
      LOGGER.error(e.fillInStackTrace().getMessage());
    }
  }

  // /**
  //  * http listen port number.
  //  *
  //  * @return port number
  //  */
  // public int getPort() {
  //   int port = 0;
  //   try {
  //     port = this.contextConfig.getInteger("port");
  //   } catch (Exception e) {
  //     LOGGER.error(e.fillInStackTrace().getMessage());
  //   }
  //   return port;
  // }

  // /**
  //  * request default id
  //  *
  //  * @return id number
  //  */
  // public int getDefaultId() {
  //   int id = 0;
  //   try {
  //     id = this.contextConfig.getJsonObject("default").getInteger("id");
  //   } catch (Exception e) {
  //     LOGGER.error(e.fillInStackTrace().getMessage());
  //   }
  //   return id;
  // }

  // /**
  //  * request default name
  //  *
  //  * @return name
  //  */
  // public String getDefaultName() {
  //   String name = "";
  //   try {
  //     name = this.contextConfig.getJsonObject("default").getString("name");
  //   } catch (Exception e) {
  //     LOGGER.error(e.fillInStackTrace().getMessage());
  //   }
  //   return name;
  // }
}
