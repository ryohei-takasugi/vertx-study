package jp.sample.vertx1.ClientServices.Models;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

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

  public String id() {
    return id;
  }

  public Integer totalCount() {
    return totalCount;
  }

  public Integer status() {
    return status;
  }

  public Map<String, Object> entities() {
    return entities;
  }
}
