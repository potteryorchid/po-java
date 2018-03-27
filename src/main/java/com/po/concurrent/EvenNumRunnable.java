package com.po.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ZJ on 27/03/2018.
 */
public class EvenNumRunnable implements Runnable {

  ReentrantLock reentrantLock;
  ThreadControlFlag threadControlFlag;

  public EvenNumRunnable(ReentrantLock reentrantLock, ThreadControlFlag threadControlFlag) {
    this.reentrantLock = reentrantLock;
    this.threadControlFlag = threadControlFlag;
  }

  @Override
  public void run() {
    int i = 1;
    while (i < 101) {
      try {
        reentrantLock.lock();
        if (!threadControlFlag.flag) {
          if (i % 2 == 0) {
            System.out
                .println("Even Num Runnable: " + i + ", Flag is:" + threadControlFlag.flag);
            threadControlFlag.setFlag(true);
            i = i + 2;
          }
        }
      } finally {
        reentrantLock.unlock();
      }
    }
  }

}
