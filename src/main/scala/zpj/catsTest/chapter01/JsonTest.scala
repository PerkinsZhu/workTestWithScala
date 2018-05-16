package zpj.catsTest.chapter01

import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/5/16 10:25
  **/
class JsonTest {

  @Test
  def testJson(): Unit = {
    val person = Person("jack", "werjlkj@gamial.com")
    import JsonWriterInstances.personWriter
    import JsonWriterInstances.stringWriter
    println(Json.toJson(person))
    import JsonSyntax.JsonWriterOps
    println(person.toJson)
    implicitly[JsonWriter[Person]]
    import JsonSyntax.optionWriter
    println(Option("qq").toJson)
    println(Json.toJson(Option("hello")))
  }

}


sealed trait Json

final case class JsObject(get: Map[String, Json]) extends Json

final case class JsString(get: String) extends Json

final case class JsNumber(get: Double) extends Json

case object JsNull extends Json


trait JsonWriter[A] {
  def write(value: A): Json
}

final case class Person(name: String, email: String)

object JsonWriterInstances {
  implicit val stringWriter: JsonWriter[String] = (value: String) => JsString(value)
  implicit val personWriter: JsonWriter[Person] = {
    person => JsObject(Map("name" -> JsString(person.name), "email" -> JsString(person.email)))
  }
}

//接口，公开，供使用者调用该方法进行json转换
object Json {
  def toJson[A](value: A)(implicit writer: JsonWriter[A]): Json = writer.write(value)
}

object JsonSyntax {

  //把A 转化为JsonWriter[A] 这样就可以调用toJson方法
  //  该隐式转换存在的目的是为了实现Person.toJson()形式的调用
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit writer: JsonWriter[A]) = writer.write(value)
  }

  //  使用隐式方法来动态构造各种类型的JsonWriter
  implicit def optionWriter[A](implicit w: JsonWriter[A]): JsonWriter[Option[A]] =
  //  注意下面的match实际上就是简写的zpj.catsTest.chapter01.JsonWriter.write的实现体。这里返回的是一个JsonWriter[Option[A]]对象
    option ⇒ option match {
      case Some(a) ⇒ w.write(a) // 代理
      case None ⇒ JsNull
    }
  implicit def toJson[A](f: A => Json)(implicit w: JsonWriter[A]): JsonWriter[A] = (value: A) => f(value)
}