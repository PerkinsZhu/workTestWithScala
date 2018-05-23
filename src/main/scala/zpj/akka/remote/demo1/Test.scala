package zpj.akka.remote.demo1

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import zpj.akka.actor.distributed.test2.SampleActor

/**
  * Created by PerkinsZhu on 2018/5/23 10:46
  **/


class Server extends Actor {
  override def receive: Receive = {
    case str: String => println(str)
  }
}

object Server {
  def main(args: Array[String]): Unit = {
    val serverSystem = ActorSystem("demo1", ConfigFactory.parseString(
      """akka {
          actor {
            provider = remote
          }
          remote {
            enabled-transports = ["akka.remote.netty.tcp"]
            netty.tcp {
              hostname = "127.0.0.1"
              port = 2552
            }
         }
        }"""))
    serverSystem.actorOf(Props[Server], "server")

  }
}

class Client extends Actor {
  override def receive: Receive = {
    case str: String => println(str)
  }
}

object Clietn {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("demo1", ConfigFactory.parseString(
      """akka {
          actor {
            deployment {
              /sampleActor {
                remote = "akka.tcp://sampleActorSystem@127.0.0.1:2553"
              }
            }
          }
        }"""))
    val actor = system.actorOf(Props[SampleActor], "sampleActor")
    actor ! "Pretty slick"
  }
}


