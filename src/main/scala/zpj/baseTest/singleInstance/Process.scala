package zpj.baseTest.singleInstance

import org.scalatest.run

/**
  * Created by PerkinsZhu on 2018/1/18 10:19
  **/
class Process {
  val name = "jack"

  def doWork(): Unit = {
    Process.show
  }
}

object Process {
  def show = println("helo")
}
