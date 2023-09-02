package jp.sample.vertx1.models.enumeration;

public enum StringEncode {
  UTF8("UTF-8"),
  SJIS("S-JIS");

  private String encode; // フィールドの定義

  private StringEncode(String e) { // コンストラクタの定義
    this.encode = e;
  }

  public String toString() {
    return this.encode;
  }
}
