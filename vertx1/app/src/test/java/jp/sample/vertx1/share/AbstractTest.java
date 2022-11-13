package jp.sample.vertx1.share;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;

/** Test に必要な共通処理です。 */
public class AbstractTest {

  /**
   * load config file
   *
   * @param vertx {@link Vertx}
   * @return config.json
   */
  protected JsonObject loadConfig(Vertx vertx) {
    FileSystem fs = vertx.fileSystem();
    JsonObject config = fs.readFileBlocking("config/config.json").toJsonObject();
    System.out.println(config.encodePrettily());
    return config;
  }

  /**
   * create http client
   *
   * @param vertx {@link Vertx}
   * @param method {@link HttpMethod}
   * @param uri request uri (ex: "/echo")
   * @return http client
   */
  protected HttpRequest<Buffer> httpClient(Vertx vertx, HttpMethod method, String uri) {
    JsonObject config = loadConfig(vertx);
    JsonObject clientCofig = new JsonObject();
    clientCofig.put("method", method.name());
    clientCofig.put("host", "localhost");
    clientCofig.put("port", config.getJsonObject("http").getInteger("port"));
    clientCofig.put("ssl", false);
    clientCofig.put("uri", uri);

    System.out.println(clientCofig.encodePrettily());
    RequestOptions option = new RequestOptions(clientCofig);

    WebClient client = WebClient.create(vertx);
    return client.request(method, option);
  }

  /**
   * server verticle options
   *
   * @param vertx {@link Vertx}
   * @return DeploymentOptions
   */
  protected DeploymentOptions serverOptions(Vertx vertx) {
    JsonObject config = loadConfig(vertx);
    return new DeploymentOptions().setConfig(config);
  }

  /**
   * server verticle options
   *
   * @param vertx {@link Vertx}
   * @return DeploymentOptions
   */
  protected DeploymentOptions serverOptions(Vertx vertx, String testFilePath) {
    JsonObject config = loadConfig(vertx);
    config.put("returnJsonData", loadFile(vertx, testFilePath).toJsonObject());
    return new DeploymentOptions().setConfig(config);
  }

  /**
   * load Test File
   *
   * @param vertx {@link Vertx}
   * @param path Test file path in test's resources folder
   * @return File Buffer Data
   */
  protected Buffer loadFile(Vertx vertx, String path) {
    FileSystem fs = vertx.fileSystem();
    return fs.readFileBlocking(path);
  }

  /**
   * @param vertx
   * @param testFilePath
   * @return
   */
  protected JsonObject testJsonData(Vertx vertx, String testFilePath) {
    return loadFile(vertx, testFilePath).toJsonObject();
  }

  /**
   * @param vertx
   * @param testFilePath
   * @return
   */
  protected Buffer testXMLData(Vertx vertx, String testFilePath) {
    return loadFile(vertx, testFilePath);
  }
}
