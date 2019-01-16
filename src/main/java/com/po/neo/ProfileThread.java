package com.po.neo;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.LongAdder;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

/**
 * Created by ZJ on 20/12/2018.
 */
public class ProfileThread extends Thread {

  long t;
  LongAdder acc;

  public ProfileThread(LongAdder acc, long t) {
    this.acc = acc;
    this.t = t;
  }

  @Override
  public void run() {

    int step = Integer.valueOf(Config.getProperty("node.step"));

    while (true) {
      Random random = new Random();
      StringBuilder sb = new StringBuilder();

      sb.append("CREATE ");
      for (int i = 0; i < step; i++) {
        sb.append("(:Profile {profileID:'");
        sb.append(UUID.randomUUID().toString());
        sb.append("',");
        sb.append(" score:");
        sb.append(random.nextDouble() * 100);
        sb.append("})");
        if (i != (step - 1)) {
          sb.append(",");
        }
      }

      try (Session session = NeoDriver.driver.session()) {
        long t1 = System.currentTimeMillis();
        StatementResult res = session.writeTransaction(new TransactionWork<StatementResult>() {
          @Override
          public StatementResult execute(Transaction tx) {
            return tx.run(sb.toString());
          }
        });
        if (null == res) {
          System.out.println("Result is null");
        }
        acc.increment();
        long t2 = System.currentTimeMillis() - t1;
        if (t2 > 100) {
          System.out.println("========= out of time 300 ms =========" + t2);
        }
        System.out.println("Node: " + acc + " | time: " + (System.currentTimeMillis() - t));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
