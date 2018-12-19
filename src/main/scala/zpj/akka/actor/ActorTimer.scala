package zpj.akka.actor

/**
  * Created by PerkinsZhu on 2017/12/6 17:03. 
  */

import akka.Done
import akka.actor.{Actor, ActorSystem, CoordinatedShutdown, Kill, PoisonPill, Props, Timers}
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object MyTimerActor {

  private case object TickKey

  private case object FirstTick

  private case object Tick

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("testSystem")
    val actor = system.actorOf(Props[MyTimerActor])
    actor ! MyTimerActor.FirstTick
    implicit val timeout = Timeout(5 seconds)
    import akka.pattern.{ask, pipe}
    val future = actor ? MyTimerActor.FirstTick
    future.map(println(_))

    import akka.pattern.gracefulStop

    val stopped01: Future[Boolean] = gracefulStop(actor, 5 seconds, "stop message or PoisonPill")

    val stopped: Future[Boolean] = gracefulStop(actor, 2 seconds)
    stopped.foreach(status => println("actor停止状态：" + status))

    actor ! PoisonPill.getInstance
    actor ! PoisonPill

    actor ! Kill

    //添加阶段任务,在执行CoordinatedShutdown.******事件执行前指向该任务
    CoordinatedShutdown(system).addTask(CoordinatedShutdown.PhaseActorSystemTerminate, "system shutDown ") { () ⇒ {println("--- i am  shut down "); Future.successful(Done)} }


    system.terminate()
    //    Thread.sleep(Int.MaxValue)


  }
}

class MyTimerActor extends Actor with Timers {

  import MyTimerActor._

  timers.startSingleTimer(TickKey, FirstTick, 500.millis)

  override def postStop {
    println("Number5::postStop called")
  }

  def receive = {
    case FirstTick ⇒
      println("receive first tick")
      timers.startPeriodicTimer(TickKey, Tick, 1.second)
      sender() ! "12313"
    case Tick ⇒
      println("receive tick")
    case "stop" => context stop self
  }

}
