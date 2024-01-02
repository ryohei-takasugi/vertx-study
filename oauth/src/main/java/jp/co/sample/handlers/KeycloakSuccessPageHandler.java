package jp.co.sample.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.handlebars.HandlebarsTemplateEngine;

public class KeycloakSuccessPageHandler implements Handler<RoutingContext> {
    /** logger. */
    private static final Logger logger = LoggerFactory.getLogger(KeycloakSuccessPageHandler.class);

    private static final String TEMPLATE_FILE_NAME = "views/advanced-keycloak.hbs";

    private final HandlebarsTemplateEngine engine;
    private final OAuth2Auth provider;

    public static KeycloakSuccessPageHandler create(HandlebarsTemplateEngine engine, OAuth2Auth provider) {
        return new KeycloakSuccessPageHandler(engine, provider);
    }

    private KeycloakSuccessPageHandler(HandlebarsTemplateEngine engine, OAuth2Auth provider) {
        this.engine = engine;
        this.provider = provider;
    }

    @Override
    public void handle(RoutingContext ctx) {
        User user = ctx.user();
        logger.debug("[{}] user: {}", ctx.get("RequestId"), user.subject());
        Future<JsonObject> fut = provider.userInfo(user);
        fut.compose(userInfo -> {
            logger.debug("[{}] render data: {}", ctx.get("RequestId"), userInfo);
            return engine.render(userInfo, TEMPLATE_FILE_NAME);
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
