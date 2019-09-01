package com.qc.leetcode;

/**
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2 。
 * 请找出这两个有序数组的中位数。要求算法的时间复杂度为 O(log (m+n)) 。
 *
 */
public class FindMedianSortedArrays {
	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int i1 = 0;
        int e1 = nums1.length-1;
        int i2 = 0;
        int e2 = nums2.length-1;
        while( (i1<e1 && i2<e2) || (e1-i1>1) || (e2-i2>1)){
            //delete one smallest and one biggest each turn
            if(e1!=-1 && i1<e1 && (e2==-1 || nums1[i1]<=nums2[i2])){
                i1++;
            }else{
                i2++;
            }
            if(e1!=-1 && i1<e1 && (e2==-1 || nums1[e1]>=nums2[e2])){
                e1--;
            }else{
                e2--;
            }
        }
        int sum = 0;
        int count = 0;
        if(e1!=-1){
            if(i1==e1){
                sum += nums1[i1];
                count++;
            }else if(i1<e1){
                sum += nums1[i1] + nums1[e1];
                count += 2;
            }
        }
        if(e2!=-1){
            if(i2==e2){
                sum += nums2[i2];
                count++;
            }else if(i2<e2){
                sum += nums2[i2] + nums2[e2];
                count += 2;
            }
        }
        return sum/count;
    }
	public static void main(String[] args) {
		System.out.println(findMedianSortedArrays(new int[]{2,3,4,5}, new int[]{1}));
	}
}
