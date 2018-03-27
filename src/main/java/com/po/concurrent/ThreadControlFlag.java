package com.po.concurrent;

/**
 * Created by ZJ on 27/03/2018.
 */
public class ThreadControlFlag {

  volatile boolean flag = true;

  public void setFlag(boolean flag) {
    this.flag = flag;
  }

}
