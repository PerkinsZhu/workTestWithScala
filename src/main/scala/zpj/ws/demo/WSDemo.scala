package zpj.ws.demo

import java.lang.Exception

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.util.ByteString
import org.junit.Test
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.{BodyWritable, InMemoryBody}
import play.api.libs.ws.ahc.StandaloneAhcWSClient
import zpj.tools.EncodeTest

import scala.concurrent.{Await, Future}
import concurrent.duration._
import scala.io.Source
import scala.util.control.NonFatal
import scala.util.{Failure, Success}

object WSDemo {


  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = materializer.executionContext
  val wsClient = StandaloneAhcWSClient()

  case class Body(title: String, content: String)

  implicit val format = Json.format[Body]

  def test03() = {
    /*    wsClient.url("http://127.0.0.1:9000/zdk/doAddSingleTagApi").post(Json.toJson(Body("title","content"))).onComplete{
          case Failure(ex)=>println("=======================")
          case Success(ex)=>{
            println(ex.statusText+"======================="+ex.body)
          }
        }*/
  }

  def main(args: Array[String]): Unit = {
    //    test03()
//    testForm
    testJson
    Thread.sleep(4000)
  }


  def test02(): Unit = {
    /* val future  = wsClient.url("https://www.baidu.com/").get().andThen{
       case result => result.get
       case e:Exception => println("----exception---"+e.getMessage)
     }*/
    /*val future  = wsClient.url("https://www.baid1u.com/").get().map(requese=>{
      println("=======================")
      requese.body
    })*/
    wsClient.url("https://www.baidu.com/").get().onComplete {
      case Failure(ex) => println("=======================")
      case Success(ex) => {

        ex.headers.foreach(println _)
        println(ex.statusText + "=======================" + ex.body)
      }
    }
    //    println(Await.result(future,100 seconds))

  }

  def test01(): Unit = {

    val postBody = Json.obj("templateMessage" -> "{\"touser\":\"olJQ3t_jAQzZYayoVmwlGbBUWLq4\",\"template_id\":\"0CRBLIbvS74VGbgsvzqz1k5Bc-qGKMuGKwexCx7EvKU\",\"url\":\"www.baidu.com\",\"data\":{\"first\":{\"value\":\"机器故障！\",\"color\":\"#173177\"},\"performance\":{\"value\":\"机器故障！\",\"color\":\"#173177\"},\"time\":{\"value\":\"其它消息！\",\"color\":\"#173177\"}}}", "appId" -> "wxf8ef8d6283d60f53");
    /*wsClient.url("http:// /").withHttpHeaders("Content-Type" -> "application/json; charset=utf-8").post(postBody.toString()).map{response=>
      println("sendTemplateMessage result:"+response.body)
    }*/

    System.setProperty("http.proxyHost", "113.118.97.119");
    System.setProperty("http.proxyPort", "9797");
    /* System.setProperty("https.proxyHost", config.getProxyHost());
     System.setProperty("https.proxyPort", config.getProxyPort());
      System.getProperties().remove("http.proxyHost");
           System.getProperties().remove("http.proxyPort");
           System.getProperties().remove("https.proxyHost");
           System.getProperties().remove("https.proxyPort");  */
    wsClient.url("https://www.baidu.com/").get().map(response => {
      println(response.body)
    })
  }

  @Test
  def testForm(): Unit = {
    import play.api.libs.ws.DefaultBodyWritables.writeableOf_urlEncodedForm
    val seqMap = Map[String, Seq[String]]("robotId" -> Seq("5b1a4d623e00004d034c947a"), "channel" -> Seq("web"), "userId" -> Seq("web-user"), "sessionId" -> Seq("1519889667217"), "question" -> Seq("hello"))
    wsClient.url("http://192.168.10.150:8081/cloud/robot/answer")
      .post(seqMap).onComplete({
      case Success(data) => println(data.body)
      case Failure(ex) => ex.printStackTrace()
    })
  }

  def testJson(): Unit ={


    implicit val jsonWritable = BodyWritable[JsValue]({ json =>
      val byteString = ByteString.fromString(json.toString())
      InMemoryBody(byteString)
    }, "application/json")

    val json = Json.obj("startTime" -> "20170901", "endTime" -> "20170930", "mercId" -> "你好你就是很慢民资")
    wsClient.url("http://127.0.0.1:9002/apiTest")
      .withHttpHeaders("Content-Type" ->"application/json;charset=utf-8")
      .post(json.toString()).onComplete({
      case Success(response) =>{
        response.headers.foreach(println)
        val body = response.body
        EncodeTest.findCode(body)
      }
      case Failure(ex) => ex.printStackTrace()
    })
  }

}