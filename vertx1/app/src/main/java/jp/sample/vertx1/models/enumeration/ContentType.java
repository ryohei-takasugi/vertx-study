package jp.sample.vertx1.models.enumeration;

public enum ContentType {
  HTML("text/html"),
  JSON("application/json");

  private String header; // フィールドの定義

  private ContentType(String header) { // コンストラクタの定義
    this.header = header;
  }

  public String toString() {
    return this.header;
  }
}
