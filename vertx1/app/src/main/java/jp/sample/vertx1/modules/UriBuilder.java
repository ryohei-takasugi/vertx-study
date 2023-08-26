package jp.sample.vertx1.modules;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** URIを生成します。 */
public class UriBuilder {
  private static final String QUERY_DELIMITER = "&";
  private static final String QUERY_STERT = "?";
  private static final String EQUAL = "=";
  private static final String PATH_DELIMITER = "/";
  private final String baseUrl;
  private LinkedHashSet<HashMap<String, String>> queries =
      new LinkedHashSet<HashMap<String, String>>();
  private LinkedHashSet<String> paths = new LinkedHashSet<String>();

  /** このクラスを生成します。 <div> ベースとなるURLは登録しません。 すべてのパスをURLエンコードします。 </div> */
  public UriBuilder() {
    this.baseUrl = new String();
  }

  /**
   * このクラスを生成します。 <div> 引数のURLをベースとなるパスに追加します。 ベースとなるパスはURLエンコードしません。 </div>
   *
   * @param baseUrl ベースURL
   */
  public UriBuilder(final String baseUrl) {
    if (baseUrl.startsWith(PATH_DELIMITER)) {
      this.baseUrl = baseUrl;
    } else {
      this.baseUrl = new String();
    }
  }

  /**
   * ベースURLにパスを追加します。 追加するパスはエンコードして追加します。
   *
   * @param addUrl
   * @return this
   */
  public UriBuilder addPaths(final String addUrl) {
    if (addUrl == null || addUrl.isEmpty()) {
      return this;
    } else {
      this.paths.add(addUrl);
      return this;
    }
  }

  /**
   * クエリを追加します。 Value値はURLエンコードします。
   *
   * @param key クエリ Key
   * @param value クエリ Value
   * @return this
   */
  public UriBuilder addQueries(final String key, final String value) {
    if (key == null || key.isEmpty() || value == null || value.isEmpty()) {
      return this;
    } else {
      var exists =
          this.queries.stream().filter(map -> map.containsKey(key)).findFirst().orElse(null);
      if (exists == null || exists.isEmpty()) {
        HashMap<String, String> keyValue = new HashMap<String, String>();
        keyValue.put(key, value);
        this.queries.add(keyValue);
      } else {
        String existValue = exists.get(key);
        exists.put(key, String.format("%s,%s", existValue, value));
      }
    }

    return this;
  }

  /**
   * URLを生成します。
   *
   * @return URL
   */
  public String toString() {
    return buildPath().append(buildQuery().toString()).toString();
  }

  // ------------------------------------------
  private StringBuilder buildPath() {
    final String base;
    if (this.baseUrl.length() > 1 && this.baseUrl.endsWith(PATH_DELIMITER)) {
      base = this.baseUrl.substring(0, this.baseUrl.length() - 1);
    } else {
      base = this.baseUrl;
    }
    StringBuilder pathUrl = new StringBuilder(base);
    this.paths.forEach(
        path -> {
          List<String> urls = Stream.of(path.split(PATH_DELIMITER)).collect(Collectors.toList());
          urls.forEach(
              url -> {
                pathUrl.append(PATH_DELIMITER).append(urlEncoding(url));
              });
        });
    return pathUrl;
  }

  private StringBuilder buildQuery() {
    // {path}?{key}={value}&{key}=....
    StringBuilder queryUrl = new StringBuilder(QUERY_STERT);
    StringBuilder buildingUrl = new StringBuilder();
    this.queries.forEach(
        query -> {
          for (var q : query.entrySet()) {
            StringBuilder keyValue = new StringBuilder(q.getKey());
            keyValue.append(EQUAL).append(urlEncoding(q.getValue()));
            if (buildingUrl.length() <= 1) {
              buildingUrl.append(keyValue.toString());
            } else {
              buildingUrl.append(QUERY_DELIMITER).append(keyValue.toString());
            }
          }
        });
    return queryUrl.append(buildingUrl.toString());
  }

  private String urlEncoding(String str) {
    try {
      return URLEncoder.encode(str, "UTF-8")
          .replace(".", "%2E")
          .replace("*", "%2A")
          .replace("+", "%20");
    } catch (UnsupportedEncodingException e) {
      return "";
    }
  }
}
