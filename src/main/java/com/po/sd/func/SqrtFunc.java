package com.po.sd.func;

/**
 * C 语言版求平方根
 *
 * Created by ZJ on 15/08/2018.
 */
public class SqrtFunc {

  /**
   * C 语言版求平方根
   *
   * 基本原理：寻找一个数 x 的平方和被开方数 agr 的差值小于设定的精度值 prec，则该数 x 即 agr 的平方根。
   *
   * 推导公式：
   * agr = x*x
   * agr/x = x
   * agr/x + x = x + x = 2x
   * (agr/x + x)/2 = x
   *
   * 即
   *
   * x = (agr / x + x) / 2.0
   *
   * @param agr 被开方数
   * @param prec 精度，大于零。eg：0.001
   */
  public static double getSqrt(double agr, double prec) {
    double x = 1.0;
    double cheak;
    do {
      x = (agr / x + x) / 2.0;
      cheak = x * x - agr;
    } while ((cheak >= 0 ? cheak : -cheak) > prec);
    return x;
  }

  public static void main(String[] args) {
    SqrtFunc.getSqrt(9, 0.001);
  }

}
