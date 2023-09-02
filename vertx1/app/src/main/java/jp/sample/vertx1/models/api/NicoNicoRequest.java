package jp.sample.vertx1.models.api;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import jp.sample.vertx1.models.eventbus.LocalSession;
import jp.sample.vertx1.modules.HandlerLogger;
import jp.sample.vertx1.modules.UriBuilder;

public class NicoNicoRequest {
  /** Logger */
  private static final HandlerLogger logger = HandlerLogger.create(NicoNicoRequest.class);

  private final LocalSession sessionId;
  private final HttpMethod method;
  private final String host;
  private final int port;
  private final boolean ssl;
  private final String uri;

  public static NicoNicoRequest create(JsonObject body) {
    return new NicoNicoRequest(body);
  }

  private NicoNicoRequest(JsonObject body) {

    if (body == null
        || body.isEmpty()
        || !(body.containsKey("sessionId"))
        || !(body.containsKey("searchWord"))) {
      throw new IllegalArgumentException("bag!");
    }
    var uri = new UriBuilder("/api/v2/snapshot/video/");
    uri.addPaths("/contents/search");
    uri.addQueries("q", body.getString("searchWord"));
    uri.addQueries("targets", "title");
    uri.addQueries("fields", "contentId");
    uri.addQueries("fields", "title");
    uri.addQueries("fields", "viewCounter");
    uri.addQueries("filters[viewCounter][gte]", "10000");
    uri.addQueries("_sort", "-viewCounter");
    uri.addQueries("_offset", "0");
    uri.addQueries("_limit", "3");
    uri.addQueries("_context", "apiguide");

    this.sessionId = LocalSession.create(body.getString("sessionId"));
    this.uri = uri.toString();
    this.method = HttpMethod.GET;
    this.host = "api.search.nicovideo.jp";
    this.ssl = true;
    this.port = 443;
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
    logger.debug(this.sessionId, json.encode());
    return json;
  }
}
