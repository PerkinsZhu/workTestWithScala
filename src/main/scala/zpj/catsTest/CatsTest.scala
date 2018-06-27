package zpj.catsTest

import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/5/16 8:58
  **/
class CatsTest {

  @Test
  def testMonoid(): Unit = {
    import cats.syntax.semigroup._
    import cats.Monoid
    import cats.instances.string._
    println(Monoid.apply[String].combine("hello", "world"))
    println("aaaa" combine "world")
    println(Monoid.apply[String].empty)
  }


}
