package zpj.akka.remote

import akka.actor.{Actor, ActorSystem, Props}
import org.junit.Test
import zpj.akka.actor.distributed.test2.SampleActor

/**
  * Created by PerkinsZhu on 2018/5/23 9:46
  **/
class TestCase {
  implicit val system = ActorSystem("QuickStart")

  //  val selection = context.actorSelection("akka.tcp://actorSystemName@10.0.0.1:2552/user/actorName")
  @Test
  def test01(): Unit = {
    val actor = system.actorOf(Props[SampleActor], "sampleActor")
    actor ! "Pretty slick"
  }

  @Test
  def test02(): Unit = {
  }

}

object TestActor extends Actor {
  val selection = context.actorSelection("akka.tcp://actorSystemName@127.0.0.1:2552/user/actorName")

  def main(args: Array[String]): Unit = {
    selection ! "Pretty awesome feature"
  }

  override def receive: Receive = {
    case str: String => println(str)
  }
}