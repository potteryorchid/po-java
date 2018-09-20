package com.po.sd.dtstruct;

/**
 * Created by ZJ on 19/09/2018.
 */
public class SinglyLinkedNode {

  private int val;

  private SinglyLinkedNode next;

  SinglyLinkedNode() {

  }

  SinglyLinkedNode(int val) {
    this.val = val;
    this.next = null;
  }

  public int getVal() {
    return val;
  }

  public SinglyLinkedNode getNext() {
    return next;
  }

  public void setNext(SinglyLinkedNode next) {
    this.next = next;
  }
}
