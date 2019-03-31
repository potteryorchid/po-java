package com.po.leetcode;

/**
 * Created by ZJ on 18/07/2018.
 */
public class StringIntern {

  synchronized void prt() {
    System.out.println(Thread.currentThread().getId());

    System.out.println(Thread.currentThread().getName());
  }

  public static void main(String[] args) {
    int i = 1000;
    System.out.println(i);

  }

}
