package zpj.scalazTest

import org.junit.Test

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

    sum(List(1,2,3))
  }
  def head[T](xs: List[T]): T = xs(0)

//  def sum[M[_],A](xs: M[A]): A
  def sum(xs: List[Int]): Int = xs.foldLeft(0){_ + _}


  @Test
  def demo1(): Unit ={
    import scalaz._
    import std.option._, std.list._

    Apply[Option].apply2(some(1), some(2))((a, b) => a + b)
//    Traverse[List].traverse(List(1, 2, 3))(i => some(i))
  }

  @Test
  def testCats(): Unit ={
    import cats.Show
    import cats.instances.int.catsStdShowForInt
    val showInt=Show[Int]
    val r:String=showInt.show(1212312)
    println(r)
    import cats.instances.list.catsStdShowForList
    print(Show[List[Int]].show(List(1,2,3,4,5)))


    import cats.syntax.show._
    import cats.instances.int.catsStdShowForInt
    println(List(1,2,3,4).show())


  }



}

trait Tellable[T] {
  def tell(t: T): String
}

case class Color(descript: String)

case class Person(name: String)


object colorTeller extends Tellable[Color] {
  override def tell(t: Color): String = s" i am color :${t.descript}"
}

