package com.po.neo;

/**
 * Created by ZJ on 22/12/2018.
 */
public class RelationLock {

  public volatile boolean flag = true;

  public void setFlag(boolean flag) {
    this.flag = flag;
  }
}
