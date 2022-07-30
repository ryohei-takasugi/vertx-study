package jp.vertx.starter.models.api;

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
   * @param null
   * @return responce date
   */
  public JsonObject get() {
    JsonObject main = new JsonObject();
    set();
    main.put("request", requestModel.get());
    main.put("responce", responce);
    return main;
  }

  /**
   * set responce data
   * 
   * @param null
   * @return null
   */
  public void set() {
    responce.put("id", getId());
    responce.put("name", getName());
  }

  /**
   * set for params
   * 
   * @param null
   * @return null
   */
  public void setForParams() {
    if (validateParam("_id", "String")) {
      setId(Integer.valueOf(requestModel.getParams().get("_id").toString()));
    }
    if (validateParam("_name", "String")) {
      setName(requestModel.getParams().get("_name").toString());
    }
  }

  /** 
   * validate params
   *  
   * @param keyName
   * @param KeyType
   * @return Existing And check OK
   */
  private boolean validateParam(String keyName, String KeyType) {
    if (requestModel.getParams().containsKey(keyName)) {
      if (requestModel.getParams().get(keyName).getClass().getName() == KeyType) {
        return true;
      }
    }
    return false;
  }

  /**
   * get id
   * 
   * @param null
   * @return id
   */ 
  public int getId() {
    return this.id;
  }

  /**
   * set id
   * 
   * @param null
   * @return null
   */ 
  public void setId(int id) {
    this.id = id;
  }

  /**
   * get name
   * 
   * @param null
   * @return name
   */ 
  public String getName() {
    return this.name;
  }

  /**
   * set name
   * 
   * @param null
   * @return null
   */ 
  public void setName(String name) {
    this.name = name;
  }
}
