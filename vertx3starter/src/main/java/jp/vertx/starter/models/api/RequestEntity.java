package jp.vertx.starter.models.api;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class RequestEntity {

  /** logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(RequestEntity.class);

  /** headers */
  private Map<String, Object> headers = new HashMap<>();

  /** Remote Address */
  private Map<String, Object> remoteAddress = new HashMap<>();

  /** http version */
  private Map<String, Object> version = new HashMap<>();

  /** params */
  private Map<String, Object> params = new HashMap<>();

  /** request port */
  private int port = 0;

  /** is SSL */
  private boolean isSSL = false;

  /** Absolute URL */
  private String absoluteURI = "";

  /** URI */
  private String uri = "";

  /** Host */
  private String host = "";

  /** Path */
  private String path = "";

  /** Query */
  private String query = "";

  /** Scheme */
  private String scheme = "";

  /** URL */
  private String url = "";

  /** Method */
  private String method = "";

  /** Acceptable Content Type */
  private String acceptableContentType = "";

  /**
   * contractor
   *
   * @return
   */
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

  public void setAbsoluteURI(String absoluteURI) {
    this.absoluteURI = absoluteURI;
  }

  public void setRemoteAddress(Map<String, Object> remoteAddress) {
    this.remoteAddress = remoteAddress;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setVersion(Map<String, Object> version) {
    this.version = version;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public void setParams(Map<String, Object> params) {
    this.params = params;
  }

  public void setHeaders(Map<String, Object> header) {
    this.headers = header;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public void setIsSSL(boolean isSSL) {
    this.isSSL = isSSL;
  }

  public void setMethod(HttpMethod method) {
    this.method = method.toString();
  }
}
