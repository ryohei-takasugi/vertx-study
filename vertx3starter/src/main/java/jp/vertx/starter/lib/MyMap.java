package jp.vertx.starter.lib;

import io.vertx.core.json.JsonObject;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyMap<K, V> implements Map<K, V> {

  private Map<K, V> map;

  public MyMap() {}

  public MyMap(Map<K, V> map) {
    this.map = map;
  }

  public JsonObject toJson() {
    JsonObject json;
    try {
      Map<String, Object> map = (Map<String, Object>) this.map;
      json = new JsonObject(map);
    } catch (Exception e) {
      json = new JsonObject();
    }
    return json;
  }

  @Override
  public int size() {
    return this.map.size();
  }

  @Override
  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return this.map.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return this.map.containsValue(value);
  }

  @Override
  public V get(Object key) {
    return (V) this.map.get(key);
  }

  @Override
  public V put(K key, V value) {
    return this.map.put(key, value);
  }

  @Override
  public V remove(Object key) {
    return this.map.remove(key);
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    this.map.putAll(m);
  }

  @Override
  public void clear() {
    this.map.clear();
  }

  @Override
  public Set<K> keySet() {
    return this.map.keySet();
  }

  @Override
  public Collection<V> values() {
    return this.map.values();
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return this.map.entrySet();
  }
}
