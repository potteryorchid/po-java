package com.po.sd.algorithm.search;

/**
 * Created by ZJ on 21/08/2018.
 */
public class BinarySearch {

  /**
   * 二分查找
   * 时间：O(log N)
   * 条件：二分查找适用于有序的集合
   * 原理：查找先取中间点数，若中间数就是要查找数，则返回，若中间数比要查找数大，则要查找的数在集合左边，反之在右边。不断重复该逻辑查找，直到跳出循环。
   *
   * @param arr 查找数据集合
   * @param con 要查找的数
   * @param <AnyType> 泛型参数，表示参数可以是任意实现继承 Comparable 的类型
   */
  public static <AnyType extends Comparable<? super AnyType>> int binarySearch(AnyType[] arr,
      AnyType con) {

    int low = 0, high = arr.length - 1;

    while (low <= high) {
      int mid = (low + high) / 2;

      if (con.compareTo(arr[mid]) < 0) {
        high = mid - 1;
      } else if (con.compareTo(arr[mid]) > 0) {
        low = mid + 1;
      } else {
        return mid;
      }
    }

    return -1;
  }

  public static void main(String[] args) {
    Integer[] d = new Integer[]{2, 5, 12, 56, 102, 870, 1923};

    System.out.println(BinarySearch.binarySearch(d, 54));
  }

}
