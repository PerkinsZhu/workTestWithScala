package zpj.catsTest.chapter02

import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/5/16 19:43
  **/
class MonoidTest {

  import cats.Monoid
  import cats.instances.string._

  @Test
  def testMonoidTest(): Unit = {
    println(Monoid[String].combine("hello", "world"))
    println(Monoid[String].empty)
    println(Monoid.apply[String].combine("hello", "world"))
    println(Monoid.apply[String].empty)

    import cats.Semigroup
    println(Semigroup.apply[String].combine("aaaa", "bbbbb"))

    import cats.instances.int._
    import cats.instances.option._
    println(Monoid[Option[Int]].combineAll(Seq(Option(1), Option(2), Option(3), None)))

    import cats.syntax.semigroup._
    println(Option(1) |+| Option(2) |+| Option(3))

    println(add(Seq(Option(1), Option(2), Option(3), None)))
    println(add2(Seq(Option(1), Option(2), Option(3), None)))
  }

  def add[A](items: Seq[A])(implicit mono: Monoid[A]): A = {
    import cats.syntax.semigroup._
    items.foldLeft(mono.empty)(_ |+| _)
  }

  //  使用上下文绑定实现 什么是上下文邦定？
  def add2[A: Monoid](items: Seq[A]): A = {
    import cats.syntax.semigroup._
    items.foldLeft(Monoid[A].empty)(_ |+| _)
  }




}


