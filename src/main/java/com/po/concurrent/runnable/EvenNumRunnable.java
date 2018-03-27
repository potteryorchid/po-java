package com.po.concurrent.runnable;

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

    int i = 2;

    while (i < 101) {
      try {
        reentrantLock.lock();
//        System.out.println("Even : " + i);
        if (!controlFlag.flag) {
          if (i % 2 == 0) {
            System.out
                .println("Even Num Runnable = " + i);
            controlFlag.setFlag(true);
            i += 2;
            reentrantLock.notify();
          }
        } else {
          try {
            reentrantLock.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      } finally {
        reentrantLock.unlock();
      }
    }

  }

}
