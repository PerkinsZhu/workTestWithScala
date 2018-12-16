package zpj.akka.actor

import java.util.concurrent.TimeUnit

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, ActorRef, ActorSystem, CoordinatedShutdown, InvalidMessageException, OneForOneStrategy, Props}
import akka.event.Logging
import akka.util.Timeout

import scala.util.{Failure, Success}

/**
  * Created by Administrator on 2017/6/3.
  */
object TestActor {
  def main(args: Array[String]): Unit = {
    testActor()

  }

  def testActor(): Unit = {
    val system = ActorSystem("HelloSystem")
    //    val system = ActorSystem.create("HelloSystem")
    val helloActor = system.actorOf(Props[HelloActor], name = "HelloActor")
    //    val byrByeActor = system.actorOf(Props(new ByeByeActor(helloActor)), name = "ByeByeActor")
    //注意：上下这两种传参方法都可以执行
    val byrByeActor = system.actorOf(Props(classOf[ByeByeActor], helloActor), "ByeByeActor")
    //    byrByeActor ! "start"
  }
}

case object Stu;

class HelloActor() extends Actor {

  import context._

  val log = Logging(system, this)
  override val supervisorStrategy =
    OneForOneStrategy() {
      case _: ArithmeticException => Resume
      case _: NullPointerException => Restart
      case _: IllegalArgumentException => Stop
      case _: InvalidMessageException => Stop
      case _: Exception => Escalate
    }

  override def preStart() {
    println("-----------")
    log.info("----------11111-")
  }

  def receive = {
    case "hello" => println("send"); sender() ! "start"
    case _ => println("您是?")
  }
}

class ByeByeActor(helloActor: ActorRef) extends Actor {
  override def receive: Receive = {
    case "start" => println("start"); helloActor ! "hello"
    case "exit" => println("byebye!")
  }
}

case object PingMessage

case object PongMessage

case object StartMessage

case object StopMessage

class Ping(pong: ActorRef) extends Actor {
  var count = 0

  def incrementAndPrint {
    count += 1; println("ping")
  }

  def receive = {
    case StartMessage =>
      incrementAndPrint
      pong ! PingMessage
    case PongMessage =>
      if (count > 9) {
        sender ! StopMessage
        println("ping stopped")
        //        context.stop(self) 关闭
        //        546
      } else {
        incrementAndPrint
        sender ! PingMessage
      }
  }
}

class Pong extends Actor {
  def receive = {
    case PingMessage =>
      println("  pong" + sender.getClass)
      sender ! PongMessage
    case StopMessage =>
      println("pong stopped")
      context.stop(self)
  }
}

object PingPongTest extends App {

  import akka.util.Timeout
  import scala.concurrent.duration._
  import akka.pattern.ask
  import akka.dispatch.ExecutionContexts._

  implicit val ec = global
  val system = ActorSystem("PingPongSystem")
  val pong = system.actorOf(Props[Pong], name = "pong")
  val ping = system.actorOf(Props(new Ping(pong)), name = "ping")
  implicit val timeout = Timeout(25 seconds)
  val future = ping ? StartMessage
  future.map { result => println("Total number of words " + result)
  }
  /*  while(!future.isCompleted){
     println("running……")
   }*/
}


//************************两个线程统计文件中总单词数量*******************************************

case class ProcessStringMsg(string: String)

case class StringProcessedMsg(words: Integer)

class StringCounterActor extends Actor {
  def receive = {
    case ProcessStringMsg(string) => {
      sender ! StringProcessedMsg(string.length)
    }
    case _ => println("Error: message not recognized")
  }
}

case class StartProcessFileMsg()

class WordCounterActor(filename: String) extends Actor {

  private var running = false
  private var totalLines = 0
  private var linesProcessed = 0
  private var result = 0
  private var fileSender: Option[ActorRef] = None

  def receive = {
    case StartProcessFileMsg() => {
      if (running) {
        println("Warning: duplicate start message received")
      } else {
        running = true
        fileSender = Some(sender)
        import scala.io.Source._
        fromFile(filename).getLines.foreach { line =>
          context.actorOf(Props[StringCounterActor]) ! ProcessStringMsg(line)
          totalLines += 1
        }
      }
    }
    case StringProcessedMsg(words) => {
      result += words
      linesProcessed += 1
      if (linesProcessed == totalLines) {
        println("=====" + result)
        fileSender.map(_ ! result) // provide result to process invoker
      }
    }
    case _ => println("message not recognized!")
  }
}


object Sample extends App {

  import akka.util.Timeout
  import scala.concurrent.duration._
  import akka.pattern.ask
  import akka.dispatch.ExecutionContexts._
  import akka.actor.ActorSystem
  import akka.actor.Props

  implicit val ec = global

  override def main(args: Array[String]) {
    val system = ActorSystem("System")
    val actor = system.actorOf(Props(new WordCounterActor("E:\\zhupingjing\\test\\123.txt")))
    implicit val timeout = Timeout(25 seconds)
    val future = actor ? StartProcessFileMsg()
    while (!future.isCompleted) {}
    println(future.value.get)
    system.stop(actor)
    /*    future.onComplete{
          case Success(res) => println("-----");println(res)
          case Failure(ex) => println(ex)
        }*/
  }
}

object ActorTest {
  def main(args: Array[String]): Unit = {
    runActor()
  }

  def runActor() = {
    val actorSystem = ActorSystem("MyActor")
    val actor = actorSystem.actorOf(Props[MyActor], "hello")
    val actor02 = actorSystem.actorOf(Props[MyActor], "NIHAO")
    actor ! "HI"
    Thread.sleep(500)
    actor02 ! "HELLO"
  }

  var count = 0;

  class MyActor extends Actor {
    override def receive: Receive = {
      case x: String => showInfo(x)
    }

    def showInfo(name: String): Unit = {
      while (true) {
        count += 1
        Thread.sleep(1000)
        println(count + "i am " + name)
      }
    }
  }

}

object RunableTest {
  def main(args: Array[String]): Unit = {
    myRunner()
  }

  def myRunner() = {
    val thread = new Thread(new Runnable {
      override def run(): Unit = {
        println("hel")
      }
    })
    thread.start()
  }
}

object TestAskActor extends App {
import scala.concurrent.ExecutionContext.Implicits.global
  import akka.pattern.ask

  class MyActor extends Actor {
    override def receive: Receive = {
      case "hello" => {
        Thread.sleep(1000); sender() ! "你好！"
      }
    }
  }
  class MyActor02 extends Actor {
    val log = Logging(context.system, this)

    def receive = {
      case "test" ⇒ log.info("received test")
      case _      ⇒ log.info("received unknown message")
    }
  }

  implicit val actorSystem = ActorSystem("ask")

  val myActor = actorSystem.actorOf(Props[MyActor], "myActor")
  implicit val timeout = Timeout(5,TimeUnit.SECONDS)
//  val future = myActor ? "hello"
  val future = myActor ? "hello"
  future.onComplete {
    case Success(res) => println(res)
    case Failure(ex) => ex.printStackTrace()
  }
  val myActor02 = actorSystem.actorOf(Props[MyActor02],"test")
  myActor02 ! "test"
  CoordinatedShutdown(actorSystem).addJvmShutdownHook {
    println("custom JVM shutdown hook...")
  }
/*  import akka.actor.ActorDSL._
  implicit val i = inbox()
  println(i.receive())

actorSystem.terminate()*/
Thread.sleep(3000)


}

