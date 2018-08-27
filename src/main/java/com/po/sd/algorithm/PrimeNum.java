package com.po.sd.algorithm;

/**
 * Created by ZJ on 27/08/2018.
 */
public class PrimeNum {

  /**
   * 获取小于 max 值的所有素数。素数定义：对于一个大于 1 的数，若该数只能被 1 和本身整除，则该数为素数。
   * 程序逻辑（穷举法），从 2 开始遍历，对于每一个素数的倍数都不为素数，所以遍历所有倍数，记录标记为 1。
   *
   * @param max Input Value：最大数
   * @return int[] 返回素数集合，素数会放到数组前面，数组后面元素为 0。
   */
  public static int[] getPrimes(int max) {
    int[] primes = new int[max / 3 + 1];
    int cursor = 0, pos = 2;
    byte[] flags = BitOper.createBitsByteArray(max);
    for (; pos < max; pos++) {
      if (!BitOper.checkBitsIs1(flags, pos)) {
        primes[cursor++] = pos;
        for (int i = pos; i < max; i += pos) {
          BitOper.setBitsTo1(flags, i);
        }
      }
    }
    return primes;
  }

  public static void main(String[] args) {
    int[] res = getPrimes(97);
    for (int p : res) {
      if (p == 0) {
        break;
      }
      System.out.println(p);
    }
  }

}
