package com.po.sd.algorithm.sort;

import java.util.LinkedList;
import java.util.List;

/**
 * 希尔排序通过将比较的全部元素分为几个区域来提升插入排序的性能。这样可以让一个元素可以一次性地朝最终位置前进一大步。
 * 然后算法再取越来越小的步长进行排序，算法的最后一步就是普通的插入排序，但是到了这步，需排序的数据几乎是已排好的了（此时插入排序较快）。
 *
 * Created by ZJ on 31/08/2018.
 */
public class ShellSort {

  public static int[] asc(int[] ins) {

    return ins;
  }

  public static int[] desc(int[] ins) {
    return ins;
  }

  /**
   * 在大数组中表现优异的步长序列是（斐波那契数列除去0和1将剩余的数以黄金分区比的两倍的幂进行运算得到的数列），3.23606797749979（黄金分区比的两倍）
   *
   * eg：(1, 9, 34, 182, 836, 4025, 19001, 90358,428481, 2034035, 9651787, 45806244, 217378076,
   * 1031612713, …)
   *
   * @return int[] 返回 shell 排序中按斐波那契数列生成的步长数列
   */
  public static long[] fibonacciArray(long[] ins) {
    for (int i = 0; i < ins.length; i++) {
      ins[i] = Math.round(Math.pow(ins[i], 3.23606797749979));
    }
    return ins;
  }

  /**
   * 根据需要排序数组的长度生成 shell 排序需要的 segdewick 步长数组，segdewick 步长数组在排序数据规模不大的情况下是相对高效的步长数组。
   *
   * @param len 需要排序的数组长度
   * @return 步长数组
   */
  public static Long[] segdewickArr(int len) {
    List<Long> res = new LinkedList<>();

    long elm = 0l;

    // 9 * (2^i) ( 2^i -1 ) -1

    for (int i = 0; i < Integer.MAX_VALUE; i++) {
      elm = 9 * (2 << 2 * i - 2 << i) + 1;
      if (elm < len) {
        res.add(elm);
      } else {
        break;
      }
    }
    Long[] resArr = new Long[res.size()];
    return res.toArray(resArr);
  }

  public static long[] test2(long[] ins) {

    return ins;
  }

  public static void main(String[] args) {
//    System.out.println(1.6180339887498948482 * 2);
//    long[] ints = fibonacciArray(new long[]{1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233});
//    for (long in : ints) {
//      System.out.print(in + " ");
//    }

    Long[] re = segdewickArr(4);
    for (Long e : re
        ) {
      System.out.println(e.longValue());
    }
  }

}
