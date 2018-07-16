package leetcode;

import org.junit.Test;

/**
 * Created by PerkinsZhu on 2018/7/16 17:39
 **/
public class LeetCodeJava {
    @Test
    public void testDemo(){
        int []  array = new int [] {2,7,11,15};
        int [] data = twoSum(array,9);
        System.out.println(data[0]+""+data[1]);
    }

    public int[] twoSum(int[] nums, int target) {
        boolean isEnd = false;
        int [] result = new int[2];
        for(int i = 0 ; i < nums.length && !isEnd; i ++){
            for (int j = i +1;j <nums.length ; j++){
                if(nums[i]+nums[j] == target){
                    isEnd = true;
                    result[0]= i ;
                    result[1]= j ;
                    break;
                }
            }
        }
        return result;
    }
}
