package zpj.baseTest.simpleTest

/**
  * Created by PerkinsZhu on 2018/3/6 14:40
  **/
class SimpleTest(name:String,age:Int) {
  require(name == "jack")


}

object SimpleTest{
  def main(args: Array[String]): Unit = {
    val simple = new SimpleTest("aaa",29)

  }
}
