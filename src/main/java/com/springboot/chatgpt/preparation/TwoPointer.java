package com.springboot.chatgpt.preparation;

public class TwoPointer {

    public static int robbing(int arr[]) {

        if(arr.length == 1) {
            return arr[0];
        }

        int pre1 = 0;
        int pre2 = 0;

        for(int num : arr) {
            int curr = Math.max(pre1,pre2+num);
            pre2 = pre1;
            pre1 = curr;
        }
        return pre1;

    }

    public static void main(String[] args) {
        int arr[] = {1, 2,3,1};
        System.out.println(robbing(arr));

        int arr2[] = {2,7,9,3,1};
        System.out.println(robbing(arr2));

    }
}
