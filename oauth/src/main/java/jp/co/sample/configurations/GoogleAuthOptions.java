package jp.co.sample.configurations;

import io.vertx.core.json.JsonObject;

public class GoogleAuthOptions {

    private final JsonObject plain;
    private final String clientId;
    private final String clientSecret;

    public GoogleAuthOptions() {
        this.plain = null;
        this.clientId = null;
        this.clientSecret = null;
    }

    public GoogleAuthOptions(JsonObject config) {
        this.plain = config;
        // google
        if (!config.containsKey("client-id") || config.getString("client-id").isBlank())
            throw new IllegalArgumentException("Please set client-id");
        this.clientId = config.getString("client-id");

        if (!config.containsKey("client-secret") || config.getString("client-secret").isBlank())
            throw new IllegalArgumentException("Please set client-secret");
        this.clientSecret = config.getString("client-secret");

    }

    public String clientId() {
        return this.clientId;
    }

    public String clientSecret() {
        return this.clientSecret;
    }

    public JsonObject toJsonObject() {
        return this.plain;
    }

    public boolean isNotEmpty() {
        return this.plain != null;
    }
}
