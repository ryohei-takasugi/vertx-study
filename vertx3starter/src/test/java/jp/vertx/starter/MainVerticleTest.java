package jp.vertx.starter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
class MainVerticleTest {

  @BeforeEach
  void prepare(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(
        MainVerticle.class.getCanonicalName(),
        testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  @DisplayName("Check that the server has started")
  void checkServerHasStarted(Vertx vertx, VertxTestContext testContext) {
    WebClient webClient = WebClient.create(vertx);
    webClient
        .get(8080, "localhost", "/")
        .expect(ResponsePredicate.status(200))
        .as(BodyCodec.string())
        .send(
            testContext.succeeding(
                response ->
                    testContext.verify(
                        () -> {
                          assertTrue(response.body().length() > 0);
                          JsonObject body = new JsonObject(response.body());
                          int status = body.getInteger("status");
                          assertTrue(status == 200);
                          testContext.completeNow();
                        })));
  }
}
