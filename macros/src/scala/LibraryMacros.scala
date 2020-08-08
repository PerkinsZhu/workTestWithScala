import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import java.util.Date

/**
  * scala 宏 教程
  * https://www.cnblogs.com/tiger-xc/p/6112143.html?utm_source=itdadao&utm_medium=referral
  *
  */
object LibraryMacros {
  def greeting(person: String): Unit = macro greetingMacro

  def greetingMacro(c: Context)(person: c.Expr[String]): c.Expr[Unit] = {
    import c.universe._
    println("compiling greeting ...")
    val now = reify {new Date().toString}
    reify {
      println("Hello " + person.splice + ", the time is: " + new Date().toString)
    }
  }

  def tell(person: String): Unit = macro MacrosImpls.tellMacro
  class MacrosImpls(val c: Context) {
    import c.universe._
    def tellMacro(person: c.Tree): c.Tree = {
      println("compiling tell ...")
      val now = new Date().toString
      q"""
          println("Hello "+$person+", it is: "+$now)
        """
    }
  }



}