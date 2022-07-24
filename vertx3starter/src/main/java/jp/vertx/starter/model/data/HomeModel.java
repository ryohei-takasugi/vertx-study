package jp.vertx.starter.model.data;

public class HomeModel {

  private int status = 999;

  public HomeModel() {}

  public int getModelStatus() {
    return this.status;
  }

  public void setModelStatus(int status) {
    this.status = status;
  }
}
