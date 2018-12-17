package zpj.akka.actor

/**
  * Created by PerkinsZhu on 2017/12/6 17:03. 
  */

import akka.actor.{Actor, ActorSystem, Props, Timers}
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
    import akka.pattern.{ ask, pipe }
    val future = actor ? MyTimerActor.FirstTick
    future.map(println(_))

    Thread.sleep(Int.MaxValue)

  }
}

class MyTimerActor extends Actor with Timers {

  import MyTimerActor._

  timers.startSingleTimer(TickKey, FirstTick, 500.millis)

  def receive = {
    case FirstTick ⇒
      println("receive first tick")
      timers.startPeriodicTimer(TickKey, Tick, 1.second)
      sender() !"12313"
    case Tick ⇒
      println("receive tick")
  }

}
