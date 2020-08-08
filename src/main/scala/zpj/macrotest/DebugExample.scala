package zpj.macrotest


/**
  * 运行方式，
  *   1、先注释所有的方法调用处执行一下，触发编译操作
  *   2、之后再取消调用处注释，执行方法
  */
object DebugExample extends App {

  import DebugMacros._


//  hello()
//  printparam(123)

  val y = 10

  def test() {
    val p = 11
    debug(p)
    debug(p + y)
  }

  test()
}
