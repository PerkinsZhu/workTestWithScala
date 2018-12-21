package zpj.akka.akkadb

/**
  * Created by PerkinsZhu on 2018/12/20 13:55
  **/
case class SetRequest(key: String, value: Object)
case class GetRequest(key: String)
case class KeyNotFoundException(key: String) extends Exception