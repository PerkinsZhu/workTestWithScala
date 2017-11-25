package zpj.akka.distributed

/**
  * Created by Administrator on 2017/6/21.
  */
import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * rpc入门
  * Created by tianjun on 2017/1/11 0011.
  */
class Worker(val masterHost:String,val masterPort:Int) extends Actor{

  var master:ActorSelection = _

  override def preStart(): Unit = {
//    注意这个路径的构建，“MasterSystem@”这个是在Master中创建actorSystem的时候定义的。“/user/MasterUser”这里的"/user"是固定的，MasterUser是在Master中创建actor的时候定义的
    master = context.actorSelection(s"akka.tcp://MasterSystem@$masterHost:$masterPort/user/MasterUser")
    master ! "connect"
  }

  override def receive: Receive = {
    case "reply" => {
      println("reply from master")
    }
  }
}

object Worker{
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
    //ActorSystem老大，辅助创建和监控actor，它是单例的
    val actorSystem = ActorSystem("WorkSystem",config)

    val actor = actorSystem.actorOf(Props(new Worker(masterHost,masterPort)),"Worker")
    actor ! "hello"
//    actorSystem.awaitTermination()
  }
}
