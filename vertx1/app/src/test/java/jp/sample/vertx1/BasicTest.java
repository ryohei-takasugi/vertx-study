package jp.sample.vertx1;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jp.sample.vertx1.share.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@DisplayName("基本的な起動確認をテストします")
@ExtendWith(VertxExtension.class)
public class BasicTest extends AbstractTest {

  @Test
  @DisplayName("MainServiceのVerticleが起動テストです")
  void startMainServiceVerticle(Vertx vertx, VertxTestContext ctx) {
    JsonObject config = loadConfig(vertx);
    DeploymentOptions options = new DeploymentOptions().setConfig(config);
    Future<String> fut = vertx.deployVerticle(new MainServiceVerticle(), options);
    fut.onSuccess(
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
  @DisplayName("ClientServiceのVerticleが起動テストです")
  void startClientServiceVerticle(Vertx vertx, VertxTestContext ctx) {
    JsonObject config = loadConfig(vertx);
    DeploymentOptions options = new DeploymentOptions().setConfig(config);
    Future<String> fut = vertx.deployVerticle(new ApiServiceVerticle(), options);
    fut.onSuccess(
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
}
