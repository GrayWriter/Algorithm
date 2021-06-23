package com.example.algorithmlib.leetcode;

import java.util.Arrays;

/**
 * 2个有序数组的合并
 */
public class LeetCode88 {

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        System.arraycopy(nums2, 0, nums1, m, n);
        Arrays.sort(nums1);
    }

    /**
     * 双指针
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge1(int[] nums1, int m, int[] nums2, int n) {
        int p1 = 0;
        int p2 = 0;
        int pTemp = 0;

        int[] temp = new int[m + n];
        System.arraycopy(nums1, 0, temp, 0, nums1.length);

        while (pTemp < m || p2 < n) {
            if (pTemp == m) {
                nums1[p1++] = nums2[p2++];
            } else if (p2 == n) {
                nums1[p1++] = temp[pTemp++];
            } else if (temp[pTemp] < nums2[p2]) {
                nums1[p1++] = temp[pTemp++];
            } else {
                nums1[p1++] = nums2[p2++];
            }
        }
    }

    /**
     * 逆向双指针
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge2(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1, p2 = n - 1;
        int tail = m + n - 1;

        while (p1 >= 0 || p2 >= 0) {
            if (p1 == -1) {
                nums1[tail--] = nums2[p2--];
            } else if (p2 == -1) {
                nums1[tail--] = nums1[p1--];
            } else if (nums1[p1] < nums2[p2]) {
                nums1[tail--] = nums2[p2--];
            } else {
                nums1[tail--] = nums1[p1--];
            }
        }
    }
}
