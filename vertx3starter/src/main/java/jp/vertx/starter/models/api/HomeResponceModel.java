package jp.vertx.starter.models.api;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class HomeResponceModel extends HomeResponceEntity {

  /** logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(HomeResponceModel.class);

  /** HomeResponceEntry */
  private static final HomeResponceEntity modelEntry = new HomeResponceEntity();

  /** request model */
  public final RequestModel requestModel;

  /** request entry */
  public static final RequestEntity requestEntity = new RequestEntity();

  /** responce */
  private JsonObject responce = new JsonObject();

  /**
   * contractor
   *
   * @param null
   */
  public HomeResponceModel(RoutingContext event) {
    this.requestModel = new RequestModel(event);
    setForParams();
  }

  /**
   * get Responce data
   *
   * @param null
   * @return responce date
   */
  public JsonObject get() {
    JsonObject main = new JsonObject();
    try {
      setResponce();
      main.put("request", requestModel.get());
      main.put("responce", responce);
      main.put("status", 200);
      return main;
    } catch (Throwable t) {
      LOGGER.error(HomeResponceModel.class.getName() + " get ", t);
      return new JsonObject().put("status", 500);
    }
  }

  /**
   * set responce data
   *
   * @param null
   * @return null
   */
  private void setResponce() {
    try {
      responce.put("id", modelEntry.getId());
      responce.put("name", modelEntry.getName());
    } catch (Throwable t) {
      LOGGER.error(HomeResponceModel.class.getName() + " setResponce ", t);
    }
  }

  /**
   * set for params
   *
   * @param null
   * @return null
   */
  private void setForParams() {
    try {
      if (validateParam("_id", "String")) {
        modelEntry.setId(Integer.valueOf(requestEntity.getParams().get("_id").toString()));
      }
      if (validateParam("_name", "String")) {
        modelEntry.setName(requestEntity.getParams().get("_name").toString());
      }
    } catch (Throwable t) {
      LOGGER.error(HomeResponceModel.class.getName() + " setForParams ", t);
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
    try {
      if (requestEntity.getParams().containsKey(keyName)) {
        if (requestEntity.getParams().get(keyName).getClass().getName() == KeyType) {
          return true;
        }
      }
      return false;
    } catch (Throwable t) {
      LOGGER.error(HomeResponceModel.class.getName() + " validateParam ", t);
      return false;
    }
  }
}
