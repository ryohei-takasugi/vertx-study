package jp.vertx.starter.models.api;

import io.vertx.core.MultiMap;
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

public class RequestModel extends RequestEntity {

  /** logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(RequestModel.class);

  /** RequestEntry */
  private static final RequestEntity requestEntity = new RequestEntity();

  private final RoutingContext event;

  /** request json */
  public JsonObject request = new JsonObject();

  /** responce json */
  public JsonObject responce = new JsonObject();

  /**
   * contractor
   *
   * @param event
   */
  public RequestModel(RoutingContext event) {
    this.event = event;
    setEntity();
  }

  public JsonObject get() {
    try {
      LOGGER.debug(
          HomeResponceModel.class.getName() + " createRequest " + requestEntity.getAbsoluteURI());
      if (!requestEntity.getAbsoluteURI().isBlank()) {
        request.put("absoluteUrl", requestEntity.getAbsoluteURI());
      }
      LOGGER.debug(
          HomeResponceModel.class.getName() + " createRequest " + requestEntity.getRemoteAddress());
      if (!requestEntity.getRemoteAddress().isEmpty()) {
        request.put("remoteAddress", new JsonObject(requestEntity.getRemoteAddress()));
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + requestEntity.getUri());
      if (!requestEntity.getUri().isBlank()) {
        request.put("uri", requestEntity.getUri());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + requestEntity.getHost());
      if (!requestEntity.getHost().isBlank()) {
        request.put("host", requestEntity.getHost());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + requestEntity.getPath());
      if (!requestEntity.getPath().isBlank()) {
        request.put("path", requestEntity.getPath());
      }
      LOGGER.debug(
          HomeResponceModel.class.getName() + " createRequest " + requestEntity.getVersion());
      if (!requestEntity.getVersion().isEmpty()) {
        request.put("version", new JsonObject(requestEntity.getVersion()));
      }
      LOGGER.debug(
          HomeResponceModel.class.getName() + " createRequest " + requestEntity.getQuery());
      if (!requestEntity.getQuery().isBlank()) {
        request.put("query", requestEntity.getQuery());
      }
      LOGGER.debug(
          HomeResponceModel.class.getName() + " createRequest " + requestEntity.getParams());
      if (!requestEntity.getParams().isEmpty()) {
        request.put("params", new JsonObject(requestEntity.getParams()));
      }
      LOGGER.debug(
          HomeResponceModel.class.getName() + " createRequest " + requestEntity.getScheme());
      if (!requestEntity.getScheme().isBlank()) {
        request.put("scheme", requestEntity.getScheme());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + requestEntity.getUrl());
      if (!requestEntity.getUrl().isBlank()) {
        request.put("url", requestEntity.getUrl());
      }
      LOGGER.debug(
          HomeResponceModel.class.getName() + " createRequest " + requestEntity.getMethod());
      if (!requestEntity.getMethod().isBlank()) {
        request.put("method", requestEntity.getMethod());
      }
      LOGGER.debug(
          HomeResponceModel.class.getName() + " createRequest " + requestEntity.getHeaders());
      if (!requestEntity.getHeaders().isEmpty()) {
        request.put("header", new JsonObject(requestEntity.getHeaders()));
      }
      LOGGER.debug(
          HomeResponceModel.class.getName()
              + " createRequest "
              + requestEntity.getAcceptableContentType());
      if (!requestEntity.getAcceptableContentType().isBlank()) {
        request.put("acceptableContentType", requestEntity.getAcceptableContentType());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " createRequest " + requestEntity.getPort());
      if (requestEntity.getPort() > 0) {
        request.put("port", requestEntity.getPort());
      }
      request.put("isSSL", requestEntity.getIsSSL());
      return request;
    } catch (Throwable t) {
      LOGGER.error(HomeResponceModel.class.getName() + " createRequest " + t);
      event.fail(500, t);
      return new JsonObject();
    }
  }

  private void setEntity() {
    try {
      HttpServerRequest hsr = event.request();
      LOGGER.debug(HomeResponceModel.class.getName() + " set 1");
      if (!hsr.method().equals(null)) {
        requestEntity.setMethod(hsr.method());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 2");
      if (!hsr.absoluteURI().equals(null)) {
        requestEntity.setAbsoluteURI(hsr.absoluteURI());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 3");
      if (!hsr.host().equals(null)) {
        requestEntity.setHost(hsr.host());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 4");
      if (!hsr.path().equals(null)) {
        requestEntity.setPath(hsr.path());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 5");
      if (!hsr.version().equals(null)) {
        setVersion(hsr.version());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 6");
      if (hsr.uri().matches("[?]_")) {
        LOGGER.debug(HomeResponceModel.class.getName() + " set 6 : " + hsr.query());
        requestEntity.setQuery(hsr.query());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 7");
      if (!hsr.scheme().equals(null)) {
        requestEntity.setScheme(hsr.scheme());
      }
      LOGGER.debug(HomeResponceModel.class.getName() + " set 8");
      if (!hsr.uri().equals(null)) {
        requestEntity.setUri(hsr.uri());
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
      requestEntity.setIsSSL(hsr.isSSL());
    } catch (Throwable t) {
      LOGGER.debug(HomeResponceModel.class.getName() + " setEntity " + t);
      event.fail(500, t);
    }
  }

  private void setRemoteAddress(SocketAddress remoteAddress) {
    try {
      Map<String, Object> ra = new HashMap<String, Object>();
      ra.put("host", remoteAddress.host());
      ra.put("hostAddress", remoteAddress.hostAddress());
      ra.put("hostName", remoteAddress.hostName());
      ra.put("path", remoteAddress.path());
      ra.put("isDomainSocket", remoteAddress.isDomainSocket());
      ra.put("isInetSocket", remoteAddress.isInetSocket());
      ra.put("port", remoteAddress.port());
      requestEntity.setRemoteAddress(ra);
    } catch (Throwable t) {
      LOGGER.error(HomeResponceModel.class.getName() + " setRemoteAddress " + t);
      event.fail(500, t);
    }
  }

  private void setVersion(HttpVersion version) {
    try {
      Map<String, Object> v = new HashMap<String, Object>();
      v.put("alpnName", version.alpnName());
      v.put("name", version.name());
      requestEntity.setVersion(v);
    } catch (Throwable t) {
      LOGGER.error(HomeResponceModel.class.getName() + " setVersion " + t);
      event.fail(500, t);
    }
  }

  private void setParams(MultiMap params) {
    try {
      Map<String, Object> pa = new HashMap<String, Object>();
      params.forEach(
          p ->
              LOGGER.debug(
                  HomeResponceModel.class.getName()
                      + " setParams "
                      + p.getKey()
                      + " = "
                      + p.getValue()));
      params.forEach(p -> pa.put(p.getKey(), p.getValue()));
      requestEntity.setParams(pa);
    } catch (Throwable t) {
      LOGGER.error(HomeResponceModel.class.getName() + " setParams " + t);
      event.fail(500, t);
    }
  }

  private void setHeaders(MultiMap header) {
    try {
      Map<String, Object> hd = new HashMap<String, Object>();
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
          hd.put(h.getKey(), tmp);
        } else {
          hd.put(h.getKey(), h.getValue());
        }
      }
      requestEntity.setHeaders(hd);
    } catch (Throwable t) {
      LOGGER.error(HomeResponceModel.class.getName() + " setHeaders " + t);
      event.fail(500, t);
    }
  }
}
