package zpj.baseTest

import java.io.{File, IOException}
import java.text.SimpleDateFormat
import java.util
import java.util.concurrent.Executors
import java.util.{Date, Random}

import akka.actor.{ActorSystem, Cancellable}
import akka.http.scaladsl.model.Uri.Query.Cons
import akka.stream.javadsl.Flow
import akka.stream.scaladsl.JavaFlowSupport.Source
import org.joda.time.{DateTime, DateTimeZone}
import play.api.libs.json.Json
import zpj.tools.WSClientTool

import scala.collection.immutable.Stream.cons
import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.runtime.Nothing$
import scala.util.{Failure, Sorting, Try}
import scala.concurrent.ExecutionContext.Implicits.global
import collection.JavaConverters._
import scala.collection.convert.Wrappers.MutableSeqWrapper
import scala.collection.mutable
import scala.compat.java8.JFunction22
import scala.concurrent.ExecutionContext._
import scala.util.control.NonFatal
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
/*    println(System.currentTimeMillis)
    println(new DateTime().getMillis)
    println(new Date().getTime)*/
    println(new DateTime(1512135057792l).toString("yyyy-MM-dd HH:mm:ss:SSS"))
    println(new DateTime(1513752919598l).toString("yyyy-MM-dd HH:mm:ss:SSS"))
    println(System.currentTimeMillis)
    println(new DateTime(System.currentTimeMillis).toString("yyyy-MM-dd HH:mm:ss:SSS"))
    println(new SimpleDateFormat("yyy-MM-dd hh:mm:ss").format(new Date(1513762144931l)))
   println(new DateTime(1513762144931l).withZone(DateTimeZone.forOffsetHours(8)).toString("yyyy-MM-dd hh:mm:ss"))
    val time = new Date().getTime
    val time2 = new DateTime()
    println(time)
    println(time2)

  }



//ticket@@@QnoHyTTRFxdTCHlkdMT9ObFuenZ0Tnb9g-p_i0esGpU7j6LKFbk-ra46_Jmog7l-Dz0A-0SAKjUOI7E8KRtfQQ
  def testFuture(): Unit = {
    val res = for(i<-doFuture())yield i
    println(res)
  }
  def doFuture():Future[Int]={
    Future{Thread.sleep(3000);10}
  }


  def testConf(): Unit = {
//    val res = ConfigFactory.load("application.conf")
 /*   val res = ConfigFactory.parseFile(new File("application.conf"))
    res.entrySet().asScala.foreach(println _)*/
  }

  def testStream2(): Unit = {
  /*  val data  = numFrom(1).take(10)
    val str = 1 #:: 2 #:: 3 #:: Stream.empty
    data.foreach(i=>{Thread.sleep(100);println(i)})*/
/*    val powers = (0 until 10 ).view.map(Math.pow(10,_))
    println(powers(5))*/
    val v = Vector(1 to 10:_*)
    v map (1+) map (2*) foreach(println)
  }

  def numFrom(num:BigInt):Stream[BigInt]=num #:: numFrom(num+1)

  def threadName = Thread.currentThread.getName
  def testPer(): Unit = {
    val vec = Vector(1 to 10000:_*)
  //vec.par foreach(i=>{println(i+"--"+Thread.currentThread().getName)})
    for(i<-(0 to 1000).par ){println(i+threadName)}
//    val map = new util.HashMap[String,Int]() with mutable.SynchronizedMap[String,Int]
//    map.put("hello",10)

  }

  def test1(): Unit = {
    val (x,y) = BigInt(10) /% 3
    println(x,y)
  }

  def testMethod(): Unit = {
  val t1 = (a:Int,b: Double) => (a*b).toInt
  val t2 =()=> println("----------")
  def t3:Unit = println("----------")
    callBack(t3)
    println("---todo----")
//    def callBack(callBack: ()=>Unit): Unit ={
    def callBack(callBack: =>Unit): Unit ={
      callBack
      println(callBack)
      println(callBack.getClass)
    }
  }

  def testJson(): Unit = {
    val str ="{\"name\":\"jack\",\"age\":12,\"boy\":true}"
    val json = Json.parse(str)
    val boy = (json \ "boy").as[Boolean]
    println(boy == true)
  }

  def testPartialFunction(): Unit = {
    val squareRoot:PartialFunction[Double,Double]={
      case x if x >= 0 => println("---"+x);Math.sqrt(x)
    }
    val x = 23L
    println(squareRoot.isDefinedAt(2))





  }
