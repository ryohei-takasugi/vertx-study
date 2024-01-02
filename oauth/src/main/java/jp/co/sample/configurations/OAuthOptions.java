package jp.co.sample.configurations;

import io.vertx.core.json.JsonObject;

public class OAuthOptions {
    private final JsonObject plain;
    private final GitHubAuthOptions githubAuthOptions;
    private final GoogleAuthOptions googleAuthOptions;
    private final KeycloakAuthOptions keycloakAuthOptions;

    public OAuthOptions() {
        this.plain = null;
        this.githubAuthOptions = new GitHubAuthOptions();
        this.googleAuthOptions = new GoogleAuthOptions();
        this.keycloakAuthOptions = new KeycloakAuthOptions();
    }

    public OAuthOptions(JsonObject oauthOptions) {
        this.plain = oauthOptions;

        if (oauthOptions.containsKey("github")) {
            final JsonObject githubAuthOptions = oauthOptions.getJsonObject("github", new JsonObject());
            this.githubAuthOptions = new GitHubAuthOptions(githubAuthOptions);
        } else
            this.githubAuthOptions = new GitHubAuthOptions();

        // google
        if (oauthOptions.containsKey("google")) {
            final JsonObject googleAuthOptions = oauthOptions.getJsonObject("google", new JsonObject());
            this.googleAuthOptions = new GoogleAuthOptions(googleAuthOptions);
        } else
            this.googleAuthOptions = new GoogleAuthOptions();

        // keycloak
        if (oauthOptions.containsKey("keycloak")) {
            final JsonObject keycloakAuthOptions = oauthOptions.getJsonObject("keycloak", new JsonObject());
            this.keycloakAuthOptions = new KeycloakAuthOptions(keycloakAuthOptions);
        } else {
            this.keycloakAuthOptions = new KeycloakAuthOptions();
        }
    }

    /**
     * GitHub の OAuth に必要な設定情報を返却します。
     *
     * @return GitHub 認証設定
     */
    public GitHubAuthOptions github() {
        return this.githubAuthOptions;
    }

    /**
     * Google の OAuth に必要な設定情報を返却します。
     *
     * @return Google 認証設定
     */
    public GoogleAuthOptions google() {
        return this.googleAuthOptions;
    }

    /**
     * Keycloak の OAuth に必要な設定情報を返却します。
     *
     * @return Keycloak 認証設定
     */
    public KeycloakAuthOptions keycloak() {
        return this.keycloakAuthOptions;
    }

    public JsonObject toJsonObject() {
        return this.plain;
    }

    public boolean isEmpty() {
        return this.plain == null;
    }
}
