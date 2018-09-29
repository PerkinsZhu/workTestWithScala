package zpj.baseTest.javaandscala

import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/9/29 19:09
  **/
class ScalaTest {


  def fetchCache1[T](f: T => String, params: List[T]) = {
    params.foreach(i => println(f(i)))
  }

  @Test
  def testCase() {
    val x = (s: String) => "--->" + s
    fetchCache1[String](x, List("A", "B"));
  }

}
