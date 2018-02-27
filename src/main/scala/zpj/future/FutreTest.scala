package zpj.future

import java.util.concurrent.atomic.AtomicReference

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success}

/**
  * Created by PerkinsZhu on 2018/1/26 15:57
  **/
object FutreTest {

  def testFuture(): Unit = {
//    println(global)
/*    val action: () => Int = () => {
      Thread.currentThread().getStackTrace.foreach(println _)
      println(" i am Sleeping ....")
      100
    }*/

//    println(action)
    val future = Future[Int] {
      //    require(10 == 3)
//      Thread.sleep(100000)
//      action()
      println("===-------")
      1000
    }
//    println(future)
/*    future.onComplete {
      case Failure(ex) => ex.printStackTrace()
      case Success(res) => println(res)
    }*/
          Thread.sleep(1000)

    /* println(future)

     println(future)*/
  }

  def testTemp(): Unit = {
    val promise = Promise.successful("hello")
    println(promise.hashCode())
    val future = promise.future
    println(future.hashCode())
    println(future)
    //    Await.result(promise.future, 5 seconds)
    //    println(classOf[future])

    //    println(new MyFuture().future.show)
  }

  def testManyFuture() = {
    Future {
      println("1 " + Thread.currentThread().getName)
      println("1 start")
      Future {
        println("2  " + Thread.currentThread().getName)
        println("2 start")
        Future {
          println("3  " + Thread.currentThread().getName)
          println(" 3 start")
          Thread.sleep(2000)
          println(" 3 end")
        }
        Future {
          println("4  " + Thread.currentThread().getName)
          println(" 4 start")
          Thread.sleep(2000)
          println(" 4 end")
        }
        Thread.sleep(2000)
        println("2 end")
      }
      Thread.sleep(2000)
      println("1 end")
    }
  }

  def testFutureNum() = {
    def createFuture(num: Int): Future[Any] = {
      if (num > 0) {
        Future {
          println(s"---${num}--${Thread.currentThread().getName}")
          createFuture(num - 1)
          Thread.sleep(1000)
        }
      } else {
        Future {
          Thread.sleep(1000)
          println(s"---$num")
          num
        }
      }
    }

    createFuture(100)

  }

  def testException() = {
    val future = Future{
      20 / 0
    }
    println("------")

    future.map(_+3).onComplete{
      case Failure(ex) => println(ex.getMessage)
      case Success(re) => println(re)
    }
  }

  def testIf() = {
    var data = new AtomicReference[Object](Nil)
    if(data.compareAndSet(Nil,5 :: Nil))(println("=="))
    else{println("-----")}
    println(data.get())
  }

  def testCase() = {
    /*var data = new AtomicReference[Object](Nil)
    data.get() match {
      case raw: List[_] =>
        println("----222-")
        val cur = raw.asInstanceOf[List[Animal]]
        println(data.compareAndSet(raw,cur))
        println(cur.getClass+"--"+ raw.getClass)
      case _ => println("-----");null
    }


    2 match {
      case 1 => println("---w")
      case 3 => println("--s-")
      case 2 => println("-d--")
      case 4 => println("-3e--")
    }*/

    var data = new AtomicReference[Object](Nil)
    println(data.compareAndSet(Nil,Success(1000)))

  }

  def testFutureDebug() = {
    for(i <- 1 to 3){
      Future{
        Thread.sleep(1000)
        println("---=======")
      }
    }
  }

  def main(args: Array[String]): Unit = {
//    testFutureNum()
//        testManyFuture()
        testFuture()
//    testFutureDebug()
    //    testTemp()
//    testCase()
//    testException()
//    testIf()
    Thread.sleep(5000000)
  }

}

trait Animal {
  def run: Unit
}

//as
class FatherFuture {
  def future: this.type = this

  val age = 10

  def show = {

    if (age > 10) {
    }
    println(this)
  }

  val name = "jackfatheel"
}

class MyFuture extends FatherFuture with Animal {

  override def run: Unit = {

  }

  override val name = "jack"

}
