package jp.sample.starter.models.responce;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import jp.sample.starter.models.request.RequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeResponceModel {

  /** logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(HomeResponceModel.class);

  /** request model */
  public final RequestModel requestModel;

  /** default id */
  private int id = 1;

  /** default name */
  private String name = "sato";

  private String modelStatus = "none";

  /**
   * contractor
   *
   * @param null
   */
  public HomeResponceModel(RequestModel requestModel) {
    this.requestModel = requestModel;
    try {
      if (validateParam("_id")) {
        this.id = Integer.valueOf(requestModel.getParams().get("_id").toString());
      }
      if (validateParam("_name")) {
        this.name = requestModel.getParams().get("_name").toString();
      }
      this.modelStatus = "success";

    } catch (Throwable t) {
      LOGGER.error(HomeResponceModel.class.getName() + " setForParams ", t);
      this.modelStatus = "error";
    }
  }

  /**
   * @return
   */
  public String getModelStatus() {
    return this.modelStatus;
  }

  /**
   * get Responce data
   *
   * @param null
   * @return responce date
   */
  public JsonObject toJsonObject() {
    JsonObject main = new JsonObject();
    JsonObject responce = new JsonObject();
    try {
      responce.put("id", this.id);
      responce.put("name", this.name);
      Future<JsonObject> request = requestModel.toJsonObject();
      if (request.succeeded()) {
        main.put("request", request.result());
      } else {
        main.put("request", "error");
      }
      main.put("responce", responce.encode());
      main.put("status", 200);
      return main;
    } catch (Throwable t) {
      LOGGER.error(HomeResponceModel.class.getName() + " get ", t);
      return new JsonObject().put("status", 500);
    }
  }

  /**
   * validate params
   *
   * @param keyName
   * @param KeyType
   * @return Existing And check OK
   */
  private boolean validateParam(String keyName) throws NullPointerException {
    if (requestModel.getParams().containsKey(keyName)) {
      return true;
    }
    return false;
  }
}
