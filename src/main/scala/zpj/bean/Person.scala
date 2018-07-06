package zpj.bean

/**
  * Created by PerkinsZhu on 2017/9/23 10:39. 
  */
/*case class Person(name:String,age:Int)
case class Student(override val name:String, override val age:Int, classs:String) extends Person(name,age)*/

case class Person(name: String, age: Int)
class Student private(name: String, age: Int) extends Person(name, age) {
  def show(): String = {
    "Hello"
  }

  def run(): Unit = {

  }

  def runWithMe(me: String): Unit = {

  }

}
class Teacher(name: String, age: Int, isMale: Boolean, salary: Float, student: Student) extends Person(name, age) {

  override def toString = s"Teacher($name,$age,$isMale,$salary,$student)"
}