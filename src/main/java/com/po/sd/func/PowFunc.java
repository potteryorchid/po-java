package com.po.sd.func;

/**
 * Created by ZJ on 22/08/2018.
 */
public class PowFunc {

  /**
   * 计算 X^n 的明显算法是使用 n-1 次乘法自乘。有一种递归算法效果更好。n<=1 是这种递归的基准情形。否则，若 n 是偶数，我们有 X^n = X^(n/2) * X^(n/2)，如果
   * N 是奇数，则 X^n = X^((n-1)/2) * X^((n-1)/2) * X 。
   */
  public static long pow(long x, int n) {
    if (n == 0) {
      return 1l;
    }
    if (isEven(n)) {
      return pow(x * x, n / 2);
    } else {
      return pow(x * x, (n - 1) / 2) * x;
    }
  }

  /**
   * 位运算判断一个数是否是偶数，若偶数返回 true.
   *
   * @param x The input value.
   * @return Boolean
   */
  public static boolean isEven(long x) {
    if ((x & 1l) == 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 取余数运算判断一个数是否是奇数，若奇数返回 true.
   *
   * @param x The input value.
   * @return Boolean
   */
  public static boolean isOdd(long x) {
    if ((x % 2) == 1) {
      return true;
    } else {
      return false;
    }
  }

  public static void main(String[] args) {
    System.out.println(pow(2, 8));
  }
}
