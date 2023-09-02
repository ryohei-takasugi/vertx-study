package jp.sample.vertx1.modules;

import io.vertx.ext.web.Session;
import jp.sample.vertx1.models.eventbus.LocalSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    } else if (s instanceof LocalSession) {
      LocalSession session = (LocalSession) s;
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
      logger.info("[sessionId: Blank] {}", msg);
    } else {
      logger.info("[sessionId: {}] {}", strSessionId, msg);
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
      logger.warn("[sessionId: Blank] {}", msg);
    } else {
      logger.warn("[sessionId: {}] {}", strSessionId, msg);
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
      logger.error("[sessionId: Blank] {}", msg, t);
    } else {
      logger.error("[sessionId: {}] {}", strSessionId, msg, t);
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
        logger.debug("[sessionId: Blank] {}", msg);
      } else {
        logger.debug("[sessionId: {}] {}", strSessionId, msg);
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
        logger.trace("[sessionId: Blank] {}", msg);
      } else {
        logger.trace("[sessionId: {}] {}", strSessionId, msg);
      }
    }
  }
}
