package jp.co.sample.functions;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;

public class Authentication implements Function<RoutingContext, Future<User>> {
  /** Logger */
  private static final Logger logger = LoggerFactory.getLogger(Authentication.class);

  public static Authentication create() {
    return new Authentication();
  }

  @Override
  public Future<User> apply(RoutingContext ctx) {
    final Promise<User> promise = Promise.promise();

    final JsonObject requestBody = ctx.body().asJsonObject();
    final User requestUser = User.create(requestBody);
    logger.debug("[{}] requestBody:{} subject:{}", ctx.get("RequestId"), requestBody, requestUser.subject());
    if ("j.d.piero924.xad@gmail.com".equals(requestUser.subject())) {
      logger.debug("[{}] requestBody:{} subject:{}", ctx.get("RequestId"), requestBody, requestUser.subject());
      ctx.setUser(requestUser);
      promise.complete(requestUser);
    } else {
      promise.complete();
    }

    return promise.future();
  }

}
