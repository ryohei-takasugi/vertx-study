package jp.vertx.starter.models.api;

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

public class RequestModel {

  /** logger */
  private Logger LOGGER = LoggerFactory.getLogger(RequestModel.class);

  /** main json */
  public JsonObject main = new JsonObject();

  public JsonObject request = new JsonObject();
  public JsonObject responce = new JsonObject();

  private boolean exption = true;

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

  public RequestModel(RoutingContext event) {
    set(event);
  }

  public JsonObject get() {
    try {
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + this.absoluteURI);
      if (!absoluteURI.isBlank()) {
        request.put("absoluteUrl", absoluteURI);
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + this.remoteAddress);
      if (!remoteAddress.isEmpty()) {
        request.put("remoteAddress", new JsonObject(remoteAddress));
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + this.uri);
      if (!uri.isBlank()) {
        request.put("uri", uri);
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + this.host);
      if (!host.isBlank()) {
        request.put("host", host);
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + this.path);
      if (!path.isBlank()) {
        request.put("path", path);
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + this.version);
      if (!version.isEmpty()) {
        request.put("version", new JsonObject(version));
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + this.query);
      if (!query.isBlank()) {
        request.put("query", query);
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + this.params);
      if (!params.isEmpty()) {
        request.put("params", new JsonObject(params));
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + this.scheme);
      if (!scheme.isBlank()) {
        request.put("scheme", scheme);
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + this.url);
      if (!url.isBlank()) {
        request.put("url", url);
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + this.method);
      if (!method.isBlank()) {
        request.put("method", method);
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + this.headers);
      if (!headers.isEmpty()) {
        request.put("header", new JsonObject(headers));
      }
      LOGGER.debug(
          HomeResponceModel.class.getName() + " createRequest " + this.acceptableContentType);
      if (!acceptableContentType.isBlank()) {
        request.put("acceptableContentType", acceptableContentType);
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + this.port);
      if (port > 0) {
        request.put("port", port);
      }
      request.put("isSSL", isSSL);
      return request;
    } catch (Exception e) {
      LOGGER.error(HomeResponceModel.class.getName() + " createRequest " + e);
      setException(true);
      return new JsonObject();
    }
  }

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

  public boolean getException() {
    return this.exption;
  }

  private void setException(boolean exption) {
    this.exption = exption;
  }

  private void setAbsoluteURI(String absoluteURI) {
    this.absoluteURI = absoluteURI;
  }

  private void setRemoteAddress(SocketAddress remoteAddress) {
    try {
      this.remoteAddress.put("host", remoteAddress.host());
      this.remoteAddress.put("hostAddress", remoteAddress.hostAddress());
      this.remoteAddress.put("hostName", remoteAddress.hostName());
      this.remoteAddress.put("path", remoteAddress.path());
      this.remoteAddress.put("isDomainSocket", remoteAddress.isDomainSocket());
      this.remoteAddress.put("isInetSocket", remoteAddress.isInetSocket());
      this.remoteAddress.put("port", remoteAddress.port());
    } catch (Exception e) {
      LOGGER.error(HomeResponceModel.class.getName() + " setRemoteAddress " + e);
      this.remoteAddress.putAll(new HashMap<>());
    }
  }

  private void setUri(String uri) {
    this.uri = uri;
  }

  private void setHost(String host) {
    this.host = host;
  }

  private void setPath(String path) {
    this.path = path;
  }

  private void setVersion(HttpVersion version) {
    try {
      this.version.put("alpnName", version.alpnName());
      this.version.put("name", version.name());
    } catch (Exception e) {
      LOGGER.error(HomeResponceModel.class.getName() + " setVersion " + e);
      this.version.putAll(new HashMap<>());
    }
  }

  private void setQuery(String query) {
    this.query = query;
  }

  private void setParams(MultiMap params) {
    params.forEach(
        p ->
            LOGGER.debug(
                HomeResponceModel.class.getName()
                    + " setParams "
                    + p.getKey()
                    + " = "
                    + p.getValue()));
    params.forEach(p -> this.params.put(p.getKey(), p.getValue()));
  }

  private void setScheme(String scheme) {
    this.scheme = scheme;
  }

  private void setIsSSL(boolean isSSL) {
    this.isSSL = isSSL;
  }

  private void setMethod(HttpMethod method) {
    this.method = method.toString();
  }

  private void setHeaders(MultiMap header) {
    try {
      for (Entry<String, String> h : header.entries()) {
        LOGGER.debug(
            HomeResponceModel.class.getName() + " setHeaders " + h.getKey() + " = " + h.getValue());
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
      LOGGER.error(HomeResponceModel.class.getName() + " setHeaders " + e);
    }
  }

  private void set(RoutingContext event) {
    try {
      HttpServerRequest hsr = event.request();
      LOGGER.debug(HomeResponceModel.class.getName() + " set 1");
      if (!hsr.method().equals(null)) {
        setMethod(hsr.method());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 2");
      if (!hsr.absoluteURI().equals(null)) {
        setAbsoluteURI(hsr.absoluteURI());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 3");
      if (!hsr.host().equals(null)) {
        setHost(hsr.host());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 4");
      if (!hsr.path().equals(null)) {
        setPath(hsr.path());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 5");
      if (!hsr.version().equals(null)) {
        setVersion(hsr.version());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 6");
      // if (hsr.uri().matches("[?]_")) {
      LOGGER.debug(HomeResponceModel.class.getName() + " set 6 : " + hsr.query());
      setQuery(hsr.query());
      // }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 7");
      if (!hsr.scheme().equals(null)) {
        setScheme(hsr.scheme());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 8");
      if (!hsr.uri().equals(null)) {
        setUri(hsr.uri());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 9");
      if (!hsr.params().isEmpty()) {
        LOGGER.debug(HomeResponceModel.class.getName() + " set 9 : " + hsr.params());
        setParams(hsr.params());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 10");
      if (!hsr.headers().isEmpty()) {
        setHeaders(hsr.headers());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 11");
      if (!hsr.remoteAddress().equals(null)) {
        setRemoteAddress(hsr.remoteAddress());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 12");
      setIsSSL(hsr.isSSL());
      setException(false);
    } catch (Exception e) {
      setException(true);
    }
  }
}
