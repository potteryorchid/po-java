package com.po.sd.algorithm;

/**
 * Created by ZJ on 21/08/2018.
 */
public class Euclidean {

  /**
   * 辗转相除法，又称欧几里得算法（英语：Euclidean algorithm），是求最大公约数的算法。两个整数的最大公约数是能够同时整除它们的最大的正整数。辗转相除法基于如下原理：两个整数的最大公约数等于其中较小的数和两数的差的最大公约数。
   * 最大公约数有一个更加巧妙的定义：a和b的最大公约数g是a和b的线性和中的最小正整数，即所有形如ua + vb（其中u和v是整数）的数中的最小正整数。
   * 可以证明，所有ua+vb都是g的整数倍（ua + vb = mg，其中m是整数）。
   *
   * @param max 较大的输入参数
   * @param min 较小的输入参数
   * @return 返回最大公约数 or 返回 -1（if max < min）
   */
  public static long greatestCommonDivisor(long max, long min) {

    if (max < min) {
      return -1L;
    }

    while (min != 0) {
      long tmp = max % min;
      max = min;
      min = tmp;
    }

    return max;
  }

  public static void main(String[] args) {
    System.out.println(Euclidean.greatestCommonDivisor(25, 15));
  }
}
