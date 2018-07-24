package zpj.macrotest

/**
  * Created by PerkinsZhu on 2018/7/24 9:56
  **/
/*
class MacroTest {

}
import scala.language.experimental.macros
import scala.reflect.macros.Context

object DebugMacros {
  def hello(): Unit = macro hello_impl

  def hello_impl(c: Context)(): c.Expr[Unit] = {
    import c.universe._
    reify { println("Hello World!") }
  }
}
object DebugExample extends App {
  import DebugMacros._
  hello()
}


object QStringImpl {

  def Qmap(p: Char => Int,elem:String):Int = macro QStringImpl.map_Impl
  def map_Impl(c: Context)(p: c.Expr[Char => Int],elem: c.Expr[String]): c.Expr[Int] = {
    import c.universe._
    //splice只能在reify使用,具体化,功能与reify相反
    reify { elem.splice.map(p.splice).sum}
  }
}
object DemoTest2 extends App{
  import QStringImpl._
  val result = Qmap(_.toInt + 1,"111111")
  println(result)
}

*/

import scala.reflect.macros.whitebox.Context
import scala.collection.mutable.{ListBuffer, Stack}
import scala.language.experimental.macros
object Macros {
  def printf(format: String, params: Any*): Unit = macro printf_impl

  def printf_impl(c: Context)(format: c.Expr[String], params: c.Expr[Any]*): c.Expr[Unit] = {
    import c.universe._
    val Literal(Constant(s_format: String)) = format.tree

    val evals = ListBuffer[ValDef]()
    def precompute(value: Tree, tpe: Type): Ident = {
      val freshName = TermName(c.fresh("eval$"))
      evals += ValDef(Modifiers(), freshName, TypeTree(tpe), value)
      Ident(freshName)
    }

    val paramsStack = Stack[Tree]((params map (_.tree)): _*)
    val refs = s_format.split("(?<=%[\\w%])|(?=%[\\w%])") map {
      case "%d" => precompute(paramsStack.pop, typeOf[Int])
      case "%s" => precompute(paramsStack.pop, typeOf[String])
      case "%%" => Literal(Constant("%"))
      case part => Literal(Constant(part))
    }

    val stats = evals ++ refs.map(ref => reify(print(c.Expr[Any](ref).splice)).tree)
    c.Expr[Unit](Block(stats.toList, Literal(Constant(()))))
  }
}