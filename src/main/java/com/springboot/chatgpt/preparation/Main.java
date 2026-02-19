package com.springboot.chatgpt.preparation;

import java.util.*;
class Solution {
    public int[] getDistances(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];

        Map<Integer, Long> countMap = new HashMap<>();
        Map<Integer, Long> sumMap = new HashMap<>();

        for(int i =0;i<n;i++) {
            int val = nums[i];

            long count = countMap.getOrDefault(val, 0L);
            long sum = sumMap.getOrDefault(val, 0L);

            long contri = count * i - sum;

            res[i] += (int) contri;

            countMap.put(val, count + 1);
            sumMap.put(val, sum + i);
        }
            countMap.clear();
            sumMap.clear();

            for(int i=n-1;i>=0;i--) {
                int val = nums[i];

                long count = countMap.getOrDefault(val,0L);
                long sum = sumMap.getOrDefault(val, 0L);
                long contri = sum-count*i;

                res[i] += (int) contri;
                countMap.put(val, count+1);
                sumMap.put(val,sum+i);
            }
        return res;
    }
}

public class Main {
    public static void main(String[] args) {

        Solution s = new Solution();
        int arr[] = {4,3,4,2,4,3};
        System.out.println(Arrays.toString(s.getDistances(arr)));
    }

}
