package jp.sample.vertx1.share;

import com.github.javafaker.Faker;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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
   * @param json faker responce
   * @return DeploymentOptions
   */
  protected DeploymentOptions serverOptions(Vertx vertx, JsonObject json) {
    JsonObject config = loadConfig(vertx);
    config.put("returnJsonData", json);
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

  /**
   * create faker repsponce
   *
   * @return dummy responce
   */
  protected JsonObject fakerResponce() {
    Faker faker = new Faker(new Locale("ja_JP"));

    JsonArray arrayData = new JsonArray();

    for (int i = 0; i < 3; i++) {
      arrayData.add(fakerResponceRecord(faker));
    }

    JsonObject meta = new JsonObject();
    meta.put("id", faker.internet().uuid());
    meta.put("totalCount", faker.number().randomNumber(20, false));
    List<Integer> httpStatus = Arrays.asList(200, 404, 500, 502);
    Random random = new Random();
    meta.put("status", httpStatus.get(random.nextInt(httpStatus.size())));

    JsonObject lv1 = new JsonObject();
    lv1.put("data", arrayData);
    lv1.put("meta", meta);

    return lv1;
  }

  /**
   * create responce record data
   *
   * @param faker
   * @return responce record data
   */
  private JsonObject fakerResponceRecord(Faker faker) {
    JsonObject lv2 = new JsonObject();
    lv2.put("contentId", faker.lorem().characters(9));
    lv2.put("title", faker.name().title());
    lv2.put("viewCounter", faker.number().randomNumber(20, false));
    return lv2;
  }
}
