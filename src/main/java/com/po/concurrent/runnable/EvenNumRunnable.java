package com.po.concurrent.runnable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 该实例是两个线程并行运行，并分别交替配合按顺序打印出奇偶数。
 *
 * Runnable 接口和 Thread 线程类区别：Runnable 是一个接口，仅仅实现了线程的 Run 方法，自身不能创建一个新的线程，需要借助 Thread 开辟一个新的线程。Thread
 * 是一个类，自身可以完成线程的创建。
 *
 * 1、Java Lock 和 synchronized 区别：synchronized 是通过 JVM 的 monitor 来实现的线程，Lock 是 Java 语言实现的线程控制。
 *
 * 2、Java Lock 可以调用 newCondition() 方法 创建 condition，从而调用 await() 和 signal() 来控制线程间的交互。
 *
 * 3、多线程配合工作场景下，当使用 Lock 控制线程安全时，建议使用 Condition 的 await() 和 signal() 来控制线程间的交互。当使用 Java Lock 时
 * unlock() 操作必须在 final 块中执行， 以保证资源的释放。
 *
 * 4、优先执行 signal() 方法，释放线程占用资源，以保证线程配合执行。
 *
 * Created by ZJ on 27/03/2018.
 */
public class EvenNumRunnable implements Runnable {

  ReentrantLock reentrantLock;
  Condition oddCdt;
  Condition evenCdt;

  public EvenNumRunnable(ReentrantLock reentrantLock, Condition oddCdt, Condition evenCdt) {
    this.reentrantLock = reentrantLock;
    this.oddCdt = oddCdt;
    this.evenCdt = evenCdt;
  }

  @Override
  public void run() {

    int i = 2;

    while (i < 1001) {
      System.out.println("Even " + i);
      try {
        reentrantLock.lock();
        System.out
            .println("Even Num Runnable = " + i);
        i += 2;

        this.oddCdt.signal();
      } finally {
        try {
          this.evenCdt.await(10, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        reentrantLock.unlock();
      }
    }

  }

}
