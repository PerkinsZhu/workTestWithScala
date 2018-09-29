package zpj.eecutioncontext

import java.util.concurrent.Executors

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

/**
  * Created by PerkinsZhu on 2018/9/29 9:35
  **/
class ExecutionContextTest {
  implicit val stdec = scala.concurrent.ExecutionContext.Implicits.global
  implicit val ec01 = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))



  val system = ActorSystem.create()
  implicit val ec02 = system.dispatchers.lookup("my-dispatcher")





}
