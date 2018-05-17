package zpj.catsTest.chapter03

import cats.Functor
import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/5/17 14:00
  **/
class TreeTest {

  @Test
  def testTree(): Unit = {
    import cats.Functor
    val t: Tree[Int] = Branch(Branch(Leaf(1), Leaf(2)), Leaf(3))
    //在调用Functor[Tree]的时候实际上是走的apply方法，apply需要一个隐式参数Functor[Tree],该隐式参数是有Object Tree中自动调供的
    println(Functor[Tree].map(t)(_ * 2))
    //使用Ops来操作
    import cats.syntax.functor._
    println(t.map(_ * 10))

  }

}

sealed trait Tree[+A]

final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

final case class Leaf[A](valueo: A) extends Tree[A]

object Tree {
  implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
      case Branch(l, r) => Branch(map(l)(f), map(r)(f))
      case Leaf(v) => Leaf(f(v))
    }
  }


}
