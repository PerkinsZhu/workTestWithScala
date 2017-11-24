package zpj.baseTest

import java.io.IOException
import java.util
import java.util.Date

import com.mongodb.util.JSON
import org.joda.time.DateTime
import play.api.libs.json.Json

import scala.collection.immutable.Stream.cons
import scala.collection.mutable.ListBuffer
import scala.runtime.Nothing$
import scala.util.Sorting

/**
  * Created by PerkinsZhu on 2017/9/22 16:11. 
  */
object BaseTest {

  def showEach(ele:Int*) = {
    ele.foreach(println _)
  }

  def testList():Unit={
    showEach(List(1,2,3):_*)
  }

  def testTypeVariable(): Unit = {
    class Car {}
    class DaZhong extends Car {}
    class CarManager {
      def showInfo[T <: Comparable[T], S](car: Array[T], other: T): Unit = {
        println(car.toString)
        car(0).compareTo(other)
      }
 /* def toCar[M <% Car](mm: M): Unit = {
        showInfo(Array(mm), AnyRef)
   }*/
    }
    trait BMWCar[T, C[_]] extends Car {

    }

    val carManager = new CarManager()
  }

  case  class  APP(name:String,info:Option[String])
  implicit val formate = Json.format[APP]
  def testCaseClass(): Unit = {
    println(Json.toJson(APP("JACL",None)))

  }

  def testEq(): Unit = {
    val a = "aaa"
    val b = "aaa"
    println(a == b)
    println(a eq b)
    println(a ne b)
  }

  lazy val t1 = {println("i am t1");10}
  lazy val t2 = {println("i am t2");20} // lazy 只能对val进行修饰
  def testLazy(): Unit = {
    t1
    t2
    t1
    t2
    println(" start .....")
  }

  def testStream(): Unit = {
/*    val data = Stream(1,2,3,4,5)
    data.foreach(println _)*/
    show({println("XXXXXX");10/2})
  }
//  def show(x :Int):Unit={
  def show(x: => Int):Unit={
    println("start..")
    println(x)//在这里使用x的值的时候才去计算x的值
    println("end..")
    lazy val y = {println("----");x}//注意下面调用了两次y,但是这里的输出语句只执行了一下。而对于x值方法中掉用了两次所以会执行两次x值的运算
    println(y,y)
    val fruit = new Friut {
      override def showInfo: Unit = println("apple")
    }
    fruit.showInfo
//    constant(10).foreach(println _)
    println()
  }
  def constant[A](a: A): Stream[A] = cons(a, constant(a))

trait Friut{
  def showInfo:Unit
}

  class A{}
  object B
  def testType(): Unit = {
    val a = new A
    val b:B.type = B
    println(b)
  }

  def testBound(): Unit = {
    /**
      * 上界是为了强制参数具有某个方法
      * 下界是为了进行类型转换
      */
    trait Animal{
      def run():Unit

    }
    class Tigger extends Animal{
      override def run: Unit = {println("tigger running ...")}
      def eat={println("eating.....")}
    }

    def startRun[T <: Animal](an:T){
      an.run()//为了限定an参数必须具有run()方法则强制指定[T <: Animal]
    }

    class Queue[+T](private val leading:List[T],private val trailing:List[T]){
      def append[U >: T](x:U)= new Queue[U](leading,x::trailing)
    }

    def demo02(tiggers:List[Tigger]):List[Animal] = {
      val animals:List[Animal] = tiggers
      animals.foreach(_.run())
      Nil
    }
    demo02(List(new Tigger()))
  }

  def testNullUnit(): Unit = {
    println(Unit)
    println(Unit)
    val n:Unit=Unit
    println(n)
    println(10.asInstanceOf[Unit])
    println(new Student().asInstanceOf[Unit])
  println("----------------")
    println()
    class Hello{type Hello;val data= "hello"}
    val a = new Hello
    println(a.data)
    println(a.getClass)
    object BB{val data="bbbb";override def toString: String = "i am  bbbbb"}
    class BB{
    override def toString: String ="i am  class BB ----class---"}
    println(BB)
    println(new BB())
    val time = new DateTime()
    print(time.centuryOfEra().addToCopy(10))
  }

  def testTime(): Unit = {
    println(System.currentTimeMillis)
    println(new DateTime().getMillis)
    println(new Date().getTime)
    println(new DateTime(1511495763658l).toString("yyyy-MM-dd HH:mm:ss:SSS"))
    println(new DateTime(1511487662517l).toString("yyyy-MM-dd HH:mm:ss:SSS"))
  }

  def main(args: Array[String]): Unit = {
    //    testFor()
    //    testThread()
    //    testReduce()
//    testTypeVariable()
//    testList
//    testCaseClass()
//    testSort()
//    testEq()
//    testLazy()
//    testStream()
//    testType()
//    testBound()
//   testNullUnit()
    testTime()
  }

  class Not extends Nothing${
    def showInfo= println("i am Not")
  }

  def testSort()={
    val map = Map(10481 -> 3.0, 5900 -> 5.0, 5395 -> 1.0).toList.sortWith(_._2>_._2).take(2)
    println(map)
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
