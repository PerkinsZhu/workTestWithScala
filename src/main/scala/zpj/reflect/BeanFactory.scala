package zpj.reflect

/**
  * Created by PerkinsZhu on 2018/7/6 11:03
  **/
object BeanFactory {

  import scala.reflect.runtime.universe._
  import scala.reflect.runtime.{universe => ru}

  def createBean[T: TypeTag](): Option[T] = {
    val typee = ru.typeOf[T]
    val constructor = typee.decl(ru.termNames.CONSTRUCTOR).asMethod
    if (constructor.isPrivate) {
      println("private class can not created ")
      None
    } else {
      val classMirror = ru.runtimeMirror(getClass.getClassLoader).reflectClass(typee.typeSymbol.asClass)
      val constructorMethod = classMirror.reflectConstructor(constructor)
      val params = constructor.paramLists.flatten.map(par => {
        if (par.typeSignature =:= typeOf[Int]) {
          0
        } else {
          if (par.typeSignature =:= typeOf[String]) {
            ""
          } else {
            if (par.typeSignature =:= typeOf[Double]) {
              0.0
            } else {
              if (par.typeSignature =:= typeOf[Float]) {
                0.0f
              } else {
                if (par.typeSignature =:= typeOf[Char]) {
                  ""
                } else {
                  if (par.typeSignature =:= typeOf[Boolean]) {
                    false
                  } else {
                    null
                  }
                }
              }
            }
          }
        }

      })
      Some(constructorMethod(params: _*).asInstanceOf[T])
    }
  }
}