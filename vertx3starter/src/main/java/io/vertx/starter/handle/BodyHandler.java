package io.vertx.starter.handle;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.starter.MainVerticle;
import io.vertx.starter.model.MySQLClient;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BodyHandler implements Handler<RoutingContext> {

  /**
   * インスタンスの生成
   * @return BodyHandlerインスタンス
   */
  public static Handler<RoutingContext> create() {
    return new BodyHandler();
  }

  /**
   * 適当な初期値
   * @FIXME 設定ファイルから読み込む
   */
  private final static Map<String, String> DEFAULT = new HashMap<String, String>(){{
    put("id", "1");
    put("name", "sato");
  }};

  /**
   * ロガー
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

  /**
   * handle処理（メイン処理）
   * @param event vert.x RoutingContext data
   * @return null
   */
  @Override
  public void handle(RoutingContext event) {
    LOGGER.info("start BodyHandler.handle");

    try {
      // お試し
      MySQLClient mySqlClient = new MySQLClient();
      mySqlClient.connection();  
    } catch (Exception e) {
      //LOGGER.error(null, e.getStackTrace(), e);
    }

    final Map<String, String> PARAM = getRequestParam(event);
    event.response().putHeader("content-type", event.getAcceptableContentType());
    event.response().end(getResponseJson(PARAM));

    LOGGER.info("end BodyHandler.handle");
  }

  /**
   * レスポンスとして返すJSONデータ
   * @param param
   * @return JSONデータ
   * @FIXME vert.xのjson系ライブラリで作成する
   */
  private String getResponseJson(Map<String, String> param) {
    return "{" +
              "\"status\": \"200\""+ "," +  
              " \"data\": " +
                "{" +
                  "\"message\": \"Hello World from Vert.x-Web!\"" + "," +
                  "\"id\": " + "\"" + param.get("id") + "\"" + "," +
                  "\"name\":" + "\"" + param.get("name") + "\"" +
              "}" +
          "}";
  }

  /**
   * URLからパラメータまたはデフォルト値から応答するデータのMapを取得する
   * @param event
   * @return requestParam
   */
  private Map<String, String> getRequestParam(RoutingContext event) {
    Map<String, String> requestParam = new HashMap<>();
    requestParam.put("id", getParam(event, "_id"));
    requestParam.put("name", getParam(event, "_name"));
    LOGGER.info("request map: " + requestParam.toString());
    return requestParam;
  }

  /**
   * URLにパラメータが設定されている場合その値を、設定されていない場合はデフォルト値を返す
   * @param event
   * @param key
   * @return value
   */
  private String getParam(RoutingContext event, String key) {
    if ( !event.request().getParam(key).isEmpty() ) {
      return DEFAULT.get(key);
    } else {
      return event.request().getParam(key).toString();
    }
  }
}
