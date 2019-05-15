package com.jake.example.jalgorithm;

import java.util.Arrays;

/**
 * @author
 * @version 1.0
 * @date 19-2-22
 */
public class Search {
    // 二分搜索
    public static int banary(int[] arr , int n , int target) {
        Arrays.sort(arr);
        int left = 0 , right = n-1; 
        while ( left<=right ){
            int mid = (left + right)/2 ;
            if (arr[mid] == target) {
                return mid;
            } else if (target > arr[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