def isRight(data:String,statue:Boolean): Boolean= {
 if(statue){

   true
 }else{

   false
 }
}

  def testAndThen(): Unit = {
/*    List("a", "bb", "c", "dd", "dd").toStream.filter(ele => {
      println(ele)
      ele.length == 2
    }).take(1) match {
      case Stream.Empty => println("------------")
      case Stream(x) => {
        println("--------" + x)
      }
    }*/

    def one(x: Int, y: Int): Int = x * y
    def two(int: Int): Int = int + 10
    def three(x: Int): Int = x * 10
    //def result(x: Int) = three _ andThen two _

    val f1 = (x: Int, y: Int) => x + y
    val f2 = (y: Int) => y * 2

    val f3 = f1.tupled andThen f2

    println(f3((1, 2)))

    val f4 = (x:Int, y:Int) => (x*10,y)
    val f5 = (x:Tuple2[Int,Int]) => x._2 + x._1
    val f6 = f4.tupled andThen f5

    println(f6(1,4))

  }

  def testListMatch(): Unit = {
    List(1,2,3,45,5,8).filter(_>5) match {
      case x:List[String] =>println(x);
      case Nil => println("_---")
    }
    Nil match {
      case x:List[_] =>println("=====");
      case Nil => println("_---")
    }

  }

  def testWithTimeOfTodayStartOfDay(): Unit = {
    val time = new DateTime(DateTimeZone.forOffsetHours(8)).plusDays(1).withTimeAtStartOfDay().toDate
    println(time)
    println(new Date())
    val temp1 = new DateTime(DateTimeZone.forOffsetHours(8)).plusDays(1).withTimeAtStartOfDay().getMillis - DateTime.now().getMillis
    println(temp1)
    println(temp1 / (1000 * 60))
  }

  def testQueue(): Unit = {
    val queue = mutable.Queue[String]()
    println(queue.hashCode())
    queue.enqueue("a")
    queue.enqueue("b")
    println(queue.hashCode())
    queue.enqueue("c")
    println(queue.head)
    println(queue.dequeue())
    println(queue)
    println(queue.hashCode())
  }

  def testShutDownHook(): Unit = {
    //JVM的shutdownHook
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable {
      override def run(): Unit = {println("---------开始进行关闭----------")}
    }))
    println("---------")
  }

  def testPrivate(): Unit = {
    class Student{
      private[this] val name = "jack"
      def showInfo(): Unit ={
          println(name)
      }

    }
    val student = new Student()
    student.showInfo()
    println()

  }

  def getTime(): Unit = {
    val oneDay = 1000L * 60 * 60 * 24
    // 获取指定时间
/*    val now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-03-01 00:00:000").getTime
    println(now)
    println(now -1000 * 60 *23)
    val start = now - 1000 * 60 * 60 * 24 * 3
    print(start +" --->  "+now )
    println(new DateTime(now).toString("yyyy-MM-dd HH:mm:ss"))
    println(new DateTime(start).toString("yyyy-MM-dd HH:mm:ss"))
    println(new DateTime(1519574404352L).toString("yyyy-MM-dd HH:mm:ss"))
    println(new DateTime(1519833597613L).toString("yyyy-MM-dd HH:mm:ss"))*/
  // 计算时间段
    val now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-03-17 00:00:001").getTime
    println(now)
    val temp = now - (oneDay * 30)
    println(temp)
    val start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-02-02 00:00:000").getTime
    println(new DateTime(now).toString("yyyy-MM-dd HH:mm:ss"))
    println(new DateTime(temp).toString("yyyy-MM-dd HH:mm:ss"))
    println(new DateTime(start).toString("yyyy-MM-dd HH:mm:ss"))
    println(start+"---"+ (start - temp) / oneDay)

    val startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-03-20 00:00:000").getTime

    val endTime =  new Date().getTime

    println(s"$startTime -- $endTime---${(endTime- startTime)/1000}")

   /* 术禾召回率统计时间
    println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-02-26 00:00:000").getTime)
    println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-02-28 00:00:000").getTime)
*/
  }

  def testSychnorized(): Unit = {
  val lock = "LOCK"
  for( i <- 1 to 3){
    Future{
      println(lock.synchronized[Int]{
        println("----i am "+i)
        Thread.sleep(5000)
        i
      }+" is over  ")
    }
  }
  Thread.sleep(10000000)
}

  def testFunction(): Unit = {
    val add = (x:Int, y1:Int, y2:Int, y3:Int, y4:Int, y5:Int, y6:Int, y7:Int, y8:Int, y9:Int, y11:Int, y12:Int, y13:Int, y14:Int, y15:Int, y16:Int, y17:Int, y22:Int, y33:Int, y44:Int, y55:Int) => x + y11

    def deal[T](body: =>T): T = body

    val getNum = 2+5
    println(deal(getNum))
  }

  def testWhile(): Unit = {
    while(true){
      println("--------------")
      Thread.sleep(2000)
    }
  }

  @volatile var totleNUm = 0

  def testVolatile(): Unit = {
    implicit val ec = fromExecutor(Executors.newFixedThreadPool(4))
    for (i <- 1 to 10) {
      Future {
        totleNUm += 1;
        println(totleNUm)
      }
    }
    Thread.sleep(2000)
  }

  def notNullTest(str:String): Unit ={
    println(str)
  }

  def testAnnotation(): Unit = {
    notNullTest("dd")
  }

  def testJsonOption() = {
    println((Json.obj() \ "NAME").asOpt[Int])

  }

  def testStreamQueue(): Unit = {
    val queue = mutable.Queue[Int]()
    queue.enqueue(1)
    queue.enqueue(2)
    queue.enqueue(3)
    queue.enqueue(4)
    var num = 0
    queue.toStream.filter(ele=>{
      println(num)
      num +=1
      ele >2
    }).take(1) match {
      case Stream(res) => println("====="+res)
      case Stream.Empty => println("-----")
    }
    println(queue.dequeue())
    println(num)



  }
  def toInt(me:Int=> Int):Unit={
    println("toInt  start")
    me(add(1,2))
    println("toInt  end")
  }
  def add(a:Int,b:Int):Int={
    println("add  start")
    a + b
  }
  val paser:Int => Int = (str)=> {
    println("=====")
    str.toInt
  }

  def testMethod2():Unit  = {
    println("+-=--=-=-=-")
    toInt(paser)

  }


  def testRegex(): Unit = {
    println("HEKLLO|NIHAO|KEFU".matches(".*?HEKLLO.*?|.*?KEFU.*?"))
  }

  def testForYield(): Unit = {
    (for{
     a <- Future{println("----a----");10}
     b <- Future{println("----b----");10+a;10/0}
     c <- Future{println("----c----");10+b}
     d <- Future{println("----d----");10+c}
    }yield {
      println(d)
    }).onComplete{
      case Failure(ex) => ex.printStackTrace()
    }

    Thread.sleep(1000)
    /*val a = Future[Option[Int]] {
      10/0
      Some(100)
    }
    val b = Future {
      100
    }
    val res = for {
      Some(aa)<- a
      bb <- Future{aa +10}
    } yield bb
    Thread.sleep(50)

    println(res.recover{
      case NonFatal(e) => println(e.getMessage);111
    })

    println(res)*/
  }

  import scala.concurrent.duration._

  def waitAllFuture(): Unit = {
    val fun = (data: Try[Int]) => println(data)
    val data = List(1, 2, 3).map(num => {
      Future {
        Thread.sleep(1000)
        println(num)
        num * 10
      }
    })

    while (data.map(_.isCompleted).contains(false)) {
      Thread.sleep(1000)
    }
  }

  def waitAllWSClient(): Unit = {
    Thread.sleep(5000)
    val ws = WSClientTool.wsClient
    val iter = (1 to 10000 ).iterator
    val list = ListBuffer[Future[String]]()
    while (iter.hasNext) {
      Thread.sleep(5)
      val data = iter.next()
      val future = ws.url("https://www.sogou.com/").get().map(respond => {
        println("receive data"+data)
        if (respond.status == 200) {
          data +"---OK"
        } else {
          data + "--ERROR"
        }
      })
      list += future
    }

    while(list.map(_.isCompleted).contains(false)){
      Thread.sleep(1000)
    }
    for (elem <- list) {
      println("--->"+elem)
    }

    println("---end----")
    System.exit(0)
  }

  def testStringHashCode(): Unit = {
    val hello = "hello"
    val list = List(hello)
    val temp = list(0)
    println(temp.hashCode)
    list.foreach(item => println(item.hashCode))
    println(hello.hashCode)
    again(hello)
  }

  def again(name: String): Unit = {
    println(name.hashCode)
  }

  def testFutureGlobalVariable(): Unit = {
    var num = 0
    val futures = (1 to 100).map(tem => Future {println(num);num += 1}).toList
    waiting(futures)
    println("--->"+num)
    System.exit(0)
  }

  val waiting = (future: List[Future[Unit]]) => {
    while (future.filter(_.isCompleted).contains(false)) {
      Thread.sleep(1000)
    }
  }

  def testSplit(): Unit = {
    println("/".split("/").length)
  }

  def testFlatFuture(): Unit = {
    val future = Future{
      Future{
        10
      }
    }
//    val res = future.flatMap(_)

  }

  case class Aaa(a: Int)

  def testListContain(): Unit = {
    println(List(Aaa(1), Aaa(2)).contains(Aaa(1)))
  }

  def testFolder(): Unit = {
    val map = List( Map("a"->"name","b"->"bbbnb"),Map("xc"->"ccccc","d"->"ddd")).foldLeft(Map.empty[String,String])((a,b)=>a.++(b))
    println(map)
  }

  def testForeach(): Unit = {
    val map = mutable.HashMap[String, List[Tuple2[String, String]]]()
    map.put("a", List(("aaa", "bbbb")))
    map.put("b", List(("bbbb", "ccc")))
    map.foreach {
      case Tuple2(k, v) => println(v)
    }
    map.foreach(item => println(item._2))
    map.foreach((item: (String, List[Tuple2[String, String]])) => println(item._2))
  }

  def main(args: Array[String]): Unit = {
    testMatch()
//    testForeach()
//    testMatch()
//    testFolder()
//    testListContain()
//    testFlatFuture()
//    testSplit()
//      testFutureGlobalVariable()
//    testStringHashCode()
//    getTime()
//    waitAllWSClient()
//    waitAllFuture()

//    getTime()
//    testForYield()
//    testRegex()
//    testListMatch()
//    testMethod2()
//    testStreamQueue()
//    testJsonOption()
//    testAnnotation()
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
//    testTime()
//    testFuture()
//    testConf()
//    testStream2()
//    testPer()
//    testTime()
//    testRandom()
//    testSortedMap()
//    testException()
//    testProxy
//    testJsonToOther()
//    testMatch
//    testTake()
//    testSortedMap()
//    testMethod()
//    testJson()
//    testPartialFunction()
//    testAndThen()
//    testListMatch()
//    testWithTimeOfTodayStartOfDay()
//    testQueue()
//    testShutDownHook()
//    testPrivate()
//    getTime()
//    testSychnorized()
//    testFunction()
//    testWhile()
//      testVolatile()
  }

  def testTake(){
    List(1,2,3).take(10).foreach(println _)
  }

  def testMatch(): Unit ={
    val ss ="Hello"
    ss match{
      case  str if (List("Hello","ss").contains[String](str)) =>{println(str)}
    }

    println("/morenfenlei/903908288413833".matches(".*903908288413833.*"))
    println("/morenfenlei/903908288413833/234234234".matches(".*903908288413833.*"))

    Nil match {
      case Nil => println("--111-")
      case x => println("---"+x)
    }


  }
