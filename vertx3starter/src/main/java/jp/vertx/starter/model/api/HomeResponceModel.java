package jp.vertx.starter.model.api;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;

public class HomeResponceModel {

  /** logger */
  private Logger LOGGER = LoggerFactory.getLogger(HomeResponceModel.class);

  /** request */
  public RequestModel requestModel;

  /** responce */
  private JsonObject responce = new JsonObject();

  /** default id */
  private int id = 1;

  /** default name */
  private String name = "sato";

  /**
   * contractor
   *
   * @param null
   */
  public HomeResponceModel(RequestModel requestModel) {
    this.requestModel = requestModel;
  }

  /**
   * get Responce data
   *
   * @return main
   */
  public JsonObject get() {
    JsonObject main = new JsonObject();
    set();
    main.put("request", requestModel.get());
    main.put("responce", responce);
    return main;
  }

  /** set */
  public void set() {
    responce.put("id", requestModel.getParams().getOrDefault("_id", this.id)); // FIXME: validate
    responce.put(
        "name", requestModel.getParams().getOrDefault("_name", this.name)); // FIXME: validate
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
