package zpj.future

import java.util.concurrent.atomic.AtomicReference

import scala.concurrent.ExecutionContext
import scala.concurrent.impl.Promise
import scala.util.Try

/**
  * Created by PerkinsZhu on 2018/2/1 11:00
  **/
trait TigTrait {
  def onComplete(f: Int => Int): Unit
}

class TigFather extends TigTrait {
  override def onComplete(f: Int => Int): Unit = {
    println("ffff")
//    f(10)
    println("dddddd")
  }
}

class Tig extends TigFather with TigTrait {
  def add(a: Int) = {
    println("cccccc")
    a + 10
  }

  def test(f: Int => Int): Unit = {
    println("1")
    onComplete { data => {
      println("bbbbbb-" + data)
      add(f(data))
    }
    }
    println("2")
  }
}

object TestDemo {
  def main(args: Array[String]): Unit = {
    new Tig().test((s: Int) => {println("aaaaa---" + s); s + 10})
  }

  trait  Value{
    def value:AnyRef
  }
  class TestData extends AtomicReference[AnyRef](Nil) with Value {
    override def value: AnyRef = ???
  }
}
