package jp.sample.vertx1.models.eventbus;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Session;
import jp.sample.vertx1.modules.HandlerLogger;
import jp.sample.vertx1.modules.UriBuilder;

public class NicoNicoRequest implements IEventBus {
  /** Logger */
  private static final HandlerLogger logger = HandlerLogger.create(NicoNicoRequest.class);

  private final LocalSession sessionId;
  private final HttpMethod method;
  private final String host;
  private final int port;
  private final boolean ssl;
  private final String uri;

  public static JsonObject createRequest(Session session) {
    var uri = new UriBuilder("/api/v2/snapshot/video/");
    uri.addPaths("/contents/search");
    uri.addQueries("q", "初音ミク");
    uri.addQueries("targets", "title");
    uri.addQueries("fields", "contentId");
    uri.addQueries("fields", "title");
    uri.addQueries("fields", "viewCounter");
    uri.addQueries("filters[viewCounter][gte]", "10000");
    uri.addQueries("_sort", "-viewCounter");
    uri.addQueries("_offset", "0");
    uri.addQueries("_limit", "3");
    uri.addQueries("_context", "apiguide");

    var request = new JsonObject();
    request.put("sessionId", session.id());
    request.put("method", HttpMethod.GET.toString());
    request.put("host", "api.search.nicovideo.jp");
    request.put("port", 443);
    request.put("ssl", true);
    request.put("uri", uri.toString());
    logger.debug(session, request.encode());
    return request;
  }

  public NicoNicoRequest(JsonObject body) {
    if (body.isEmpty()) {
      new IllegalArgumentException();
    }
    if (body.containsKey("sessionId")) {
      this.sessionId = IEventBus.LocalSession.create(body.getString("sessionId"));
    } else {
      this.sessionId = null;
    }
    if (body.containsKey("method")) {
      this.method = HttpMethod.valueOf(body.getString("method"));
    } else {
      this.method = HttpMethod.GET;
    }
    if (body.containsKey("host")) {
      this.host = body.getString("host");
    } else {
      this.host = "localhost";
    }
    if (body.containsKey("port")) {
      this.port = body.getNumber("port").intValue();
    } else {
      this.port = 80;
    }
    if (body.containsKey("ssl")) {
      this.ssl = body.getBoolean("ssl");
    } else {
      this.ssl = true;
    }
    if (body.containsKey("uri")) {
      this.uri = body.getString("uri");
    } else {
      this.uri = "/";
    }
  }

  public LocalSession sessionId() {
    return sessionId;
  }

  public HttpMethod method() {
    return method;
  }

  public String host() {
    return host;
  }

  public int port() {
    return port;
  }

  public Boolean ssl() {
    return ssl;
  }

  public String uri() {
    return uri;
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    json.put("method", method.toString());
    json.put("host", host);
    json.put("port", port);
    json.put("ssl", ssl);
    json.put("uri", uri);
    return json;
  }
}
