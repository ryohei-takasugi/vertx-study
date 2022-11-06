package jp.sample.vertx1.ClientServices.Models;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

/**
 * This is the data model for video search results on Nico Nico Douga.
 *
 * <p>success responce
 *
 * <p>
 *
 * <table>
 *   <thead>
 * .   <tr>
 *       <th>フィールド名</th>
 *       <th>型</th>
 *       <th>例</th>
 *       <th>説明</th>
 *     </tr>
 *   </thead>
 *   <tbody>
 *     <tr>
 *       <th>meta</th>
 *       <th>object</th>
 *       <th>-</th>
 *       <th>レスポンスのメタ情報フィールド</th>
 *     </tr>
 *     <tr>
 *       <th>meta.status</th>
 *       <th>integer</th>
 *       <th>200</th>
 *       <th>HTTPステータス（成功の場合200）</th>
 *     </tr>
 *     <tr>
 *       <th>meta.id</th>
 *       <th>object</th>
 *       <th>594513df-85ea-4122-9859-f4ec2701cacf</th>
 *       <th>リクエストID</th>
 *     </tr>
 *     <tr>
 *       <th>meta.totalCount</th>
 *       <th>integer</th>
 *       <th>12673</th>
 *       <th>ヒット件数</th>
 *     </tr>
 *     <tr>
 *       <th>data</th>
 *       <th>array</th>
 *       <th>-</th>
 *       <th>ヒットしたコンテンツ。要素の内容はパラメータfieldsによって異なります</th>
 *     </tr>
 *   </tbody>
 * </table>
 *
 * error responce
 *
 * <p>
 *
 * <table>
 *   <thead>
 * .   <tr>
 *       <th>フィールド名</th>
 *       <th>型</th>
 *       <th>例</th>
 *       <th>説明</th>
 *     </tr>
 *   </thead>
 *   <tbody>
 *     <tr>
 *       <th>meta</th>
 *       <th>object</th>
 *       <th>-</th>
 *       <th>レスポンスのメタ情報フィールド</th>
 *     </tr>
 *     <tr>
 *       <th>meta.status</th>
 *       <th>integer</th>
 *       <th>400</th>
 *       <th>HTTPステータス（エラーの場合200以外）</th>
 *     </tr>
 *     <tr>
 *       <th>meta.errorCode</th>
 *       <th>string</th>
 *       <th>QUERY_PARSE_ERROR</th>
 *       <th>エラーコード</th>
 *     </tr>
 *     <tr>
 *       <th>meta.errorMessage</th>
 *       <th>string</th>
 *       <th>query parse error</th>
 *       <th>エラー内容</th>
 *     </tr>
 *   </tbody>
 * </table>
 *
 * @see https://site.nicovideo.jp/search-api-docs/snapshot
 *     <p>
 */
public class NicoNicoModel {

  /** Logger */
  // private static final Logger LOGGER = LoggerFactory.getLogger(NicoNicoModel.class);

  private final String id;

  private final Integer totalCount;
  private final Integer status;
  private Map<String, Object> entities = new HashMap<String, Object>();

  public NicoNicoModel(JsonObject responce) {
    if (responce == null || responce.isEmpty()) {
      throw new IllegalArgumentException("responce data is null");
    }

    JsonObject body = responce.getJsonObject("body");
    if (body == null || body.isEmpty()) {
      throw new IllegalArgumentException("body data is null");
    }

    JsonObject meta = body.getJsonObject("meta");
    if (meta == null || meta.isEmpty()) {
      throw new IllegalArgumentException("meta data is null");
    }

    JsonArray data = body.getJsonArray("data");
    if (data == null || data.isEmpty()) {
      throw new IllegalArgumentException("data data is null");
    }

    this.status = responce.getInteger("status");
    this.id = meta.getString("id");
    this.totalCount = meta.getInteger("totalCount");

    List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
    if (data.getList() instanceof List) {
      @SuppressWarnings("unchecked")
      List<JsonObject> listJson = body.getJsonArray("data").getList();

      for (int i = 0; i < listJson.size(); i++) {
        if (listJson.get(i) instanceof JsonObject) {
          Map<String, Object> d = listJson.get(i).getMap();
          listMap.add(d);
        }
      }
      this.entities.put("data", listMap);
    }
  }

  /**
   * Response id.
   *
   * @return id
   */
  public String id() {
    return id;
  }

  /**
   * The total number of views for all videos included in the response
   *
   * @return total count number
   */
  public Integer totalCount() {
    return totalCount;
  }

  /**
   * Response status.
   *
   * @return status code
   */
  public Integer status() {
    return status;
  }

  /**
   * All videos included in the response. The Key name is {@code "data"}. The Values is video list.
   * The video list is of type List&lt;Map&lt;String, Object&gt;&gt;. Map&lt;String, Object&gt; type
   * contains {@code contentId, title and viewCounter} for each video.
   *
   * @return data map
   */
  public Map<String, Object> entities() {
    return entities;
  }
}
