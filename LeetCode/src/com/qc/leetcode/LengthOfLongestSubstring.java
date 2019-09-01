package com.qc.leetcode;

/**
 * 无重复字符的最长子串
 * @see https://leetcode-cn.com/articles/longest-substring-without-repeating-characters/
 * 给定一个字符串，找出不含有重复字符的最长子串的长度。
 * 示例：
 * 	给定 "abcabcbb" ，没有重复字符的最长子串是 "abc" ，那么长度就是3。
 * 	给定 "bbbbb" ，最长的子串就是 "b" ，长度是1。
 * 	给定 "pwwkew" ，最长子串是 "wke" ，长度是3。请注意答案必须是一个子串，"pwke" 是 子序列  而不是子串。
 * @author Administrator
 */
public class LengthOfLongestSubstring {
	/**
	 * 优化的滑动窗口
	 * 以前的我们都没有对字符串 s 所使用的字符集进行假设。
	 * 当我们知道该字符集比较小的时侯，我们可以用一个整数数组作为直接访问表来替换 Map。
	 * 常用的表如下所示：
	 * 	int [26] 用于字母 ‘a’ - ‘z’或 ‘A’ - ‘Z’
	 * 	int [128] 用于ASCII码
	 * 	int [256] 用于扩展ASCII码
	 * @param s
	 * @return
	 */
    public static int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[128]; // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }
	
	public static void main(String[] args) {
		System.out.println(lengthOfLongestSubstring("abcdedbc"));
	}
}
