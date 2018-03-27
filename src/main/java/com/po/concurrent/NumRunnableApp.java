package com.po.concurrent;

import com.po.concurrent.common.ControlFlag;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ZJ on 27/03/2018.
 */
public class NumRunnableApp {


  public static void main(String[] args) {

    ReentrantLock reentrantLock = new ReentrantLock();
    ControlFlag controlFlag = new ControlFlag();

    EvenNumRunnable evenNumRunnable = new EvenNumRunnable(reentrantLock, controlFlag);
    OddNumRunnable oddNumRunnable = new OddNumRunnable(reentrantLock, controlFlag);

    new Thread(oddNumRunnable).start();
    new Thread(evenNumRunnable).start();

  }

}
