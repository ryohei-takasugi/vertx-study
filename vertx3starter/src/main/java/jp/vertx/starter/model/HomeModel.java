package jp.vertx.starter.model;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import jp.vertx.starter.model.module.ResponceFormat;

public class HomeModel extends ResponceFormat {

  private int id = 1;
  private String name = "sato";

  public HomeModel(RoutingContext event) {
    set(event);
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

  public JsonObject get() {
    super.get();
    this.responce.put("id", this.id);
    this.responce.put("name", this.name);
    return this.main;
  }
}
