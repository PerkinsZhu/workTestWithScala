package zpj.baseTest.javaandscala

import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/9/29 19:09
  **/
object ScalaTest {


  def fetchCache1[T](f: (T) => String, params: List[T]) = {
    params.foreach(i => println(f(i)))
  }

  @Test
  def testCase() {
    val x = (s: String) => "--->" + s
    fetchCache1[String](x, List("A", "B"));
  }

  def fetchCache2[P0](p: P0, f: (P0) => String) = {
    f(p)
  }
}
