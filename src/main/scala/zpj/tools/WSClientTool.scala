package zpj.tools

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest.{FlatSpec, FunSuite}
import play.api.libs.ws.ahc.StandaloneAhcWSClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * Created by PerkinsZhu on 2018/3/19 12:44
  **/


object WSClientTool {
  private implicit val system = ActorSystem()
  private implicit val materializer = ActorMaterializer()
  val wsClient = StandaloneAhcWSClient()

  def testTimeOut(): Unit = {
    //play WSClient 默认超时时间: 120000 ms
//    java.util.concurrent.TimeoutException: Request timeout to /192.168.10.192:8094 after 120000 ms

    val start = System.currentTimeMillis()
    wsClient.url("http://192.168.10.192:8094/test/timeOut").get().map(respond => {
      println(respond.body)
    }).onComplete {
      case Success(value) => {
        println("success ->" + (System.currentTimeMillis() - start))
        println(value)
      }
      case Failure(exception) => {
        println("failure ->" + (System.currentTimeMillis() - start))
        exception.printStackTrace()
      }
    }
  }

  def main(args: Array[String]): Unit = {
    /*    wsClient.url("http://192.168.10.192:8094/test/timeOut").get().map(respond => {

          println(respond.body)
        }).onComplete {
          case _ => close()
        }*/
    testTimeOut()
  }

  def close(): Unit = {
    wsClient.close()
    system.terminate()
  }
}

class WSClientToolTest extends FunSuite {
  test("WSClient demo") {
    assert("Hello" equals ("Hello"))
  }
}