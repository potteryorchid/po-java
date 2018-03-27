package com.po.concurrent.runnable;

import com.po.concurrent.common.ControlFlag;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ZJ on 27/03/2018.
 */
public class RunnableOddEvenApp {


  public static void main(String[] args) {

    CyclicBarrier cyclicBarrier;
    ReentrantLock reentrantLock = new ReentrantLock();
    ControlFlag controlFlag = new ControlFlag();

    EvenNumRunnable evenNumRunnable = new EvenNumRunnable(reentrantLock, controlFlag);
    OddNumRunnable oddNumRunnable = new OddNumRunnable(reentrantLock, controlFlag);

    new Thread(oddNumRunnable).start();
    new Thread(evenNumRunnable).start();

  }

}
