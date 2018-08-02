package zpj.leetcode

import org.junit.Test

import scala.collection.mutable.ListBuffer

/**
  * Created by PerkinsZhu on 2018/7/16 13:37
  **/
class LeetCode {
  @Test
  def demo(): Unit = {
    // twoSum(Array(2,7,11,15), 9).foreach(println)
    testAddTwoNode()
  }

  @Test
  def testAddTwoNode(): Unit = {
    //    [2,4,3]
    //    [5,6,4]
    val node1 = new ListNode(2)
    val node2 = new ListNode(4)
    val node3 = new ListNode(3)

    val node4 = new ListNode(5)
    val node5 = new ListNode(6)
    val node6 = new ListNode(4)
    node1.next = node2
    node2.next = node3

    node4.next = node5
    node5.next = node6

    print(addTwoNumbers(node1, node4))
  }


  def num2Node(num: Int): ListNode = {
    var temp: ListNode = null
    var result: ListNode = null
    num.toString.toSeq.reverse.map(i => new ListNode(i.asDigit)) foreach { item =>
      if (temp == null) {
        temp = item
        result = item
      } else {
        temp.next = item
        temp = item
      }
    }
    result
  }

  def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
    val num1 = getNumber(l1)
    val num2 = getNumber(l2)
    num2Node(num1 + num2)
  }

  def getNumber(node: ListNode): Int = {
    var temp = node
    val list = ListBuffer.empty[Int]
    while (temp != null) {
      list.+=(temp.x)
      temp = temp.next
    }
    list.zipWithIndex.map(item => {item._1 * (math.pow(10, item._2)).toInt}).foldLeft(0)(_ + _)
  }


  class ListNode(var _x: Int = 0) {
    var next: ListNode = null
    var x: Int = _x

    override def toString: String = x + " --->" + next
  }

  def twoSum(nums: Array[Int], target: Int): Array[Int] = {
    val size = nums.length
    val map = nums.zipWithIndex.toMap
    var a = 0
    var b = 0
    while (a == b) {
      val temp = target - nums(a)
      if (map.contains(temp)) {

      }
    }
    Array()
  }

}
