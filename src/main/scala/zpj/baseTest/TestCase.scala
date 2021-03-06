package zpj.baseTest

import java.io.{DefaultFileSystem, File}
import java.net.URL
import java.nio.file.FileSystem
import java.text.SimpleDateFormat
import java.time._
import java.util
import java.util.{Collections, Date}
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{TimeUnit => _, _}

import akka.actor.ActorSystem
import cats.Monoid
import de.l3s.boilerpipe.extractors.CommonExtractors
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput
import org.apache.commons.lang3.StringUtils
import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}
import org.junit.Test
import org.scalatest.FlatSpec
import org.xml.sax.InputSource
import play.api.libs.json._
import zpj.proxy.User

import scala.annotation.tailrec
import scala.beans.BeanProperty
import scala.collection.immutable.{HashMap, Queue, Stack}
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.collection.parallel.ForkJoinTaskSupport
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal
import scala.util.{Failure, Success}

/**
  * Created by PerkinsZhu on 2017/12/14 19:21.
  */
class TestCase extends FlatSpec {
  "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }

  it should "produce NoSuchElementException when head is invoked" in {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }

  "Nil" should ("is empty") in {
    assert(List().isEmpty)
    //    assert(List("w").isEmpty)
    assert(Nil.isEmpty)
  }

  //1.1 test3
  //1.1 test4
}


class UtilTest {

  @Test
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
    val now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-09-01 00:00:001").getTime
    println(now)
    val temp = now - (oneDay * 30)
    println(temp)
    val start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-02-02 00:00:000").getTime
    println(new DateTime(now).toString("yyyy-MM-dd HH:mm:ss"))
    println(new DateTime(temp).toString("yyyy-MM-dd HH:mm:ss"))
    println(new DateTime(start).toString("yyyy-MM-dd HH:mm:ss"))
    println(start + "---" + (start - temp) / oneDay)

    val startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-05-14 00:00:000").getTime

    val endTime = new Date().getTime

    println(s"$startTime -- $endTime---${(endTime - startTime) / 1000}")


