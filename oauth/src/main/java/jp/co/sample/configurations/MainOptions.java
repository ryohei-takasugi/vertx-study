package jp.co.sample.configurations;

import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;

/**
 * MainVerticle の 設定情報のデータクラスです。
 * 設定例: <code>
  {
    "main-verticle": {
      "http-server-options": {
        "port": 8080
      },
      "app-options": {
        "sample": "test_github_actions"
      },
      "session-options": {
        "path": "/",
        "max-age": 60,
        "timeout": 60
      },
      "oauth-options": {
        "github": {
          "client-id": "",
          "client-secret": ""
        },
        "google": {
          "client-id": "",
          "client-secret": ""
        },
        "keycloak": {
          "resource": "account",
          "auth-server-url": "http://192.168.10.105/",
          "credentials": {
            "secret": "A7VeqklHQJ5FnLvuzvWOF5o0uGhwdi63"
          },
          "realm": "sample"
        }
      }
    },
    "api-verticle": {
      "database-api": {
        "http-options": {
          "endpoint": "http://localhost:80/api/database"
        }
      },
      "session-api": {
        "http-options": {
          "endpoint": "http://localhost:80/api/session"
        }
      },
      "file-api": {
        "http-options": {
          "endpoint": "http://localhost:80/api/file"
        }
      }
    }
  }
  </code>
 */
public class MainOptions {

  private final HttpServerOptions httpServerOptions;
  private final OAuthOptions oauthOptions;
  private final SessionOptions sessionOptions;

  /**
   * MainOptionsのインスタンスを生成します。
   * 設定が全くない場合は初期値を、設定値があるが間違いがある場合はインスタンスを生成する際にエラーにします。
   *
   * @param config config of boot parameter
   */
  public static MainOptions create(JsonObject config) {
    JsonObject mainVerticle = config.getJsonObject("main-verticle");
    return new MainOptions(mainVerticle);
  }

  /**
   * インスタンスを生成します。
   *
   * @param mainVerticle
   */
  public MainOptions(JsonObject mainVerticle) {
    // httpServerOptions
    if (mainVerticle.containsKey("http-server-options")) {
      JsonObject httpServerOptions = mainVerticle.getJsonObject("http-server-options", new JsonObject());
      this.httpServerOptions = new HttpServerOptions(httpServerOptions);
    } else
      this.httpServerOptions = new HttpServerOptions();

    // oauth options
    if (mainVerticle.containsKey("oauth-options")) {
      JsonObject oauthOptions = mainVerticle.getJsonObject("oauth-options", new JsonObject());
      this.oauthOptions = new OAuthOptions(oauthOptions);
    } else
      this.oauthOptions = new OAuthOptions();

    // session
    if (mainVerticle.containsKey("session-options")) {
      final JsonObject sessionOptions = mainVerticle.getJsonObject("session-options", new JsonObject());
      this.sessionOptions = new SessionOptions(sessionOptions);
    } else {
      this.sessionOptions = new SessionOptions();
    }

  }

  /**
   * HTTTP Server 設定情報を返却します。
   *
   * @return HTTTP Server 設定情報
   */
  public HttpServerOptions httpServer() {
    return this.httpServerOptions;
  }

  /**
   * OAuth 認証設定情報を返却します。
   *
   * @return OAuth 認証設定情報
   */
  public OAuthOptions oauth() {
    return this.oauthOptions;
  }

  /**
   * Session 設定情報を返却します。
   *
   * @return Session 設定情報
   */
  public SessionOptions session() {
    return this.sessionOptions;
  }

  /**
   * このクラスを JsonObject として返却します。
   *
   * @return 全設定情報
   */
  public JsonObject toJsonObject() {
    return new JsonObject();
  }
}