//master  test1
//master  test2
//master  test3
//  1.1 test1
  //1.1 test4

  import play.api.libs.json._
  import play.api.libs.json.Reads._
  import play.api.libs.functional.syntax._

//  implicit val formatQA = Json.format[QA]

  def testJsonToOther(): Unit ={
    println(Json.toJson(List("aaa","bbb","ccc")))
//    println(Json.toJson(List(QA("JACK",List("sss","sdsd"),"sss"))))
    println(Json.toJson(Map("aaa"->111,"bbb"->2222,"ccc"->3333)))
    println(Json.toJson(Map("aaa"->List("aaa","bbb","ccc"))))
//    println(Json.toJson(HHH("JACK",QA(List("问题1","问题2","问题3"),"123456"))))
//    println(Json.toJson(HHH("JACK",List(QA(List("问题1","问题2","问题3"),"123456")))))
  }

  private def testProxy = {
    while (true) {
      print(System.getProperties().getProperty("http.proxyHost"))
      Thread.sleep(1000)
    }
  }

  object MyEnum extends Enumeration{
    val SS = Value
  }

def testException(): Unit ={
  println(getRes())
}
  def getRes():Int ={
    try {
      10 / 2
    } catch {
      case ex:Exception =>println("-----");10
    }
  }

  def testSortedMap(): Unit ={
    val res = mutable.SortedMap.empty[String,Int]
    res +=("d"->132)
    res +=("a"->122)
    res +=("c"->42)
    res.foreach(println _)
  }
  def testRandom(): Unit ={
    for(i <-1 to 100){
      println(getIp)
    }
  }
  private def getIp = {
    val temp1 = new Random().nextInt(100)+100
    val temp2 = new Random().nextInt(100)+100
    val numIp = new Random().nextInt(240) % (240 - 5 + 1) + 5;
    val ip = "10."+temp1+"."+temp2+"." + numIp
    ip
  }
/*  class Not extends Nothing{
    def showInfo= println("i am Not")
  }*/

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
   /* val jarray = new util.ArrayList[String]();
    jarray.add("hello")
    import scala.collection.JavaConverters._
    jarray.asScala.foreach(println(_))
    jarray.asScala.asJava.forEach((str: String) => println(str))*/
  }

}

package com{

  class Demo2{
    import  com.zpj.demo.Demo4
    val name=new Demo4()
  }
  package zpj{
    package demo{
      class Demo1{
        val d2 = new Demo3
      }
    }
  }
}