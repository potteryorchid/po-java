package com.po.sd.concurrent;

/**
 * The synchronized keyword can be used to mark four different types of blocks:
 * 1. Instance methods
 * 2. Static methods
 * 3. Code blocks inside instance methods
 * 4. Code blocks inside static methods
 *
 * Created by po on 30/03/2019.
 */
public class SynchronizedKeyword {

  /**
   * A synchronized instance method in Java is synchronized on the instance (object) owning the
   * method. Thus, each instance has its synchronized methods synchronized on a different object:
   * the owning instance. Only one thread can execute inside a synchronized instance method. If more
   * than one instance exist, then one thread at a time can execute inside a synchronized instance
   * method per instance. One thread per instance.
   */
  public synchronized void instanceMethod() {
    this.toString();
  }

  public static synchronized void met() {
  }
}
