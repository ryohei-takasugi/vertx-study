package jp.co.sample.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.handlebars.HandlebarsTemplateEngine;

public class GithubSamplePageHandler implements Handler<RoutingContext> {

    private final HandlebarsTemplateEngine engine;
    private final String clientId;

    public static GithubSamplePageHandler create(HandlebarsTemplateEngine engine, String clientId) {
        return new GithubSamplePageHandler(engine, clientId);
    }

    private GithubSamplePageHandler(HandlebarsTemplateEngine engine, String clientId) {
        this.engine = engine;
        this.clientId = clientId;
    }

    @Override
    public void handle(RoutingContext ctx) {
        // we pass the client id to the template
        JsonObject data = new JsonObject()
                .put("client_id", clientId);
        // and now delegate to the engine to render it.
        engine.render(data, "views/index.hbs", res -> {
            if (res.succeeded()) {
                ctx.response()
                        .putHeader("Content-Type", "text/html")
                        .end(res.result());
            } else {
                ctx.fail(res.cause());
            }
        });
    }

}
