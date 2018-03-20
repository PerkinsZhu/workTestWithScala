package zpj.tools

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest.{FlatSpec, FunSuite}
import play.api.libs.ws.ahc.StandaloneAhcWSClient


/**
  * Created by PerkinsZhu on 2018/3/19 12:44
  **/


class WsClientTool {
  private implicit val system = ActorSystem()
  private implicit val materializer = ActorMaterializer()
  val wsClient = StandaloneAhcWSClient()

  def main(args: Array[String]): Unit = {
    /*    wsClient.url("http://www.baidu.com").get().map(respond => {
          println(respond.body)
        }).onComplete {
          case _ => close()
        }*/
  }

  def close(): Unit = {
    wsClient.close()
    system.terminate()
  }
}

class WsClientToolTest extends FunSuite {
  test("WSClient demo") {
    assert("Hello" equals ("Hello"))
  }
}