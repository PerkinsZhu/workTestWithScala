package zpj.scalazTest

import java.util.Date

import cats.{Monoid, Semigroup}
import org.junit.Test

import scala.concurrent.{ExecutionContext, Future}
import scalaz.Apply

/**
  * Created by PerkinsZhu on 2018/5/12 13:35
  **/
class ScalazTest {

  def tell[T](t: T)(implicit M: Tellable[T]) = {
    M.tell(t)
  }

  @Test
  def test1(): Unit = {

    implicit val personTeller = new Tellable[Person] {
      def tell(t: Person): String = "I am " + t.name
    }
    implicit val intTeller = new Tellable[Int] {
      def tell(t: Int): String = "I am Int " + t.toString
    }
    implicit object colorTeller extends Tellable[Color] {
      override def tell(t: Color): String = s" i am color :${t.descript}"
    }

    println(tell(Color("RED")))
    println(tell(Person("John")))
    println(tell(43))

    implicit object listTeller extends Tellable[List[Color]] {
      def tell(t: List[Color]): String = {
        (t.map(c => c.descript)).mkString("I am list of color [", ",", "]")
      }
    }
    println(tell[List[Color]](List(Color("RED"), Color("BLACK"), Color("YELLOW"), Color("BLUE"))))
  }


  @Test
  def test2(): Unit = {
    head(List(1, 2, 3, 4))
    head(List("a", "b", "c"))
    case class Car(manu: String)
    head(Car("Honda") :: Car("Toyota") :: Nil)
    //    println(head(Nil))

    sum(List(1, 2, 3))
  }

  def head[T](xs: List[T]): T = xs(0)

  //  def sum[M[_],A](xs: M[A]): A
  def sum(xs: List[Int]): Int = xs.foldLeft(0) {
    _ + _
  }


  @Test
  def demo1(): Unit = {
    import scalaz._
    import std.option._, std.list._

    Apply[Option].apply2(some(1), some(2))((a, b) => a + b)
    //    Traverse[List].traverse(List(1, 2, 3))(i => some(i))
  }

  @Test
  def testCats(): Unit = {
    import cats.Show
    import cats.instances.int.catsStdShowForInt
    val showInt = Show[Int]
    val r: String = showInt.show(1212312)
    println(r)
    import cats.instances.list.catsStdShowForList
    print(Show[List[Int]].show(List(1, 2, 3, 4, 5)))


    //注意这里涉及到两个隐式转换，T-> Opt[T]  还有 一个隐式变量 T-> ShowAble[T]
    import cats.syntax.show._
    import cats.instances.int.catsStdShowForInt
    println(List(1, 2, 3, 4).show)

    import cats.Eq
    import cats.instances.int._
    val qeInt = Eq[Int]
    println(qeInt.eqv(1, 11))

    import cats.syntax.eq._
    println(1 === 1)
    println(1 =!= 2)

    import cats.instances.string._
    println("11" === "222")

    import cats.instances.long._
    implicit val dateEqual = Eq.instance[Date] { (date1, date2) =>
      date1.getTime === date2.getTime
    }

    val x = new Date()
    val y = new Date()
    println(x === y)
    println(x === x)
  }

  @Test
  def testMonid(): Unit = {
    val intAdditionMonoid: Monoid[Int] = new Monoid[Int] {
      def empty: Int = 0

      def combine(x: Int, y: Int): Int = x + y
    }

    def combineAll[A: Monoid](as: List[A]): A = as.foldLeft(Monoid[A].empty)(Monoid[A].combine)
    import cats.instances.all._

    combineAll(List(1, 2, 3))
    // res4: Int = 6

    combineAll(List("hello", " ", "world"))
    // res5: String = hello world

    combineAll(List(Map('a' -> 1), Map('a' -> 2, 'b' -> 3), Map('b' -> 4, 'c' -> 5)))
    // res6: scala.collection.immutable.Map[Char,Int] = Map(b -> 7, c -> 5, a -> 3)

    combineAll(List(Set(1, 2), Set(2, 3, 4, 5)))
    // res7: scala.collection.immutable.Set[Int] = Set(5, 1, 2, 3, 4)

  }

