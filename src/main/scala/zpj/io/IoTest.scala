package zpj.io

import java.io.{File, PrintStream}

/**
  * Created by PerkinsZhu on 2017/9/25 8:50. 
  */
object IoTest {

  def intToChar(): Unit = {
    val ps = new PrintStream(new File("CC.txt"))
    for (i <- 0 to 65535) {
      //ps.write(i.toChar)
      println(i.toChar + "---" + i)
    }
    ps.close()
  }

  def main(args: Array[String]): Unit = {
    intToChar()
  }
}
