package com.po.sd.dtstruct;

/**
 * Created by po on 2017/7/10.
 */
public class ListLinkedNode<T> {

    private T data;

    private ListLinkedNode<T> next;

    public ListLinkedNode() {
        this.setData(null);
        this.setNext(null);
    }

    public ListLinkedNode(T data, ListLinkedNode<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ListLinkedNode<T> getNext() {
        return next;
    }

    public void setNext(ListLinkedNode<T> next) {
        this.next = next;
    }
}
