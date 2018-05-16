package zpj.catsTest.chapter01

import java.util.Date

import cats.Show
import org.joda.time.DateTime
import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/5/16 12:03
  **/

trait Printable[A] {
  def format(value: A): String
}

object PrintableInstances {
  implicit val stringPrintable: Printable[String] = (value: String) => value
  implicit val intPrintable: Printable[Int] = (value: Int) => s"value is $value"
}

object Printable {
  def format[A](a: A)(implicit p: Printable[A]): String = p.format(a)

  def print[A](a: A)()(implicit p: Printable[A]): Unit = println(format(a))
}

final case class Cat(name: String, age: Int, color: String)

object Cat {

  import PrintableInstances._

  implicit val catPrintable: Printable[Cat] = (cat: Cat) => {
    val name = Printable.format(cat.name)
    val age = Printable.format(cat.age)
    val color = Printable.format(cat.color)
    s"name is $name age is $age color is $color"
  }
}

object PrintableSyntax {

  implicit class PrintableOps[A](value: A) {
    def format(implicit p: Printable[A]): String = p.format(value)

    def print(implicit p: Printable[A]): Unit = println(p.format(value))
  }

}

class TestCase {
  @Test
  def testCat(): Unit = {
    val cat = Cat("jack", 23, "red")
    Printable.print(cat)
    println(Printable.format(cat))
    import PrintableSyntax.PrintableOps
    cat.print
    println(cat.format)
  }

  object CatsInstances {
    implicit val dataInstance: Show[Date] = date => s"now time is ${date}"
    implicit val dateTimeShow1: Show[DateTime] = Show.show(_.toString)
    implicit val dateTimeShow2: Show[DateTime] = Show.fromToString
  }


  @Test
  def testCatWithCats(): Unit = {
    import cats.Show
    import cats.instances.int.catsStdShowForInt
    import cats.instances.string.catsStdShowForString

    import cats.syntax.show._
    println(Show[Int].show(233))
    println(Show[String].show("hello"))
    println(123.show)
    println("jack".show)

    import CatsInstances.dataInstance
    println(new Date().show)
    import CatsInstances.dateTimeShow1
    println(DateTime.now().show)

  }

}