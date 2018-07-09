package zpj.reflect

import org.junit.Test
import zpj.bean.Teacher

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
        /* par.typeSignature match {
           case ru.definitions.IntTpe => 0
           case ru.definitions.CharTpe => ""
           case ru.definitions.LongTpe => 0.0
           case ru.definitions.BooleanTpe => false
           case ru.definitions.ObjectTpe => null
           case ru.definitions.FloatTpe => 0.0f
           case ru.definitions.AnyRefTpe => "--"
           case ru.definitions.AnyValTpe => "--"
           case ru.definitions.NullTpe => null
           case ru.definitions.UnitTpe => null
           case ru.definitions.AnyTpe => null
           case ru.definitions.NothingTpe => null
           case item => {
             println(item)
             null
           }
         }*/
 /*       par.typeSignature.typeSymbol.asClass match {
          case ru.definitions.IntClass => 0
          case ru.definitions.CharClass => ""
          case ru.definitions.LongClass => 0.0
          case ru.definitions.BooleanClass => false
          case ru.definitions.ObjectClass => null
          case ru.definitions.FloatClass => 0.0f
          case ru.definitions.AnyRefClass => "--"
          case ru.definitions.AnyValClass => "--"
          case ru.definitions.NullClass => null
          case ru.definitions.UnitClass => null
          case ru.definitions.AnyClass => null
          case ru.definitions.NothingClass => null
          case ru.definitions.StringClass => ""
          case item => {
            println(item)
            null
          }
        }*/
        /*if (par.typeSignature =:= typeOf[Int]) {
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
        }*/

      }
      )
      Some(constructorMethod(params: _*).asInstanceOf[T])
    }
  }
}

class TestBeanFactory {
  @Test
  def test(): Unit = {
    val bean = BeanFactory.createBean[Teacher]()
    println(bean)

  }
}