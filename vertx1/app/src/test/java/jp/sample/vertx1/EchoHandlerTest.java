package jp.sample.vertx1;

import static org.assertj.core.api.Assertions.assertThat;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jp.sample.vertx1.MainServices.MainServiceVerticle;
import jp.sample.vertx1.share.AbstractTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

@DisplayName("EchoHandlerに対する試験を行います")
@ExtendWith(VertxExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class EchoHandlerTest extends AbstractTest {

  /** テスト対象のURI */
  private String uri = "/echo";

  @BeforeAll
  @DisplayName("テスト対象を起動します")
  void startVerticle(Vertx vertx, VertxTestContext ctx) {
    Checkpoint check = ctx.checkpoint();
    DeploymentOptions options = serverOptions(vertx);
    Future<String> fut = vertx.deployVerticle(new MainServiceVerticle(), options);
    fut.onSuccess(
            id -> {
              // 起動できたら成功
              check.flag();
            })
        .onFailure(
            th -> {
              // 起動しなければ失敗
              ctx.failNow(th);
            });
  }

  @Test
  @DisplayName("戻り値のstatus:200であることを確認します")
  void testStatus(Vertx vertx, VertxTestContext ctx) {
    HttpRequest<Buffer> request = httpClient(vertx, HttpMethod.GET, uri);
    Future<HttpResponse<Buffer>> fut = request.send();
    fut.onSuccess(
            responce -> {
              ctx.verify(
                  () -> {
                    assertThat(responce.statusCode()).isEqualTo(200);
                  });
              ctx.completeNow();
            })
        .onFailure(
            th -> {
              ctx.failNow(th);
            });
  }

  @Test
  @DisplayName("戻り値のbodyJsonObject型であり、Port番号を含むことを確認します")
  void testReturnBody(Vertx vertx, VertxTestContext ctx) {
    JsonObject config = loadConfig(vertx);
    int configPort = config.getJsonObject("http").getInteger("port");

    HttpRequest<Buffer> request = httpClient(vertx, HttpMethod.GET, uri);
    Future<HttpResponse<Buffer>> fut = request.send();
    fut.onSuccess(
            responce -> {
              ctx.verify(
                  () -> {
                    assertThat(responce.body().toJsonObject()).isNotEqualTo(new JsonObject());
                    JsonObject http = responce.body().toJsonObject().getJsonObject("http");
                    int port = http.getInteger("port");
                    assertThat(port).isEqualTo(configPort);
                  });
              ctx.completeNow();
            })
        .onFailure(
            th -> {
              ctx.failNow(th);
            });
  }
}
