package zpj.baseTest.callBack

/**
  * Created by PerkinsZhu on 2018/1/18 16:36
  **/
object Test extends App {
  new Worker().doWork(new CallBacker)
}

class CallBacker extends CallBack {
  override def doCallBack(result: String): Unit = println(result)
}
