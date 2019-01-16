package com.po.neo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by ZJ on 21/12/2018.
 */
public class ScheduledRun {

  public void run() {
    ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
  }

}
