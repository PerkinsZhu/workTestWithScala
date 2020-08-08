package mcro
import scala.annotation.StaticAnnotation
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

class TalkingAnimal(val voice: String) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro TalkingAnimalImpl.implAnnot
}

object TalkingAnimalImpl {
  def implAnnot(c: Context)(annottees: c.Tree*): c.Tree = {
    import c.universe._

    annottees.head match {
      case q"$mods class $cname[..$tparams] $ctorMods(..$params) extends Animal with ..$parents {$self => ..$stats}" =>
        val voice = c.prefix.tree match {
          case q"new TalkingAnimal($sound)" => c.eval[String](c.Expr(sound))
          case _ =>
            c.abort(c.enclosingPosition,
              "TalkingAnimal must provide voice sample!")
        }
        val animalType = cname.toString()
        q"""
            $mods class $cname(..$params) extends Animal {
              ..$stats
              def sayHello: Unit =
                println("Hello, I'm a " + $animalType + " and my name is " + name + " " + $voice + "...")
            }
          """
      case _ =>
        c.abort(c.enclosingPosition,
          "Annotation TalkingAnimal only apply to Animal inherited!")
    }
  }
}