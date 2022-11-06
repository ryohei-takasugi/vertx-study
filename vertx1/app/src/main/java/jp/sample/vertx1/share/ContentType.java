package jp.sample.vertx1.share;

public enum ContentType {
  HTML("text/html"),
  JSON("application/json");

  private String header; // フィールドの定義

  private ContentType(String header) { // コンストラクタの定義
    this.header = header;
  }

  public String getString() {
    return this.header;
  }
}
