package zpj.catsTest.chapter04

import org.junit.Test

import scala.util.Try

/**
  * Created by PerkinsZhu on 2018/5/18 9:03
  **/
class MonadTest {

  @Test
  def testMap(): Unit = {
    Some(2).map(_ * 10).foreach(println _)
    Some(2).flatMap(i => Some(i * 10)).foreach(println _)
    Some(2).flatMap(i => None).foreach(re => println(s"---$re"))
    Try[Int](23 / 0).map(_ + 10).foreach(println _)
  }

  @Test
  def testMonad(): Unit = {
    import cats.Monad
    import cats.instances.list._
    val list = List(1, 2, 3)
    val listMonad = Monad[List]

    val t1 = listMonad.flatMap(list)(x => {
      val t2 = listMonad.pure(x * 10)
      println(t2)
      t2
    })
    println(t1)

    import cats.instances.option._
    val o = Option(1)
    val optionMonad = Monad[Option]
    val t2 = optionMonad.flatMap(o)(x => {
      val t3 = optionMonad.pure(x * 10)
      println(t3)
      t3
    })
    println(t2)


    /*implicit val listOption = new Monad[List[Option[Int]]] {
      override def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = {
        fa.flatMap(f(_))
      }

      override def tailRecM[A, B](a: A)(f: A => List[Either[A, B]]): List[B] = {
        f(a).map({
          case Right(res) => res
        })
      }

      override def pure[A](x: A): List[A] = List(x)
    }
    val listOptionMonad = Monad[List[Option[Int]]]
    val t3 = listOptionMonad.flatMap(List(Option(), Option(2)))(x => {
      println(x)
      val t1 = listOptionMonad.pure(x)
      println(t1)
      t1
    })
    println(t3)*/

    println(List(1, 2, "aa").map({
      //case 中如果没有找到匹配项则会报错
      case a: Int => a
      case _ => 0
    }))

  }

}


