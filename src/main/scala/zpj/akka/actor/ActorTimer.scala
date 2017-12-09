package zpj.akka.actor

import akka.actor.{Actor, Timers}

/**
  * Created by PerkinsZhu on 2017/12/6 17:03. 
  */
class ActorTimer {


}
package zz{

private class MyActor extends Actor with Timers{
  override def receive: Receive = {
  case "1"=>println("----")
}
  timers
}
}
