package com.po.tmp;

/**
 * Created by po on 13/06/2019.
 */
public class Test {

  public static void main(String[] args) {
    String s = "234";
    System.out.println(Integer.parseInt(s));

    byte b = 0;
    b |= 0x01 << (255 & 0x07);

    System.out.println(b);

    byte a = 0;
    b = (byte) 0x80;
    System.out.println(b);
  }

}