  @Test
  def testSemigroup(): Unit = {
    import cats.instances.all._
    import cats.syntax.semigroup._

    def optionCombine[A: Semigroup](a: A, opt: Option[A]): A =
      opt.map(a |+| _).getOrElse(a)

    def mergeMap[K, V: Semigroup](lhs: Map[K, V], rhs: Map[K, V]): Map[K, V] =
      lhs.foldLeft(rhs) {
        case (acc, (k, v)) => acc.updated(k, optionCombine(v, acc.get(k)))
      }

    val xm1 = Map('a' -> 1, 'b' -> 2)
    // xm1: scala.collection.immutable.Map[Char,Int] = Map(a -> 1, b -> 2)

    val xm2 = Map('b' -> 3, 'c' -> 4)
    // xm2: scala.collection.immutable.Map[Char,Int] = Map(b -> 3, c -> 4)

    val x = mergeMap(xm1, xm2)
    // x: Map[Char,Int] = Map(b -> 5, c -> 4, a -> 1)

    val ym1 = Map(1 -> List("hello"))
    // ym1: scala.collection.immutable.Map[Int,List[String]] = Map(1 -> List(hello))

    val ym2 = Map(2 -> List("cats"), 1 -> List("world"))
    // ym2: scala.collection.immutable.Map[Int,List[String]] = Map(2 -> List(cats), 1 -> List(world))

    val y = mergeMap(ym1, ym2)
    // y: Map[Int,List[String]] = Map(2 -> List(cats), 1 -> List(hello, world))

    Semigroup[Map[Char, Int]].combine(xm1, xm2) == x
    // res8: Boolean = true

    Semigroup[Map[Int, List[String]]].combine(ym1, ym2) == y
    // res9: Boolean = true
  }

  @Test
  def testOptionMonoid(): Unit = {
    val list = List(NonEmptyList(1, List(2, 3)), NonEmptyList(4, List(5, 6)))
    val lifted = list.map(nel => Option(nel))
    println(list)
    println(lifted)
    Monoid.combineAll(lifted)
  }

  import cats.syntax.semigroup._

  implicit def optionMonoid[A: Semigroup]: Monoid[Option[A]] = new Monoid[Option[A]] {
    def empty: Option[A] = None

    def combine(x: Option[A], y: Option[A]): Option[A] =
      x match {
        case None => y
        case Some(xv) =>
          y match {
            case None => x
            case Some(yv) => Some(xv |+| yv)
          }
      }
  }


  @Test
  def testType(): Unit = {
    def showInfo[A, M[T]](a: M[A]): Unit = {
      println(a)
    }

    showInfo(List(2.3, 43, 4))
  }


  import scala.concurrent.ExecutionContext.Implicits.global

//  @Test
  /*def testTraversable(): Unit = {
    val f = (a: Int) => Future[Int] {a}
//    traverseFuture[Int, Int](List(1, 23, 4))(f)

    implicit val functorForOption: Functor[Option] = new Functor[Option] {
      def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa match {
        case None => None
        case Some(a) => Some(f(a))
      }
    }

  }*/

//  def traverseFuture[A, B](as: List[A])(f: A => Future[B])(implicit ec: ExecutionContext): Future[List[B]] = Future.traverse(as)(f)

}

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object NonEmptyList {
  implicit def nonEmptyListSemigroup[A]: Semigroup[NonEmptyList[A]] =
    new Semigroup[NonEmptyList[A]] {
      def combine(x: NonEmptyList[A], y: NonEmptyList[A]): NonEmptyList[A] = x ++ y
    }
}

final case class NonEmptyList[A](head: A, tail: List[A]) {
  def ++(other: NonEmptyList[A]): NonEmptyList[A] = NonEmptyList(head, tail ++ other.toList)

  def toList: List[A] = head :: tail
}


trait Tellable[T] {
  def tell(t: T): String
}

case class Color(descript: String)

case class Person(name: String)


object colorTeller extends Tellable[Color] {
  override def tell(t: Color): String = s" i am color :${t.descript}"
}

