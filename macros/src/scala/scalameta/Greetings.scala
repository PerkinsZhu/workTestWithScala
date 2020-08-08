package scalameta
import scala.language.experimental.macros

import scala.meta._

class Greetings extends scala.annotation.StaticAnnotation {

  @inline def apply(defn: Any): Any = meta {
    defn match {
      case q"object $name {..$stats}" => {
        q"""
              object $name {
                def sayHello(msg: String): Unit = println("Hello," + msg)
                ..$stats
              }
            """
      }
      case _ => abort("annottee must be object!")
    }
  }
}