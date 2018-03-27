package com.po.concurrent.runnable;

import com.po.concurrent.common.ControlFlag;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ZJ on 27/03/2018.
 */
public class OddNumRunnable implements Runnable {

  ReentrantLock reentrantLock;
  ControlFlag controlFlag;

  public OddNumRunnable(ReentrantLock reentrantLock, ControlFlag controlFlag) {
    this.reentrantLock = reentrantLock;
    this.controlFlag = controlFlag;
  }

  @Override
  public void run() {

    int i = 1;

    while (i < 101) {
      try {
        reentrantLock.lock();
//        System.out.println("Odd : " + i);
        if (controlFlag.flag) {
          if (i % 2 == 1) {
            System.out
                .println("Odd Num Runnable = " + i);
            controlFlag.setFlag(false);
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
