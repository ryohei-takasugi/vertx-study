package jp.co.sample.configurations;

import io.vertx.core.json.JsonObject;

public class KeycloakAuthOptions {
    private final JsonObject plain;
    private final String resource;
    private final String authServerUrl;
    private final String secret;
    private final String realm;

    public KeycloakAuthOptions() {
        this.plain = null;
        this.resource = null;
        this.authServerUrl = null;
        this.secret = null;
        this.realm = null;
    }

    public KeycloakAuthOptions(JsonObject config) {
        this.plain = config;
        // google
        if (!config.containsKey("resource")
                || config.getString("resource").isBlank())
            throw new IllegalArgumentException("Please set resource");
        this.resource = config.getString("resource");

        if (!config.containsKey("auth-server-url")
                || config.getString("auth-server-url").isBlank())
            throw new IllegalArgumentException("Please set auth-server-url");
        this.authServerUrl = config.getString("auth-server-url");

        if (!config.containsKey("credentials")
                || config.getJsonObject("credentials").isEmpty())
            throw new IllegalArgumentException("Please set credentials");
        if (!config.getJsonObject("credentials").containsKey("secret")
                || config.getJsonObject("credentials").getString("secret").isBlank())
            throw new IllegalArgumentException("Please set credentials secret");
        this.secret = config.getJsonObject("credentials").getString("secret");

        if (!config.containsKey("realm") || config.getString("realm").isBlank())
            throw new IllegalArgumentException("Please set realm");
        this.realm = config.getString("realm");
    }

    public String resource() {
        return this.resource;
    }

    public String clientSecret() {
        return this.authServerUrl;
    }

    public String secret() {
        return this.secret;
    }

    public String realm() {
        return this.realm;
    }

    public JsonObject toJsonObject() {
        return this.plain;
    }

    public boolean isNotEmpty() {
        return this.plain != null;
    }
}
