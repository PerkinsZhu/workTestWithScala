package zpj.akka.remote.demo2

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by PerkinsZhu on 2018/5/23 11:11
  **/
class TestCase {

}

object Server {
  def main(args: Array[String]): Unit = {

  }
}

case object Start

case class Message(msg: String)

object HelloRemote extends App {
  val system = ActorSystem("HelloRemoteSystem")
  val remoteActor = system.actorOf(Props[RemoteActor], "yourconf")
  remoteActor ! Message("The RemoteActor is alive")
}

class RemoteActor extends Actor {
  def receive = {
    case Message(msg) =>
      println(s"RemoteActor received message '$msg'")
      sender ! Message("Hello from the RemoteActor") // 回复消息
    case _ =>
      println("RemoteActor got something unexpected.")
  }
}

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props

object HelloLocal extends App {
  System.setProperty("akka.remote.netty.port", "5152")
  implicit val system = ActorSystem("LocalSystem")
  val localActor = system.actorOf(Props[LocalActor], name = "LocalActor") // the local actor
  localActor ! Start
}

class LocalActor extends Actor {

  // create the remote actor
  val remote = context.actorSelection("akka.tcp://HelloRemoteSystem@127.0.0.1:5150/user/RemoteActor")
  var counter = 0

  def receive = {
    case Start =>
      remote ! Message("Hello from the LocalActor")
    case Message(msg) =>
      println(s"LocalActor received message: '$msg'")
      if (counter < 5) {
        sender ! Message("Hello back to you")
        counter += 1
      }
    case _ =>
      println("LocalActor got something unexpected.")
  }

}
