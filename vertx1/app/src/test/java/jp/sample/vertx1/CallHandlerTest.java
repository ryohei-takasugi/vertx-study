package jp.sample.vertx1;

import static org.assertj.core.api.Assertions.assertThat;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jp.sample.vertx1.MainServices.MainServiceVerticle;
import jp.sample.vertx1.share.AbstractTest;
import jp.sample.vertx1.share.MockClientServiceVerticle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

@DisplayName("CallHandlerに対する試験を行います")
@ExtendWith(VertxExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CallHandlerTest extends AbstractTest {

  /** テスト対象のURI */
  private String uri = "/call";

  private JsonObject responce = new JsonObject();

  @BeforeAll
  @DisplayName("テスト対象を起動します")
  void startVerticle(Vertx vertx, VertxTestContext ctx) {
    Checkpoint check = ctx.checkpoint();
    DeploymentOptions mainOptions = serverOptions(vertx);
    Future<String> main = vertx.deployVerticle(new MainServiceVerticle(), mainOptions);
    main.onSuccess(
            id -> {
              // 起動できたら成功
              check.flag();
            })
        .onFailure(
            th -> {
              // 起動しなければ失敗
              ctx.failNow(th);
            });

    this.responce = fakerResponce();
    DeploymentOptions clientOptions = serverOptions(vertx, this.responce);
    Future<String> client = vertx.deployVerticle(new MockClientServiceVerticle(), clientOptions);
    client
        .onSuccess(
            id -> {
              // 起動できたら成功
              ctx.completeNow();
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
  @DisplayName("戻り値のHTMLにテストデータが含まれることを確認します")
  void testReturnBody(Vertx vertx, VertxTestContext ctx) {
    HttpRequest<Buffer> request = httpClient(vertx, HttpMethod.GET, uri);
    Future<HttpResponse<Buffer>> fut = request.send();
    fut.onSuccess(
            responce -> {
              ctx.verify(
                  () -> {
                    String body = responce.bodyAsString("UTF-8");
                    JsonObject testJsonData = this.responce;
                    JsonArray array = testJsonData.getJsonArray("data");
                    for (int i = 0; i < array.size(); i++) {
                      JsonObject target = array.getJsonObject(i);
                      assertThat(body).contains(target.getString("contentId"));
                      assertThat(body).contains(target.getString("title"));
                      assertThat(body).contains(target.getString("viewCounter"));
                    }
                  });
              ctx.completeNow();
            })
        .onFailure(
            th -> {
              ctx.failNow(th);
            });
  }
}
