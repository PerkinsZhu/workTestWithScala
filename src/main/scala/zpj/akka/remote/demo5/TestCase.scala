package zpj.akka.remote.demo5

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by PerkinsZhu on 2018/5/23 14:03
  **/
object TestCase {
  /**
    * 案例：
    *   1个actor同时向100个actor轮训发送消息
    * 注意：
    *   要抛弃java中socket中客户端和服务端的概念。
    *   对于每一个actor， 既可以接收消息，也可以发送消息。也即是既可以当做server，也可以当做client。
    *   当做client的时候使用 ! 发送消息，当做 server的时候，使用reserve方法接收消息
    *   这里不再有socket中的accept和connect方法。
    *   要想使用actor，只需获取actorRef即可。无论是通过system.actorOf还是通过system.actorSelection，只需要获取actorRef即可
    */
  def main(args: Array[String]): Unit = {
    ActorManager.startActor()
    ActorManager.testActor()
  }
}

case class Message(msg: String)

object ActorManager {
  val host = "127.0.0.1"
  val port = 5050
  val configStr =
    s"""
       |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname = "$host"
       |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
  val config = ConfigFactory.parseString(configStr)
  val system = ActorSystem("Server", config)
  system.whenTerminated.onComplete(_ => system.terminate())

  def startActor(): Unit = {
    1 to 100 foreach (i => {
      val actor = system.actorOf(Props(new MyActor(i.toString)), s"actor-$i")
      //通过创建的时候持有actorRef的方式发送消息
      actor ! Message(s"服务器($i)出生！")
    })
  }

  def testActor(): Unit = {
    1 to 100 foreach (i => {
      //通过远程网络的方式获取actorRef进行访问
      val actor = system.actorSelection(s"akka.tcp://Server@$host:$port/user/actor-$i")
      actor ! Message(s"我拿到了服务器($i)！")
    })
  }

}

class MyActor(name: String) extends Actor {
  override def receive: Receive = {
    case msg: Message => println(s"Server($name)--->$msg")
  }
}