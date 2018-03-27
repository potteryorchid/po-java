package com.po.concurrent.thread;

import com.po.concurrent.common.ControlFlag;

/**
 * 该实例是两个线程并行运行，并分别交替配合按顺序打印出奇偶数。多线程配合工作场景下，当使用 synchronized 控制线程安全时，建议使用 wait() 和 notify()。
 *
 * 1、synchronized 关键字控制线程安全，同时使用 wait() 和 notify() 方法控制线程的继续执行，该方式可以有效避免同步锁的竞争，提高线程效率。当多个线程协调进行工作时建议使用
 * wait() 和 notify()。
 *
 * 2、wait() 和 notify() 必须在 synchronized 作用范围内使用，否则会报异常错误: Exception in thread "Thread-1"
 * java.lang.IllegalMonitorStateException.
 *
 * 3、notify() 和  notifyAll() ：当确定只有一个线程需要被唤醒时才可以使用 notify()，其他情况都使用 notifyAll()，表示唤醒其他等待线程。
 *
 * 4、wait() 带时间参数，表示最大等待被唤醒时间，超时将重试获取竞争资源。不带时间参数会一直等待下去，直到被 notify() 或 notifyAll() 唤醒。建议使用带时间参数的
 * wait() 方法。
 *
 * Created by ZJ on 20/03/2018.
 */
public class EvenNumThread extends Thread {

  ControlFlag controlFlag;

  public EvenNumThread(ControlFlag controlFlag) {
    this.controlFlag = controlFlag;
  }

  @Override
  public void run() {
    int i = 2;
    while (i < 10001) {
      synchronized (controlFlag) {
        System.out.println("Even: " + i);
        if (!controlFlag.flag) {
          if (i % 2 == 0) {
            System.out.println("Even Num Thread = " + i);
            controlFlag.setFlag(true);
            i = i + 2;
            // 在此处唤醒另一个等待的线程
            controlFlag.notify();
          }
        } else {
          System.out.println("Even wait" + i);
          try {
            // 在条件不满足是执行等待操作，释放掉竞争资源
            controlFlag.wait(10);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

}
