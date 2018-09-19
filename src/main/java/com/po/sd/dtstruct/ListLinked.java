package com.po.sd.dtstruct;

/**
 * Created by po on 2017/7/10.
 * <p>
 * 双指针单项链表
 * head：指向头节点；
 * tail：指向尾节点；
 */
public class ListLinked<T> {

    private ListLinkedNode<T> head;
    private ListLinkedNode<T> tail;

    public ListLinked() {
        head = new ListLinkedNode<T>();
        tail = new ListLinkedNode<T>();
    }

    public void addFromHead(T data) {
        ListLinkedNode<T> node = new ListLinkedNode<T>();
        node.setData(data);
        node.setNext(head.getNext());
        if (head.getNext() == null) {
            tail.setNext(node);
        }
        head.setNext(node);
    }

    public void addFromTail(T data) {
        ListLinkedNode<T> node = new ListLinkedNode<T>();
        node.setData(data);
        node.setNext(null);
        if (tail.getNext() == null) {
            head.setNext(node);
        } else {
            tail.getNext().setNext(node);
        }
        tail.setNext(node);
    }

    public ListLinked<T> inverse() {
        ListLinkedNode<T> node = head.getNext();
        if (node == null || node.getNext() == null) {
            return this;
        } else {
            return this;
        }
    }

    public static void main(String[] args) {
        ListLinked<String> ll = new ListLinked<String>();

        ll.addFromTail("aa");
        ll.addFromTail("bb");

        ll.addFromHead("a");
        ll.addFromHead("b");
        ll.addFromHead("c");

        ll.addFromTail("a");
        ll.addFromTail("b");
        ll.addFromTail("c");

        ListLinkedNode node = ll.head.getNext();
        while (node != null) {
            System.out.println(node.getData());
            node = node.getNext();
        }
    }
}
