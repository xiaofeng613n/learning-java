package com.xf.basic.offer;

/**
 * @Auther: xiaofeng
 * @Date: 2019-08-18 11:12
 * @Description:
 * 数组中的重复数字
 */
public class Solution03 {

    //输出数组中重复的数字，空间复杂度O(1)，时间复杂度O(n)
    public static int duplicate(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            while (arr[i] != i) {
                if (arr[i] == arr[arr[i]]) {
                    return arr[i];
                } else {
                    int temp = arr[i];
                    arr[i] = arr[temp];
                    arr[temp] = temp;
                }
            }
        }
        return -1;
    }

    public static int getDuplication(int[] arr) {
        int start = 1;
        int end = arr.length - 1;
        while (end >= start) {
            int middle = (end -start) /2 + start;
            int count = getCount(arr, start, middle);
            if (end == start) {
                if ( count > 1)
                    return start;
                else
                    break;
            }
            if (count > middle - start + 1) {
                end = middle;
            } else {
                start = middle + 1;
            }
        }
        return -1;
    }

    private static int getCount(int[] arr, int start, int end) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if ( arr[i] >= start && arr[i] <= end)
                count ++;
        }
        return count;
    }


    public static void main(String[] args) {
        int[] arr = {2, 1, 3, 1};
        System.out.println(duplicate(arr));

        int[] arr2 = {2, 3, 5, 4, 3, 2, 6, 7};
        System.out.println(getDuplication(arr2));
    }
}
