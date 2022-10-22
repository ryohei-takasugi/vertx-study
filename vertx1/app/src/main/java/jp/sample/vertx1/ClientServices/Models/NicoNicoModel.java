package jp.sample.vertx1.ClientServices.Models;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.Map;

public class NicoNicoModel {

    private final String id;
    private final Integer totalCount;
    private final Integer status;
    private Map<String, NicoNicoGetEntity> entities;

    public NicoNicoModel(JsonObject data) {
        if (data != null && !data.isEmpty()) {
            this.id = data.getString("id");
            this.totalCount = data.getInteger("totalCount");
            this.status = data.getInteger("status");
            JsonArray d = data.getJsonArray("data");
            for (int i = 0; i < d.size(); i++) {
                NicoNicoGetEntity entity = new NicoNicoGetEntity(data);
                this.entities.put(entity.contentId(), entity);
            }

        } else {
            this.id = "0";
            this.totalCount = 0;
            this.status = 500;
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

    public Map<String, NicoNicoGetEntity> entities() {
        return entities;
    }
}
