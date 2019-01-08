package zpj.withjava

/**
  * Created by PerkinsZhu on 2019/1/8 10:11
  **/
object FunctionUtil {
  def testFunction(f: Integer => Integer): Integer = f(5)
}

abstract  class AbstractFunctionIntForJava extends ((Integer) => Integer) {
}