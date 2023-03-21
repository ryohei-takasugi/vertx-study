package jp.sample.vertx1.share.model;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Session;
import jp.sample.vertx1.share.MyLogger;

public class CallEventBusModel implements IEventBusModel {
  /** Logger */
  private static final MyLogger LOGGER = MyLogger.create(CallEventBusModel.class);

  private final String sessionId;
  private final HttpMethod method;
  private final String host;
  private final int port;
  private final boolean ssl;
  private final String uri;

  public static JsonObject createRequest(Session session) {
    var request = new JsonObject();
    request.put("sessionId", session.id());
    request.put("method", HttpMethod.GET.toString());
    request.put("host", "api.search.nicovideo.jp");
    request.put("port", 443);
    request.put("ssl", true);
    request.put(
        "uri",
        "/api/v2/snapshot/video/contents/search?q=%E5%88%9D%E9%9F%B3%E3%83%9F%E3%82%AF&targets=title&fields=contentId,title,viewCounter&filters[viewCounter][gte]=10000&_sort=-viewCounter&_offset=0&_limit=3&_context=apiguide");
    LOGGER.debug(session, request.encode());
    return request;
  }

  public CallEventBusModel(JsonObject body) {
    if (body.isEmpty()) {
      new IllegalArgumentException();
    }
    if (body.containsKey("sessionId")) {
      this.sessionId = body.getString("sessionId");
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

  public String sessionId() {
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
