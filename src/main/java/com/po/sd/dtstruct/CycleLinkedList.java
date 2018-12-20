package com.po.sd.dtstruct;

/**
 * CycleLinkedList: 环形链表，有一个头指针，节点next指针构成一个环状链表。
 * 环形链表：
 * 1、一般数据集合规模不会很大。
 *
 * Created by ZJ on 20/09/2018.
 */
public class CycleLinkedList {

  private Node head;

  public CycleLinkedList() {
    head = new Node();
  }

  public void addAtHead(int val) {
    Node node = new Node(val);
    if (null == head.next) {
      head = node;
      node.next = node;
    } else {
      node.next = head.next;
      head.next = node;
    }
  }

  /**
   * 从第一个节点开始，如果有下一个节点则获取下一节点，将获取的下一节点（第二个节点）和 head.next（第一个节点） 比较，若链表中有一个节点跟 head.next
   * 相同，则可以说明该链表是环形链表。即最后一个节点next指向第一个节点。
   *
   * @param head 链表的头指针
   * @return boolean
   */
  public boolean hasCycle(Node head) {
    Node node = head.next;
    while (null != node) {
      node = node.next;
      if (head.next == node) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    CycleLinkedList lis = new CycleLinkedList();
    lis.addAtHead(23);
    lis.addAtHead(66);
    lis.addAtHead(37);
    System.out.println(lis.hasCycle(lis.head));
  }

  private static class Node {

    int val;
    Node next;

    public Node() {
      this.next = null;
    }

    public Node(int val) {
      this.val = val;
      this.next = null;
    }
  }

}
