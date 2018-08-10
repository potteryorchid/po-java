package com.po.sd.concurrent.runnable;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ZJ on 27/03/2018.
 */
public class RunnableOddEvenApp {


  public static void main(String[] args) {

    ReentrantLock reentrantLock = new ReentrantLock();
    Condition evenCdt = reentrantLock.newCondition();
    Condition oddCdt = reentrantLock.newCondition();

    EvenNumRunnable evenNumRunnable = new EvenNumRunnable(reentrantLock, oddCdt, evenCdt);
    OddNumRunnable oddNumRunnable = new OddNumRunnable(reentrantLock, oddCdt, evenCdt);

    new Thread(oddNumRunnable).start();
    new Thread(evenNumRunnable).start();

  }

}
