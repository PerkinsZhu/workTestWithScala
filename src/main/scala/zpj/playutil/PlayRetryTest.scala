package zpj.playutil

import java.util.concurrent.atomic.AtomicInteger

import retry.{FixedDelayRetry, Retry}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.ActorSystem
import akka.io.SelectionHandler
import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/9/19 17:47
  **/
class PlayRetryTest {

  @Test
  def testRetry(): Unit = {
    val actorSystem = ActorSystem.create();
    val retry = new Retry(global, actorSystem);
    val i = new AtomicInteger(0)
    val result = Await.result(
      retry.withFixedDelay[Int](30, 1 seconds) { () =>
        println("---" + i.get())
        Future.successful(i.addAndGet(1))
      }.stopWhen(_ == 10)
      , 10 seconds)
  }

  @Test
  def testRetry02(): Unit = {
    implicit val actorSystem = ActorSystem.create();
    implicit val scheduler = actorSystem.scheduler
    val num = new AtomicInteger(0)
    val future = Retry.withFixedDelay(15, 1 seconds).apply(() => {
      println("---->" + num.get())
      if(num.get() == 3){
//        10 / 0
        throw new RuntimeException
      }
      println("======")
      Future.successful(num.getAndIncrement())
    })
      .stopWhen(i => i > 5)

    println(Await.result(future, 100 seconds))
    actorSystem.terminate()
  }
}
