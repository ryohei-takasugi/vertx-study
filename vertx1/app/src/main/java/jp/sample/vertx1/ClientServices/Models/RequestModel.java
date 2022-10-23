package jp.sample.vertx1.ClientServices.Models;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestModel {

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(RequestModel.class);

  private String host = null;
  private int port = 0;
  private String requestURI = null;
  private Map<String, String> queries = new HashMap<>();

  /**
   * Set the host to request in Web client
   *
   * @param host
   * @return this
   */
  public RequestModel setHost(String host) {
    if (this.host == null) {
      this.host = host;
    } else {
      LOGGER.warn("already set host");
    }
    return this;
  }

  /**
   * Set the port to request in Web client
   *
   * @param port
   * @return this
   */
  public RequestModel setPort(int port) {
    if (this.port == 0) {
      this.port = port;
    } else {
      LOGGER.warn("already set port");
    }
    return this;
  }

  /**
   * Set the requestURI to request in Web client
   *
   * @param requestURI
   * @return this
   */
  public RequestModel setRequestURI(String requestURI) {
    if (this.requestURI == null) {
      this.requestURI = requestURI;
    } else {
      LOGGER.warn("already set requestURI");
    }
    return this;
  }

  /**
   * Set the query(key and value) to request in Web client
   *
   * @param queryKey
   * @param queryValue
   * @return this
   */
  public RequestModel setQuery(String queryKey, String queryValue) {
    if (!this.queries.containsKey(queryKey)) {
      this.queries.put(queryKey, queryValue);
    } else {
      LOGGER.warn("already set query");
    }
    return this;
  }

  /**
   * Get the host to request in web client
   *
   * @return host
   */
  public String getHost() {
    return host;
  }

  /**
   * Get the port to request in web client
   *
   * @return port
   */
  public int getPort() {
    return port;
  }

  /**
   * Get the requestURI to request in web client
   *
   * @return requestURI
   */
  public String getRequestURI() {
    return requestURI;
  }

  /**
   * Get the Query to request in web client
   *
   * @return Query
   */
  public Map<String, String> getQueries() {
    return queries;
  }

  /**
   * Get the Query size to request in web client
   *
   * @return Query size
   */
  public int getQuerySize() {
    return queries.size();
  }
}
