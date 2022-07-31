package jp.vertx.starter.models.api;

public class HomeResponceEntity {

  /** default id */
  private int id = 1;

  /** default name */
  private String name = "sato";

  /**
   * get id
   *
   * @param null
   * @return id
   */
  public int getId() {
    return this.id;
  }

  /**
   * set id
   *
   * @param null
   * @return null
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * get name
   *
   * @param null
   * @return name
   */
  public String getName() {
    return this.name;
  }

  /**
   * set name
   *
   * @param null
   * @return null
   */
  public void setName(String name) {
    this.name = name;
  }
}
