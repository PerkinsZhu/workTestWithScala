package zpj.akka.distributed.computing

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

case class Add(a: Int, b: Int)

case class Result(result: Int)

class Node extends Actor {
  override def receive: Receive = {
    case Add(x, y) => sender() ! Result(x + y)
    case "connect" => println("node 创建连接！");sender() ! "success"
    case _ => println("无效参数！")
  }
}
object Node {
  def main(args: Array[String]) {
    val host = "127.0.0.1"
    val port = 8881
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    val actorSystem = ActorSystem("NodeSystem", config)
    actorSystem.actorOf(Props(new Node), "Node01")
//    actorSystem.awaitTermination()
  }
}
