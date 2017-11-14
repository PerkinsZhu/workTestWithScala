package zpj.machinelearning.tianchiMatch

import com.mongodb.casbah.commons.MongoDBObject
import org.joda.time.LocalDate
import play.api.libs.json.Json
import zpj.machinelearning.tianchiMatch.PurchaseForecast.users

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Created by PerkinsZhu on 2017/11/14 10:36. 
  */
object Test {

  def testFuture(): Unit = {
    for (i <- 0 to 100) {
      val future = Future {
        //Future开启的是线程执行
        println(Thread.currentThread().getName)
        "'ssd"
      }
      future.onComplete{
        case Success(re)=>println("----"+(re+10))
        case Failure(ex)=>println("+++"+ex+10)
      }
    }
    Thread.sleep(2000000)
  }


  def testList(): Unit = {
    var list = scala.collection.mutable.ListBuffer.empty[Int]
    val temp = List(1,2,3,4)
    println(list +: temp)
    println(list)
    println(list :+ temp)
    println(list)
    println(list ++ temp)
    println(list += 12)
    println(list)
  }

  def main(args: Array[String]): Unit = {
    //    val result =PurchaseForecast.users.find(BasicDBObject.parse(Json.obj("uid"->"10001082","bhvt"->Json.obj("$gte"->"4")).toString()),MongoDBObject("uid"->true,"gid"->true,"bhvt"->true,"icat"->true,"time"->true)).toArray.asScala.toList
    //    val result =PurchaseForecast.users.find(BasicDBObject.parse(Json.obj("uid"->"10001082","time"->Json.obj("$lte"->"2014-12-02 15")).toString()),MongoDBObject("uid"->true,"gid"->true,"bhvt"->true,"icat"->true,"time"->true)).toArray.asScala.toList
    //    val result =PurchaseForecast.users.find(MongoDBObject(Json.obj("uid"->"10001082","time"->Json.obj("$lte"->"2014-12-02 15")).toString()),MongoDBObject("uid"->true,"gid"->true,"bhvt"->true,"icat"->true,"time"->true)).toArray.asScala.toList
    //    val result =PurchaseForecast.users.find(MongoDBObject(Json.obj("uid"->"10001082","time"->Json.obj("$regex"->"2014-12-02 ..")).toString()),MongoDBObject("uid"->true,"gid"->true,"bhvt"->true,"icat"->true,"time"->true)).toArray.asScala.toList
    //    result.foreach(println _)
    val resu = users.find(MongoDBObject(Json.obj("uid"->"102309547","time"->Json.obj("$lte"->"2014-11-10")).toString()),MongoDBObject("gid"->true,"bhvt"->true,"time"->true)).toArray.asScala.toList
        resu.foreach(print _)
//        val newDay = LocalDate.parse("2017-12-31").plusDays(1)
//        print(newDay)
//    testFuture()
//    testList()
  }
}
