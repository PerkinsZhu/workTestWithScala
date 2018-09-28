package slicktest

import org.junit.Test
import slick.jdbc.PostgresProfile.api._
import slick.util.AsyncExecutor
import slicktest.bean.{Coffees, Suppliers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * Created by PerkinsZhu on 2018/9/28 16:30
  **/
object SlickTestCase {
  val db = Database.forURL("jdbc:postgresql://127.0.0.1/test", "postgres", password = "jinzhao", driver = "org.postgresql.Driver", executor = AsyncExecutor("test1", numThreads = 10, queueSize = 1000))
  val coffees = TableQuery[Coffees]
  val suppliers = TableQuery[Suppliers]

  @Test
  //普通查询
  def testQuery01(): Unit = {
    // 可以添加各种查询条件
    val q2 = for {
      c <- coffees if c.price < 9.0
      s <- suppliers if s.id === c.supID
    } yield (c.name, s.name)

    db.run(q2.result).map(_.foreach(t =>
      println("  " + t._1 + " supplied by " + t._2)
    ))


  }

  def testQuery02() = {
    // 全表查询
    db.run(coffees.result).map(_.foreach {
      case (name, supID, price, sales, total) =>
        println("  " + name + "\t" + supID + "\t" + price + "\t" + sales + "\t" + total)
    }).onComplete({
      case Failure(exception) => exception.printStackTrace()
      case Success(value) => print(value)
    })
  }

  def testQuery03(): Unit = {
    // count
    //    db.run(coffees.length.result).map(println _)

    val q01 = coffees.filter(_.price > 9.0).map(cof => {
      cof.name
    })

    db.run(q01.result).foreach(data => {
      println(data.toList)
    })
  }

  def testAdd() = {
    // 测试添加数据
    val actions = DBIO.seq {
      coffees += ("JACK", 101, 234.0, 234, 23)
      coffees ++= Seq(
        ("Tom", 49, 234.0, 2334, 23),
        ("zhangsan", 150, 224.0, 234, 23)
      )
    }
    //      .andFinally()
    db.run(actions).onComplete({
      case Success(value) => println(value)
      case Failure(exception) => exception.printStackTrace()
    })
  }

  def testUpdate() = {
    val update = coffees.filter(_.price > 9.0).map(_.price).update(10).transactionally
    db.run(update).onComplete({
      case Success(value) => println(value)
      case Failure(exception) => exception.printStackTrace()
    })
  }

  def main(args: Array[String]): Unit = {
    //    testQuery01()
    //    testQuery02()
    //    testQuery03()
    //    testAdd()
    testUpdate()
    Thread.sleep(Int.MaxValue)
  }
}
