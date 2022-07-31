package jp.vertx.starter.models.data;

public class HomeModel extends HomeEntity {

  private int status = 999;

  public HomeModel() {}

  public int getModelStatus() {
    return this.status;
  }

  public void setModelStatus(int status) {
    this.status = status;
  }
}
