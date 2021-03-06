package zpj.baseTest

import org.joda.time.DateTime
import org.slf4j
import org.slf4j.LoggerFactory
import zpj.exception.MyUncaughtException


/**
  * Created by PerkinsZhu on 2017/12/7 19:34.
  */
object TraitTest {
  Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtException());

  //  BasicConfigurator.configure
  // import org.apache.log4j.PropertyConfigurator
  //  PropertyConfigurator.configure("E:\\zhupingjing\\sources\\git\\workTestWithScala\\src\\main\\scala\\log4j.properties")
  def test1(): Unit = {
    val student = new Student().showInfo
    //TODO 调用方法的时候加（）和不加（）有什么区别
    val ming = new MrMing()
    ming.sayMyName
    ming.sayName
    ming.showInfo
  }
def test3(): Unit ={
  def showInfo(xx:TaskType.Value)= println("--------"+xx.id)
  showInfo(TaskType.ERRORQA)
}
def main(args: Array[String]): Unit = {
  //test1()
  //showPerson(new AAA {override def showName: Unit = {println("name")} })
  test3()
}
def test2()={
  2.addTime()
}
implicit class IntToString(m:Int){
  def addTime(): Unit ={
    println(m+new DateTime().toString("yyyy-MM-dd HH:mm:ss"))
  }
}

  def showPerson(p:AAA)={
    p.showName
  }

}



trait AAA{
  def showName:Unit
}

trait Person {
  def showName:Unit
  val logger = {
    println(this.getClass.getName); 1 / 0; LoggerFactory.getLogger(this.getClass)
  }
}

class Student extends Person {
  def showInfo = logger.info("i am student")

  override def showName: Unit = println("--------")
}

class MrMing extends Student {
  def sayName = super.showInfo

  def sayMyName = logger.info("my name is Ming")
}

class ConsoleLogger extends Logger {
  override def log(msg: String): Unit = println("---" + msg)
}

trait Logger {
  def log(msg: String): Unit
}
package comtest {

  class Demo3 {

    import zpj.demo.Demo1

    val name = new Demo1()
  }
  package zpj {
    package demo {
      class Demo4 {
        val d2 = new Demo2
      }
    }
  }
}
class SSS(x:String){
}
object TaskType extends Enumeration{
  val ERRORQA = Value(10)
  val LIKEQA = Value(20)
}