package jp.sample.vertx1.modules;

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
    if (s instanceof Session) {
      Session session = (Session) s;
      return session.id();
    } else if (s instanceof String) {
      return s.toString();
    } else {
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
      LOGGER.info("[Blank] {}", msg);
    } else {
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
      LOGGER.warn("[Blank] {}", msg);
    } else {
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
      LOGGER.error("[Blank] {}", msg, t);
    } else {
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
    if (DEBUG_ENABLED) {
      if (strSessionId.isBlank()) {
        LOGGER.debug("[Blank] {}", msg);
      } else {
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
    if (TRACE_ENABLED) {
      if (strSessionId.isBlank()) {
        LOGGER.trace("[Blank] {}", msg);
      } else {
        LOGGER.trace("[{}] {}", strSessionId, msg);
      }
    }
  }
}
