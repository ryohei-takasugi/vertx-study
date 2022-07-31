package jp.vertx.starter.models.config;

public class ConfigEntity {
  private String name = "";
  private int id = 0;
  private int port = 0;

  /**
   * http listen port number.
   *
   * @return port number
   */
  public int getPort() {
    return this.port;
  }

  /**
   * user id
   *
   * @return id number
   */
  public int getDefaultId() {
    return this.id;
  }

  /**
   * user name
   *
   * @return name
   */
  public String getDefaultName() {
    return this.name;
  }


  /**
   * http listen port number.
   *
   * @return port number
   */
  public void setPort(int port) {
    this.port = port; 
  }

  /**
   * user id
   *
   * @return id number
   */
  public void setDefaultId(int id) {
    this.id = id;
  }

  /**
   * user name
   *
   * @return name
   */
  public void setDefaultName(String name) {
    this.name = name;
  }
}
