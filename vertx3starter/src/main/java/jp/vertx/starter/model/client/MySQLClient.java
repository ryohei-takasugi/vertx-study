package jp.vertx.starter.model.client;

import io.vertx.core.Future;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Query;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;

public class MySQLClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(MySQLClient.class);

  public void connection() {
    MySQLConnectOptions connectOptions =
        new MySQLConnectOptions()
            .setPort(3306)
            .setHost("localhost")
            .setDatabase("first_app_development")
            .setUser("root");
    // Pool options
    PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

    // Create the client pool
    SqlClient client = MySQLPool.client(connectOptions, poolOptions);

    // A simple query
    Query<RowSet<Row>> query = client.query("SELECT * FROM users WHERE id='1'");
    Future<RowSet<Row>> execute = query.execute();
    RowSet<Row> reuslt;
    if (execute.succeeded()) {
      reuslt = execute.result();
      LOGGER.info("succeeded: " + reuslt.toString());
    } else {
      LOGGER.error("faild: " + execute.cause().getMessage());
    }
  }
}
