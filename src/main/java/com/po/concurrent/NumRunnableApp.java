package com.po.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ZJ on 27/03/2018.
 */
public class NumRunnableApp {


  public static void main(String[] args) {

    ReentrantLock reentrantLock = new ReentrantLock();
    ThreadControlFlag threadControlFlag = new ThreadControlFlag();

    EvenNumRunnable evenNumRunnable = new EvenNumRunnable(reentrantLock, threadControlFlag);
    OddNumRunnable oddNumRunnable = new OddNumRunnable(reentrantLock, threadControlFlag);

    new Thread(oddNumRunnable).start();
    new Thread(evenNumRunnable).start();

  }

}
