package jp.co.sample.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.ext.web.templ.handlebars.HandlebarsTemplateEngine;

public class GithubSuccessPageHandler implements Handler<RoutingContext> {
    /** logger. */
    private static final Logger logger = LoggerFactory.getLogger(GithubSuccessPageHandler.class);

    private static final String TEMPLATE_FILE_NAME = "views/advanced.hbs";

    private final Vertx vertx;
    private final HandlebarsTemplateEngine engine;
    private final OAuth2Auth provider;

    public static GithubSuccessPageHandler create(Vertx vertx, HandlebarsTemplateEngine engine, OAuth2Auth provider) {
        return new GithubSuccessPageHandler(vertx, engine, provider);
    }

    private GithubSuccessPageHandler(Vertx vertx, HandlebarsTemplateEngine engine, OAuth2Auth provider) {
        this.vertx = vertx;
        this.engine = engine;
        this.provider = provider;
    }

    @Override
    public void handle(RoutingContext ctx) {
        User user = ctx.user();
        // retrieve the user profile, this is a common feature but not from the official
        // OAuth2 spec
        Future<JsonObject> fut = provider.userInfo(user);
        fut.compose(userInfo -> {
            // the request succeeded, so we use the API to fetch the user's emails
            // fetch the user emails from the github API
            // the web client will retrieve any resource and ensure the right
            // secure headers are passed.
            return WebClient.create(vertx)
                    .getAbs("https://api.github.com/user/emails")
                    .authentication(new TokenCredentials(user.<String>get("access_token")))
                    .as(BodyCodec.jsonArray())
                    .send();

        }).compose(emails -> {
            JsonObject userInfo = fut.result();
            userInfo.put("private_emails", emails.bodyAsJsonArray());
            // we pass the client info to the template
            JsonObject data = new JsonObject()
                    .put("userInfo", userInfo);
            logger.debug("[{}] render data: {}", ctx.get("RequestId"), data);
            // and now delegate to the engine to render it.
            return engine.render(data, TEMPLATE_FILE_NAME);

        }).onSuccess(buf -> {
            try {
                ctx.response()
                        .putHeader(HttpHeaders.CONTENT_TYPE, "text/html")
                        .end(buf);
            } catch (Throwable th) {
                // request didn't succeed because the token was revoked so we
                // invalidate the token stored in the session and render the
                // index page so that the user can start the OAuth flow again
                ctx.session().destroy();
                ctx.fail(th);
            }
        }).onFailure(th -> {
            // request didn't succeed because the token was revoked so we
            // invalidate the token stored in the session and render the
            // index page so that the user can start the OAuth flow again
            ctx.session().destroy();
            ctx.fail(th);
        });
    }
}
