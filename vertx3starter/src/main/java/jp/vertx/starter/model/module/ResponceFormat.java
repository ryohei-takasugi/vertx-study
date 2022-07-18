package jp.vertx.starter.model.module;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpVersion;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.RoutingContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ResponceFormat {

  /** logger */
  private Logger LOGGER = LoggerFactory.getLogger(ResponceFormat.class);

  /** main json */
  public JsonObject main = new JsonObject();

  public JsonObject request = new JsonObject();
  public JsonObject responce = new JsonObject();

  /** headers */
  private Map<String, Object> headers = new HashMap<>();

  private Map<String, Object> remoteAddress = new HashMap<>();
  private Map<String, Object> version = new HashMap<>();
  private Map<String, Object> params = new HashMap<>();
  private int port = 0;
  private boolean isSSL = false;
  private String absoluteURI = "";
  private String uri = "";
  private String host = "";
  private String path = "";
  private String query = "";
  private String scheme = "";
  private String url = "";
  private String method = "";
  private String acceptableContentType = "";

  public String getAbsoluteURI() {
    return this.absoluteURI;
  }

  public Map<String, Object> getRemoteAddress() {
    return this.remoteAddress;
  }

  public String getUri() {
    return this.uri;
  }

  public String getHost() {
    return this.host;
  }

  public int getPort() {
    return this.port;
  }

  public String getPath() {
    return this.path;
  }

  public Map<String, Object> getVersion() {
    return this.version;
  }

  public String getQuery() {
    return this.query;
  }

  public Map<String, Object> getParams() {
    return this.params;
  }

  public String getScheme() {
    return this.scheme;
  }

  public boolean getIsSSL() {
    return this.isSSL;
  }

  public String getUrl() {
    return this.url;
  }

  public String getMethod() {
    return this.method;
  }

  public Map<String, Object> getHeaders() {
    return this.headers;
  }

  public String getAcceptableContentType() {
    return this.acceptableContentType;
  }

  public JsonObject getRequest() {
    createRequest();
    return this.request;
  }

  public void setAbsoluteURI(String absoluteURI) {
    this.absoluteURI = absoluteURI;
  }

  public void setRemoteAddress(SocketAddress remoteAddress) {
    try {
      this.remoteAddress.put("host", remoteAddress.host());
      this.remoteAddress.put("hostAddress", remoteAddress.hostAddress());
      this.remoteAddress.put("hostName", remoteAddress.hostName());
      this.remoteAddress.put("path", remoteAddress.path());
      this.remoteAddress.put("isDomainSocket", remoteAddress.isDomainSocket());
      this.remoteAddress.put("isInetSocket", remoteAddress.isInetSocket());
      this.remoteAddress.put("port", remoteAddress.port());
    } catch (Exception e) {
      LOGGER.error(ResponceFormat.class.getName() + " setRemoteAddress " + e);
      this.remoteAddress.putAll(new HashMap<>());
    }
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setVersion(HttpVersion version) {
    try {
      this.version.put("alpnName", version.alpnName());
      this.version.put("name", version.name());
    } catch (Exception e) {
      LOGGER.error(ResponceFormat.class.getName() + " setVersion " + e);
      this.version.putAll(new HashMap<>());
    }
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public void setParams(MultiMap params) {
    LOGGER.error(ResponceFormat.class.getName() + " setParams " + params);
    params.forEach(
        p ->
            LOGGER.debug(
                ResponceFormat.class.getName()
                    + " setParams "
                    + p.getKey()
                    + " = "
                    + p.getValue()));
    params.forEach(p -> this.params.put(p.getKey(), p.getValue()));
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public void setIsSSL(boolean isSSL) {
    this.isSSL = isSSL;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setMethod(HttpMethod method) {
    this.method = method.toString();
  }

  public void setHeaders(MultiMap header) {
    try {
      for (Entry<String, String> h : header.entries()) {
        LOGGER.debug(
            ResponceFormat.class.getName() + " setHeaders " + h.getKey() + " = " + h.getValue());
        if (h.getKey().equals("Cookie") || h.getKey().equals("Cache-Control")) {
          String[] vList = h.getValue().split(";");
          JsonObject tmp = new JsonObject();
          for (int i = 0; i < vList.length; i++) {
            String[] v = vList[i].split("=", 2);
            tmp.put(v[0], v[1]);
          }
          this.headers.put(h.getKey(), tmp);
        } else {
          this.headers.put(h.getKey(), h.getValue());
        }
      }
    } catch (Exception e) {
      LOGGER.error(ResponceFormat.class.getName() + " setHeaders " + e);
    }
  }

  public void setAcceptableContentType(String acceptableContentType) {
    this.acceptableContentType = acceptableContentType;
  }

  private void createRequest() {
    try {
      LOGGER.debug(ResponceFormat.class.getName() + " createRequest " + this.absoluteURI);
      if (!absoluteURI.isBlank()) {
        request.put("absoluteUrl", absoluteURI);
      }
      LOGGER.debug(ResponceFormat.class.getName() + " createRequest " + this.remoteAddress);
      if (!remoteAddress.isEmpty()) {
        request.put("remoteAddress", new JsonObject(remoteAddress));
      }
      LOGGER.debug(ResponceFormat.class.getName() + " createRequest " + this.uri);
      if (!uri.isBlank()) {
        request.put("uri", uri);
      }
      LOGGER.debug(ResponceFormat.class.getName() + " createRequest " + this.host);
      if (!host.isBlank()) {
        request.put("host", host);
      }
      LOGGER.debug(ResponceFormat.class.getName() + " createRequest " + this.path);
      if (!path.isBlank()) {
        request.put("path", path);
      }
      LOGGER.debug(ResponceFormat.class.getName() + " createRequest " + this.version);
      if (!version.isEmpty()) {
        request.put("version", new JsonObject(version));
      }
      LOGGER.debug(ResponceFormat.class.getName() + " createRequest " + this.query);
      if (!query.isBlank()) {
        request.put("query", query);
      }
      LOGGER.debug(ResponceFormat.class.getName() + " createRequest " + this.params);
      if (!params.isEmpty()) {
        request.put("params", new JsonObject(params));
      }
      LOGGER.debug(ResponceFormat.class.getName() + " createRequest " + this.scheme);
      if (!scheme.isBlank()) {
        request.put("scheme", scheme);
      }
      LOGGER.debug(ResponceFormat.class.getName() + " createRequest " + this.url);
      if (!url.isBlank()) {
        request.put("url", url);
      }
      LOGGER.debug(ResponceFormat.class.getName() + " createRequest " + this.method);
      if (!method.isBlank()) {
        request.put("method", method);
      }
      LOGGER.debug(ResponceFormat.class.getName() + " createRequest " + this.headers);
      if (!headers.isEmpty()) {
        request.put("header", new JsonObject(headers));
      }
      LOGGER.debug(ResponceFormat.class.getName() + " createRequest " + this.acceptableContentType);
      if (!acceptableContentType.isBlank()) {
        request.put("acceptableContentType", acceptableContentType);
      }
      LOGGER.debug(ResponceFormat.class.getName() + " createRequest " + this.port);
      if (port > 0) {
        request.put("port", port);
      }
      request.put("isSSL", isSSL);
    } catch (Exception e) {
      LOGGER.error(ResponceFormat.class.getName() + " createRequest " + e);
    }
  }

  public JsonObject get() {
    createRequest();
    this.main.put("request", request);
    this.main.put("responce", responce);
    return this.main;
  }

  public void set(RoutingContext event) {
    HttpServerRequest hsr = event.request();
    LOGGER.debug(ResponceFormat.class.getName() + " set 1");
    if (!hsr.method().equals(null)) {
      setMethod(hsr.method());
    }
    LOGGER.debug(ResponceFormat.class.getName() + " set 2");
    if (!hsr.absoluteURI().equals(null)) {
      setAbsoluteURI(hsr.absoluteURI());
    }
    LOGGER.debug(ResponceFormat.class.getName() + " set 3");
    if (!hsr.host().equals(null)) {
      setHost(hsr.host());
    }
    LOGGER.debug(ResponceFormat.class.getName() + " set 4");
    if (!hsr.path().equals(null)) {
      setPath(hsr.path());
    }
    LOGGER.debug(ResponceFormat.class.getName() + " set 5");
    if (!hsr.version().equals(null)) {
      setVersion(hsr.version());
    }
    LOGGER.debug(ResponceFormat.class.getName() + " set 6");
    if (hsr.uri().matches("[?]_")) {
      LOGGER.debug(ResponceFormat.class.getName() + " set 6 : " + hsr.query());
      setQuery(hsr.query());
    }
    LOGGER.debug(ResponceFormat.class.getName() + " set 7");
    if (!hsr.scheme().equals(null)) {
      setScheme(hsr.scheme());
    }
    LOGGER.debug(ResponceFormat.class.getName() + " set 8");
    if (!hsr.uri().equals(null)) {
      setUri(hsr.uri());
    }
    LOGGER.debug(ResponceFormat.class.getName() + " set 9");
    if (!hsr.params().isEmpty()) {
      LOGGER.debug(ResponceFormat.class.getName() + " set 9 : " + hsr.params());
      setParams(hsr.params());
    }
    LOGGER.debug(ResponceFormat.class.getName() + " set 10");
    if (!hsr.headers().isEmpty()) {
      setHeaders(hsr.headers());
    }
    LOGGER.debug(ResponceFormat.class.getName() + " set 11");
    if (!hsr.remoteAddress().equals(null)) {
      setRemoteAddress(hsr.remoteAddress());
    }
    LOGGER.debug(ResponceFormat.class.getName() + " set 12");
    setIsSSL(hsr.isSSL());
  }
}
