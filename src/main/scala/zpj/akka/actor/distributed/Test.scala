package zpj.akka.distributed

/**
  * Created by Administrator on 2017/6/21.
  */
import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * 基于akka的rpc
  * Created by tianjun on 2017/1/11 0011.
  */
class Master extends Actor{

  println("constructor invoked!")

  override def preStart(): Unit = {
    println("preStart invoked!")
  }

  override def receive: Receive = {
    case "connect" => {
      println("a r connected")
      sender ! "reply"
    }
    case "hello" => {
      println("hello")
    }
  }
}

object Master{

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
    //ActorSystem老大，辅助创建和监控actor，它是单例的
    val actorSystem = ActorSystem("MasterSystem",config)

    val master = actorSystem.actorOf(Props(new Master),"MasterUser")

    //master自己给自己发信息
    master ! "hello"

//    actorSystem.awaitTermination()

  }
}