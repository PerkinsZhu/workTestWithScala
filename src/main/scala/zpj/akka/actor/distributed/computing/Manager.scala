package zpj.akka.distributed.computing

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class Manager(val masterHost:String,val masterPort:Int) extends Actor{

  var node:ActorSelection = _

  override def preStart(): Unit = {
    node = context.actorSelection(s"akka.tcp://NodeSystem@$masterHost:$masterPort/user/Node01")
    node ! "connect"
  }
  override def receive: Receive = {
    case result:Result => {
      println("--result--"+result.result)
    }
    case "success" => sender() ! Add(12,23)
  }
}

object Manager {
  def main(args: Array[String]) {
    val host = "127.0.0.1"
    val port = 8888
    val masterHost="127.0.0.1"
    val masterPort = 8881
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    val actorSystem = ActorSystem("ManagerSystem",config)
    val actor = actorSystem.actorOf(Props(new Manager(masterHost,masterPort)),"Manager")

//    actorSystem.awaitTermination()
  }
}
