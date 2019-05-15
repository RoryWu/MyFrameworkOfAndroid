package com.jake.example.jalgorithm;

import java.util.List;

/**
 * @author
 * @version 1.0
 * @date 19-2-22
 */
public class Sort {

    public static void sortNormal(int[] list) {
        for (int i = 0; i < list.length - 1; i++) {
            for (int j = list.length-1 ; j > i ; j --) {
                if (list[i] > list[j]) {
                    list[i] = swap(list[j], list[j] = list[i]);
                }
            }
        }

        for (int k : list) {
            System.out.println(k);
        }
    }

    /***
     * 问题75 一个数组中只有 0 , 1 , 2 三种值, 请将他们排序
     */
    /**
     * 计数排序
     * 整体思路 把数组中的所有的 0 , 1 , 2  的个数分别统计出来, 然后按照顺序放到新的数组中
     */
    public static int[] sortByRecordNum(int[] list) throws Exception {
        int[] count = new int[3];
        for (int i = 0; i < list.length; i++) {
            if ((list[i] < 0) || (list[i] > 2)) {
                throw new Exception("list is unlegal");
            }
            count[list[i]]++;
        }

        int index = 0;
        for (int i = 0; i < count[0]; i++) {
            list[index++] = 0;
        }
        for (int i = 0; i < count[1]; i++) {
            list[index++] = 1;
        }
        for (int i = 0; i < count[2]; i++) {
            list[index++] = 2;
        }
        return list;
    }

    public static int swap(int value1, int value2) {
        return value1;
    }


}
