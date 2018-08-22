package com.po.sd.base;

import java.text.MessageFormat;

/**
 * Created by ZJ on 22/08/2018.
 */
public class PrintFormat {

  public static void main(String[] args) {
    /**
     * System.out.printf() 格式化输出
     *
     * %d : 输出 Integer 类型 printInteger
     *
     * %f : 输出 float 类型 printFloat
     *
     * %s : 输出 String 类型
     *
     */
    System.out.printf("Res: %d\n", 56);
    System.out.printf("Res: %f\n", 56.3);
    System.out.printf("Res: %s\n", "100");

    /**
     * String.format() 占位符
     *
     * %n$s : 第n个参数 String 类型
     */
    System.out.printf(String.format("我是%1$s,我来自%2$s,今年%3$s岁", "中国人", "北京",
        "22"));

    /**
     * MessageFormat.format() 占位符
     *
     * {n} : 第n个参数
     */
    System.out.println(MessageFormat
        .format("该网站{0}被访问了 {1} 次.", "https://www.google.com", 89));
  }
}
