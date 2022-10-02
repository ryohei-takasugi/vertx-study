package jp.sample.starter.utilities;

import io.vertx.core.json.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class mapConverter<T> {

  private Map<String, T> map;

  public mapConverter(Map<String, T> map) {
    this.map = map;
  }

  public Map<String, Object> toObjectMap() throws NullPointerException, ClassCastException {
    Map<String, Object> object = new HashMap<>();
    for (Entry e : this.map.entrySet()) {
      object.put((String) e.getKey(), (Object) e.getValue());
    }
    return object;
  }

  public JsonObject tJsonObject() {
    Map<String, Object> map = toObjectMap();
    return new JsonObject(map);
  }
}
