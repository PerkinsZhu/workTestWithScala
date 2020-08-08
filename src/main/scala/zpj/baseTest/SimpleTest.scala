package zpj.baseTest

import org.junit.{Assert, Test}

@Test
class SimpleTest extends Assert {

  @Test
  def helTest: Unit = {
    val someValue = true
    assert(someValue == true)
  }

  @Test
  def streamTest = {
    (1 to 1000).toStream.map(i => i * i).foreach(println)
  }





}
