package zpj.ws.demo

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json.Json
import play.api.libs.ws.ahc.StandaloneAhcWSClient

object WSDemo extends App {


  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = materializer.executionContext
  val wsClient = StandaloneAhcWSClient()
  val postBody = Json.obj("templateMessage"->"{\"touser\":\"olJQ3t_jAQzZYayoVmwlGbBUWLq4\",\"template_id\":\"0CRBLIbvS74VGbgsvzqz1k5Bc-qGKMuGKwexCx7EvKU\",\"url\":\"www.baidu.com\",\"data\":{\"first\":{\"value\":\"机器故障！\",\"color\":\"#173177\"},\"performance\":{\"value\":\"机器故障！\",\"color\":\"#173177\"},\"time\":{\"value\":\"其它消息！\",\"color\":\"#173177\"}}}","appId"->"wxf8ef8d6283d60f53");
  wsClient.url("http://weixin-test.chatbot.cn/cloud/sendTemplateMessage/").withHttpHeaders("Content-Type" -> "application/json; charset=utf-8").post(postBody.toString()).map{response=>
    println("sendTemplateMessage result:"+response.body)
  }

  Thread.sleep(1000000)




}
