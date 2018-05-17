package zpj.catsTest.chapter03

import java.util.Date

import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/5/17 15:13
  **/
class CodecTest {

  implicit val box = Box("jjj",23)
  @Test
  def test01(): Unit = {
    //    println(encode(12))
    println(new Date().getTime)
//    val ibox = implicitly[Box]()

  }

  def encode[A](value: A)(implicit c: Codec[A]): String = c.encode(value)

  def decode[A](value: String)(implicit c: Codec[A]): A = c.decode(value)

  implicit val stringCodec: Codec[String] = new Codec[String] {
    override def encode(value: String): String = value

    override def decode(value: String): String = value
  }
//  implicit val intCode: Codec[Int] = stringCodec.imap(_.toInt, _.toString)
//  implicit val booleanCodec: Codec[Boolean] = stringCodec.imap(_.toBoolean, _.toString)
//  implicit val doubleCodec: Codec[Double] = stringCodec.imap(_.toDouble, _.toString)
}

case class Box[A](value: A)

trait Codec[A] {
  def encode(value: A): String

  def decode(value: String): A

//  def imap[B](dec: A => B, enc: B => A): Codec[B] =
}


