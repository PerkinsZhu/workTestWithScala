package zpj.sprayjson

import spray.json.DefaultJsonProtocol
import spray.json._
import DefaultJsonProtocol._
import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/9/30 13:27
  **/
class TestCase {

  import spray.json._

  //  val json = Color("CadetBlue", 95, 158, 160).toJson
  //  val color = json.convertTo[Color]


  @Test
  def test01(): Unit = {
    println(23.toJson)
    println(JsNumber(42))
//    JsonParser(""" { "key" :42, "key2": "value" }""") === JsObject("key" -> JsNumber(42), "key2" -> JsString("value"))


  }

}

case class Color(name: String, red: Int, green: Int, blue: Int)

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val colorFormat = jsonFormat4(Color)
}