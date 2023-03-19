package jp.sample.vertx1.share;

import io.vertx.ext.web.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogger {

  private final Logger LOGGER;
  private final Boolean DEBUG_ENABLED;
  private final Boolean TRACE_ENABLED;

  private MyLogger(Logger l) {
    LOGGER = l;
    DEBUG_ENABLED = l.isDebugEnabled();
    TRACE_ENABLED = l.isDebugEnabled() || l.isTraceEnabled();
  }

  public static MyLogger create(Class<?> clazz) {
    Logger logger = LoggerFactory.getLogger(clazz);
    return new MyLogger(logger);
  }

  private String getStringSessionId(Object s) {
    switch (s.getClass().getName()) {
      case "io.vertx.ext.web.Session":
        Session session = (Session) s;
        return session.id();
      case "java.lang.String":
        return s.toString();
      default:
        return "";
    }
  }

  /**
   * message: info
   *
   * @param msg
   */
  public void info(Object session, String msg) {
    final String strSessionId = getStringSessionId(session);
    if (strSessionId.isBlank()) {
      LOGGER.info("[{}] {}", strSessionId, msg);
    }
  }

  /**
   * message: warning
   *
   * @param msg
   */
  public void warn(Object session, String msg) {
    final String strSessionId = getStringSessionId(session);
    if (strSessionId.isBlank()) {
      LOGGER.warn("[{}] {}", strSessionId, msg);
    }
  }

  /**
   * message: error
   *
   * @param msg
   */
  public void error(Object session, String msg, Throwable t) {
    final String strSessionId = getStringSessionId(session);
    if (strSessionId.isBlank()) {
      LOGGER.error("[{}] {}", strSessionId, msg, t);
    }
  }

  /**
   * message: debug
   *
   * @param msg
   */
  public void debug(Object session, String msg) {
    final String strSessionId = getStringSessionId(session);
    if (strSessionId.isBlank()) {
      if (DEBUG_ENABLED) {
        LOGGER.debug("[{}] {}", strSessionId, msg);
      }
    }
  }

  /**
   * message: trace
   *
   * @param msg
   */
  public void trace(Object session, String msg) {
    final String strSessionId = getStringSessionId(session);
    if (strSessionId.isBlank()) {
      if (TRACE_ENABLED) {
        LOGGER.trace("[{}] {}", strSessionId, msg);
      }
    }
  }
}
