package jp.sample.starter.models.request;

import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpVersion;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.PatternSyntaxException;
import jp.sample.starter.utilities.mapConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestModel {

  /** logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(RequestModel.class);

  /** Entity Status */
  private String modelStatus = "none";

  /** headers */
  private Map<String, String> headers = new HashMap<>();

  /** Remote Address */
  private Map<String, String> remoteAddress = new HashMap<>();

  /** http version */
  private Map<String, String> version = new HashMap<>();

  /** params */
  private Map<String, String> params = new HashMap<>();

  /** cookies */
  private Map<String, String> cookies = new HashMap<>();

  /** request port */
  private int requestPort = 0;

  /** remote port */
  private int remotePort = 0;

  /** Remote Address Is Domain Socket */
  private boolean remoteAddressIsDomainSocket = false;

  /** Remote Address Is Inet Socket */
  private boolean remoteAddressIsInetSocket = false;

  /** is SSL */
  private boolean isSSL = false;

  /** Absolute URL */
  private String absoluteURI = "";

  /** URI */
  private String uri = "";

  /** Host */
  private String requestHost = "";

  /** Path */
  private String path = "";

  /** Query */
  private String query = "";

  /** Scheme */
  private String scheme = "";

  /** URL */
  private String url = "";

  /** Method */
  private HttpMethod method = HttpMethod.OPTIONS;

  /** Acceptable Content Type */
  private String acceptableContentType = "";

  /**
   * contractor
   *
   * @param event Vert.x RoutingContext
   * @return instance
   */
  public RequestModel(RoutingContext event) {
    try {
      HttpServerRequest hsr = event.request();

      if (hsr.method() != null) {
        this.method = hsr.method();
      }

      if (hsr.absoluteURI() != null) {
        this.absoluteURI = hsr.absoluteURI();
      }

      if (hsr.host() != null) {
        this.requestHost = hsr.host();
      }

      if (hsr.path() != null) {
        this.path = hsr.path();
      }

      if (hsr.version() != null) {
        this.version = setVersion(hsr.version());
      }

      if (hsr.query() != null && hsr.query().isEmpty()) {
        this.query = hsr.query();
      }

      if (hsr.scheme() != null) {
        this.scheme = hsr.scheme();
      }

      if (hsr.uri() != null) {
        this.uri = (hsr.uri());
      }

      if (hsr.params() != null && !hsr.params().isEmpty()) {
        this.params = setParams(hsr.params());
      }

      if (hsr.headers() != null && !hsr.headers().isEmpty()) {
        this.headers = setHeaders(hsr.headers());
        this.cookies = setCookie(hsr.headers());
      }

      // if (hsr.remoteAddress() != null) {
      //   this.remoteAddress = setRemoteAddressHost(hsr.remoteAddress());
      //   this.remoteAddressIsDomainSocket = hsr.remoteAddress().isDomainSocket();
      //   this.remoteAddressIsInetSocket = hsr.remoteAddress().isInetSocket();
      //   this.remotePort = hsr.remoteAddress().port();
      // }

      this.isSSL = hsr.isSSL();
      this.modelStatus = "success";
    } catch (Throwable t) {
      LOGGER.debug(RequestModel.class.getName() + " setEntity " + t);
      this.modelStatus = "error";
    }
  }

  /**
   * Entity Status.
   *
   * @return
   */
  public String getModelStatus() {
    return this.modelStatus;
  }

  /**
   * @return
   */
  public String getAbsoluteURI() {
    return this.absoluteURI;
  }

  /**
   * @return
   */
  public Map<String, String> getRemoteAddress() {
    return this.remoteAddress;
  }

  /**
   * @return
   */
  public Boolean getRemoteAddressIsDomainSocket() {
    return this.remoteAddressIsDomainSocket;
  }

  /**
   * @return
   */
  public Boolean getRemoteAddressIsInetSocket() {
    return this.remoteAddressIsInetSocket;
  }

  /**
   * @return
   */
  public String getUri() {
    return this.uri;
  }

  /**
   * @return
   */
  public String getRequestHost() {
    return this.requestHost;
  }

  /**
   * @return
   */
  public int getRequestPort() {
    return this.requestPort;
  }

  /**
   * @return
   */
  public int getRemotePort() {
    return this.remotePort;
  }

  /**
   * @return
   */
  public String getPath() {
    return this.path;
  }

  /**
   * @return
   */
  public Map<String, String> getVersion() {
    return this.version;
  }

  /**
   * @return
   */
  public String getQuery() {
    return this.query;
  }

  /**
   * @return
   */
  public Map<String, String> getParams() {
    return this.params;
  }

  /**
   * @return
   */
  public String getScheme() {
    return this.scheme;
  }

  /**
   * @return
   */
  public boolean getIsSSL() {
    return this.isSSL;
  }

  /**
   * @return
   */
  public String getUrl() {
    return this.url;
  }

  /**
   * @return
   */
  public HttpMethod getMethod() {
    return this.method;
  }

  /**
   * @return
   */
  public Map<String, String> getHeaders() {
    return this.headers;
  }

  /**
   * @return
   */
  public Map<String, String> getCookies() {
    return this.cookies;
  }

  /**
   * @return
   */
  public String getAcceptableContentType() {
    return this.acceptableContentType;
  }

  /**
   * Convert to JsonObject
   *
   * @return
   */
  public Future<JsonObject> toJsonObject() {
    Promise<JsonObject> promise = Promise.promise();
    JsonObject request = new JsonObject();

    try {
      request.put("remoteAddress", new mapConverter<String>(getRemoteAddress()).tJsonObject());
      request.put("remoteAddressIsDomainSocket", getRemoteAddressIsDomainSocket());
      request.put("remoteAddressIsInetSocket", getRemoteAddressIsInetSocket());
      request.put("remotePort", getRemotePort());
      request.put("requestHost", getRequestHost());
      request.put("requestPort", getRequestPort());
      request.put("method", getMethod().name());
      request.put("absoluteUrl", getAbsoluteURI());
      request.put("uri", getUri());
      request.put("url", getUrl());
      request.put("path", getPath());
      request.put("query", getQuery());
      request.put("params", new mapConverter<String>(getParams()).tJsonObject());
      request.put("header", new mapConverter<String>(getHeaders()).tJsonObject());
      request.put("acceptableContentType", getAcceptableContentType());
      request.put("isSSL", getIsSSL());
      request.put("scheme", getScheme());
      request.put("version", new mapConverter<String>(getVersion()).tJsonObject());
      promise.complete(request);
    } catch (Throwable t) {
      LOGGER.error(RequestModel.class.getName() + " createRequest " + t);
      promise.fail(t);
    }
    return promise.future();
  }

  // /**
  //  * @param remoteAddress
  //  * @return
  //  */
  // private Map<String, String> setRemoteAddressHost(SocketAddress remoteAddress) {
  //   Map<String, String> ra = new HashMap<String, String>();
  //   try {
  //     ra.put("host", remoteAddress.host());
  //     ra.put("hostAddress", remoteAddress.hostAddress());
  //     ra.put("hostName", remoteAddress.hostName());
  //     ra.put("path", remoteAddress.path());
  //   } catch (Throwable t) {
  //     LOGGER.error(RequestModel.class.getName() + " setRemoteAddress " + t);
  //     this.modelStatus = "error";
  //   }
  //   return ra;
  // }

  /**
   * @param version
   * @return
   */
  private Map<String, String> setVersion(HttpVersion version) throws NullPointerException {
    Map<String, String> v = new HashMap<String, String>();
    // v.put("alpnName", version.alpnName());
    v.put("name", version.name());
    return v;
  }

  /**
   * @param params
   * @return
   * @throws NullPointerException
   */
  private Map<String, String> setParams(MultiMap params) throws NullPointerException {
    Map<String, String> pa = new HashMap<String, String>();
    params.forEach(p -> pa.put(p.getKey(), p.getValue()));
    return pa;
  }

  /**
   * @param header
   * @return
   */
  private Map<String, String> setHeaders(MultiMap header)
      throws NullPointerException, ClassCastException {
    Map<String, String> head = new HashMap<String, String>();
    List<String> exclusion = new ArrayList<>();
    exclusion.add("Cookie");
    if (header.size() > 0) {
      for (Entry<String, String> h : header.entries()) {
        if (!exclusion.contains(h.getKey())) {
          head.put(h.getKey(), h.getValue());
        }
      }
    }
    return head;
  }

  private Map<String, String> setCookie(MultiMap header)
      throws PatternSyntaxException, IndexOutOfBoundsException {
    Map<String, String> cookies = new HashMap<String, String>();
    String c = header.get("Cookie");
    if (c != null && c.isEmpty()) {
      String[] list = c.split(";");
      for (int i = 0; i < list.length; i++) {
        String[] v = list[i].split("=", 2);
        cookies.put(v[0], v[1]);
      }
    }
    return cookies;
  }
}
