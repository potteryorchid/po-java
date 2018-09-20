package com.po.sd.dtstruct;

/**
 * 单链表：头、尾指针为单独指针不存储数据。
 *
 * Created by ZJ on 19/09/2018.
 */
public class SinglyLinkedList {

  private long size = 0;
  private SinglyLinkedNode head;
  private SinglyLinkedNode tail;

  public SinglyLinkedList() {
    head = new SinglyLinkedNode();
    tail = new SinglyLinkedNode();
  }

  public void addAtHead(int val) {
    SinglyLinkedNode node = new SinglyLinkedNode(val);
    node.setNext(head.getNext());
    head.setNext(node);
    if (null == tail.getNext()) {
      tail.setNext(node);
    }
    size++;
  }

  public void addAtTail(int val) {
    SinglyLinkedNode node = new SinglyLinkedNode(val);
    if (null != tail.getNext()) {
      tail.getNext().setNext(node);
    }
    tail.setNext(node);
    if (null == head.getNext()) {
      head.setNext(node);
    }
    size++;
  }

  /**
   * index ==0 head insertion
   * index != common insertion
   *
   * @param index index
   * @param val insertion value
   */
  public void addAtIndex(long index, int val) {
    if (index < 0 || index > size) {
      return;
    } else if (index < size) {
      long i = 0;
      SinglyLinkedNode node = head;
      while (i != index) {
        node = node.getNext();
        i++;
      }
      SinglyLinkedNode insertion = new SinglyLinkedNode(val);
      insertion.setNext(node.getNext());
      node.setNext(insertion);
      size++;
    } else if (index == size) {
      this.addAtTail(val);
    }
  }

  public int get(long index) {
    // verify index
    if (index > size - 1 || index < 0) {
      return -1;
    }
    long i = 0;
    SinglyLinkedNode node = head.getNext();
    while (i != index) {
      node = node.getNext();
      i++;
    }
    return node.getVal();
  }

  public void deleteAtIndex(long index) {
    if (index >= 0 && index < size) {
      long i = 0;
      SinglyLinkedNode node = head;
      while (i != index) {
        node = node.getNext();
        i++;
      }
      SinglyLinkedNode delete = node.getNext();
      node.setNext(delete.getNext());
      delete.setNext(null);
      if (index == size - 1) {
        tail.setNext(node);
      }
      size--;
    }
  }

  public static void main(String[] args) {
    SinglyLinkedList linkedList = new SinglyLinkedList();
    System.out.println(linkedList.get(0));
    linkedList.addAtIndex(1, 2);  // linked list becomes 1->2

    System.out.println(linkedList.get(0));
    System.out.println(linkedList.get(1));
    linkedList.addAtIndex(0, 1);
    linkedList.get(0);
    linkedList.get(1);

    System.out.println("==");

  }

}
