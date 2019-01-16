package com.po.neo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * index: 905314
 * Created by ZJ on 20/12/2018.
 */
public class NeoRun {

  public static void main(String[] args) {


    ExecutorService exec = Executors.newFixedThreadPool(NeoDriver.threadSize);
    RelationLock lock = new RelationLock();

    long t = System.currentTimeMillis();
    LongAdder acc = new LongAdder();
    LongAdder acc1 = new LongAdder();

    for (int i = 0; i < NeoDriver.threadSize; i++) {
      if (Config.getProperty("is.node").equals("1")) {
        exec.execute(new ProfileThread(acc, t));
      } else {
        exec.execute(new RelationsThread(lock, t, acc, acc1));
      }
    }

    try {
      exec.awaitTermination(12, TimeUnit.HOURS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
