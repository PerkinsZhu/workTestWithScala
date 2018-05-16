package zpj.catsTest.chapter01

import cats.Monoid
import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/5/16 14:38
  **/
class EQTestCase {
  @Test
  def testEQ() {
    import cats.Eq
    import cats.instances.int._
    val eqInt = Eq[Int]
    println(eqInt.eqv(12, 12))
    import cats.syntax.eq._
    println(1 === 2)
    println(12 =!= 23)

    import cats.instances.option._
    println(Option(1) === None)
    println((Some(2): Option[Int]) === None)
  }

  @Test
  def myEq(): Unit = {
    import java.util.Date
    import cats.Eq
    import cats.instances.long._
    import cats.instances.string._
    import cats.instances.int._
    import cats.syntax.eq._
    implicit val dateEq: Eq[Date] = Eq.instance(_.getTime === _.getTime)
    val d1 = new Date()
    import java.util.concurrent.TimeUnit
    TimeUnit.SECONDS.sleep(1)
    val d2 = new Date()
    println(d1 === d2)

    implicit val eqCat: Eq[Cat] = Eq.instance((cat1, cat2) ⇒ {
      cat1.name === cat2.name && cat1.age === cat2.age && cat1.color === cat2.color
    })
    println(Cat("jack", 12, "red") === Cat("tome", 23, "blue"))
    import cats.instances.option._
    println(Option(Cat("jack", 12, "red")) === Option(Cat("tome", 23, "blue")))

  }

  @Test
  def testOther1(): Unit = {
    implicit def unionMonoid[A]: Monoid[Set[A]] =
      new Monoid[Set[A]] {
        override def empty: Set[A] = Set.empty[A]

        override def combine(x: Set[A], y: Set[A]): Set[A] = x union y
      }

    println(associativeLaw(Set(1, 2, 3), Set(4, 5), Set(6))(unionMonoid))
    println(identityLaw(Set(1, 2, 3))(unionMonoid))
  }

  //  结合律
  def associativeLaw[A](x: A, y: A, z: A)(implicit m: Monoid[A]): Boolean =
    m.combine(x, m.combine(y, z)) == m.combine(m.combine(x, y), z)

  //同一律
  def identityLaw[A](x: A)(implicit m: Monoid[A]): Boolean =
    (m.combine(x, m.empty) == x) && (m.combine(m.empty, x) == x)


}
