package jp.sample.vertx1.modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.ext.web.Session;

public class HandlerLogger {

  private final Logger logger;
  private final Boolean isDebugEnabled;
  private final Boolean isTraceEnabled;

  private HandlerLogger(Logger l) {
    logger = l;
    isDebugEnabled = l.isDebugEnabled();
    isTraceEnabled = l.isDebugEnabled() || l.isTraceEnabled();
  }

  public static HandlerLogger create(Class<?> clazz) {
    Logger logger = LoggerFactory.getLogger(clazz);
    return new HandlerLogger(logger);
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
      logger.info("[Blank] {}", msg);
    } else {
      logger.info("[{}] {}", strSessionId, msg);
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
      logger.warn("[Blank] {}", msg);
    } else {
      logger.warn("[{}] {}", strSessionId, msg);
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
      logger.error("[Blank] {}", msg, t);
    } else {
      logger.error("[{}] {}", strSessionId, msg, t);
    }
  }

  /**
   * message: debug
   *
   * @param msg
   */
  public void debug(Object session, String msg) {
    final String strSessionId = getStringSessionId(session);
    if (isDebugEnabled) {
      if (strSessionId.isBlank()) {
        logger.debug("[Blank] {}", msg);
      } else {
        logger.debug("[{}] {}", strSessionId, msg);
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
    if (isTraceEnabled) {
      if (strSessionId.isBlank()) {
        logger.trace("[Blank] {}", msg);
      } else {
        logger.trace("[{}] {}", strSessionId, msg);
      }
    }
  }
}
