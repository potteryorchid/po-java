package com.po.picture;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ZJ on 11/01/2019.
 */
public class ImageConcurrentRun {

  public static void main(String[] args) throws InterruptedException {

    ArrayList<String> urls = new ArrayList<>();
    for (int j = 0; j < 1000; j++) {
      urls.add("http://10.199.2.44:8080/v4/photos/a17-AAABZ_N_mz1L1AgQAAAAAg==/data");
    }

    int no = 4;

    ExecutorService exec = Executors.newFixedThreadPool(no);
    ReentrantLock lock = new ReentrantLock();
    LongAdder adder = new LongAdder();
    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(1001, 0.9f, 16);

    for (int j = 0; j < no; j++) {
      exec.execute(new ImageConcurrentTool(urls, map, adder, lock));
    }

    // Is shutdown has bean called, if not call shutdown.
    if (!exec.isShutdown()) {
      exec.shutdown();
    }
    // Monitor thread pool status per 3 seconds.
    while (!exec.isTerminated()) {
      exec.awaitTermination(3, TimeUnit.SECONDS);
    }
  }
}
