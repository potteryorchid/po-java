package com.po.sd.concurrent;

/**
 * Created by po on 01/04/2019.
 */
public class SynchronizedKeywordThread extends Thread {

  protected SynchronizedKeyword synchronizedKeyword = null;

  public SynchronizedKeywordThread(SynchronizedKeyword synchronizedKeyword) {
    this.synchronizedKeyword = synchronizedKeyword;
  }

  @Override
  public void run() {
    for (int i = 0; i < 5; i++) {
      synchronizedKeyword.instanceMethod(1);
    }
  }

  public static void main(String[] args) {
    SynchronizedKeyword count = new SynchronizedKeyword();
    SynchronizedKeyword count1 = new SynchronizedKeyword();

    SynchronizedKeywordThread thread1 = new SynchronizedKeywordThread(count);
    SynchronizedKeywordThread thread2 = new SynchronizedKeywordThread(count);
    SynchronizedKeywordThread thread3 = new SynchronizedKeywordThread(count1);

    // only thread1 or thread2 can run at a time.
    thread1.start();
    thread2.start();
    // thread3 can run with thread1 and thread2 at the same time.
    thread3.start();

    try {
      // wait for the threads run over.
      sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println(count.count);
    System.out.println(count1.count);
  }
}
