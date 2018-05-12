package zpj.ws.demo

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.ahc.StandaloneAhcWSClient

import scala.concurrent.Await
import concurrent.duration._
import scala.util.{Failure, Success}

object WSDemo {


  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = materializer.executionContext
  val wsClient = StandaloneAhcWSClient()

  case class Body(title:String,content:String)
  implicit  val format = Json.format[Body]
  def test03() = {
/*    wsClient.url("http://127.0.0.1:9000/zdk/doAddSingleTagApi").post(Json.toJson(Body("title","content"))).onComplete{
      case Failure(ex)=>println("=======================")
      case Success(ex)=>{
        println(ex.statusText+"======================="+ex.body)
      }
    }*/
  }

  def main(args: Array[String]): Unit = {
    test03()
    Thread.sleep(4000)
  }


  def test02(): Unit ={
   /* val future  = wsClient.url("https://www.baidu.com/").get().andThen{
      case result => result.get
      case e:Exception => println("----exception---"+e.getMessage)
    }*/
   /*val future  = wsClient.url("https://www.baid1u.com/").get().map(requese=>{
     println("=======================")
     requese.body
   })*/
   wsClient.url("https://www.baidu.com/").get().onComplete{
     case Failure(ex)=>println("=======================")
     case Success(ex)=>{

       ex.headers.foreach(println _)
       println(ex.statusText+"======================="+ex.body)
     }
   }
//    println(Await.result(future,100 seconds))

  }

def test01(): Unit ={

  val postBody = Json.obj("templateMessage"->"{\"touser\":\"olJQ3t_jAQzZYayoVmwlGbBUWLq4\",\"template_id\":\"0CRBLIbvS74VGbgsvzqz1k5Bc-qGKMuGKwexCx7EvKU\",\"url\":\"www.baidu.com\",\"data\":{\"first\":{\"value\":\"机器故障！\",\"color\":\"#173177\"},\"performance\":{\"value\":\"机器故障！\",\"color\":\"#173177\"},\"time\":{\"value\":\"其它消息！\",\"color\":\"#173177\"}}}","appId"->"wxf8ef8d6283d60f53");
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
  wsClient.url("https://www.baidu.com/").get().map(response=>{
    println(response.body)
  })
}


}
