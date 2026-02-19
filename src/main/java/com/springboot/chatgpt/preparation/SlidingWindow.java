package com.springboot.chatgpt.preparation;


import java.util.*;

public class SlidingWindow {

    public static void main(String args[]) {
        //LongestSubarrayWithinGivenArrayForSum
        int arr[] = {1,2,1,1,4,5,6,1,2,1};

    }
    int check(int arr[],int k) {
        int maxLen = 0;
        int left = 0;
        int sum = 0;
        for(int right =0;right< arr.length;right++) {
            sum += arr[left];
            while(sum>k) {
                sum -= arr[left];
                left++;
            }
            if(sum==k) {
                maxLen++;
            }
        }

        return maxLen;
    }

    //ongestSubstirngWithoutRepeatingCharacters
    int check(String str) {
        Set<Character> set = new HashSet<>();
        int maxLen = 0;
        int left = 0;
        for(int r = 0;r<str.length();r++) {
            while(set.contains(str.charAt(r))) {
                set.remove(str.charAt(left));
                left++;
            }
            set.add(str.charAt(left));
            maxLen = Math.max(maxLen,r-left+1);
        }
        return maxLen;
    }

    //CheckAnagram

    boolean checkAnagram(String s1, String s2) {
        if(s1.length() != s2.length()) {
            return false;
        }
        int[] result = new int[256];
        for(int i=0;i<s1.length();i++) {
            result[s1.charAt(i)]++;
            result[s2.charAt(i)]--;
        }
        for(int i=0;i< s1.length();i++) {
            if(result[s2.charAt(i)] != 0) {
                return false;
            }
        }
        return true;
    }
}
