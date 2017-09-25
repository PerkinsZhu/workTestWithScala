package zpj.baseTest

import java.io.IOException
import java.util

import scala.util.Sorting

/**
  * Created by PerkinsZhu on 2017/9/22 16:11. 
  */
object BaseTest {

  def testTypeVariable(): Unit = {
    class Car {}
    class DaZhong extends Car {}
    class CarManager {
      def showInfo[T <: Comparable[T], S](car: Array[T], other: T): Unit = {
        println(car.toString)
        car(0).compareTo(other)
      }

      def toCar[M <% Car](mm: M): Unit = {
        showInfo(Array(mm), AnyRef)
      }
    }
    trait BMWCar[T,C[_]] extends Car{

    }

    val carManager = new CarManager()


  }

  def main(args: Array[String]): Unit = {
    //    testFor()
    //    testThread()
    //    testReduce()
    testTypeVariable()
  }


  def testReduce(): Unit = {

    println((1 to 10).reduce((x, y) => x - y))
    println((1 to 10).reduceRight((x, y) => x - y))
    println((1 to 10).reduceLeft((x, y) => x - y))
    //注意区别 fold 有初始参数，reduce没有初始参数
    println((1 to 10).fold(10)((x, y) => {
      println(x, y);
      x - y
    }))
    println((1 to 10).foldLeft(10)((x, y) => {
      println(x, y);
      x - y
    }))
    println((1 to 10).foldRight(10)((x, y) => {
      println(x, y);
      x - y
    }))
  }

  def testThread(): Unit = {
    try {
      56 / 0
    } catch {
      case ex: IOException => ex.printStackTrace()
      case _: Exception => println("有异常抛出") //如果异常不需要使用，则可以使用_J进行代替
    }
  }

  def testFor(): Unit = {
    val array = Array("aaaa", "bbbbb", "ccccc")
    /*    for (i <- array if (i.length > 4); j <- 0 to 20 if (j % 2 == 0)) {
          println(i * j)
        }*/
    (for (i <- array if (i.length > 4); j <- 0 to 20 if (j % 2 == 0)) yield i * j) foreach (item => println(item))
    //    println(data)
    //    data.foreach(item => println(item))
    //    foreach(println _)
    val arrayBuffer = array.toBuffer += ("dddd", "eeeee")
    arrayBuffer ++= Array("fff", "ggg")
    arrayBuffer.foreach(println)
    val tempArray = arrayBuffer.toArray.reverse;
    tempArray.foreach(item => println("-----" + item))
    tempArray.mkString("=====")
    Sorting.quickSort(tempArray)

    tempArray.foreach(item => println("-----" + item))
    val jarray = new util.ArrayList[String]();
    jarray.add("hello")
    import scala.collection.JavaConverters._
    jarray.asScala.foreach(println(_))
    jarray.asScala.asJava.forEach((str: String) => println(str))

  }

}
