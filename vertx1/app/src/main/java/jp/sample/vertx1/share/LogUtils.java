package jp.sample.vertx1.share;

import io.vertx.ext.web.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

  public final Logger LOGGER;

  public LogUtils(Class<?> clazz) {
    LOGGER = LoggerFactory.getLogger(clazz);
  }

  /**
   * message: info
   *
   * @param msg
   */
  public void info(Session session, String msg) {
    LOGGER.info("[{}] {}", session.id(), msg);
  }

  /**
   * message: info
   *
   * @param msg
   */
  public void info(String sessionId, String msg) {
    LOGGER.info("[{}] {}", sessionId, msg);
  }

  /**
   * message: warning
   *
   * @param msg
   */
  public void warn(Session session, String msg) {
    LOGGER.warn("[{}] {}", session.id(), msg);
  }

  /**
   * message: warning
   *
   * @param msg
   */
  public void warn(String sessionId, String msg) {
    LOGGER.warn("[{}] {}", sessionId, msg);
  }

  /**
   * message: error
   *
   * @param msg
   */
  public void error(Session session, String msg, Throwable t) {
    LOGGER.error("[{}] {}", session.id(), msg);
  }

  /**
   * message: error
   *
   * @param msg
   */
  public void error(String sessionId, String msg, Throwable t) {
    LOGGER.error("[{}] {}", sessionId, msg);
  }

  /**
   * message: debug
   *
   * @param msg
   */
  public void debug(Session session, String msg) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("[{}] {}", session.id(), msg);
    }
  }

  /**
   * message: debug
   *
   * @param msg
   */
  public void debug(String sessionId, String msg) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("[{}] {}", sessionId, msg);
    }
  }

  /**
   * message: trace
   *
   * @param msg
   */
  public void trace(Session session, String msg) {
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace("[{}] {}", session.id(), msg);
    }
  }

  /**
   * message: trace
   *
   * @param msg
   */
  public void trace(String sessionId, String msg) {
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace("[{}] {}", sessionId, msg);
    }
  }
}
