package zpj.akka.akkadb

import java.util

import akka.actor.Actor
import akka.event.Logging

import scala.collection.mutable.HashMap

/**
  * Created by PerkinsZhu on 2018/12/20 13:58
  **/
class AkkademyDb extends Actor {
  val log = Logging(context.system, this)
  val map = new HashMap[String, Object]

  override def receive: Receive = {
    case SetRequest(key, value) => {
      log.info("received SetRequest - key: {} value: {}", key, value)
      map.put(key, value)
      sender ! "success"
    }
    case o => log.info("received unknown message: {}", o)
  }
}
