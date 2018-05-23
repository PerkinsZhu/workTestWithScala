package zpj.akka.remote.demo4

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by PerkinsZhu on 2018/5/23 12:03
  **/
class Worker(serverHost: String, serverPort: String) extends Actor {
  /**
    * 每一个actor既可以当做客户端，也可以当做服务端
    *
    * @return
    */

  override def receive: Receive = {
    case str: String => println(s"客户端接收到消息->$str")
  }

  override def preStart(): Unit = {
    super.preStart()
    /**
      * 路径注意点：
      *   akka.<protocol>://<actor system name>@<hostname>:<port>/<actor path>
      * actor system name：目标actorSystem（server）的名字
      * <actor path> = /user/actorName
      *
      */
    val server = context.actorSelection(s"akka.tcp://Server@$serverHost:$serverPort/user/serverActor")
    server ! "我是客户端"
  }

}

object Worker {
  val host = "127.0.0.1"
  val port = 5050
  val configStr =
    s"""
       |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname = "$host"
       |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
  val config = ConfigFactory.parseString(configStr)
  val system = ActorSystem("Worker", config)

  def test01(): Unit = {
    val serverHost = "127.0.0.1"
    val serverPort = "5051"
    val workerActor = system.actorOf(Props(new Worker(serverHost, serverPort)), "workerActor")
    workerActor ! "自己给自己发送的消息"
    system.whenTerminated.onComplete(res => system.terminate())
  }

  def test02(): Unit = {
    //直接从system中链接远程的actor
    val serverActor = system.actorSelection("akka.tcp://Server@127.0.0.1:5051/user/serverActor")
    serverActor ! "我是第二个客户端"
  }

  def main(args: Array[String]): Unit = {
    //    test01()
    test02()
  }


}

class Server extends Actor {
  override def receive: Receive = {
    case str: String => println(s"服务器接收到消息->$str")
  }
}

object Server {

  def main(args: Array[String]): Unit = {
    val host = "127.0.0.1"
    val port = 5051
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    val system = ActorSystem("Server", config)
    val serverActor = system.actorOf(Props(new Server), "serverActor")
    serverActor ! "自己给自己发送的消息"
    system.whenTerminated.onComplete(_ => system.terminate())
  }


}

