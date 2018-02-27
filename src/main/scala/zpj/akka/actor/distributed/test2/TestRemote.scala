package zpj.akka.actor.distributed.test2

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._

/**
  * Created by PerkinsZhu on 2018/2/25 17:36
  **/
object TestRemote {
  val system = ActorSystem("testRemote")

  def testActor(): Unit = {
    val system = ActorSystem("Sys", ConfigFactory.load("actor/actor"))
    val actor = system.actorOf(Props[SampleActor], "sampleActor")
    actor ! "hello"
  }

  def testLocalActor(): Unit = {

    import akka.util.Timeout
    import akka.pattern.ask
    import scala.concurrent.ExecutionContext.Implicits.global
    implicit val timeout = Timeout(5 seconds)

    val actor = system.actorOf(Props[SampleActor])
    println(actor ! "hello")
    (actor ? "hello").onComplete(res => println(res))

  }

  def main(args: Array[String]): Unit = {
    testLocalActor()
//    Thread.sleep(6000)
    system.terminate()
  }

}

class SampleActor extends Actor {
  context.setReceiveTimeout(30 milliseconds)
  override def receive: Receive = {
    case "hello" => println("接收到数据"); sender() ! "baybay"
  }
}