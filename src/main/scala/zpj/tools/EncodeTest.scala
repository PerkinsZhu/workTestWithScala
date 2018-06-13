package zpj.tools

/**
  * Created by PerkinsZhu on 2018/6/13 11:52
  **/
object EncodeTest {

  def findCode(data: String): Unit = {
    println("UTF-8:" + new String(data.getBytes("UTF-8"), "UTF-8"))
    println("ISO8859-1:" + new String(data.getBytes("ISO8859-1"), "UTF-8"))
    println("GBK:" + new String(data.getBytes("GBK"), "UTF-8"))
    println("GB2312:" + new String(data.getBytes("GB2312"), "UTF-8"))
    println("GB18030:" + new String(data.getBytes("GB18030"), "UTF-8"))
  }
}
