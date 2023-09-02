package jp.sample.vertx1.handlers.main;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpVersion;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.RoutingContext;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerHandler implements Handler<RoutingContext> {

  private static final Logger logger = LoggerFactory.getLogger(LoggerHandler.class);

  protected LoggerHandler() {}

  @Override
  public void handle(RoutingContext context) {
    // common logging data
    long timestamp = System.currentTimeMillis();
    String remoteClient = getClientAddress(context.request().remoteAddress());
    HttpMethod method = context.request().method();
    String uri = context.request().uri();
    HttpVersion version = context.request().version();
    String sessionId = context.session().id();

    context.addBodyEndHandler(
        v -> log(context, timestamp, remoteClient, version, method, uri, sessionId));

    context.next();
  }

  private String getClientAddress(SocketAddress inetSocketAddress) {
    if (inetSocketAddress == null) {
      return null;
    }
    return inetSocketAddress.host();
  }

  private void log(
      RoutingContext context,
      long timestamp,
      String remoteClient,
      HttpVersion version,
      HttpMethod method,
      String uri,
      String sessionId) {
    HttpServerRequest request = context.request();
    long contentLength = 0;
    contentLength = request.response().bytesWritten();
    String versionFormatted = "-";
    switch (version) {
      case HTTP_1_0:
        versionFormatted = "HTTP/1.0";
        break;
      case HTTP_1_1:
        versionFormatted = "HTTP/1.1";
        break;
      case HTTP_2:
        versionFormatted = "HTTP/2.0";
        break;
    }

    final MultiMap headers = request.headers();
    int status = request.response().getStatusCode();
    String message = null;
    String referrer =
        headers.contains("referrer") ? headers.get("referrer") : headers.get("referer");
    String userAgent = request.headers().get("user-agent");
    referrer = referrer == null ? "-" : referrer;
    userAgent = userAgent == null ? "-" : userAgent;
    Timestamp ts = new Timestamp(timestamp);

    message =
        String.format(
            "[sessionId: %s] remoteClient:%s \"%s %s %s\" status:%d Content-Length:%d referrer:\"%s\" userAgent:\"%s\" ExecutionTime:\"%s\"ms",
            sessionId,
            remoteClient,
            method,
            uri,
            versionFormatted,
            status,
            contentLength,
            referrer,
            userAgent,
            (System.currentTimeMillis() - timestamp));

    doLog(status, message);
  }

  protected void doLog(int status, String message) {
    if (status >= 500) {
      logger.error(message);
    } else if (status >= 400) {
      logger.warn(message);
    } else {
      logger.info(message);
    }
  }
}
