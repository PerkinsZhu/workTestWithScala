package leetcode;

import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by PerkinsZhu on 2018/7/16 17:39
 **/
public class LeetCodeJava {
    @Test
    public void testDemo() {
        int[] array = new int[]{2, 7, 11, 15};
        int[] data = twoSum(array, 9);
        System.out.println(data[0] + "" + data[1]);
    }

    public int[] twoSum(int[] nums, int target) {
        boolean isEnd = false;
        int[] result = new int[2];
        for (int i = 0; i < nums.length && !isEnd; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    isEnd = true;
                    result[0] = i;
                    result[1] = j;
                    break;
                }
            }
        }
        return result;
    }


    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "ListNode{" + "val=" + val + ", next=" + next + '}';
        }
    }

    @Test
    public void testAddTwoNumbers() {
        BigDecimal a = new BigDecimal("243");
        BigDecimal b = new BigDecimal("564");
        ListNode n1 = getResult(a);
        ListNode n2 = getResult(b);
        System.out.println(n1);
        System.out.println(n2);
        System.out.println(addTwoNumber4(n1, n2));
    }

    public ListNode addTwoNumber4(ListNode l1, ListNode l2) {
        ListNode h = new ListNode(0), t = h;
        int u = 0;
        int n1 = 0;
        int n2 = 0;
        while (l1 != null || l2 != null) {
            n1 = 0;
            n2 = 0;
            if (l1 != null) {
                n1 = l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                n2 = l2.val;
                l2 = l2.next;
            }

            int n3 = n1 + n2 + u;
            u = n3 >= 10 ? 1 : 0;
            t.next = new ListNode(n3 % 10);
            t = t.next;
        }
        if (u == 1) {
            t.next = new ListNode(1);
        }
        return h.next;
    }

    private ListNode createResult(String result) {
        char[] temp = result.toCharArray();
        ListNode h = new ListNode(0);
        ListNode t = h;
        for (int i = 0; i < temp.length; i++) {
            ListNode node = new ListNode(temp[i] - '0');
            t.next = node;
            t = node;
        }
        return h.next;
    }

    public ListNode addTwoNumbers3(ListNode l1, ListNode l2) {
        ListNode h = new ListNode(0);
        ListNode t = h;
        int m = 0;
        int i;
        int j;
        while (l1 != null || l2 != null) {
            i = 0;
            j = 0;
            if (l1 != null) {
                i = l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                j = l2.val;
                l2 = l2.next;
            }

            int n3 = i + j + m;
            if (n3 >= 10) {
                m = 1;
                ListNode n = new ListNode(n3 - 10);
                t.next = n;
                t = n;
            } else {
                m = 0;
                ListNode n = new ListNode(n3);
                t.next = n;
                t = n;
            }
        }
        if (m == 1) {
            t.next = new ListNode(1);
        }
        return h.next;
    }


    @Nullable
    private ListNode createResult(BigDecimal result) {
        char[] temp = result.toString().toCharArray();
        ListNode head = null;
        ListNode tempNode = null;
        for (int i = temp.length - 1; i > -1; i--) {
            if (head == null) {
                head = new ListNode(temp[i] - '0');
                tempNode = head;
            } else {
                ListNode node = new ListNode(temp[i] - '0');
                tempNode.next = node;
                tempNode = node;
            }
        }
        return head;
    }

    @Nullable
    private ListNode getResult(BigDecimal result) {
        char[] temp = result.toString().toCharArray();
        ListNode head = null;
        ListNode tempNode = null;
        for (int i = 0; i < temp.length; i++) {
            if (head == null) {
                head = new ListNode(temp[i] - '0');
                tempNode = head;
            } else {
                ListNode node = new ListNode(temp[i] - '0');
                tempNode.next = node;
                tempNode = node;
            }
        }
        return head;
    }


    @Test
    public void testBigInteger() {
        BigDecimal a = new BigDecimal("10000000000000000000000000000000001");
        BigDecimal b = new BigDecimal("654");
        BigDecimal c = a.add(b);
        System.out.println(c);
        System.out.println(a.add(b));
    }


    @Test
    public void testLengthOfLongestSubString() {
        System.out.println("---" + lengthOfLongestSubstringTwo("au"));
        System.out.println("---" + lengthOfLongestSubstringThree("au"));
        System.out.println("---" + lengthOfLongestSubstringFour("au"));
    }

    public int lengthOfLongestSubstringFour(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[128];
        for (int i = 0, j = 0; j < n; j++) {
            i = Math.max(i, index[s.charAt(j)]);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }

    public int lengthOfLongestSubstringThree(String s) {
        int len = s.length();
        int res = 0, i = 0, j = 0;
        int[] data = new int[128];
        while (j < len) {
            char charr = s.charAt(j);
            if (data[charr] != 0) {
                res = Math.max(res, (j - i));
                i = data[charr] > i ? data[charr] : i;
            }
            data[charr] = ++j;
        }
        return Math.max(res, (j - i));
    }

    public int lengthOfLongestSubstring(String s) {
        if (s.trim().length() == 0) {
            if (s.length() == 0) {
                return 0;
            } else {
                return 1;
            }
        }
        char[] array = s.trim().toCharArray();
        int[] data = new int[172];
        int i = 200;
        int count = 0;
        int endIndex = 200;
        boolean isFirst = true;
        for (; i < array.length + 200; i++) {
            int temp = array[i - 200];
            int lastIndex = data[temp];
            if (lastIndex != 0) {
                int m = i - lastIndex;
                count = m > count ? m : count;
                endIndex = i;
                isFirst = false;
            } else {
                if (isFirst) count = i - (200 - 1);
            }
            data[temp] = i;
        }
        System.out.println(count);
        int temp = array.length - (endIndex - 200);
        if (temp > count) {
            count = temp;
        }
        return count;
    }

    public int lengthOfLongestSubstringTwo(String s) {
        int result = 0;
        int len;
        String temp = "";
        char[] data = s.toCharArray();
        for (int i = 0; i < data.length; i++) {
            char cchar = data[i];
            int index = temp.indexOf(cchar);
            if (index >= 0) {
                len = temp.length();
                if (result < len) {
                    result = len;
                }
                temp = temp.substring(index + 1);
            }
            temp += cchar;
        }

        len = temp.length();
        if (result < len) {
            result = len;
        }
        return result;
    }


    @Test
    public void test04() {
        int[] a = new int[3];
        a[0] = 2;
        a[1] = 4;
        a[2] = 6;
        int[] b = new int[4];
        b[0] = 4;
        b[1] = 7;
        b[2] = 8;
        b[3] = 10;

        int[] a1 = new int[2];
        a1[0] = 1;
        a1[1] = 2;
        int[] b1 = new int[2];
        b1[0] = 3;
        b1[1] = 4;

        System.out.println("\n--->" + findMedianSortedArrays(a1, b1));
        System.out.println("\n--->" + findMedianSortedArraysTwo(a1, b1));
    }

    public double findMedianSortedArraysTwo(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int len3 = len1 + len2;
        int[] data = new int[len3];
        double mid = 0.0;
        //TODO  找到中位数之后就终止，不再对后面的数据进行循环排序
        if (len3 % 2 == 0) {
            int m = (len3 / 2) - 1;
            int n = len3 / 2;

            for (int i = 0, j = 0, k = 0; k < n; k++) {
                if (i < len1) {

                } else {

                }

                if (nums1[i] > nums2[j]) {
                    j++;
                } else {
                    i++;
                }
            }

        } else {
            int n = len3 / 2;
        }
        return mid;
    }


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int len3 = len1 + len2;
        int[] data = new int[len3];
        int i = 0, j = 0, k = 0;
        for (; i < len1 && j < len2; k++) {
            if (nums1[i] > nums2[j]) {
                data[k] = nums2[j];
                j++;
            } else {
                data[k] = nums1[i];
                i++;
            }
        }

        if (j < len2) {
            for (; j < len2; j++) {
                data[k++] = nums2[j];
            }
        } else {
            for (; i < len1; i++) {
                data[k++] = nums1[i];
            }
        }

        for (int m = 0; m < data.length; m++) {
            System.out.print("\t" + data[m]);
        }

        double mid = 0.0;

        if (len3 % 2 == 0) {
            mid = (data[(len3 / 2) - 1] + data[len3 / 2]) / 2.0;
        } else {
            mid = data[len3 / 2] / 1.0;
        }
        return mid;
    }


    @Test
    public void testFive() {
        String str = "asddsawe";
        System.out.println("\n--->" + longestPalindrome(str));
    }

    public String longestPalindrome(String s) {
        int len = s.length();
        //递归，传入i，j 。 判断是否是回文串 如果是返回长度 ，如果不是 返回0
        class Task {
            //TODO
            public int doTask(int i, int j) {
                if (s.charAt(i) == s.charAt(j)) return 1;
                return doTask(i + 1, j - 1);
            }
        }


        return "";
    }


    @Test
    public void test53() {
        int[] a = new int[5];
        a[0] = -1;
        a[1] = -1;
        a[2] = 6;
        a[3] = -1;
        a[4] = 8;
        System.out.println("\n--->" + maxSubArray(a));
        System.out.println("\n--->" + maxSubArray02(a));
        System.out.println("\n--->" + maxSubArray03(a));
        System.out.println("\n--->" + maxSubArray04(a));
    }


    public int maxSubArray04(int[] nums) {
        int sum = nums[0];
        int ans = sum;
        for (int i = 1; i < nums.length; i++) {
            // 每多一个元素A，只需要比较元素A、所有元素(前面的元素+A)、前面元素 三者最大子串和即可
            sum = sum + nums[i] > nums[i] ? sum + nums[i] : nums[i];
            ans = ans > sum ? ans : sum;
        }
        return ans;
    }

    public int maxSubArray03(int[] nums) {
        class Temp {
            private int doTask(int left, int right) {
                //                System.out.println(left + "----" + right);
                if (left == right) {
                    return nums[left];
                }
                int mid = left + right >> 1;
                int leftMax = doTask(left, mid);
                int rightMax = doTask(mid + 1, right);
                //                System.out.println(left + "--" + mid + "--" + right);
                int midMax01 = nums[mid], sum = 0;
                for (int i = mid; i >= left; i--) {
                    sum += nums[i];
                    midMax01 = Math.max(sum, midMax01);
                }

                sum = 0;
                int midMax02 = nums[mid + 1];
                for (int i = mid + 1; i <= right; i++) {
                    sum += nums[i];
                    midMax02 = Math.max(sum, midMax02);
                }

                int midMax = midMax01 + midMax02;
                //                System.out.println(leftMax + "--" + midMax + "(" + midMax01 + "、" + midMax02 + ")" + "--" + rightMax);
                return Math.max(midMax, Math.max(leftMax, rightMax));
            }
        }
        return new Temp().doTask(0, nums.length - 1);
    }

    public int maxSubArray02(int[] nums) {
        int[] sum = new int[nums.length];
        sum[0] = nums[0];
        int ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
            if (ans < sum[i]) {
                ans = sum[i];
            }
        }
        for (int i = 1; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                int s = sum[j] - sum[i - 1];
                if (s > ans) {
                    ans = s;
                }
            }
        }
        return ans;
    }

    public int maxSubArray(int[] nums) {
        int ans = nums[0];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                int s = 0;
                for (int k = i; k <= j; k++) {
                    s += nums[k];
                }
                if (s > ans) {
                    ans = s;
                }
            }
        }
        return ans;
    }


    @Test
    public void test70() {
        System.out.println("\n--->" + climbStairs(44));
        System.out.println("\n--->" + climbStairs02(44));
    }

    public int climbStairs02(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        int[] data = new int[n];
        data[0] = 1;
        data[1] = 2;
        for (int i = 2; i < n; i++) {
            data[i] = data[i - 1] + data[i - 2];
        }
        return data[n - 1];
    }

    public int climbStairs(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    @Test
    public void test121() {
        int[] a = new int[5];
        a[0] = 7;
        a[1] = 3;
        a[2] = 6;
        a[3] = 9;
        a[4] = 2;
        System.out.println("\n--->" + maxProfit(a));
        System.out.println("\n--->" + maxProfit02(a));
    }

    public int maxProfit02(int[] prices) {
        int res = 0, len = prices.length, sum = 0;
        for (int i = 1; i < len; i++) {
            int temp = prices[i] - prices[i - 1];
            sum = sum >= 0 ? sum + temp : temp;
            res = Math.max(res, sum);
        }
        return res < 0 ? 0 : res;
    }


    public int maxProfit(int[] prices) {
        int res = 0, len = prices.length, sum = 0;
        if (len < 2) return 0;

        int[] data = new int[len - 1];
        for (int i = 1; i < len; i++) {
            data[i - 1] = prices[i] - prices[i - 1];
        }

        sum = data[0];
        res = sum;
        int dl = data.length;
        for (int i = 1; i < dl; i++) {
            sum = sum > 0 ? sum + data[i] : data[i];
            res = Math.max(res, sum);
        }
        return res < 0 ? 0 : res;
    }


    @Test
    public void test198() {
        int[] a = new int[0];
        //        a[0] = 7;
        //        a[1] = 3;
        //        a[2] = 6;
        //        a[3] = 9;
        //        a[4] = 2;
        System.out.println("\n--->" + rob(a));
    }

    public int rob(int[] nums) {
        int len = nums.length;
        if (len == 0) return 0;
        if (len == 1) return nums[0];
        if (len == 2) return Math.max(nums[0], nums[1]);

        int a = nums[0];
        int b = Math.max(nums[0], nums[1]);
        for (int i = 2; i < len; i++) {
            int temp = Math.max(a + nums[i], b);
            a = b;
            b = temp;
        }
        return Math.max(a, b);
    }

    @Test
    public void test303() {
        int[] a = new int[6];
        a[0] = -2;
        a[1] = 0;
        a[2] = 3;
        a[3] = -5;
        a[4] = 2;
        a[5] = -1;
        NumArray obj = new NumArray(a);
        System.out.println("\n--->" + obj.sumRange(0, 2));
        System.out.println("\n--->" + obj.sumRange(2, 5));
        System.out.println("\n--->" + obj.sumRange(0, 5));
    }

    class NumArray {
        private int[] data;

        public NumArray(int[] nums) {
            if (nums == null || nums.length < 1) return;
            int len = nums.length;
            if (len > 0) {
                data = new int[len + 1];
                for (int i = 0; i < len; i++) {
                    data[i + 1] = data[i] + nums[i];
                }
            }
        }

        public int sumRange(int i, int j) {
            return data[j + 1] - data[i];
        }
    }


    @Test
    public void test746() {
        int[] a = new int[10];
        a[0] = 1;
        a[1] = 100;
        a[2] = 1;
        a[3] = 1;
        a[4] = 1;
        a[5] = 100;
        a[6] = 1;
        a[7] = 1;
        a[8] = 100;
        a[9] = 1;
        System.out.println("\n--->" + minCostClimbingStairs(a));
    }

    public int minCostClimbingStairs(int[] cost) {
        int len = cost.length;
        if (len == 1) return cost[0];
        if (len == 2) return Math.min(cost[0], cost[1]);

        int a = cost[0];
        int b = cost[1];
        for (int i = 2; i < len; i++) {
            int temp = Math.min(a, b) + cost[i];
            a = b;
            b = temp;
        }
        return Math.min(a, b);
    }
}
