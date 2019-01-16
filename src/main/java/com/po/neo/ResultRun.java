package com.po.neo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by ZJ on 20/12/2018.
 */
public class ResultRun {

  public static void main(String[] args) {

    long l = System.currentTimeMillis();
    LongAdder acc1 = new LongAdder();
    LongAdder acc2 = new LongAdder();
    LongAdder acc3 = new LongAdder();

    ExecutorService exec = Executors.newFixedThreadPool(NeoDriver.threadSize);

    for (int i = 0; i < NeoDriver.threadSize; i++) {
      exec.execute(new ReturnRes(acc1, acc2, acc3, l));
    }

    try {
      exec.awaitTermination(12, TimeUnit.HOURS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
