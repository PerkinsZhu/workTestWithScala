package zpj.codeanalysis

import scala.runtime.Nothing$

/**
  * Created by PerkinsZhu on 2017/11/16 11:54. 
  */
object ListTest {
  def main(args: Array[String]): Unit = {
/*//    val list = List(1, 2, 3, 4)
    println(null ##)
//    println(null hashCode())
    println(Unit == null)
    println(Unit)
    println(Unit.getClass)
    println(Int)
    println(Int.getClass)
    println(AnyRef.getClass)
    println(Product7)
    println("sdsd".##)
    println("sdsd".hashCode)
    Array(1,2,3,5).sum
    Array(1,2,3,5).foreach[Int](_+10)*/
    val list = List(1,2,3,4,5,6,7,8,9,10)
    println(list.foldRight(10)((a,b)=>{print(a,b);a+b}))
    println()
    list.foldLeft(10)((a,b)=>{print(a,b);a+b})
    println()
    list.scan(10)((a,b)=>{print(a,b);a+b})
    println()
    list.scanLeft(10)((a,b)=>{print(a,b);math.max(a,b)})
    println()
    list.scanRight(10)((a,b)=>{print(a,b);math.max(a,b)})
    println()
    list.reduceLeft((a,b)=>{print(a,b);math.max(a,b)})
    println()
    list.reduceRight((a,b)=>{print(a,b);math.max(a,b)})
  }

}
