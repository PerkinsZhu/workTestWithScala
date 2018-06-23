package zpj.baseTest

import java.text.SimpleDateFormat
import java.time.{Instant, LocalDate, LocalTime}
import java.util.concurrent.TimeUnit
import java.util.{Comparator, Date}

import cats.Monoid
import org.joda.time.{DateTime, DateTimeZone}
import org.junit.Test
import org.scalatest.FlatSpec
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import zpj.catsTest.chapter01.JsObject

import scala.annotation.tailrec
import scala.collection.mutable
import scala.concurrent.Future
import scala.util.Try

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
      //      case Cons(s,::) => println("")
      case x :: h => println(h)
      case _ => println(l1)
    }

    println(l1.reduceLeft(_ * _))


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

  case class Student(name: String, age: Int, little: Boolean)

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


    import sys.process._
    import java.net.URL
    import java.io.File
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
    println(TimeUnit.MILLISECONDS.toNanos(16L))
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
  def testString02(): Unit ={
    val temp = "替你还产^品分?为|1-4周，]利率：[&一周0%-(0.6%、二周0)%-1.3%、三周0%-2.0%、四周0%-2.7%，如按时还款不会产生其他费用；如逾期，罚息为贷款余额*罚息费率*逾期天数，罚息费率：逾期1-30天（含）-0.06%/日，逾期30天以上-0.1%/日，若日息低于1元的，则按照1元收取"
      .replaceAll("[%*/()\\[\\]\\^\\|\\.\\{\\}\\&\\?]","")
    println(temp)
  }

  @Test
  def testZipWithIndex(): Unit ={
(1 to 100).map(i => s"--$i--").zipWithIndex.foreach(println)
  }

}
