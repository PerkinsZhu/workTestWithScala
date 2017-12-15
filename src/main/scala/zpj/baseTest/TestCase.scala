package zpj.baseTest

import org.scalatest.FlatSpec

/**
  * Created by PerkinsZhu on 2017/12/14 19:21. 
  */
class TestCase extends FlatSpec  {
  "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }

  it should "produce NoSuchElementException when head is invoked" in {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }

}
