package com.jake.example.jalgorithm;

import android.service.autofill.FillRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author
 * @version 1.0
 * @date 19-2-22
 */
public class TestClass {
    public static void main(String[] args) throws Exception {
//        int[] nums = {1,1,2,2,1,2,0,0,1,2
//        };
//        int [] result = Sort.sortByRecordNum(nums);
//        for (int i = 0; i < result.length; i++) {
//            System.out.print("result = "+result[i]);
//        }
//        int a = 1, b = 2;
//        a = Sort.swap(b, b = a);
        
//        int a = 1, b = 2;
//        int [] k = Sort.swap(a, b);
//        System.out.print(k[0] +":"+ k[1]);
        
//        int[] nums = {9,5,8,6,3,7,1,4};
//        Sort.sortNormal(nums);
//        int[] k = {1, 2, 5, 7, 8, 10, 13};
//        int target = 5;
//        int v = Search.banary(k, k.length, target);
//        System.out.print("v="+v);
        
        twoSum1();
//        twoSum2();
//       int leng =  getK("ljadhlfkajdfasjlfiqwm");
//       System.out.print(leng);
//        findSameElementFromTwoData();
    }

    public static void twoSum1() {
//        int[] k = {1, 2, 5, 7, 8, 10, 13};
        int[] k ={0,3,-3,4,-1};
        int target = -1;

        for (int i = 0; i < k.length; i++) {
            int searchRes = Search.banary(k , k.length , target- k[i]);
            if (searchRes != -1 ){
                System.out.print("匹配两个数字:"+ k[i] +":"+ k[searchRes]);
            }
        }
    }

    public static int[] twoSum2() throws Exception {
        int[] k = {1, 2, 5, 7, 8, 10, 13};
        int target = 19;

        int left = 0, right = k.length - 1;
        while (left < right) {
            if (k[left] + k[right] == target) {
                System.out.print("匹配两个数字:"+ k[left] +":"+ k[right]);
                return new int[]{k[left], k[right]};
            } else if (k[left] + k[right] < target) {
                left++;
            } else if (k[left] + k[right] > target) {
                right--; 
            }
        }
        throw new Exception("no pair");
    }

    public static int getK(String s) {

        Set<Character> sChat = new HashSet();

        int length = s.length();
        int maxLength =0, left= 0 , right = 0 ;

        while (left < length && right < length) {
            if (!sChat.contains(s.charAt(right))) {
                sChat.add(s.charAt(right));
                right++;
                maxLength = Math.max(maxLength, right - left);
            } else {
                sChat.remove(s.charAt(left));
                left++;
            } 
        }
        
        return maxLength;

    }

    public static void findSameElementFromTwoData() {
        int[] int1 = {1, 2, 3, 54, 123, 21, 5};
        int[] int2 = {3, 4, 213, 324, 123, 3, 2};

        Set s1 = new HashSet();
        Set<Integer> resultSet = new HashSet();
        for (int i = 0; i < int1.length; i++) {
            s1.add(int1[i]);
        }

        for (int j = 0; j < int2.length; j++) {
            if (s1.contains(int2[j])) {
                resultSet.add(int2[j]);
            }
        }
        for (int k : resultSet) {
            System.out.println(k);
        }
    }
    
    public static void threeSum(int[] nums ) {
//        Array.sort(sum)
//        Collections.sort(sum);
        Arrays.sort(nums);
    }
        
        
}
