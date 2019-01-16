package com.po.neo;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

/**
 * Created by ZJ on 20/12/2018.
 */
public final class NeoDriver implements AutoCloseable, Serializable {

  final static Driver driver;

  final static int poolSize = Integer
      .parseInt(com.po.neo.Config.getProperty("driver.pool.size"));

  final static int threadSize = Integer
      .parseInt(com.po.neo.Config.getProperty("driver.thread.size"));

  static {
    Config config = Config.builder()
        .withMaxConnectionLifetime(30, TimeUnit.SECONDS)
        .withMaxConnectionPoolSize(poolSize)
        .withConnectionAcquisitionTimeout(30,
            TimeUnit.SECONDS)  // Set waiting time when connection pool is at its max capacity
        .withConnectionTimeout(20, TimeUnit.SECONDS)
        .withMaxTransactionRetryTime(20, TimeUnit.SECONDS)
        .build();

    driver = GraphDatabase
        .driver(com.po.neo.Config.getProperty("neo.uri"), AuthTokens
            .basic(com.po.neo.Config.getProperty("neo.usr"),
                com.po.neo.Config.getProperty("neo.pwd")), config);
  }

  @Override
  public void close() throws Exception {
    driver.close();
  }
}
