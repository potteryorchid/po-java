package com.po.neo;

import java.util.Random;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Condition;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.neo4j.driver.v1.exceptions.ServiceUnavailableException;

/**
 * Created by ZJ on 20/12/2018.
 */
public class RelationsThread extends Thread {

  RelationLock lock;
  long t;
  LongAdder acc;
  LongAdder acc1;

  public RelationsThread(RelationLock lock, long t, LongAdder acc, LongAdder acc1) {
    this.lock = lock;
    this.t = t;
    this.acc = acc;
    this.acc1 = acc1;
  }

  public void relation() {

    int step = Integer.valueOf(Config.getProperty("relationship.step"));
    int count = Integer.valueOf(Config.getProperty("node.count"));
    Random random = new Random();

    while (true) {
      int s = -1;
      long t1 = System.currentTimeMillis();
      synchronized (lock) {
        s = Config.getIndex();
        lock.notify();
      }
      long t2 = System.currentTimeMillis() - t1;
      if ((t2) > 70) {
        System.out.println("Get index is out of time === " + t2);
      }

      if (s != -1 && s < count) {
        for (int i = 0; i < step; i++) {
          StringBuilder sb = new StringBuilder();
          sb.append("MATCH(s:Profile) WHERE ID(s)=");
          sb.append(s);
          sb.append(" MATCH(d:Profile) WHERE ID(d) in [");
          for (int j = 0; j < 10; j++) {
            sb.append(random.nextInt(count));
            if (j != 9) {
              sb.append(",");
            }
          }
          sb.append("] MERGE (s)-[:RELATION {sc:");
          sb.append(random.nextDouble() * 100);
          sb.append("}]->(d)");

//          long t3 = System.currentTimeMillis();
          try (Session session = NeoDriver.driver.session()) {
            StatementResult result = session
                .readTransaction(new TransactionWork<StatementResult>() {
                  @Override
                  public StatementResult execute(Transaction tx) {
                    return tx.run(sb.toString());
                  }
                });
            if (null == result) {
              System.out.println("Result is null");
            }
//            long t4 = System.currentTimeMillis() - t3;
//            System.out.println(t4);
//            if ((t4) < 100) {
//              acc1.increment();
//            }
          } catch (ServiceUnavailableException ex) {
            ex.printStackTrace();
          }
        }
        acc.increment();

        StringBuilder str = new StringBuilder();
        str.append("==== Index: ");
        str.append(s);
        str.append(" | count: ");
        str.append(acc);
//        str.append(" | < 100: ");
//        str.append(acc1.doubleValue() / step);
        str.append(" | time: ");
        str.append(System.currentTimeMillis()
            - t);

        System.out.println(str.toString());
      }
    }

  }

  @Override
  public void run() {
    this.relation();
  }
}
