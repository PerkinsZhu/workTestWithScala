package zpj.proxy

import java.lang.reflect.Method

import net.sf.cglib.proxy.{MethodInterceptor, MethodProxy}

/**
  * Created by PerkinsZhu on 2018/1/24 11:12
  **/
class User {

  def show(): Unit = {
    println("i am running")
  }
}

class UserProxy extends MethodInterceptor {
  private var targetObject: Any = null

  import net.sf.cglib.proxy.Enhancer

  def getInstance(target: Any): Any = {
    this.targetObject = target
    val enhancer = new Enhancer
    enhancer.setSuperclass(target.getClass)
    enhancer.setCallback(this)
    val proxyObj = enhancer.create
    proxyObj
  }

  override def intercept(proxy: scala.Any, method: Method, args: Array[AnyRef], methodProxy: MethodProxy): AnyRef = {
    var obj: AnyRef = null
    System.out.println("doSomething---------start")
    obj = method.invoke(targetObject, args: _*)
    System.out.println("doSomething---------end")
    obj
  }
}

object TestUser {
  def main(args: Array[String]): Unit = {
    new UserProxy().getInstance(new User()).asInstanceOf[User].show()
  }
}