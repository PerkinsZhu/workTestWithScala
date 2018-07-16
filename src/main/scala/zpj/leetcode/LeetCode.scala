package zpj.leetcode

import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/7/16 13:37
  **/
class LeetCode {


  @Test
  def demo(): Unit = {
    twoSum(Array(2,7,11,15), 9).foreach(println)
  }


  def twoSum(nums: Array[Int], target: Int): Array[Int] = {
    val size = nums.length
    val map = nums.zipWithIndex.toMap
    var a = 0
    var b = 0
    while (a == b ){
      val temp = target - nums(a)
      if(map.contains(temp)){

      }
    }
    Array()
  }

}
