package zpj.baseTest.simpleTest


import scala.collection.mutable.ListBuffer

/**
  * Created by PerkinsZhu on 2018/3/6 14:40
  **/
class SimpleTest(name:String,age:Int) {
  require(name == "jack")


}
class Student{
  val name = "hhhhhhhhhhhhhhh"
}
object SimpleTest{
  def main(args: Array[String]): Unit = {
    var list = ListBuffer[Student]()
    while(true){
      Thread.sleep(1000)
      list.+= (new Student)
      val simple = new SimpleTest("jack",29)
      println("当前线程容量:"+list.size)
    }
  }
}
