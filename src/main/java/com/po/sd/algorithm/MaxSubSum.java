package com.po.sd.algorithm;

/**
 * Created by ZJ on 21/08/2018.
 */
public class MaxSubSum {

  /**
   * 在数组中查找连续的一个子序列数组，这个子序列数组满足：若对其求和，和数是所有连续子序列数组中最大的。若中间Sum 结果小于0，则子序列应从下一个位置开始计算。
   *
   * @param dts 传入数组参数
   * @return 数组中 Sum 求和最大的连续子序列
   */
  public static double getMaxSubSum(double[] dts) {

    double maxSum = 0.0;
    double tmpSum = 0.0;

    for (double dt : dts) {
      tmpSum += dt;
      if (tmpSum < 0) {
        tmpSum = 0.0;
      } else {
        maxSum = tmpSum;
      }
    }
    return maxSum;
  }

  public static void main(String[] args) {

    double[] dts = new double[]{
        1.0, -2.3, 3.4, 5, 6, -12, 76, 23
    };

    System.out.println(MaxSubSum.getMaxSubSum(dts));
  }
}
