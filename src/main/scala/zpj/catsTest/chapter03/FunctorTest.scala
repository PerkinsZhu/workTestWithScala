package zpj.catsTest.chapter03

import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/5/17 9:43
  **/
class FunctorTest {

  @Test
  def testOther01(): Unit = {
    import scala.language.higherKinds

    import cats.Functor
    import cats.instances.list._
    import cats.instances.option._

    // 通过 Functor.apply 获取 Functor 实例
    val listFunctor: Functor[List] = Functor[List]
    val optionFunctor: Functor[Option] = Functor[Option]

    val xs = List(1, 2, 3)
    listFunctor.map(xs)(_ + 1) // 2, 3, 4

    val o = Option(1)
    optionFunctor.map(o)(_ + 1) // Some(2)

    val f: Int ⇒ Int = x ⇒ x * 2

    // 使用 Functor.lift 将 Int => Int 转换为 List[Int] => List[Int]
    val fList: List[Int] ⇒ List[Int] = listFunctor.lift(f)
    fList(xs) // 2, 4, 6
  }

  @Test
  def testOther2(): Unit = {
    import scala.language.higherKinds
    import cats.Functor
    import cats.instances.list._ // for Functor
    import cats.instances.option._ // for Functor
    val list1 = List(1, 2, 3)
    // list1: List[Int] = List(1, 2, 3)
    val list2 = Functor[List].map(list1)(_ * 2)
    // list2: List[Int] = List(2, 4, 6)
    val option1 = Option(123)
    // option1: Option[Int] = Some(123)
    val option2 = Functor[Option].map(option1)(_.toString)
    val func = (x: Int) => x + 1
    // func: Int => Int = <function1>
    val liftedFunc = Functor[Option].lift(func)
    liftedFunc(Option(1))
    // res0: Option[Int] = Some(2)

    println(Functor[List].map(List("q", "22"))(_ * 2))
    println(Functor[List].map(List(Seq(1, 2, 3), Seq(2, 3, 4)))(_.mkString("--")))

  }

  @Test
  def testFunctor(): Unit = {
    import scala.language.higherKinds
    import cats.Functor
    import cats.instances.function._
    import cats.syntax.functor._
    import cats.instances.int._
    import cats.instances.function._
    import cats.syntax.functor._
    val func1 = (a: Int) => a + 1
    val func2 = (a: Int) => a * 2
    val func3 = (a: Int) => a + "!"
    val func4 = func1.andThen(func2).andThen(func3)
    func4(123)
  }

  @Test
  def testOther03(): Unit = {
    import cats.Functor
    import cats.instances.function._
    import cats.syntax.functor._
    val f1: Int ⇒ Int = x ⇒ x + 1
    val f2: Int ⇒ Int = x ⇒ x * 6
    val f = f1 map f2
    println(f(110)) // 666
  }


  @Test
  def testOther04(): Unit = {
    import cats.syntax.functor._
    import cats.Functor
    def doMath[F[_]](start: F[Int])(implicit functor: Functor[F]): F[Int] = start.map(n => n + 1 * 2)

    import cats.instances.option._ // for Functor
    import cats.instances.list._ // for Functor
    println(doMath(Option(20)))
    // res3: Option[Int] = Some(22)
    println(doMath(List(1, 2, 3)))
    // res4: List[Int] = List(3, 4, 5)

  }

}

class TestCase {
  @Test
  def test01(): Unit = {
    //    该隐式转换是：implicit instance : cats.Functor[F]
    import cats.instances.function._
    //    该隐式转换的目的是为了转换为FunctorOption ,然后就可以调用其map方法
    import cats.syntax.functor._
    val f: Int ⇒ Double = (x: Int) => x.toDouble
    val g: Double => Double = (x: Double) => x * 2
    println((f map g) (1))
    println((f andThen g) (1))
    println(g(f(1)))
    val func = ((x: Int) => x.toDouble).map(x => x + 1).map(x => x * 2).map(x => x + "!")
    println(func(23))
  }

  @Test
  def test02(): Unit = {
    import cats.Functor
    //    注意这里的F和A的关系 F是content 例如： List Option Function1
    //    A 是参数类型  例如： Int String Boolean
    //    这里传入的是必须是嵌套类型 即：F[_] ；类型
    implicit class FunctorOps[F[_], A](src: F[A]) {
      def map[B](func: A => B)(implicit functor: Functor[F]): F[B] =
        functor.map(src)(func)
    }
    import cats.instances.list._
    println(new FunctorOps(List(222)).map(x => x + 1))
    import cats.instances.option._
    println(new FunctorOps(Option(222)).map(x => x + 1))
    // 下面使用Function1来演示 implicit FunctorOps的使用(不适用List和Option的原因是因为其已具备map方法)
    val f: Int => Int = x => x + 1
    //下面的map方法需要一个隐式参数 implicit functor: Functor[F]
    import cats.instances.function._
    val g = f map (x => x * 10)
    println(g(10))

    //    import cats.instances.list.catsStdInstancesForList
    //    println(List(1,2,3,43) map (f)( cats.instances.list.catsStdInstancesForList ))
    import scala.concurrent.Future
    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.ExecutionContext
    implicit def futureFunctor(implicit ec: ExecutionContext): Functor[Future] =
      new Functor[Future] {
        override def map[A, B](fa: Future[A])(f: A ⇒ B): Future[B] =
          fa.map(f)(ec)
      }

    println(Functor[Future](futureFunctor).map(Future(10))(_ * 10))
  }

}

