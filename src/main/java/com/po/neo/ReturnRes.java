package com.po.neo;

import java.util.Random;
import java.util.concurrent.atomic.LongAdder;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.neo4j.driver.v1.exceptions.ServiceUnavailableException;

/**
 * Created by ZJ on 22/12/2018.
 */
public class ReturnRes extends Thread {

  LongAdder adder;
  LongAdder adder1;
  LongAdder adder2;
  long time;

  public ReturnRes(LongAdder adder, LongAdder adder1, LongAdder adder2, long time) {
    this.adder = adder;
    this.adder1 = adder1;
    this.adder2 = adder2;
    this.time = time;
  }

  public void getRes() {

    while (true) {
      long t1 = System.currentTimeMillis();

      StringBuilder sb = new StringBuilder();
      Random random = new Random();
      int count = Integer.valueOf(Config.getProperty("node.count"));

      sb.append("MATCH(s:Profile) WHERE ID(s)=");
      sb.append(random.nextInt(count));
      sb.append(" MATCH p=(s)-[r:RELATION]->() WHERE r.sc>80 RETURN p LIMIT 150");

      try (Session session = NeoDriver.driver.session()) {
        long t2 = System.currentTimeMillis();
        if ((t2 - t1) > 400) {
          System.out.println("Get session is out of time. ###");
        }
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
        long t3 = System.currentTimeMillis();
        if ((t3 - t2) > 300) {
          System.out.println("GET RELATIONSHIP OUT OF TIME. &&&");
        }

        long t = t3 - t1;
        if (t > 100) {
          adder2.increment();
        } else if (t > 50) {
          adder1.increment();
        } else {
          adder.increment();
        }

        System.out.println(">100: " + adder2);
        System.out.println("50-100: " + adder1);
        System.out.println("<50: " + adder);
        System.out.println("total time: " + (System.currentTimeMillis() - time));
      } catch (ServiceUnavailableException ex) {
        ex.printStackTrace();
      }
    }
  }

  @Override
  public void run() {
    getRes();
  }
}
