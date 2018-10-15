package nio

import java.nio.ByteBuffer

import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/7/13 9:16
  **/
class TestCase {
  @Test
  def testRead(): Unit = {
    val buffer = ByteBuffer.allocate(10)
    println(buffer.capacity())
    println(buffer.position)
    buffer.put("1234567890".getBytes("UTF-8"))
    println(buffer.position)
    buffer.flip()
    println(buffer.position)


  }
}
