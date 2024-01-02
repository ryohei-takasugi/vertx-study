package jp.co.sample.routers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.providers.GithubAuth;
import io.vertx.ext.auth.oauth2.providers.GoogleAuth;
import io.vertx.ext.auth.oauth2.providers.KeycloakAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.OAuth2AuthHandler;
import io.vertx.ext.web.templ.handlebars.HandlebarsTemplateEngine;
import jp.co.sample.configurations.MainOptions;
import jp.co.sample.handlers.GithubSamplePageHandler;
import jp.co.sample.handlers.GithubSuccessPageHandler;
import jp.co.sample.handlers.KeycloakSuccessPageHandler;

public class OAuthSubRouter {
  /** logger. */
  private static final Logger logger = LoggerFactory.getLogger(OAuthSubRouter.class);

  public static Router create(Vertx vertx, Router router, MainOptions options) {

    // In order to use a template we first need to create an engine
    final HandlebarsTemplateEngine engine = HandlebarsTemplateEngine.create(vertx);

    if (options.oauth().github().isNotEmpty()) {
      // you should never store these in code,
      // these are your github application credentials
      // private final String CLIENT_ID = "0632b4b9698cefe48798";
      // private final String CLIENT_SECRET =
      // "89635dbb1e122b7e45e34778cca464b89a574bb5";

      // Simple auth service which uses a GitHub to authenticate the user
      final OAuth2Auth githubProvider = GithubAuth.create(
          vertx, options.oauth().github().clientId(), options.oauth().github().clientSecret());

      // // Entry point to the application, this will render a custom template.
      router.get("/github").handler(GithubSamplePageHandler.create(engine, options.oauth().github().clientId()));

      router.route("/github/callback").handler(ctx -> {
        // TODO: Session情報が足りず401になる原因を調査する予定
        ctx.session()
            .put("state", ctx.request().getParam("state"))
            .put("redirect_uri", "/github/protected");
        ctx.next();
      });

      // The protected resource
      router.get("/github/protected")
          .handler(OAuth2AuthHandler.create(vertx, githubProvider, "http://localhost:8080/github/callback")
              // we now configure the oauth2 handler, it will setup the callback handler
              // as expected by your oauth2 provider.
              .setupCallback(router.route("/github/callback"))
              // for this resource we require that users have the authority to retrieve the
              // user emails
              .withScope("user:email"))
          .handler(GithubSuccessPageHandler.create(vertx, engine, githubProvider));
    }

    if (options.oauth().keycloak().isNotEmpty()) {
      // Simple auth service which uses a Keycloak to authenticate the user
      final OAuth2Auth keycloakProvider = KeycloakAuth.create(vertx, options.oauth().keycloak().toJsonObject());

      router.route("/keycloak/callback").handler(ctx -> {
        // TODO: Session情報が足りず401になる原因を調査する予定
        ctx.session()
            .put("state", ctx.request().getParam("state"))
            .put("redirect_uri", "/keycloak/protected");
        ctx.next();
      });

      router.get("/keycloak/protected")
          .handler(OAuth2AuthHandler.create(vertx, keycloakProvider, "http://127.0.0.1:8080/keycloak/callback")
              .setupCallback(router.route("/keycloak/callback")).withScope("openid"))
          .handler(KeycloakSuccessPageHandler.create(engine, keycloakProvider));

    }

    if (options.oauth().google().isNotEmpty()) {
      // Simple auth service which uses a Google to authenticate the user
      // "254183073377-9f89pqngj7a56bg9iluuv7p9kufnk6iv.apps.googleusercontent.com",
      // "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"
      final OAuth2Auth googleProvider = GoogleAuth.create(
          vertx, options.oauth().google().clientId(), options.oauth().google().clientSecret());

      router.route("/google/callback").handler(ctx -> {
        // TODO: Session情報が足りず401になる原因を調査する予定
        ctx.session()
            .put("state", ctx.request().getParam("state"))
            .put("redirect_uri", "/google/protected");
        ctx.next();
      });

      router.get("/google/protected")
          .handler(OAuth2AuthHandler.create(vertx, googleProvider, "http://127.0.0.1:8080/google/callback")
              .setupCallback(router.route("/google/callback")).withScope("openid"))
          .handler(ctx -> {
            User user = ctx.user();
            // retrieve the user profile, this is a common feature but not from the official
            // OAuth2 spec
            Future<JsonObject> fut = googleProvider.userInfo(user);
            fut.compose(userInfo -> {
              // we pass the client info to the template
              JsonObject data = new JsonObject()
                  .put("userInfo", userInfo);
              logger.debug("[{}] render data: {}", ctx.get("RequestId"), data);
              // and now delegate to the engine to render it.
              return engine.render(data, "views/advanced-google.hbs");

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
          });
    }

    return router;
  }

}
