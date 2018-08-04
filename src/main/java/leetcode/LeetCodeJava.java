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
        System.out.println("---" + lengthOfLongestSubstring("abba"));
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


}
