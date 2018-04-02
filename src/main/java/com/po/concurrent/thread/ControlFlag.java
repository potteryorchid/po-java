package com.po.concurrent.thread;

/**
 * Created by ZJ on 27/03/2018.
 */
public class ControlFlag {

  public volatile boolean flag = true;

  public void setFlag(boolean flag) {
    this.flag = flag;
  }

}