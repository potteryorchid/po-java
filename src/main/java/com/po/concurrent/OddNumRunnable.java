package com.po.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ZJ on 27/03/2018.
 */
public class OddNumRunnable implements Runnable {

  ReentrantLock reentrantLock;
  ThreadControlFlag threadControlFlag;

  public OddNumRunnable(ReentrantLock reentrantLock, ThreadControlFlag threadControlFlag) {
    this.reentrantLock = reentrantLock;
    this.threadControlFlag = threadControlFlag;
  }

  @Override
  public void run() {
    int i = 1;
    while (i < 101) {
      try {
        reentrantLock.lock();
        if (threadControlFlag.flag) {
          if (i % 2 == 1) {
            System.out
                .println("Odd Num Runnable: " + i + ", Flag is:" + threadControlFlag.flag);
            threadControlFlag.setFlag(false);
            i = i + 2;
          }
        }
      } finally {
        reentrantLock.unlock();
      }
    }
  }

}
