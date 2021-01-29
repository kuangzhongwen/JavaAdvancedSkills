package io.kzw.advance.main;

import java.util.*;

/**
 * Leet code - ByteDance.
 *
 * @author kuang on 2018/12/10.
 */
public class LeetCode_ByteDance {

    public static void main(String[] args) {
//        System.out.println(lengthOfLongestSubstring(" "));
//        System.out.println(multiply("123456789", "987654320"));
//        System.out.println(reverseWords(" the sky is blue "));
        List<List<Integer>> result = threeSum(new int[]{-4, -2, 1, -5, -4, -4, 4, -2, 0, 4, 0, -2, 3, 1, -5, 0});
        System.out.println(Arrays.toString(result.toArray()));
    }

    // ================================ 字符串 ================================================

    /**
     * 1. 无重复字符的最长子串
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     * 示例 1:
     * <p>
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     * <p>
     * 输入: "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     * <p>
     * 输入: "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     */
    private static int lengthOfLongestSubstring(String s) {
        int len = s.length();
        if (len == 1)
            return 1;
        int maxLen = 0;
        Map<Integer, Character> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            map.clear();
            Character character = s.charAt(i);
            map.put(i, character);
            for (int j = i + 1; j < len; j++) {
                if (map.containsValue(s.charAt(j))) {
                    break;
                } else if (map.get(j - 1) != null) {
                    map.put(j, s.charAt(j));
                }
            }
            maxLen = Math.max(maxLen, map.size());
        }
        return maxLen;
    }

    /**
     * 2. 最长公共前缀
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * <p>
     * 如果不存在公共前缀，返回空字符串 ""。
     * <p>
     * 示例 1:
     * <p>
     * 输入: ["flower","flow","flight"]
     * 输出: "fl"
     * 示例 2:
     * <p>
     * 输入: ["dog","racecar","car"]
     * 输出: ""
     * 解释: 输入不存在公共前缀。
     * 说明:
     * <p>
     * 所有输入只包含小写字母 a-z 。
     */
    private static String longestCommonPrefix(String[] strs) {
        int len = strs.length;
        if (len == 0 || strs[0].equals("")) {
            return "";
        }
        String common = strs[0];
        for (int i = 1; i < len; i++) {
            int lenI = strs[i].length();
            if (lenI == 0) {
                return "";
            }
            int commonLen = common.length();
            if (lenI < commonLen) {
                for (int j = 0; j < lenI; j++) {
                    if (common.charAt(j) != strs[i].charAt(j)) {
                        common = strs[i].substring(0, j);
                        break;
                    }
                    if (j == lenI - 1) {
                        common = strs[i];
                    }
                }
            } else {
                for (int j = 0; j < commonLen; j++) {
                    if (common.charAt(j) != strs[i].charAt(j)) {
                        common = strs[i].substring(0, j);
                        break;
                    }
                }
            }
        }
        return common;
    }

    /**
     * 3. 字符串的排列
     * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
     * <p>
     * 换句话说，第一个字符串的排列之一是第二个字符串的子串。
     * <p>
     * 示例1:
     * <p>
     * 输入: s1 = "ab" s2 = "eidbaooo"
     * 输出: True
     * 解释: s2 包含 s1 的排列之一 ("ba").
     * <p>
     * <p>
     * 示例2:
     * <p>
     * 输入: s1= "ab" s2 = "eidboaoo"
     * 输出: False
     * <p>
     * <p>
     * 注意：
     * <p>
     * 输入的字符串只包含小写字母
     * 两个字符串的长度都在 [1, 10,000] 之间
     */
    private static boolean checkInclusion(String s1, String s2) {
        int l1 = s1.length();
        int l2 = s2.length();
        int[] c1 = new int[26];
        int[] c2 = new int[26];
        for (char c : s1.toCharArray())
            c1[c - 'a']++;
        for (int i = 0; i < l2; i++) {
            if (i >= l1)
                --c2[s2.charAt(i - l1) - 'a'];
            c2[s2.charAt(i) - 'a']++;
            if (Arrays.equals(c1, c2))
                return true;
        }
        return false;
    }

    /**
     * 4. 字符串相乘
     * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
     * <p>
     * 示例 1:
     * <p>
     * 输入: num1 = "2", num2 = "3"
     * 输出: "6"
     * 示例 2:
     * <p>
     * 输入: num1 = "123", num2 = "456"
     * 输出: "56088"
     * 说明：
     * <p>
     * num1 和 num2 的长度小于110。
     * num1 和 num2 只包含数字 0-9。
     * num1 和 num2 均不以零开头，除非是数字 0 本身。
     * 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。
     */
    private static String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0"))
            return "0";
        num1 = new StringBuilder(num1).reverse().toString();
        num2 = new StringBuilder(num2).reverse().toString();
        int len1 = num1.length();
        int len2 = num2.length();
        int[] d = new int[len1 + len2];
        for (int i = 0; i < len1; i++) {
            int numI0 = num1.charAt(i) - '0';
            for (int j = 0; j < len2; j++) {
                int numI1 = num2.charAt(j) - '0';
                // 模拟数学的每位运算，拿到乘积的数字
                d[i + j] += numI0 * numI1;
            }
        }
        System.out.println(Arrays.toString(d));
        StringBuilder stringBuilder = new StringBuilder();
        int dLen = d.length;
        // convert to string
        for (int i = 0; i < dLen; i++) {
            int num = d[i] / 10;
            int yu = d[i] % 10;
            stringBuilder.insert(0, yu);
            if (i < dLen - 1)
                d[i + 1] += num;
        }
        System.out.println(stringBuilder.toString());
        while (stringBuilder.length() > 0 && stringBuilder.charAt(0) == '0') {
            stringBuilder.deleteCharAt(0);
        }
        return stringBuilder.toString();
    }

    /**
     * 5. 翻转字符串里的单词
     * 给定一个字符串，逐个翻转字符串中的每个单词。
     * <p>
     * 示例:
     * <p>
     * 输入: "the sky is blue",
     * 输出: "blue is sky the".
     * 说明:
     * <p>
     * 无空格字符构成一个单词。
     * 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
     * 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
     * 进阶: 请选用C语言的用户尝试使用 O(1) 空间复杂度的原地解法。
     */
    private static String reverseWords(String s) {
        if (!s.contains(" "))
            return s;
        String[] array = s.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (int len = array.length, i = len - 1; i >= 0; i--) {
            if (!array[i].equals(" ") && !array[i].equals("") && array[i] != null)
                stringBuilder.append(array[i]).append(" ");
        }
        if (stringBuilder.length() > 0)
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    /**
     * 6. 简化路径
     * 给定一个文档 (Unix-style) 的完全路径，请进行路径简化。
     * <p>
     * 例如，
     * path = "/home/", => "/home"
     * path = "/a/./b/../../c/", => "/c"
     * <p>
     * 边界情况:
     * <p>
     * 你是否考虑了 路径 = "/../" 的情况？
     * 在这种情况下，你需返回 "/" 。
     * 此外，路径中也可能包含多个斜杠 '/' ，如 "/home//foo/" 。
     * 在这种情况下，你可忽略多余的斜杠，返回 "/home/foo" 。
     */
    private static String simplifyPath(String path) {
        // Write your code here
        String[] Names = path.split("/");
        Stack<String> stack = new Stack<>();
        for (int i = 0, len = Names.length; i < len; ++i) {
            if (".".equals(Names[i]) || "".equals(Names[i]))
                continue;
            if ("..".equals(Names[i])) {
                if (!stack.empty())
                    stack.pop();
            } else
                stack.push(Names[i]);
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (!stack.empty()) {
            stringBuilder.insert(0, stack.pop());
            stringBuilder.insert(0, "/");
        }
        if (stringBuilder.length() == 0)
            stringBuilder.append("/");
        return stringBuilder.toString();
    }

    /**
     * 7. 复原IP地址
     * 给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。
     * <p>
     * 示例:
     * <p>
     * 输入: "25525511135"
     * 输出: ["255.255.11.135", "255.255.111.35"]
     */
    private static List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        int len = s.length();
        for (int i = 1; i < 4 && i < len - 2; i++) {
            for (int j = i + 1; j < i + 4 && j < len - 1; j++) {
                for (int k = j + 1; k < j + 4 && k < len; k++) {
                    String s1 = s.substring(0, i),
                            s2 = s.substring(i, j),
                            s3 = s.substring(j, k),
                            s4 = s.substring(k, len);
                    if (isValid(s1) && isValid(s2)
                            && isValid(s3) && isValid(s4)) {
                        res.add(s1 + "." + s2 + "."
                                + s3 + "." + s4);
                    }
                }
            }
        }
        return res;
    }

    private static boolean isValid(String s) {
        if (s.length() > 3 || s.length() == 0
                || (s.charAt(0) == '0' && s.length() > 1)
                || Integer.parseInt(s) > 255)
            return false;
        return true;
    }

    // ================================ 数组与排序 ================================================

    /**
     * 1. 三数之和
     * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
     * <p>
     * 注意：答案中不可以包含重复的三元组。
     * <p>
     * 例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
     * <p>
     * 满足要求的三元组集合为：
     * [
     * [-1, 0, 1],
     * [-1, -1, 2]
     * ]
     */
    private static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) break;
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int j = nums.length - 1;
            int target = 0 - nums[i];
            int k = i + 1;
            while (k < j) {
                if (nums[k] + nums[j] == target) {
                    List<Integer> item = Arrays.asList(nums[i], nums[k], nums[j]);
                    result.add(item);
                    while (k < j && nums[k] == nums[k + 1]) k++;
                    while (k < j && nums[j] == nums[j - 1]) j--;
                    k++;
                    j--;
                } else if (nums[k] + nums[j] < target) {
                    k++;
                } else {
                    j--;
                }
            }
        }
        return result;
    }
}