    /* 术禾召回率统计时间
     println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-02-26 00:00:000").getTime)
     println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-02-28 00:00:000").getTime)
 */
  }


  @Test
  def testTime(): Unit = {
    println(new DateTime(1523934330251L).toString("yyyy-MM-dd HH:mm:ss"))
    val now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-05-14 00:00:000").getTime
    println(now)
    val time = DateTime.now()
    val time2 = new Date()
    val utcTime = DateTime.now(DateTimeZone.UTC)
    println(time2 + "--->" + time2.getTime)
    println(time + "--->" + time.getMillis)
    println(utcTime + "--->" + utcTime.getMillis)
    println(utcTime.toString("yyyy-MM-dd HH:mm:ss"))
    println(time.toString("yyyy-MM-dd HH:mm:ss"))
    println(time.withZone(DateTimeZone.forOffsetHours(8)).toString("yyyy-MM-dd HH:mm:ss"))

  }


  @Test
  def testJavaTime(): Unit = {
    println(LocalDate.now())
    println(LocalTime.now())
    println(Instant.now())
  }


  @Test
  def testSplit(): Unit = {
    "sadsd234224701fsa;中sdf国；werw".split("(;|；)").toList.foreach(println _)
  }

  @Test
  def testMap(): Unit = {
    List(("a", List(1, 2, 3, 4)), ("a", List(11, 22, 33, 44)), ("b", List(1, 2, 3, 4))).toMap.foreach(println _)
    val map = Map("a:" -> 1, "b" -> 2)
    val hashMap = HashMap(("a", 1))


  }

  @Test
  def testList(): Unit = {
    val l1 = List(1, 2, 3)
    val l2 = List(4, 5, 6)
    println(l1 ::: l2)
    println(l2 ::: l1)
    println(l2.+:(3))
    println(l2.++(l1))
    println(l2.::("a"))

    l1 match {
      // case Cons(s,::) => println("")
      case x :: h => println(h)
      case _ => println(l1)
    }

    println(l1.reduceLeft(_ * _))

    l1.reverse
  }

  @Test
  def testListToSet(): Unit = {
    println(List(23, 34, 2, 2, 12, 23, 234, 1, 21, 1).toSet)
    println(List(23, 34, 2, 2, 12, 23, 234, 1, 21, 1).toSet.toList)
  }

  @Test
  def testMathc(): Unit = {
    println("lkwersdlfgpjef1sd".matches(".*(sd|地址).*"))
    println("lkwersdlfgpjef1sd".matches("(.*sd.*|.*地址.*"))
  }

  @Test
  def testMatch(): Unit = {
    val res = "hello" match {
      case "ss" => println(22)
      case "hello" => println(11112)
    }
    println(res)
  }

  @Test
  def runList(): Unit = {
    println((1 to 10).partition(_ % 2 == 0))
    println((1 to 10).find(_ % 2 == 0))
    println((1 to 10).filterNot(_ % 2 == 0))
    println((1 to 10).takeWhile(_ > 5))
    println(List(1, 2, 3, -4, 5, 6, 7, 8, 9, 10) takeWhile (_ > 0))
    println(List(List(1, 2, List(11, 22, 33, 44)), List(4, 5, 6), List(7, 8, 9)).flatten)
    println((1 to 10) reduceLeft (_ + _))
    println((1 to 10).toList.foldLeft(100)(_ + _))
    println(List.range(1, 10).foldLeft(1)(_ + _))

    val t1 = (1 to 10)
    println(t1.getClass)
    println(t1.toList.getClass)
    println(List(1).getClass)
  }


  @Test
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

    def onee(x: Int, y: Int): Int = x * y

    def twoo(int: Int): Int = int + 10

    def threee(x: Int): Int = x * 10

    def result(x: Int) = threee _ andThen twoo _

    val f1 = (x: Int, y: Int) => x + y
    val f2 = (y: Int) => y * 2

    val f3 = f1.tupled andThen f2

    println(f3((1, 2)))

    val f4 = (x: Int, y: Int) => (x * 10, y)
    val f5 = (x: Tuple2[Int, Int]) => x._2 + x._1
    val f6 = f4.tupled andThen f5

    println(f6(1, 4))

    val f7 = (x: Int, y: Int, z: Int) => x * y * z

    val f8 = f7.tupled andThen f2
    //    val f9 = f7.tupled compose  f2

    println(f8(1, 2, 3))

    def f(s: String) = "f(" + s + ")"

    def g(s: String) = "g(" + s + ")"

    val fComposeG = f _ compose g _
    println(fComposeG("yay"))

    val fAndThenG = f _ andThen g _

    println(fAndThenG("yay"))
    //偏函数（）
    val one: PartialFunction[Int, String] = {
      case 1 => "one"
    }
    println(one.isDefinedAt(1))
    println(one.isDefinedAt(2))
    println(one(1))

    val two: PartialFunction[Int, String] = {
      case 2 => "two"
    }
    val three: PartialFunction[Int, String] = {
      case 3 => "three"
    }
    val wildcard: PartialFunction[Int, String] = {
      case _ => "something else"
    }
    val partial = one orElse two orElse three orElse wildcard
    println(partial(5))
    println(partial(4))
    println(partial(3))
    println(partial(1))

    // case  使用
    List(1, 2, 3, "hello", "world").filter({
      case num: Int => num > 1
      case _ => false
    }).foreach(println _)
  }

  @Test
  def testParameter(): Unit = {
    val param = List(Some(), None, None, Nil, List(1, 2, 3), List()) zip List("aaa", "bbb", "ccc", "ddd", "eee", "fff")

    val add = sum _
    //    val check: PartialFunction[T, Boolean] = {case T => false}
    //    val result = param.toStream.filter(item => check(item._1)).take(1)
    //    println(result.isEmpty)

  }

  def sum(i: Int)(j: Int)(k: Int): Int = {
    i + j + k
  }

  val add = (a: Int, f: Int => Int) => f(a)

  @Test
  def testString(): Unit = {
    println("My age is %d".format(100))

    var a = "a"
    a += "b"
    println(a)
    a ++= "asdfasdfas"
    println(a)

  }


  @Test
  def testRecursion(): Unit = {
    @annotation.tailrec
    def go(n: Int, num: Int): Int = {
      if (n < 1) num else go(n - 1, n * num)
    }

    println(go(10, 1))
  }


  @Test
  def testFib(): Unit = {
    //    @annotation.tailrec
    def fib(n: Int): Int = if (n < 2) n else fib(n - 1) + fib(n - 2)

    println(fib(3))
  }

  @Test
  def testFuction(): Unit = {

  }

  def curry[A, B, C](f: (A, B) => C): A => (B => C) = {
    (a: A) => (b: B) => f(a, b)
  }

  def uncurry[A, B, C](f: A => B => C): (A, B) => C = {
    (a: A, b: B) => f(a)(b)
  }

  def compose[A, B, C](f: B => C, g: A => B): A => C = {
    a: A => f(g(a))
  }

  def operationToLog(msg: String): ((Boolean, String)) => ((Boolean, String)) = {
    a: (Boolean, String) => a
  }

  def temp(a: Int, f: () => String) {

  }


  @Test
  def testStream() {
    val stream = Stream(1, 2, 3, 4, 5)
    println(stream)

    val other = 20;
    println(addNum(23))
  }

  val other = 10
  val addNum = (x: Int) => x + other
  //  这是函数  函数可单独存在 可赋值给其他常量
  (a: Int, b: Int) => a + b
  val a = (a: Int, b: Int) => a + b

  //  这是方法  方法需要调用  无法直接赋值给常量 可转化为函数后赋值
  def ddd(a: Int, b: Int) = a + b

  //  val f = ddd     无法直接赋值给常量
  val f = ddd _ //可转化为函数后赋值 ddd _ 即是把方法转化为函数

  val addThem1 = new Function2[Int, Int, Int] {
    def apply(a: Int, b: Int) = a + b
  }
  val addThem2 = new ((Int, Int) => Int) {
    def apply(a: Int, b: Int) = a + b
  }


  @Test
  def test2(): Unit = {
    Nil.map(println _)
    List(1, 2, 3).map(println _)
    val l1 = List(1, 2, 3, 3, 4)
    /*val l2 = l1.map(item => {
      println("---->"+item)
      item * 2
    })*/
    l1.map(item => {
      println(l1.map(item => {
        println("---->" + item)
        item * 2
      }))
      println(item)
    })
    println((l1.::(None.getOrElse(""))))
    println((l1.::(Some("===").get)))
  }

  case class Student(var name: String, age: Int, little: Boolean)

  @Test
  def testSort(): Unit = {
    println(List(Student("aa", 23, false), Student("bb", 12, true), Student(" cc", 52, false)).sortBy(!_.little))
  }

  @Test
  def testPlayJson(): Unit = {
    val data = Json.parse("{\n  \"name\" : \"Watership Down\",\n  \"location\" : {\n    \"lat\" : 51.235685,\n    \"long\" : -1.309197\n  },\n  \"residents\" : [ {\n    \"name\" : \"Fiver\",\n    \"age\" : 4,\n    \"role\" : null\n  }, {\n    \"name\" : \"Bigwig\",\n    \"age\" : 6,\n    \"role\" : \"Owsla\"\n  } ]\n}")
    println(data)
    println(data \ "name")
    println(data \\ "name")
    println((data \ "name").validate[Int])
    println((data \ "name").validate[String])

    (data \ "name").validate[Int] match {
      case s: JsSuccess[String] => println(s.get)
      case ex: JsError => println(JsError.toJson(ex).toString())
    }
    println("asfEDRFWwerf23".toLowerCase)
  }

  def creatArray[A: Manifest](a: A, b: A): Unit = {
    val r = new Array[A](2)
    r(0) = a
    r(1) = b
    println(r)
  }

  @Test
  def testClassType(): Unit = {
    creatArray[String]("a", "b")
    List(1, 23, 3)
  }

  @Test
  def testOther1(): Unit = {
    //    val fib: Stream[Int] = 0 #:: fib.scanLeft(1)(_ + _)
    //    println(fib.take(10).toList)

    val seq = IndexedSeq.empty[Int]
    val seq2 = mutable.Seq.empty[Int]
    seq.+("www")
    seq2.+("www")

    seq.foreach(println _)
    seq2.foreach(println _)


  }

  abstract class Person(name: String, age: Int)

  case class LittleStudent(name: String, age: Int, little: Boolean, bigAge: Int) extends Person(name, age)

  @Test
  def testTry(): Unit = {
    println(scala.util.Try(Integer.parseInt("")).toOption)
  }


  @Test
  def test02(): Unit = {
    (false, "aaa")
  }


  @Test
  def testOrdering(): Unit = {
    //    上下文界定测试
    val jack = Student("jack", 23, false)
    val tome = Student("tome", 26, false)

    implicit val stuOrder = new Ordering[Student] {
      override def compare(x: Student, y: Student): Int = x.age - y.age
    }

    println(max(jack, tome))
  }

  @Test
  def testBound(): Unit = {
    //    上下文绑定测试
    val jack = Student("jack", 23, false)
    val tome = Student("tome", 26, false)
    println(max2(jack, tome))
    println(max3(jack, tome))
  }


  /**
    * 上下文界定 通过T:M 形式来指定该方法的作用域中需要存在M[T]的隐式参数
    * 如下 [T: Ordering]的存在则限定了该方法的调用处必须存在一个隐式参数order
    * 如：zpj.baseTest.UtilTest#testOrdering()
    */
  def max[T: Ordering](a: T, b: T)(implicit order: Ordering[T]) = {
    if (order.compare(a, b) > 0) a else b
  }

  //  上下文绑定到时候，该隐式变量必须存在。否则zpj.baseTest.UtilTest.max2无法从上下文中找到该隐式变量
  implicit val stuOrder = new Ordering[Student] {
    override def compare(x: Student, y: Student): Int = x.age - y.age
  }

  def max2[T: Ordering](a: T, b: T) = {
    val order = implicitly[Ordering[T]]
    if (order.compare(a, b) > 0) a else b
  }

  //  也可以直接使用隐式参数，这时不是上下文绑定。而是从上下文中自动找到的隐式变量
  def max3[T: Ordering](a: T, b: T)(implicit order: Ordering[T]) = {
    if (order.compare(a, b) > 0) a else b
  }

  @Test
  def testOther(): Unit = {

    //    (1 to 1000).par.sum
    val list = List(2, 0, 1, 4, 12, 5)
    println(list.zipWithIndex)
    println(list.zipWithIndex.sorted)
    println(list.zipWithIndex.sorted.reverse)
    val seq = (1 to 200)
    println(Seq("a", "fd", "sw", "a", "wer", "wer", "ssw", "sw").groupBy(a => a).mapValues(_.length))
    println(Map("a" -> Seq(1, 2, 3), "b" -> Seq(2, 4, 4)).mapValues(_.length))
    println("wwekUIOHLkj".capitalize)
    println("wwekUIOHLkj".slice(2, 5))


    import java.io.File
    import java.net.URL

    import sys.process._
    new URL("https://my.oschina.net/joymufeng/blog/353654") #> new File("G:\\test\\abc.html") !!
  }

  @Test
  def testGame(): Unit = {
    val temp = Stream.iterate(0)(_ + 0)
    //    val fibs: Stream[Int] = 0 #:: fibs.scanLeft(1) {_ + _}
    //    println(fibs take 10 toList)

    val seq = Seq(1, 2, 3)
    println(seq.scanLeft(11)(_ + _))

    println(Stream.iterate(0)(_ + 0).scanLeft(1)(_ + _).take(100).toList)
  }

  @Test
  def testGame2(): Unit = {
    println(List("1", "2", "3").foldLeft("1111")(strMonoid.combine(_, _)))
    println(List("1", "2", "3").foldLeft("1111")(strMonoidScalaz.append(_, _)))
  }

  val strMonoidScalaz = new scalaz.Monoid[String] {
    override def zero: String = ""

    override def append(f1: String, f2: => String): String = f1 + f2
  }
  val strMonoid = new Monoid[String] {
    override def empty: String = ""

    override def combine(x: String, y: String): String = x + y
  }


  /**
    * Y-组合子的实现函数
    *
    * @param function 需要被递归执行的函数
    * @tparam T 函数递归元素类型
    * @return 可递归执行的函数
    */
  def Y[T](function: (T => T) => (T => T)): (T => T) = function(Y(function))(_)

  @tailrec
  val fibonacci = Y[Int] { f =>
    n =>
      n match {
        case x if x <= 1 => x
        case _ => f(n - 2) + f(n - 1)
      }
  }

  @Test
  def testY(): Unit = {
    (0 to 10) foreach { i => println(fibonacci(i)) }
  }

  @Test
  def testLoop(): Unit = {
    val list = 1 to 20 toList

    println(list.scan(10)(_ + _))
    println(list.scanLeft(10)(_ + _))
    println(list.scanRight(10)(_ + _))
    println(list.reduce(_ + _))
    println(list.reduceLeft(_ + _))
    println(list.reduceRight(_ + _))
    println(list.fold(10)(_ + _))
    println(list.foldLeft(10)(_ + _))
    println(list.foldRight(10)(_ + _))
  }

  @Test
  def testTail(): Unit = {
    println(List(1, 2, 3, 4, 5).tail)
    println("".trim == "")
  }

  @Test
  def testTime3(): Unit = {
    println(java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(16L))
  }

  @Test
  def testTime2(): Unit = {
    val time = DateTime.now()
    println(time.toDate.getTime)
    println(time.getMillis)

    println(List[Int](1, 23, 2, 3, 43).sortBy(_.toInt))

    println(List(1, 2, 3).map(_ + 2))

    println(System.getProperty("user.dir"))
  }

  @Test
  def testFile(): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global
    //    println(List(Future(1), Future(2), Future(3)))
    //    println(List(List(1), List(2), List(3)).flatten)


    println(List(Future(1), Future(2), Future(3)).foldLeft(Future(List.empty[Int]))((a, b) => a.flatMap(list => b.map(list.:+(_)))))
    val future = List(Future(Json.obj("a" -> 1)), Future(Json.obj("b" -> 2)), Future(Json.obj("c" -> 3))).foldLeft(Future(List.empty[JsValue]))((a, b) => a.flatMap(list => b.map(list.:+(_))))

    println(List(List(1), List(2), List(3)).foldLeft(List.empty[Int])((a, b) => a ::: b))

    Thread.sleep(1000)
    println(future)
  }

  @Test
  def testLoopDea(): Unit = {
    while (true) {
      Thread.sleep(1000)
      println("----")
    }
  }

  @Test
  def testString02(): Unit = {
    val temp = "替你还产^品分?为|1-4周，]利率：[&一周0%-(0.6%、二周0)%-1.3%、三周0%-2.0%、四周0%-2.7%，如按时还款不会产生其他费用；如逾期，罚息为贷款余额*罚息费率*逾期天数，罚息费率：逾期1-30天（含）-0.06%/日，逾期30天以上-0.1%/日，若日息低于1元的，则按照1元收取"
      .replaceAll("[%*/()\\[\\]\\^\\|\\.\\{\\}\\&\\?]", "")
    println(temp)
  }

  @Test
  def testZipWithIndex(): Unit = {
    (1 to 100).map(i => s"--$i--").zipWithIndex.foreach(println)
  }

  @Test
  def isPalindrome() = {
    println(List.fill[Int](10, 4)(101))
    println(List.range(10, 100, 10))
    println(0 until(100, 10) toList)
  }

  @Test
  def testIterator(): Unit = {
    val iterator = (1 to 20).toList.grouped(10)
  }

  val add1 = (a: Int) => {
    println("add--" + a)
    a + 1
  }
  val mul = (a: Int) => {
    println("mul--" + a)
    a * 10
  }

  @Test
  def testView(): Unit = {
    val list = (1 to 10).toList
    // list.map(add1).map(mul)
    val view = list.view.map(add1).map(mul).takeWhile(_ < 40).force
    println(view)
    val temp = list.toStream.map(add1).map(mul).takeWhile(_ < 40).force
    println(temp)
  }

  @Test
  def testNone(): Unit = {
    println(List(Some("111"), None).filterNot(_.isEmpty).map(_.get))
  }

  @Test
  def testPar(): Unit = {
    val set = mutable.Set[String]()
    // 多线程并行处理集合
    val par = (1 to 1000000).toList.par
    par.tasksupport = new ForkJoinTaskSupport(new scala.concurrent.forkjoin.ForkJoinPool(10))
    val total = par.map(i => {
      val name = Thread.currentThread().getName
      set.add(name)
      i * i
    }).sum
    println(set)
    println(total)
  }

  @Test
  def testParArray(): Unit = {
    //    (1 to 100).toArray.par.foreach(println)
    val pa = scala.collection.parallel.mutable.ParArray.tabulate(1000)(x => 2 * x + 1)
    //    println(pa.reduce(_ + _))
    //    并行向量
    val pv = scala.collection.parallel.immutable.ParVector.tabulate(1000)(x => x)
    //    pv.filter(_ % 2 == 0).foreach(println)
    //    并行范围
    //    (1 to 10).par.foreach(println)
  }


  @Test
  def testLoopForeever(): Unit = {
    while (true) {
      Thread.sleep(1000)
    }
  }

  @Test
  def testCollections(): Unit = {
    List(1, 2, 3)
    show(1, 2, 3, 3)
    show("a", "b", "c", "d")
    val list = new ::(1, Nil)
    println(list)
    val list2 = (1 :: 2 :: 3 :: Nil)

    list2 match {
      case head :: tail => println("i am  head:" + head)
      case Nil => println("i am  nil")
    }
    val builder = List.newBuilder[Int]
    builder.+=(10)
    builder.+=(11, 12, 13, 14, 15)

    println(builder.result())
    val temp = builder.+("qqqqqq")
    println(builder.result())
    println(temp)
    println(100 + "2")
    val stream = builder.result().toStream
    stream.print("==")
    println(stream.force)

    println(stream.toList.init)
    println(stream.init)
    val list3 = stream.toList
    println(list3)
    val list4 = list3.dropWhile(_ > 11)
    println(list4)
    val list5 = Range.apply(1, 10).toList
    println(list5)

    //dropWhile在第一个不满足条件的地方结束，并返回结果。因此，不能跳元素，必须从左向右判断，直至第一个不满足条件的结果。
    println(list5.dropWhile(_ < 5))
    println(list5.dropWhile(_ > 5))
    println(list5.dropWhile(_ == 5))

    println(Stream.iterate(1)(_ * 10).take(10).toList)

  }

  def show[A](a: A*): Unit = {
    a.toList
    println(a.getClass)
  }

  @Test
  def testJsNull(): Unit = {
    println(Json.obj("name" -> JsNull))
    //    println(Seq().head)
    println(Nil.nonEmpty)
    println(Nil.isEmpty)

    val list = List.range(1, 10, 1)
    println(list)
    println(list.slice(100, 200))

    println(list.contains(1, 2))
  }


  @Test
  def testMailMatch(): Unit = {
    val mail = "zpjtest_04@chatbot.cn"
    println(mail.matches("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9\\u4e00-\\u9fa5]+(\\.[a-zA-Z0-9\\u4e00-\\u9fa5]+)+$"))
  }

  @Test
  def testString03(): Unit = {
    println("".nonEmpty)

    println("Global_Admin|Robot_Admin".contains("Robot_Admin"))

    println(10 / 2 * 3)

    val map = new ConcurrentHashMap[String, String](10)
    import scala.collection.JavaConverters._
    print(System.getProperties.asScala.mkString("\t\n"))
  }

  @Test
  def testBreak(): Unit = {
    import scala.util.control.Breaks._
    breakable {
      for (i <- 1 to 10000) {
        if (i > 1000) {
          break;
        }
        println(i)
      }
    }
  }

  @Test
  def testBean(): Unit = {
    class Person(@BeanProperty var name: String) {
      @BeanProperty var age: Int = 10
      private var privSex = "boy"
      private[this] var selay = 10

      def sex = privSex

      def sex_=(newSex: String): Unit = {
        println("i am sex_")
        privSex = newSex
      }

      def comper(that: Person): Unit = {
        //        that.selay = 222  //该字段只能在自己的类中使用
      }


    }
    val person = new Person("jack")
    println(person.getName)
    println(person.getAge)
    println(person.sex)
    person.sex = "girl"
    println(person.sex)


    class FF(private val name: String)
    class EE(private[this] val name: String)
    class AA(name: String)
    class BB(val name: String)
    case class CC(name: String)
    case class DD(val name: String)
    case class GG(var name: String)

    val a = new AA("JACK")
    val b = new BB("JACK")
    val c = new BB("JACK")
    val d = new BB("JACK")
    val e = new EE("JACK")
    val f = new FF("JACK")
    val g = new GG("JACK")

    /*    println(a.name)
        println(b.name)
        println(c.name)
        println(d.name)
        println(e.name)
        println(f.name)
        println(g.name)
        g.name = "tom"
        println(g.name)*/

    //
    println(List(1, 2, 3, 3, 4, 5).productElement(2))
  }

  class Creature {
    val range = 10
    val env = new Array[String](range)
    println(env.size + "--" + range)
  }

  class Ant extends Creature {
    override val range: Int = 2
  }

  trait Bnt extends Creature {
    override val range: Int = 2
  }


  class Tigger extends Ant with Bnt {

  }

  class Pandar extends Bnt {

  }

  case object AA {}

  @Test
  def testExtends(): Unit = {
    val ant = new Ant
    println(ant.env.size)
    println(ant.range)
  }

  @Test
  def testSubType(): Unit = {
    val animal = new Animal("tigger")
    showInfo(Animal("tigger"))
    showInfo(new {val name: String = "HHH"})
    showInfo(new {val name: String = "world"})

    def showInfo(a: {val name: String}): Unit = {
      println(a.name)
    }
  }

  case class Animal(val name: String)

  @Test
  def testEnmu(): Unit = {
    showColor(Color.BLUE)

    def showColor(color: Color.Value): Unit = {
      color match {
        case Color.RED => println("red")
        case Color.BLUE => println("blue")
      }
    }

  }

  @Test
  def testEnter() {
    // \r是回车，光标回到本行初始位置；\n是换行，光标回到下一行的同一列
    println("a\nb\r\nc\\nd")
    println("hello")
  }

  @Test
  def testArray(): Unit = {
    val array = Array(1, 2, 3, 4)
    val array2 = new Array[Int](5)
    array.length
    println(array2.apply(5))
    val set = Set(1, 2, 3, 4)
    set.+(23)
  }

  @Test
  def testQueue(): Unit = {
    val queue = Queue.empty[Int]
    val newQueue = queue.enqueue(12).enqueue(13).enqueue(14)
    newQueue.foreach(println)
    val list = List.empty[Int].+:(2).+:(3).+:(4)
    println(list)
    println(list.:+(5))
    println(list.:+(5).::(6))
    list.foreach(println)
    //可用list 替代
    val stack = new Stack()

    val index = IndexedSeq.range(10, 50)
    println(index)

    val map = Map(("a", 1), ("b", 2))
    map.+(("c", 3))

    val hashMap = HashMap(("a", 1))
    hashMap.+(("b", 2))

    12 match {
      case a@(12 | 13) => println(s"---$a---")
    }


  }

  case class A(b: B)

  case class B(a: A)

  @Test
  def testDepend(): Unit = {
    val b: Nothing = throw new Exception();
    val a = A(b)
  }

  @Test
  def testTrait(): Unit = {
    println(new DD().show())
  }

  class BB extends AA {
    override def show(): String = " i am B"
  }

  class CC extends AA {
    override def show(): String = "I AM  CC"
  }

  trait EE extends AA {
    override def show(): String = "i am EE"
  }

  class DD extends BB with AA with EE {
    // override def show(): String = "i am DD"
  }

  trait AA {
    def show(): String
  }


  @Test
  def testString2(): Unit = {
    val name = "jack"
    println(f"i am $name")
    println(s"i am $name")
  }


  @Test
  def testNonFatal(): Unit = {
    try {
      // throw new OutOfMemoryError("2222")
      throw new NullPointerException("2222")

    } catch {
      case NonFatal(a) => {
        println("---nonfatal----")
        //        a.printStackTrace()
      }
      case th: Throwable => println("--Throw--")
      case ex: Exception => print("--exce--")
      case ex: Error => print("--error--")
    }

  }

  @Test
  def testEither(): Unit = {
    str2Int("1111") match {
      case Left(value) => print("errr because:" + value)
      case Right(value) => print("result is :" + value)
    }
    val result = str2Int("10")
    println(result.isLeft)
    println(result.isRight)
    println(result.right.map(_ * 10))
    None

  }

  @Test
  def testJson(): Unit = {
    val json = Json.obj("data" -> Json.obj("slot" -> Json.obj()))

    println(json \ "data.slot")
  }

  def testEther(): (String, User) = {
    Failure(new RuntimeException)
    Tuple2("", null)
  }

  def str2Int(str: String): Either[String, Int] = {
    if (StringUtils.isBlank(str)) {
      Left("无效的参数")
    } else {
      Right(str.toInt)
    }
  }


  object Color extends Enumeration {
    type Color = Value
    val RED, BLUE = Value
  }


  object UtilTestObject {
    def testJavaThread(): Unit = {
      val executor = Executors.newFixedThreadPool(2)
      executor.submit(new Runnable {
        override def run(): Unit = {
          while (true) {
            Thread.sleep(100)
            println("-------")
          }
        }
      })
    }

    def main(args: Array[String]): Unit = {
      testJavaThread()
    }
  }

  @Test
  def testJson2(): Unit = {
    val json = Json.obj("data" -> Json.obj("slot" -> Json.obj()))

    println(json \ "data" \ "slot")
  }

  @Test
  def testRturn(): Unit = {
    1 to 10 foreach (i => {
      1 to 5 foreach (j => {
        if (i + j == 8) {
          return;
        }
        println(i + "--" + j)
      })
      println(i)

    })
  }


  @Test
  def testPar2(): Unit = {
    //par 也是阻塞进行的
    1 to 10 foreach (i => {
      Thread.sleep(100)
      println(i)
    })
    println("over1")

    (1 to 10).toList.par foreach (i => {
      Thread.sleep(100)
      println(i)
    })
    println("over2")
  }

  @Test
  def testSum(): Unit = {
    println(List(1, 2, 3).sum)
  }

  /**
    * scala 泛型测试
    * 上界 T <: V 下界 T >:V
    * 视图界定 T<% V  存在一个T到V的隐式转换
    * 上下文界定(上下文绑定)  T：V 存在一个V[T]的隐式值
    * 多重界定
    * 类型约束 T=:=V  T<:<V  T<%<V
    * 不变、协变，逆变 T +T  -T
    * 类型通配符 _
    */

  @Test
  def testT(): Unit = {
    class B {
      def show(): Unit = {
        println(" i am B..")
      }
    }
    class BB extends B {}
    class BBB extends B {}

    //  上界 父类为B
    def demo1[T <: B](a: T): Unit = a.show()

    //下界 父类为T
    def demo2[T >: B](a: T): Unit = {
      //这里的T 应该是一个AnyRef类型的
      // a.show()  这里是编译通不过的
      //      println(ru.typeOf[a.type])
      println(a.getClass)
    }

    demo1(new B)
    demo1(new BB)
    demo1(new BBB)

    //下面的调用应该是按照 AnyRef 类型传入的
    demo2(new B)
    demo2(new BB)
    demo2(new BBB)
    demo2(new AnyRef)
    demo2(AnyRef)
    demo2(new Object)
    demo2(1)
    demo2(List(1, 2, 4))
    demo2(this)

    def demo3[T >: B](a: Any): T = {
      // a.show()  这里是编译通不过的
      //这里的T是一个AnyRef类型的
      println(a.getClass)
      val temp = a.asInstanceOf[T]
      println(temp.getClass)
      temp
    }

    println("===TEST DEMO3=====")
    demo3(new B)
    demo3(new BB)
    demo3(new BBB)
    demo3(1)
    demo3(new Integer(1))
    demo3(12.6)

  }

  @Test
  def testOther2(): Unit = {
    var str: String = ""
    println(str)
    val builder = new StringBuilder("")
    builder.+:("hello")
    builder.+:(" world")
    builder.append("jjjjwjjjejjejj")
    println(builder.toString())
  }

  import scala.concurrent.ExecutionContext.Implicits.global

  @Test
  def testRecover(): Unit = {
    val future = Future {
      10 / 2
    }.map(_ - 10).map(_ * 100).recover({
      case ex: Exception => {
        //exception中的信息会被一致传递过来
        println("exception")
        ex.printStackTrace()
        10
      }
      case _ => {
        println("other")
        20
      }
    })
    Thread.sleep(100)
    println(future)
  }

  @Test
  def testSeq(): Unit = {
    Seq(1, 2, 3).:+(6).foreach(println _)
    println("----")
    Seq(1, 2, 3).+:(6).foreach(println _)
    println("----")
    Seq(1, 2, 3).++(Seq(5, 6, 7)).foreach(println _)
  }

  @Test
  def testDate(): Unit = {
    println(new Date().getTime)
  }

  @Test
  def testRecoverWith(): Unit = {
    Future(400 / 0).recoverWith({
      case data => {
        println(data)
        Future.successful(10)
      }
    })

    Thread.sleep(Int.MaxValue)
  }


  @Test
  def testRecoverFailure(): Unit = {
    val future = Future(10 / 0).recoverWith({
      case _ => {
        println("----1")
        Future.successful(10 / 0)
      }
    }).recoverWith({
      case _ => {
        println("----2")
        Future.successful(10 / 0)
      }
    }).recoverWith({
      case _ => {
        println("----3")
        Future.successful(10 / 0)
      }
    }).recoverWith({
      case _ => {
        println("----4")
        Future.successful(10 / 0)
      }
    }).recoverWith({
      case _ => {
        println("----5")
        Future.successful(10 / 0)
      }
    })
    println(concurrent.Await.result(future, 10 seconds))

  }

  import akka.pattern.after


  def getFuture(): Future[Int] = {
    Future {
      println("--0--")
      10
    }
  }

  @Test
  def testFutureAfter(): Unit = {
    val fu = after(2 seconds, ActorSystem.create().scheduler)({
      println("--------")
      getFuture()
    })

    println(concurrent.Await.result(fu, 10 seconds))
    Thread.sleep(100000)
  }


  @Test
  def testCycle(): Unit = {
    val i = new AtomicInteger(0)
    val f = () => Future {
      println("i---" + i.get())
      if (i.get() == 3) {
        10 / 0
      }
      Future.successful(i.addAndGet(1))
    }
    //    cycle(0, f)
    cycle2(5, f)
    Thread.sleep(Int.MaxValue)
  }

  def cycle[T](num: Int, data: () => Future[T]): Future[T] = {
    Thread.sleep(1000)
    val f = data()
    f.flatMap(i => {
      println("=====" + num)
      if (num < 5) {
        cycle(num + 1, data)
      } else {
        f
      }
    }).recoverWith({
      case _ => {
        if (num < 7) {
          println("-------" + num)
          cycle(num + 1, data)
        } else {
          println("--over ----")
          f
        }
      }
    })
  }


  def cycle2[T](num: Int, fu: () => Future[T]): Future[T] = {
    println("num--->" + num)
    val f = fu()
    Thread.sleep(1000)
    if (num < 0) {
      println("---over--")
      f
    } else {
      f.recoverWith({
        case _ => {
          println("recover -->" + num)
          cycle2(num - 1, fu)
        }
      })
    }
  }

  @Test
  def testAndThen2(): Unit = {
    val f = Future {100 / 0}.andThen({
      case Failure(exception) => 0
      case Success(value) => value
    })


    Thread.sleep(Int.MaxValue)
  }


  @Test
  def testConfig(): Unit = {
    val config = com.typesafe.config.ConfigFactory.parseFile(new File("F:\\myCode\\workTestWithScala\\application.conf"))
    println(config)
  }

  case class AAA()

  @Test
  def testEqual(): Unit = {
    val a = AAA()
    val aa = AAA()

    println(a == aa)
    println(a eq aa)
    println(a equals aa)
    println(a ne aa)
    println(null ne null)
    println(null == null)
    println(null equals null)

  }

  @Test
  def testXX(): Unit = {
    println(5 / 2)
    println(4 / 2)

    //    val set = Seq(1,2,3,4,5,6,7)
    val set = (0 to 100).toSet[Int]

    set.par.foreach(i => println(s"threadName:${Thread.currentThread().getName} -->$i"))

    set.map(_ * 10).map(_ + 1).sum / set.size
    Map.empty[String, String].+("a" -> 1).+("b" -> 2).+("c" -> 3)


    type MyType = Tuple4[String, Student, Int, Map[String, Int]]
    val stuendt = Student("jack", 20, true)

    def getStudentInfo(): MyType = ("jack", stuendt, 20, Map("驾龄" -> 3)) //返回自定义类型

  }

  @Test
  def testNothing(): Unit = {
    //    val i: Int = null;
    //    println(i)
    def j: Nothing = throw new RuntimeException

    println("---")
    var y = 0
    val x = y = 1

    def s(x: Int): Unit = {
      x + 1
    } //指定返回值类型为Unit
    def ss(x: Int) {
      x + 1
    } //第一种的简写,把返回值类型和‘=’同时省略，如同 :Unit=  ，返回值类型为Unit
    def sss(x: Int) = {
      x + 1
    } //未定义返回值类型，自动推断出为 Int
    def ssss(x: Int): Int = {
      x + 1
    } //指定返回值类型为Int
    println(ss(2)) // ()
    println(sss(2)) // 3
    println(ssss(2)) // 3

  }

  @Test
  def testCase(): Unit = {
    val ss = Student("jack", 23, true)
    ss.name = "22";

    ("lllll1ll1ll1l1ll1ll1l1ll1l1ll1l1ll1l1ll1l1ll1l1ll1l1l1ll1lll1l1ll1ll1l1l1ll1l1lll1l1ll1l1ll1l11ll1l1l11llllllll111l1lll1l1l1ll" +
      "l1l1ll1l1ll1l1ll1l1l11ll1l1llll1l1ll1l1l1ll1l22ll2l2l2l2ll112345ljkd;;;;;;;;;sddddddddddssdpowpeofsdjfsadhiweiksadfhwe" +
      "hhhhidfllll1lllll1llll1llll1lll1ll").toCharArray
      .foreach(item => println(item.toInt))

    Instant.now().getNano
    Instant.now().getEpochSecond


    synchronized[Int] {
      23
    }

  }


  @Test
  def testThreadPool(): Unit = {
    // implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(5))
    implicit val ec = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())
    // val ec2 = scala.concurrent.ExecutionContext.Implicits.global
    //从配置文件中读取线程池配置
    val actorEC: ExecutionContext = ActorSystem.create("MySystem").dispatchers.lookup("my-context")

    Future {
      println(Thread.currentThread().getName + "----->" + 100)
      Thread.sleep(500)
    }
    Future {
      println(Thread.currentThread().getName + "----->" + 1001)
      Thread.sleep(500)
    }
    Future {
      println(Thread.currentThread().getName + "----->" + 1002)
      Thread.sleep(500)
    }
    //自定义线程池 线程池工作原理

    val pool = Executors.newFixedThreadPool(10)
    pool.execute(() => {println("---->1")})

    Thread.sleep(3000)
  }

  case class AAB(name: String)

  @Test
  def testFastJson(): Unit = {
    import com.alibaba.fastjson.JSON
    val json = JSON.parseObject("{name:'jack'}")
    println(json)
    //    val aa = json.toJavaObject(AAB.getClass)
    //    println(aa)
    println("---->")
    val map = new util.HashMap[String, String](10)
    println(map.size())

    val i = 0;
    while (true) {
      i * 100;
      Thread.sleep(100)
    }

  }

  @Test
  def testTimer(): Unit = {
    // 不要使用Timer + TimerTask 执行周期性任务。Timer是单线程的，当同一个Timer执行多个任务时，其中一个TimerTask如果发生
    //异常，会导致所有的Task结束。另外，Task之间的执行时串行的，会导致时间推迟
    // https://www.jianshu.com/p/08181b779706
    val executor = new ScheduledThreadPoolExecutor(2)
    println("startTime:" + LocalTime.now())
    var i = 0
    val future = executor.scheduleAtFixedRate(() => {
      println(Thread.currentThread().getName + "--->" + LocalTime.now())
      if (i == 3) {
        println(Thread.currentThread().getName + "--> 抛出异常")
        throw new RuntimeException("my exception....")
      }
      i = i + 1
    }, 2l, 5l, SECONDS)

    Thread.sleep(100000)
  }

  @Test
  def testExecutorCompletionService(): Unit = {
    val executor = Executors.newFixedThreadPool(5)

    val cs = new ExecutorCompletionService[Integer](executor)

    cs.submit(() => {
      Thread.sleep(5000)
      println("---sleep over ----")
      10
    })

    // 这里的take 实际上是封装的queue.take ,会阻塞在这里等待结果
    val fu = cs.take()
    println("get future -->" + fu.isDone)
    println("get result -->" + fu.get());
    Thread.sleep(10000)

  }

  @Test
  def testConcurrentLinkedDeque(): Unit = {
    val deque = new ConcurrentLinkedDeque[String]()
    deque.addFirst("B")
    deque.addLast("A")
    println(deque.getLast)
    println(deque.getFirst)
    println(deque.peek())
    println(deque.poll())
  }


  @Test
  def testMatch2(): Unit = {
    val a = "a"
    val b = "b"
    val c = "c"
    // case 项的符号是 ~ 一个键位的键
    "c" match {
      case `a` => println("i am  a")
      case `b` => println("i am b")
      case `c` => println("i am c")
    }

  }

  @Test
  def testMap2(): Unit = {

    var map = mutable.Map.empty[String, String]
    val data = List(1, 2, 3, 4).map(item => {

      map.+=((item + "", item + ""))
    })
    println(map)
  }

  /*  val res = Map(List(1, 2, 3, 4).map(i => (i, i + "--")): _*)
    println(res)

    val res2 = List(1, 2, 3, 4).map(i => (i, i + "--")).toMap
    println(res2)*/

  @Test
  def optionForeach(): Unit = {
    /* val data:Option[List[String]] = None
     data.foreach(println(_))*/

    val future = Future {5 / 1}
    val temp = future.recover {
      case e: Exception => {
        println("--error")
        10
      }
      case _ => {
        println("--other")
        100
      }
    }
    println(future)
    println(temp)


    "asd,sdf，飒飒大师的；sdfas;sdfsd".split("(,|，|;|；)").toList.foreach(println)
  }

  @Test
  def testTime33(): Unit = {
    val dataStr = LocalDate.now.plusDays(-7).toEpochDay
    println(dataStr)

    val temp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    val date = temp.parse("2018-12-11T16:00:00.000Z")
    val dateTime = new DateTime(date.getTime + (8 * 60 * 60 * 1000))

    println(dateTime.toString("yyyy-MM-dd HH:mm:ss.SSS "))


    println("2018-12-11T16:00:00.000Z".substring(0, 10).replace("-", ""))
    println("2018-12-11T16:00:00.000Z".substring(11, 19))

    println(LocalDate.now().getDayOfMonth)
    println(LocalDate.now().toString)
    println(LocalDate.now().minusMonths(2).toString)
  }

  @Test
  def testList03(): Unit = {
    val list = List(1, 2, 3)
    println(list(0))
    println(list(1))
    var a = 23
    val c, b: Int = 23
    Future.successful(123).mapTo[Int].map(_ + 10)
  }

  @Test
  def listFutureToFutureList(): Unit = {
    val listFuture = (1 to 10).map(i => Future.successful(i))
    listFuture
  }

  @Test
  def testBoilerpipe(): Unit = {
    val url = "http://news.cctv.com/2018/12/21/ARTI6TWgj2PmdQym4hoCM8me181221.shtml";
    val doc = new BoilerpipeSAXInput(new InputSource(new URL(url).openStream())).getTextDocument();
    val extractor = CommonExtractors.ARTICLE_EXTRACTOR;
    extractor.process(doc);
    System.out.println("title:" + doc.getTitle());
    System.out.println("content:" + doc.getContent());
  }

  @Test
  def testJsNull02: Unit = {
    val j1 = JsNull
    val j2 = JsNull
    val j3 = JsNull
    val j4 = JsNull
    println(j1 == j2)
    println(j1 == j3)
    println(j1 == j4)
    println(j3 == j4)
    println(j2 == j4)
    import scala.collection.JavaConverters._
    val num = 100
    val list = new util.ArrayList[String](num)
    (0 to num).foreach(i => list.add(""))
    /*(0 to num ).foreach(i =>{
      list.add(i, "-->"+i)
    })*/

    // 这里是增加，不是替换
    list.add(2, "-->2")
    list.add(12, "-->12")
    list.add(24, "-->24")
    list.add(4, "-->4")
    list.add(34, "-->34")

    println(list)
    println(list.asScala.zipWithIndex)

    //TODO 为什么 zipWithIndex 会错乱？
  }


  import scala.collection.JavaConverters._

  @Test
  def testZipWithIndex02(): Unit = {
    // 1、不能跳过元素 链表不允许断开
    //2、 add 是添加元素，不是覆盖元素
    //3、 set 替换指定位置元素

    val num = 100
    val list = new util.ArrayList[String](num) //这里的参数如果是无效的，为什么还有接收呢？ 注意 size和initialCapacity的区别
    (0 until num) foreach (i => list.add(""))
    println(list.size())
    (0 until num) foreach (i => {
      list.set(i, i + "")
    })
    println(list.size())
    val temp = list.asScala.zipWithIndex
    println(temp)
    temp.filter(item => item._1 != item._2 + "").foreach(println(_))

  }

  @Test
  def testZipWithIndex03(): Unit = {
    //TODO listBuffer 有set(index,ele)方法吗/**/
    val num = 100
    val list = new ListBuffer[String]()
    (0 until num) foreach (i => list.append(""))
    println(list.size)
    (0 until num) foreach (i => {
      if (i % 2 == 0) {
        list.insert(i, i + "")
      }
    })
    println(list.size)
    val temp = list.zipWithIndex
    println(temp)
    temp.filter(item => item._1 != item._2 + "").foreach(println(_))

  }

  @Test
  def testZipWithIndex04(): Unit = {

    val num = 100
    val array = new ArrayBuffer[String](num)
    (0 until num) foreach (i => array.append(""))
    println(array.size)
    (0 until num) foreach (i => {
      if (i % 2 == 0) {
        array(i) = i + ""
      }
    })
    println(array.size)
    val temp = array.zipWithIndex
    println(temp)
    temp.filter(item => item._1 != item._2 + "").foreach(println(_))

  }

  @Test
  def testStream2(): Unit = {
    val resutlt = List(1, 2, 3, 4, 2, 2).toStream.filter(_ == 2).take(1).toList
    println(resutlt)
  }

  @Test
  def testForExpression(): Unit = {
    for {
      i <- Some("a")
      j <- {
        // for表达式不允许混合类型返回
        Future.successful(20)
        Some("a")
      }
    } yield {
      println(i + "--" + j)
    }
  }

  @Test
  def tsetJsonRemove(): Unit = {
    val list = 1 to 5 map (i => Json.obj("age" -> i, "name" -> i))
    val data = Json.obj("key" -> list, "data" -> Json.obj("aaa" -> Json.obj("ccc" -> 123), "bbb" -> 2342))
    println(data)
    println(data.-("key.age"))
    println(data.-("key"))
    println(data.+("key.$.age", JsNull))
    println(data.-("data.bbb"))
    println(data.-("data"))
    println(data.keys)


    println("======================")
    val json = Json.parse("{\"_id\":\"5bff95bd670000e500dd076d-5c333324ca8005647c0ad101-1\",\"stage\":\"slot\",\"nodeId\":\"1\",\"data\":{\"inquire\":{\"slots\":[\"5c340e3a1b0000f4006f6368\",\"5c340e731b0000f4006f6369\",\"5c340ecd1b0000f2006f636a\"],\"cases\":[{\"caseItemGroups\":[[{\"slotList\":[{\"slotId\":\"5c340d831b0000f5006f6365\",\"slotName\":\"订机票_航班\"},{\"slotId\":\"5c340da41b0000f2006f6366\",\"slotName\":\"订机票_航班号\"},{\"slotId\":\"5c340dda1b0000f5006f6367\",\"slotName\":\"订机票_舱位\"},{\"slotId\":\"5c340e3a1b0000f4006f6368\",\"slotName\":\"订机票_出发城市\"},{\"slotId\":\"5c340e731b0000f4006f6369\",\"slotName\":\"订机票_目的城市\"},{\"slotId\":\"5c340ecd1b0000f2006f636a\",\"slotName\":\"订机票_日期\"},{\"slotId\":\"5c340ef71b0000f2006f636b\",\"slotName\":\"订机票_出发时间\"},{\"slotId\":\"5c340f121b0000f4006f636c\",\"slotName\":\"订机票_到达时间\"},{\"slotId\":\"5c344a471b0000e9096f6370\",\"slotName\":\"订机票_电话\"},{\"slotId\":\"5c344a9f1b0000e9096f6371\",\"slotName\":\"订机票_姓氏\"},{\"slotId\":\"5c344ace1b000020016f6372\",\"slotName\":\"订机票_身份证号\"},{\"slotId\":\"N\",\"slotName\":\"请选择\"}],\"operatored\":[{\"text\":\"请选择\",\"value\":\"N\"},{\"text\":\"已填充\",\"value\":\"filled\"},{\"text\":\"未填充\",\"value\":\"unfilled\"}],\"operator\":\"unfilled\",\"slotsId\":\"5c340e3a1b0000f4006f6368\",\"values\":[],\"itemType\":\"slot\"},{\"itemType\":\"logic\",\"value\":\"and\"},{\"slotList\":[{\"slotId\":\"5c340d831b0000f5006f6365\",\"slotName\":\"订机票_航班\"},{\"slotId\":\"5c340da41b0000f2006f6366\",\"slotName\":\"订机票_航班号\"},{\"slotId\":\"5c340dda1b0000f5006f6367\",\"slotName\":\"订机票_舱位\"},{\"slotId\":\"5c340e3a1b0000f4006f6368\",\"slotName\":\"订机票_出发城市\"},{\"slotId\":\"5c340e731b0000f4006f6369\",\"slotName\":\"订机票_目的城市\"},{\"slotId\":\"5c340ecd1b0000f2006f636a\",\"slotName\":\"订机票_日期\"},{\"slotId\":\"5c340ef71b0000f2006f636b\",\"slotName\":\"订机票_出发时间\"},{\"slotId\":\"5c340f121b0000f4006f636c\",\"slotName\":\"订机票_到达时间\"},{\"slotId\":\"5c344a471b0000e9096f6370\",\"slotName\":\"订机票_电话\"},{\"slotId\":\"5c344a9f1b0000e9096f6371\",\"slotName\":\"订机票_姓氏\"},{\"slotId\":\"5c344ace1b000020016f6372\",\"slotName\":\"订机票_身份证号\"},{\"slotId\":\"N\",\"slotName\":\"请选择\"}],\"operatored\":[{\"text\":\"请选择\",\"value\":\"N\"},{\"text\":\"已填充\",\"value\":\"filled\"},{\"text\":\"未填充\",\"value\":\"unfilled\"}],\"operator\":\"unfilled\",\"slotsId\":\"5c340e731b0000f4006f6369\",\"values\":[],\"itemType\":\"slot\"},{\"itemType\":\"logic\",\"value\":\"and\"},{\"slotList\":[{\"slotId\":\"5c340d831b0000f5006f6365\",\"slotName\":\"订机票_航班\"},{\"slotId\":\"5c340da41b0000f2006f6366\",\"slotName\":\"订机票_航班号\"},{\"slotId\":\"5c340dda1b0000f5006f6367\",\"slotName\":\"订机票_舱位\"},{\"slotId\":\"5c340e3a1b0000f4006f6368\",\"slotName\":\"订机票_出发城市\"},{\"slotId\":\"5c340e731b0000f4006f6369\",\"slotName\":\"订机票_目的城市\"},{\"slotId\":\"5c340ecd1b0000f2006f636a\",\"slotName\":\"订机票_日期\"},{\"slotId\":\"5c340ef71b0000f2006f636b\",\"slotName\":\"订机票_出发时间\"},{\"slotId\":\"5c340f121b0000f4006f636c\",\"slotName\":\"订机票_到达时间\"},{\"slotId\":\"5c344a471b0000e9096f6370\",\"slotName\":\"订机票_电话\"},{\"slotId\":\"5c344a9f1b0000e9096f6371\",\"slotName\":\"订机票_姓氏\"},{\"slotId\":\"5c344ace1b000020016f6372\",\"slotName\":\"订机票_身份证号\"},{\"slotId\":\"N\",\"slotName\":\"请选择\"}],\"operatored\":[{\"text\":\"请选择\",\"value\":\"N\"},{\"text\":\"已填充\",\"value\":\"filled\"},{\"text\":\"未填充\",\"value\":\"unfilled\"}],\"operator\":\"unfilled\",\"slotsId\":\"5c340ecd1b0000f2006f636a\",\"values\":[],\"itemType\":\"slot\"}]],\"actions\":[{\"name\":\"请选择\",\"val\":\"N\"},{\"name\":\"询问动作\",\"val\":\"inquire\"}],\"action\":\"inquire\",\"slotsList\":[{\"selected\":\"5c340e3a1b0000f4006f6368\",\"list\":[{\"slotId\":\"5c340d831b0000f5006f6365\",\"slotName\":\"订机票_航班\"},{\"slotId\":\"5c340da41b0000f2006f6366\",\"slotName\":\"订机票_航班号\"},{\"slotId\":\"5c340dda1b0000f5006f6367\",\"slotName\":\"订机票_舱位\"},{\"slotId\":\"5c340e3a1b0000f4006f6368\",\"slotName\":\"订机票_出发城市\"},{\"slotId\":\"5c340e731b0000f4006f6369\",\"slotName\":\"订机票_目的城市\"},{\"slotId\":\"5c340ecd1b0000f2006f636a\",\"slotName\":\"订机票_日期\"},{\"slotId\":\"5c340ef71b0000f2006f636b\",\"slotName\":\"订机票_出发时间\"},{\"slotId\":\"5c340f121b0000f4006f636c\",\"slotName\":\"订机票_到达时间\"},{\"slotId\":\"5c344a471b0000e9096f6370\",\"slotName\":\"订机票_电话\"},{\"slotId\":\"5c344a9f1b0000e9096f6371\",\"slotName\":\"订机票_姓氏\"},{\"slotId\":\"5c344ace1b000020016f6372\",\"slotName\":\"订机票_身份证号\"},{\"slotId\":\"N\",\"slotName\":\"请选择\"}]},{\"selected\":\"5c340e731b0000f4006f6369\",\"list\":[{\"slotId\":\"5c340d831b0000f5006f6365\",\"slotName\":\"订机票_航班\"},{\"slotId\":\"5c340da41b0000f2006f6366\",\"slotName\":\"订机票_航班号\"},{\"slotId\":\"5c340dda1b0000f5006f6367\",\"slotName\":\"订机票_舱位\"},{\"slotId\":\"5c340e3a1b0000f4006f6368\",\"slotName\":\"订机票_出发城市\"},{\"slotId\":\"5c340e731b0000f4006f6369\",\"slotName\":\"订机票_目的城市\"},{\"slotId\":\"5c340ecd1b0000f2006f636a\",\"slotName\":\"订机票_日期\"},{\"slotId\":\"5c340ef71b0000f2006f636b\",\"slotName\":\"订机票_出发时间\"},{\"slotId\":\"5c340f121b0000f4006f636c\",\"slotName\":\"订机票_到达时间\"},{\"slotId\":\"5c344a471b0000e9096f6370\",\"slotName\":\"订机票_电话\"},{\"slotId\":\"5c344a9f1b0000e9096f6371\",\"slotName\":\"订机票_姓氏\"},{\"slotId\":\"5c344ace1b000020016f6372\",\"slotName\":\"订机票_身份证号\"},{\"slotId\":\"N\",\"slotName\":\"请选择\"}]},{\"selected\":\"5c340ecd1b0000f2006f636a\",\"list\":[{\"slotId\":\"5c340d831b0000f5006f6365\",\"slotName\":\"订机票_航班\"},{\"slotId\":\"5c340da41b0000f2006f6366\",\"slotName\":\"订机票_航班号\"},{\"slotId\":\"5c340dda1b0000f5006f6367\",\"slotName\":\"订机票_舱位\"},{\"slotId\":\"5c340e3a1b0000f4006f6368\",\"slotName\":\"订机票_出发城市\"},{\"slotId\":\"5c340e731b0000f4006f6369\",\"slotName\":\"订机票_目的城市\"},{\"slotId\":\"5c340ecd1b0000f2006f636a\",\"slotName\":\"订机票_日期\"},{\"slotId\":\"5c340ef71b0000f2006f636b\",\"slotName\":\"订机票_出发时间\"},{\"slotId\":\"5c340f121b0000f4006f636c\",\"slotName\":\"订机票_到达时间\"},{\"slotId\":\"5c344a471b0000e9096f6370\",\"slotName\":\"订机票_电话\"},{\"slotId\":\"5c344a9f1b0000e9096f6371\",\"slotName\":\"订机票_姓氏\"},{\"slotId\":\"5c344ace1b000020016f6372\",\"slotName\":\"订机票_身份证号\"},{\"slotId\":\"N\",\"slotName\":\"请选择\"}]}],\"inquire\":\"请问您从哪个城市去往哪个城市，什么时候出发？\",\"radio\":\"auto\",\"options\":[\"\"]}]},\"check\":[{\"caseItemGroups\":[[{\"slotList\":[{\"slotId\":\"5c340d831b0000f5006f6365\",\"slotName\":\"订机票_航班\"},{\"slotId\":\"5c340da41b0000f2006f6366\",\"slotName\":\"订机票_航班号\"},{\"slotId\":\"5c340dda1b0000f5006f6367\",\"slotName\":\"订机票_舱位\"},{\"slotId\":\"5c340e3a1b0000f4006f6368\",\"slotName\":\"订机票_出发城市\"},{\"slotId\":\"5c340e731b0000f4006f6369\",\"slotName\":\"订机票_目的城市\"},{\"slotId\":\"5c340ecd1b0000f2006f636a\",\"slotName\":\"订机票_日期\"},{\"slotId\":\"5c340ef71b0000f2006f636b\",\"slotName\":\"订机票_出发时间\"},{\"slotId\":\"5c340f121b0000f4006f636c\",\"slotName\":\"订机票_到达时间\"},{\"slotId\":\"5c344a471b0000e9096f6370\",\"slotName\":\"订机票_电话\"},{\"slotId\":\"5c344a9f1b0000e9096f6371\",\"slotName\":\"订机票_姓氏\"},{\"slotId\":\"5c344ace1b000020016f6372\",\"slotName\":\"订机票_身份证号\"},{\"slotId\":\"N\",\"slotName\":\"请选择\"}],\"operatored\":[{\"text\":\"请选择\",\"value\":\"N\"},{\"text\":\"已填充\",\"value\":\"filled\"},{\"text\":\"未填充\",\"value\":\"unfilled\"},{\"text\":\"等于\",\"value\":\"equal\"},{\"text\":\"不等于\",\"value\":\"not_equal\"},{\"text\":\"包含\",\"value\":\"contain\"},{\"text\":\"不包含\",\"value\":\"not_contain\"},{\"text\":\"大于(对应数值型词槽)\",\"value\":\"great_than\"},{\"text\":\"小于(对应数值型词槽)\",\"value\":\"less_than\"},{\"text\":\"大于等于(对应数值型词槽)\",\"value\":\"great_than_equal\"},{\"text\":\"小于等于(对应数值型词槽)\",\"value\":\"less_than_equal\"},{\"text\":\"大于(对应时间段型词槽)\",\"value\":\"great_than_time\"},{\"text\":\"小于(对应时间段型词槽)\",\"value\":\"less_than_time\"},{\"text\":\"早于(对应日期时间型词槽)\",\"value\":\"new_than\"},{\"text\":\"晚于(对应日期时间型词槽)\",\"value\":\"old_than\"}],\"operator\":\"not_equal\",\"operator1\":\"N\",\"time\":\"\",\"time1\":\"\",\"slotsId\":\"5c340e3a1b0000f4006f6368\",\"values\":[{\"id\":\"\",\"name\":\"北京\"},{\"id\":\"\",\"name\":\"上海\"},{\"id\":\"\",\"name\":\"广州\"},{\"id\":\"\",\"name\":\"深圳\"},{\"id\":\"\",\"name\":\"成都\"},{\"id\":\"\",\"name\":\"昆明\"},{\"id\":\"\",\"name\":\"海口\"},{\"id\":\"\",\"name\":\"西安\"},{\"id\":\"\",\"name\":\"杭州\"},{\"id\":\"\",\"name\":\"厦门\"},{\"id\":\"\",\"name\":\"重庆\"},{\"id\":\"\",\"name\":\"青岛\"},{\"id\":\"\",\"name\":\"大连\"},{\"id\":\"\",\"name\":\"武汉\"},{\"id\":\"\",\"name\":\"沈阳\"},{\"id\":\"\",\"name\":\"乌鲁木齐\"},{\"id\":\"\",\"name\":\"长沙\"},{\"id\":\"\",\"name\":\"福州\"},{\"id\":\"\",\"name\":\"桂林\"},{\"id\":\"\",\"name\":\"哈尔滨\"},{\"id\":\"\",\"name\":\"贵阳\"},{\"id\":\"\",\"name\":\"郑州\"},{\"id\":\"\",\"name\":\"温州\"},{\"id\":\"\",\"name\":\"济南\"},{\"id\":\"\",\"name\":\"宁波\"},{\"id\":\"\",\"name\":\"天津\"},{\"id\":\"\",\"name\":\"太原\"},{\"id\":\"\",\"name\":\"南宁\"},{\"id\":\"\",\"name\":\"南昌\"},{\"id\":\"\",\"name\":\"张家界\"},{\"id\":\"\",\"name\":\"合肥\"},{\"id\":\"\",\"name\":\"兰州\"},{\"id\":\"\",\"name\":\"烟台\"},{\"id\":\"\",\"name\":\"九寨沟\"},{\"id\":\"\",\"name\":\"丽江\"},{\"id\":\"\",\"name\":\"呼和浩特\"},{\"id\":\"\",\"name\":\"拉萨\"},{\"id\":\"\",\"name\":\"珠海\"},{\"id\":\"\",\"name\":\"银川\"},{\"id\":\"\",\"name\":\"延吉\"},{\"id\":\"\",\"name\":\"黄山\"},{\"id\":\"\",\"name\":\"宜昌\"},{\"id\":\"\",\"name\":\"包头\"},{\"id\":\"\",\"name\":\"北海\"},{\"id\":\"\",\"name\":\"石家庄\"},{\"id\":\"\",\"name\":\"常州\"},{\"id\":\"\",\"name\":\"黄岩\"},{\"id\":\"\",\"name\":\"攀枝花\"},{\"id\":\"\",\"name\":\"绵阳\"},{\"id\":\"\",\"name\":\"牡丹江\"},{\"id\":\"\",\"name\":\"徐州\"},{\"id\":\"\",\"name\":\"宜宾\"},{\"id\":\"\",\"name\":\"西昌\"},{\"id\":\"\",\"name\":\"柳州\"},{\"id\":\"\",\"name\":\"海拉尔\"},{\"id\":\"\",\"name\":\"连云港\"},{\"id\":\"\",\"name\":\"南通\"},{\"id\":\"\",\"name\":\"榆林\"},{\"id\":\"\",\"name\":\"洛阳\"},{\"id\":\"\",\"name\":\"铜仁\"},{\"id\":\"\",\"name\":\"常德\"},{\"id\":\"\",\"name\":\"佳木斯\"},{\"id\":\"\",\"name\":\"长治\"},{\"id\":\"\",\"name\":\"齐齐哈尔\"},{\"id\":\"\",\"name\":\"赣州\"},{\"id\":\"\",\"name\":\"延安\"},{\"id\":\"\",\"name\":\"丹东\"},{\"id\":\"\",\"name\":\"吉林\"},{\"id\":\"\",\"name\":\"南阳\"},{\"id\":\"\",\"name\":\"秦皇岛\"},{\"id\":\"\",\"name\":\"锦州\"},{\"id\":\"\",\"name\":\"\"},{\"id\":\"\",\"name\":\"\"}],\"itemType\":\"slot\"}]],\"method\":\"local\",\"reply\":\"<订机票_出发城市>没有机场！\",\"isClear\":true,\"retryClearSlots\":\"true\",\"apiId\":\"N\",\"apiFieldMapping\":[{\"forItem\":\"N\",\"apiFieldName\":\"N\",\"slotId\":\"N\",\"apiFiledList\":[],\"slotList\":[{\"slotId\":\"5c340d831b0000f5006f6365\",\"slotName\":\"订机票_航班\"},{\"slotId\":\"5c340da41b0000f2006f6366\",\"slotName\":\"订机票_航班号\"},{\"slotId\":\"5c340dda1b0000f5006f6367\",\"slotName\":\"订机票_舱位\"},{\"slotId\":\"5c340e3a1b0000f4006f6368\",\"slotName\":\"订机票_出发城市\"},{\"slotId\":\"5c340e731b0000f4006f6369\",\"slotName\":\"订机票_目的城市\"},{\"slotId\":\"5c340ecd1b0000f2006f636a\",\"slotName\":\"订机票_日期\"},{\"slotId\":\"5c340ef71b0000f2006f636b\",\"slotName\":\"订机票_出发时间\"},{\"slotId\":\"5c340f121b0000f4006f636c\",\"slotName\":\"订机票_到达时间\"},{\"slotId\":\"5c344a471b0000e9096f6370\",\"slotName\":\"订机票_电话\"},{\"slotId\":\"5c344a9f1b0000e9096f6371\",\"slotName\":\"订机票_姓氏\"},{\"slotId\":\"5c344ace1b000020016f6372\",\"slotName\":\"订机票_身份证号\"},{\"slotId\":\"N\",\"slotName\":\"请选择\"}]}],\"check\":{\"apiFieldName\":\"N\",\"operator\":\"N\",\"value\":\"N\"},\"slotIds\":[{\"slotId\":\"5c340e3a1b0000f4006f6368\",\"list\":[{\"slotId\":\"5c340d831b0000f5006f6365\",\"slotName\":\"订机票_航班\"},{\"slotId\":\"5c340da41b0000f2006f6366\",\"slotName\":\"订机票_航班号\"},{\"slotId\":\"5c340dda1b0000f5006f6367\",\"slotName\":\"订机票_舱位\"},{\"slotId\":\"5c340e3a1b0000f4006f6368\",\"slotName\":\"订机票_出发城市\"},{\"slotId\":\"5c340e731b0000f4006f6369\",\"slotName\":\"订机票_目的城市\"},{\"slotId\":\"5c340ecd1b0000f2006f636a\",\"slotName\":\"订机票_日期\"},{\"slotId\":\"5c340ef71b0000f2006f636b\",\"slotName\":\"订机票_出发时间\"},{\"slotId\":\"5c340f121b0000f4006f636c\",\"slotName\":\"订机票_到达时间\"},{\"slotId\":\"5c344a471b0000e9096f6370\",\"slotName\":\"订机票_电话\"},{\"slotId\":\"5c344a9f1b0000e9096f6371\",\"slotName\":\"订机票_姓氏\"},{\"slotId\":\"5c344ace1b000020016f6372\",\"slotName\":\"订机票_身份证号\"},{\"slotId\":\"N\",\"slotName\":\"请选择\"}]}]},{\"caseItemGroups\":[[{\"slotList\":[{\"slotId\":\"5c340d831b0000f5006f6365\",\"slotName\":\"订机票_航班\"},{\"slotId\":\"5c340da41b0000f2006f6366\",\"slotName\":\"订机票_航班号\"},{\"slotId\":\"5c340dda1b0000f5006f6367\",\"slotName\":\"订机票_舱位\"},{\"slotId\":\"5c340e3a1b0000f4006f6368\",\"slotName\":\"订机票_出发城市\"},{\"slotId\":\"5c340e731b0000f4006f6369\",\"slotName\":\"订机票_目的城市\"},{\"slotId\":\"5c340ecd1b0000f2006f636a\",\"slotName\":\"订机票_日期\"},{\"slotId\":\"5c340ef71b0000f2006f636b\",\"slotName\":\"订机票_出发时间\"},{\"slotId\":\"5c340f121b0000f4006f636c\",\"slotName\":\"订机票_到达时间\"},{\"slotId\":\"5c344a471b0000e9096f6370\",\"slotName\":\"订机票_电话\"},{\"slotId\":\"5c344a9f1b0000e9096f6371\",\"slotName\":\"订机票_姓氏\"},{\"slotId\":\"5c344ace1b000020016f6372\",\"slotName\":\"订机票_身份证号\"},{\"slotId\":\"N\",\"slotName\":\"请选择\"}],\"operatored\":[{\"text\":\"请选择\",\"value\":\"N\"},{\"text\":\"已填充\",\"value\":\"filled\"},{\"text\":\"未填充\",\"value\":\"unfilled\"},{\"text\":\"等于\",\"value\":\"equal\"},{\"text\":\"不等于\",\"value\":\"not_equal\"},{\"text\":\"包含\",\"value\":\"contain\"},{\"text\":\"不包含\",\"value\":\"not_contain\"},{\"text\":\"大于(对应数值型词槽)\",\"value\":\"great_than\"},{\"text\":\"小于(对应数值型词槽)\",\"value\":\"less_than\"},{\"text\":\"大于等于(对应数值型词槽)\",\"value\":\"great_than_equal\"},{\"text\":\"小于等于(对应数值型词槽)\",\"value\":\"less_than_equal\"},{\"text\":\"大于(对应时间段型词槽)\",\"value\":\"great_than_time\"},{\"text\":\"小于(对应时间段型词槽)\",\"value\":\"less_than_time\"},{\"text\":\"早于(对应日期时间型词槽)\",\"value\":\"new_than\"},{\"text\":\"晚于(对应日期时间型词槽)\",\"value\":\"old_than\"}],\"operator\":\"not_equal\",\"operator1\":\"N\",\"time\":\"\",\"time1\":\"\",\"slotsId\":\"5c340e731b0000f4006f6369\",\"values\":[{\"id\":\"\",\"name\":\"北京\"},{\"id\":\"\",\"name\":\"上海\"},{\"id\":\"\",\"name\":\"广州\"},{\"id\":\"\",\"name\":\"深圳\"},{\"id\":\"\",\"name\":\"成都\"},{\"id\":\"\",\"name\":\"昆明\"},{\"id\":\"\",\"name\":\"海口\"},{\"id\":\"\",\"name\":\"西安\"},{\"id\":\"\",\"name\":\"杭州\"},{\"id\":\"\",\"name\":\"厦门\"},{\"id\":\"\",\"name\":\"重庆\"},{\"id\":\"\",\"name\":\"青岛\"},{\"id\":\"\",\"name\":\"大连\"},{\"id\":\"\",\"name\":\"武汉\"},{\"id\":\"\",\"name\":\"沈阳\"},{\"id\":\"\",\"name\":\"乌鲁木齐\"},{\"id\":\"\",\"name\":\"长沙\"},{\"id\":\"\",\"name\":\"福州\"},{\"id\":\"\",\"name\":\"桂林\"},{\"id\":\"\",\"name\":\"哈尔滨\"},{\"id\":\"\",\"name\":\"贵阳\"},{\"id\":\"\",\"name\":\"郑州\"},{\"id\":\"\",\"name\":\"温州\"},{\"id\":\"\",\"name\":\"济南\"},{\"id\":\"\",\"name\":\"宁波\"},{\"id\":\"\",\"name\":\"天津\"},{\"id\":\"\",\"name\":\"太原\"},{\"id\":\"\",\"name\":\"南宁\"},{\"id\":\"\",\"name\":\"南昌\"},{\"id\":\"\",\"name\":\"张家界\"},{\"id\":\"\",\"name\":\"合肥\"},{\"id\":\"\",\"name\":\"兰州\"},{\"id\":\"\",\"name\":\"烟台\"},{\"id\":\"\",\"name\":\"九寨沟\"},{\"id\":\"\",\"name\":\"丽江\"},{\"id\":\"\",\"name\":\"呼和浩特\"},{\"id\":\"\",\"name\":\"拉萨\"},{\"id\":\"\",\"name\":\"珠海\"},{\"id\":\"\",\"name\":\"银川\"},{\"id\":\"\",\"name\":\"延吉\"},{\"id\":\"\",\"name\":\"黄山\"},{\"id\":\"\",\"name\":\"宜昌\"},{\"id\":\"\",\"name\":\"包头\"},{\"id\":\"\",\"name\":\"北海\"},{\"id\":\"\",\"name\":\"石家庄\"},{\"id\":\"\",\"name\":\"常州\"},{\"id\":\"\",\"name\":\"黄岩\"},{\"id\":\"\",\"name\":\"攀枝花\"},{\"id\":\"\",\"name\":\"绵阳\"},{\"id\":\"\",\"name\":\"牡丹江\"},{\"id\":\"\",\"name\":\"徐州\"},{\"id\":\"\",\"name\":\"宜宾\"},{\"id\":\"\",\"name\":\"西昌\"},{\"id\":\"\",\"name\":\"柳州\"},{\"id\":\"\",\"name\":\"海拉尔\"},{\"id\":\"\",\"name\":\"连云港\"},{\"id\":\"\",\"name\":\"南通\"},{\"id\":\"\",\"name\":\"榆林\"},{\"id\":\"\",\"name\":\"洛阳\"},{\"id\":\"\",\"name\":\"铜仁\"},{\"id\":\"\",\"name\":\"常德\"},{\"id\":\"\",\"name\":\"佳木斯\"},{\"id\":\"\",\"name\":\"长治\"},{\"id\":\"\",\"name\":\"齐齐哈尔\"},{\"id\":\"\",\"name\":\"赣州\"},{\"id\":\"\",\"name\":\"延安\"},{\"id\":\"\",\"name\":\"丹东\"},{\"id\":\"\",\"name\":\"吉林\"},{\"id\":\"\",\"name\":\"南阳\"},{\"id\":\"\",\"name\":\"秦皇岛\"},{\"id\":\"\",\"name\":\"锦州\"},{\"id\":\"\",\"name\":\"\"}],\"itemType\":\"slot\"}]],\"method\":\"local\",\"reply\":\"<订机票_目的城市>没有机场！\",\"isClear\":true,\"retryClearSlots\":\"true\",\"apiId\":\"N\",\"apiFieldMapping\":[{\"forItem\":\"N\",\"apiFieldName\":\"N\",\"slotId\":\"N\",\"apiFiledList\":[],\"slotList\":[{\"slotId\":\"5c340d831b0000f5006f6365\",\"slotName\":\"订机票_航班\"},{\"slotId\":\"5c340da41b0000f2006f6366\",\"slotName\":\"订机票_航班号\"},{\"slotId\":\"5c340dda1b0000f5006f6367\",\"slotName\":\"订机票_舱位\"},{\"slotId\":\"5c340e3a1b0000f4006f6368\",\"slotName\":\"订机票_出发城市\"},{\"slotId\":\"5c340e731b0000f4006f6369\",\"slotName\":\"订机票_目的城市\"},{\"slotId\":\"5c340ecd1b0000f2006f636a\",\"slotName\":\"订机票_日期\"},{\"slotId\":\"5c340ef71b0000f2006f636b\",\"slotName\":\"订机票_出发时间\"},{\"slotId\":\"5c340f121b0000f4006f636c\",\"slotName\":\"订机票_到达时间\"},{\"slotId\":\"5c344a471b0000e9096f6370\",\"slotName\":\"订机票_电话\"},{\"slotId\":\"5c344a9f1b0000e9096f6371\",\"slotName\":\"订机票_姓氏\"},{\"slotId\":\"5c344ace1b000020016f6372\",\"slotName\":\"订机票_身份证号\"},{\"slotId\":\"N\",\"slotName\":\"请选择\"}]}],\"check\":{\"apiFieldName\":\"N\",\"operator\":\"N\",\"value\":\"N\"},\"slotIds\":[{\"slotId\":\"5c340e731b0000f4006f6369\",\"list\":[{\"slotId\":\"5c340d831b0000f5006f6365\",\"slotName\":\"订机票_航班\"},{\"slotId\":\"5c340da41b0000f2006f6366\",\"slotName\":\"订机票_航班号\"},{\"slotId\":\"5c340dda1b0000f5006f6367\",\"slotName\":\"订机票_舱位\"},{\"slotId\":\"5c340e3a1b0000f4006f6368\",\"slotName\":\"订机票_出发城市\"},{\"slotId\":\"5c340e731b0000f4006f6369\",\"slotName\":\"订机票_目的城市\"},{\"slotId\":\"5c340ecd1b0000f2006f636a\",\"slotName\":\"订机票_日期\"},{\"slotId\":\"5c340ef71b0000f2006f636b\",\"slotName\":\"订机票_出发时间\"},{\"slotId\":\"5c340f121b0000f4006f636c\",\"slotName\":\"订机票_到达时间\"},{\"slotId\":\"5c344a471b0000e9096f6370\",\"slotName\":\"订机票_电话\"},{\"slotId\":\"5c344a9f1b0000e9096f6371\",\"slotName\":\"订机票_姓氏\"},{\"slotId\":\"5c344ace1b000020016f6372\",\"slotName\":\"订机票_身份证号\"},{\"slotId\":\"N\",\"slotName\":\"请选择\"}]}]}],\"mark\":[{\"question\":\"从北京到上海的机票\",\"words\":[{\"word\":\"北京\",\"slotId\":\"5c340e3a1b0000f4006f6368\"},{\"word\":\"上海\",\"slotId\":\"5c340e731b0000f4006f6369\"}]},{\"question\":\"明天去北京\",\"words\":[{\"word\":\"明天\",\"slotId\":\"5c340ecd1b0000f2006f636a\"},{\"word\":\"北京\",\"slotId\":\"5c340e731b0000f4006f6369\"}]}],\"slot\":[{\"condition\":\"none\",\"askQuestion\":\"N\",\"operator\":\"contain\",\"answerType\":\"dict\",\"value\":\"CITY\",\"conditions\":[[],{\"itemType\":\"logic\",\"value\":\"or\"},[{\"itemType\":\"case\",\"measure\":\"left\",\"limit\":\"1\",\"operator\":\"contain\",\"value\":\"从，在\"}],{\"itemType\":\"logic\",\"value\":\"or\"},[{\"itemType\":\"case\",\"measure\":\"right\",\"limit\":\"2\",\"operator\":\"contain\",\"value\":\"出发，开始，去，到，飞，往\"},{\"itemType\":\"logic\",\"value\":\"and\"},{\"itemType\":\"case\",\"measure\":\"left\",\"limit\":\"1\",\"operator\":\"not_contain\",\"value\":\"到\"}]],\"resultSlotId\":\"5c340e3a1b0000f4006f6368\"},{\"condition\":\"none\",\"askQuestion\":\"N\",\"operator\":\"contain\",\"answerType\":\"dict\",\"value\":\"CITY\",\"conditions\":[[],{\"itemType\":\"logic\",\"value\":\"or\"},[{\"itemType\":\"case\",\"measure\":\"left\",\"limit\":\"2\",\"operator\":\"contain\",\"value\":\"目的，往，飞，到，去\"}]],\"resultSlotId\":\"5c340e731b0000f4006f6369\"}]},\"dialogId\":\"5bff95bd670000e500dd076d-5c333324ca8005647c0ad101\",\"robotId\":\"5bff95bd670000e500dd076d\"}")
    val resutl = removeAllNode(Set("slotList", "list"), "")(JsDefined(json))
    println(resutl)


  }


  def removeAllNode(removeNodeNames: Set[String], key: String)(result: JsLookupResult): JsValue = {
    result match {
      case JsDefined(obj: JsObject) => {
        val temp = obj.value.map(pair => {
          val (key, value) = pair
          if (removeNodeNames.contains(key)) {
            key -> JsNull
          } else {
            key -> removeAllNode(removeNodeNames, key)(JsDefined(value))
          }
        })
        Json.toJson(temp)
      }
      case JsDefined(arr: JsArray) => {
        val array = arr.value.map(item => (removeAllNode(removeNodeNames, key)(JsDefined(item))))
        Json.toJson(array)
      }
      case x => x.get
    }
  }

  @Test
  def testSetDiff(): Unit = {
    println(Set(1, 2, 3) -- Set(3, 4, 5))
    val id = "/11670469704996648"
    val start = id.lastIndexOf("/")
    println(id.substring(start + 1, id.length))
  }


  @Test
  def testFB(): Unit = {
    println(5L !) // 120
    println(100L !) // 120

    def factorial(i: BigDecimal): BigDecimal = if (i < 1) 1 else i * factorial(i - 1)

    println(factorial(100))
  }

  implicit def int2IntOper: Long => IntOper = (i: Long) => new IntOper(i)

  class IntOper(x: Long) {
    def !(): Long = if (x == 0) 1 else x * ((x - 1) !)
  }


  @Test
  def testAt() {
    case class AA(age: Int)

    AA(12) match {
      case e@AA(age) => { // @类似于赋值语句，把@后面的内容赋值给e
        println(e)
        println(e.age)
        println(age)
      }
    }

  }

  @Test
  def testMapWithDefault(): Unit = {

    val map = Map[String, Int]("a" -> 1).withDefaultValue(100)
    println(map("a"))
    println(map.get("a"))
    println(map("b"))
    println(map.get("b"))


    val m = Map[Int, List[String]]().withDefaultValue(List())
    println(m(123))


  }

  @Test
  def testStream03(): Unit = {
    println(List(1, 2, 3, 4, 5).toStream.filter(_ == 2).isEmpty)
    println("" == "")
  }

  @Test
  def createFile(): Unit = {
    val fs = new File("C:\\Users\\DIY\\AppData\\Local\\Temp\\playtemp5305434099464858440\\123\\multipartBody88445714197351sdfsd47182asTemporaryFile.tmp").createNewFile()
    Collections.synchronizedMap(null)

  }

  @Test
  def hello2019(): Unit = {
    println("Hello 2019 !!!")
  }

  @Test
  def testList04() {
    val list = List(1, 2, 3, 4)
    println(list.++:(List(5, 6, 7)))
    println(List(5, 6, 7).++:(list))
    println(List(5, 6, 7).tail.++:(list))

    System.err.println("error message")
  }

  @Test
  def testStream04(): Unit = {
    println(List(1, 2, 3, 4).toStream.filter(_ > 10).take(4).toList)

    val list = List.empty[Int]
    val list2 = List.empty[Int]
    val list3 = list2.++:(list)
    println(list3)

    println("itbot.gf.com.cn/qizhi_gf".replace("itbot", "itbotfw"))
  }

  @Test
  def testCase02(): Unit = {
    "kkk" match {
      case i if (i == "aa" || i == "bb") => println(i)
      case "cc" => println("cc")
      case _ => println("====")
    }

    val key = "会12ds" +
      ""
    val contentKey = if(key.size > 2 ){
      key.charAt(0)+" "+key.substring(1, key.size)
    }else{
      key
    }
    println(contentKey)
  }

  @Test
  def testJsonFormat(): Unit ={
   /* print(Json.parse("{}"))
    println(((9590) / 9590.toFloat).formatted("%.4f"))*/
    val data = "aaa".split("\\|")
    data.foreach(println)
    println(data)
  }
  @Test
  def testLocalDate(): Unit ={
    val nowDate = LocalDateTime.now().minusMonths(1L).toInstant(ZoneOffset.of("+8"))
    println(nowDate.toEpochMilli())

    (0 until 15).map(i => LocalDateTime.now().minusDays(i).toLocalDate.toString()).foreach(println)

    val result = List(1,2,3,4,5).sum
    println(result)


    val yesterday = LocalDate.now().minusDays(1L).toEpochDay
    val beforYesterday = LocalDateTime.now().minusDays(2L).toInstant(ZoneOffset.of("+8")).toEpochMilli
    println(yesterday)
    println(beforYesterday)
    val timeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS Z")
    val yesterdayStr = new DateTime().minusDays(1).toString("yyyy-MM-dd")
    val yesterdayStartTime = timeFormat.withOffsetParsed.parseDateTime(yesterdayStr + " 00:00:00.000 +0800").getMillis
    println(yesterdayStartTime)

  }

  @Test
  def testSplit02(): Unit ={
    "aaaa;bbb；ddd,ccc,eee，fff".split("(,|，|;|；)").foreach(println(_))

    List(1,2,3).:+(4) // 1234

    val time = new Date(1544085026000L)


    println(time)


    val res = List(1,2,3,4).find(_ > 2)
    println(res)
    val text = "\r\n123\n\t"
    println("=============")
    println(text.trim)
    println("=============")
  }


  @Test
  def testFile02(): Unit = {
    val file = new File("G:\\test\\run_api_bak.log")
    println(file.getName)
    val fileName = file.getName
    val suffix = fileName.substring(fileName.lastIndexOf(".") + 1)
    println(suffix)
    println(fileName.substring(0,fileName.indexOf(".")))

  }

  @Test
  def testReplace03(): Unit ={
    val a = "assss${aaa}"
    val tem = "${aaa}".replace("$","\\$")
      .replace("{","\\{")
      .replace("}","\\}")
    println(tem)
    println(a.replaceAll(tem,"sss"))
  }

  @Test
  def testStringLength(): Unit = {
    val str = "tempAnser"
    println(str.length)
    val d1 = DateTime.now
    val d2 = DateTime.parse("2019-05-23")
    println(d2.toString())
    println(d1.toString())
    println(d2.compareTo(d1))
    println(d1.compareTo(d2))

  }

}

object test {
  def main(args: Array[String]): Unit = {
    printStackTrace()
  }

  def printStackTrace(): Unit = {

    Thread.currentThread.getStackTrace.toList.foreach(item => {
      println(item)
    })
  }
}