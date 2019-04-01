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

  int count = 0;

  /**
   * A synchronized instance method in Java is synchronized on the instance (object) owning the
   * method. Thus, each instance has its synchronized methods synchronized on a different object:
   * the owning instance. Only one thread can execute inside a synchronized instance method. If more
   * than one instance exist, then one thread at a time can execute inside a synchronized instance
   * method per instance. One thread per instance.
   */
  public synchronized void instanceMethod(int arg) {
    count += arg;
  }

  /**
   * Synchronized static methods are synchronized on the class object of the class the synchronized
   * static method belongs to. Since only one class object exists in the Java VM per class, only one
   * thread can execute inside a static synchronized method in the same class. If the static
   * synchronized methods are located in different classes, then one thread can execute inside the
   * static synchronized methods of each class. One thread per class regardless of which static
   * synchronized method it calls.
   */
  public static synchronized void staticMethod(int arg) {
  }

  /**
   * You do not have to synchronize a whole method. Sometimes it is preferable to synchronize only
   * part of a method. Java synchronized blocks inside methods makes this possible. Notice how the
   * Java synchronized block construct takes an object in parentheses. In the example "this" is
   * used, which is the instance the add method is called on. The object taken in the parentheses by
   * the synchronized construct is called a monitor object. T he code is said to be synchronized on
   * the monitor object. A synchronized instance method uses the object it belongs to as monitor
   * object. Only one thread can execute inside a Java code block synchronized on the same monitor
   * object.
   */
  public void instanceBlock(int arg) {
    synchronized (this) {
      count += arg;
    }
  }

  /**
   * Only one thread can execute inside any of these two methods at the same time.
   * Had the second synchronized block been synchronized on a diWfferent object than MyClass.class,
   * then one thread could execute inside each method at the same time.
   */
  public static void staticBlock(int arg) {
    synchronized (SynchronizedKeyword.class) {

    }
  }
}
