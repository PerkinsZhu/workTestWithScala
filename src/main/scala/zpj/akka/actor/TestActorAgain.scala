package zpj.akka.actor

import akka.actor.{Actor, ActorSystem, Inbox, Props}
import akka.event.Logging

/**
  * Created by PerkinsZhu on 2017/12/7 16:35. 
  */
object TestActorAgain {
  val system = ActorSystem("testAgain")
  //  val props = Props[MyActor]
  //  val props = Props(new MyActor("hello"))
  val props = Props(classOf[MyActor], "hello", 20)
  //可以添加多个参数
  val myActor = system.actorOf(props, "myactor")

  def test1(): Unit = {
    myActor ! "hello"
  }

  def main(args: Array[String]): Unit = {
    test1()
  }

}

class MyActor(name: String) extends Actor{

/*  val inbox = Inbox.create(context)
  inbox watch(sender())*/

  val log = Logging(context.system, this)
  var age = 0;
  def this(name: String, age: Int) {
    this(name)
    this.age = age
  }

  override def receive: Receive = {
    case _ => log.info(name + "------" + age)
  }
}