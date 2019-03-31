package com.po.leetcode;

/**
 * 找出两个数组的下标，使得两个值相加等于给定值
 *
 * Created by po on 28/03/2019.
 */
public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        if (nums.length == 0) {
            throw new IllegalArgumentException("No such two numbers");
        }

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 1 + i; j < nums.length; j++) {
                if (target == (nums[i] + nums[j])) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("No such two numbers");
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        TwoSum t = new TwoSum();
        int[] tt = t.twoSum(nums, target);
        if (tt == null) {
            System.out.println("null");
        } else {
            System.out.println(tt[0] + "==" + tt[1]);
        }

    }
}
