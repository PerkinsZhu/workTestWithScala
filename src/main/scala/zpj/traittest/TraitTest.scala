package zpj.traittest

import java.text.SimpleDateFormat
import java.util.Date


/**
  * Created by PerkinsZhu on 2017/9/23 10:15. 
  */
trait Logger {
  def log(info: String): Unit;
}

class ConsoleLogger extends Logger {
  //当做接口使用
  override def log(info: String): Unit = println(info)
}

trait FileLogger {
  def log(info: String): Unit = {
    println("---file ---" + info);
  }
}

class Person {}

trait Logged {
  def log(info: String): Unit = {}
}

trait ConsoleLog extends Logged {
  override def log(info: String): Unit = {
    println(info)
  }
}

class Student extends Person with Logged {
  def showInfo(): Unit = log("i am student!")
}

trait TimeStampLog extends Logged {
  override def log(info: String): Unit = {
    super.log(info + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date))
  }
}

trait ShortLog extends Logged {
  override def log(info: String): Unit = super.log(if (info.length > 10) info.substring(0, 10) else info)
}

class Add {
  def add(num01: Int, num02: Int): Int = {
    num01 + num02
  }
}

trait Count extends Add {//tiait继承class
  def add6(num: Int): Int = {
    add(num, 6)
  }
}
class OtherNum extends Add{
  def show(num:Int): Unit ={
    println(num)
  }
}

//class Number extends OtherNum with Count { }//类可继承trait ，前提是该trait已经继承的有类
class Number extends OtherNum with Count { }//如果该类已经有超类，而混合的trait也继承了超类，那么只有当该类的超类为trait的超类的类的子类(必须是子类而不能是超类)的时候才允许如此继承。

//类可继承trait
object TraitTest {
  def testLog(): Unit = {
    //    new ConsoleLogger().log("hello")
    (new Student() with ConsoleLog with TimeStampLog with ShortLog).showInfo()
    (new Student() with ConsoleLog with ShortLog with TimeStampLog).showInfo()
    println(new Number().add6(10))
  }

  def main(args: Array[String]): Unit = {
    testLog()
  }
}

