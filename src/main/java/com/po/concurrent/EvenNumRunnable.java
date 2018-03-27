package com.po.concurrent;

import com.po.concurrent.common.ControlFlag;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ZJ on 27/03/2018.
 */
public class EvenNumRunnable implements Runnable {

  ReentrantLock reentrantLock;
  ControlFlag controlFlag;

  public EvenNumRunnable(ReentrantLock reentrantLock, ControlFlag controlFlag) {
    this.reentrantLock = reentrantLock;
    this.controlFlag = controlFlag;
  }

  @Override
  public void run() {
    int i = 1;
    while (i < 101) {
      try {
        reentrantLock.lock();
        if (!controlFlag.flag) {
          if (i % 2 == 0) {
            System.out
                .println("Even Num Runnable: " + i + ", Flag is:" + controlFlag.flag);
            controlFlag.setFlag(true);
            i = i + 2;
          }
        }
      } finally {
        reentrantLock.unlock();
      }
    }
  }

}
