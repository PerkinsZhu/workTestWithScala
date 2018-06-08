package zpj.catsTest.chapter03

import org.apache.commons.lang3.StringUtils
import org.junit.Test
import zpj.catsTest.chapter01.Printable

/**
  * Created by PerkinsZhu on 2018/5/17 14:52
  **/
class TestOther {


  @Test
  def testSplit(): Unit ={
    val temp = "nihao;sdfwe；sdfsdf;dfsf；sdferee;;;;;；；;".split("(；|;)").filter(!StringUtils.isBlank(_))
      println(temp.size)
      temp.foreach(println)
  }

  @Test
  def testString(): Unit = {
    import NewPrintableInstances.stringPrintable
    println(stringPrintable.format("hello"))
  }

  @Test
  def test2(): Unit = {
    implicit val stringPrintable: Printable[String] = (value: String) => "\"" + value + "\""
    implicit val booleanPrintable: Printable[Boolean] = (value: Boolean) => if (value) "yes" else "no"

  }

  @Test
  def test3(): Unit = {
  println("0--")
  }
}

object NewPrintableSyntax {

  implicit class PrintableOps[A](value: A) {
    def format(implicit p: NewPrintable[A]): String = p.format(value)

    def print(implicit p: NewPrintable[A]): Unit = println(p.format(value))
  }

}

object NewPrintableInstances {
  implicit val stringPrintable: NewPrintable[String] = (value: String) => value
  implicit val intPrintable: NewPrintable[Int] = (value: Int) => s"value is $value"
}

trait NewPrintable[A] extends Printable[A] {
  def contramap[B](func: B => A): NewPrintable[B] = (value: B) => ???
}

final case class NewBox[A](value: A)