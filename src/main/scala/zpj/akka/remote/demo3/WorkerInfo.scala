package zpj.akka.remote.demo3

/**
  * Created by PerkinsZhu on 2018/5/23 11:40
  **/
class WorkerInfo(val id: String, val memory: Int, val cores: Int) {
  // 上一次心跳
  var lastHeartbeatTime : Long = _
}
