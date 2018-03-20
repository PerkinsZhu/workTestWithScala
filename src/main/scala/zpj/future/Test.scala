package zpj.future

import org.scalatest.FunSuite

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by PerkinsZhu on 2018/3/18 11:34
  **/
class Test extends FunSuite {

  test("future demo 1") {
    Future {
      println("hello world !!!")
    }
    sleep
  }

  val sleep = Thread.sleep(1000)
}


object Test extends App{
  Future {
    println("hello world !!!")
  }
  Future { 10 }.map( _ + 10).map(_ * 10)

  Thread.sleep(1000000000)
}
