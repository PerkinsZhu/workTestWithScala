package zpj.machinelearning.tianchiMatch

import com.mongodb.{BasicDBObject, DBObject}
import com.mongodb.casbah.commons.MongoDBObject
import org.joda.time.{DateTime, Days}
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.{JsObject, Json}
import zpj.database.MongodbTool
import zpj.machinelearning.tianchiMatch.PurchaseForecast.users

import scala.collection.JavaConverters._
import scala.collection.immutable
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by PerkinsZhu on 2017/11/14 17:09. 
  */
object Demo2 {
  val dbManager = MongodbTool("mongodb://localhost:27017/").mongoClient
  val goods =  dbManager("test").getCollection("goods")
  val allData = users.find(MongoDBObject(),MongoDBObject("uid"->true)).toArray.asScala.map(item=>{item.get("uid").toString}).toSet
  val goodsTable = users.find(MongoDBObject("bhvt"-> "4" ),MongoDBObject("gid"->true)).toArray.asScala.map(_.get("gid").toString).toSet.toList
  var dealNum = 0;
  var allNum = allData.size;
  def main(args: Array[String]): Unit = {
    doHandel()
    while(dealNum != allNum){Thread.sleep(2000)}
  }
  def getTime(time:String):Int=Days.daysBetween(DateTimeFormat.forPattern("yyyy-MM-dd HH").parseDateTime(time), new DateTime(2014,12,19,23,0,0)).getDays
  def getPreData(user: String, time: String) = users.find(MongoDBObject(Json.obj("uid"->user,"time"->Json.obj("$lte"->time)).toString()),MongoDBObject("gid"->true,"bhvt"->true,"time"->true)).toArray.asScala.toList

  def dealuserResult(res: immutable.IndexedSeq[(String,(Float, Float, Float))]): Unit = {
    res.foreach(item=>println(item._1+"---->"+item._2))
  }

  def doHandel(): Unit = {
    allData.foreach(user => {
      val future = Future {
        val resutlDate = for (i <- 0 to 20) yield {
          val time = new DateTime("2014-11-28").plusDays(i).toString("yyyy-MM-dd")
          val preData = getPreData(user, time)
          var dateMap = scala.collection.mutable.Map.empty[String, Float]
          preData.foreach(item => {
//          val score = getScore(Integer.parseInt(item.get("bhvt").toString), getTime(item.get("time").toString))
            val score = getScore(Integer.parseInt(item.get("bhvt").toString))
            val gid = item.get("gid").toString
            val newScore = dateMap.getOrElse(gid, 0.0f) + score
            dateMap.put(gid, newScore)
          })
          val sortGoods = dateMap.toList.sortWith(_._2 > _._2)
          val validateData = users.find(BasicDBObject.parse(Json.obj("uid" -> user, "bhvt" -> "4", "time" -> Json.obj("$regex" -> (time+" .."))).toString()), MongoDBObject("gid" -> true)).toArray().asScala.map(_.get("gid").toString).toList
          val recommend = (if (sortGoods.length > 5) {sortGoods.take(5) } else {sortGoods})
          (user,correctRate(recommend, validateData))
        }
        resutlDate
      }
      future.onComplete{
        case Failure(ex)=>print(ex)
        case Success(res)=>dealuserResult(res)
      }

    })
  }
  def correctRate(recommend: List[(String, Float)], validateData: List[String]):Tuple3[Float,Float,Float] = {
    var errorNum = 0.0f
    var correctNum = 0.0f
    recommend.foreach(good=>{
      if(validateData.contains(good._1)){correctNum=correctNum+1}else{errorNum=errorNum+1}
    })
    val rate = errorNum+correctNum
    (correctNum,errorNum,if(rate == 0){0.0f}else{correctNum/rate})
  }

//  def getScore(typ:Int,time:Int):Float= if(typ == 4){(time-10)*typ*0.1f/(time)}else{typ*1.0f/(time)}
  def getScore(typ:Int):Float= 0.1f*typ
  def getUserDate(uid:String,time:String):List[DBObject]={
    users.find(MongoDBObject(Json.obj("uid"->uid,"time"->Json.obj("$lte"->time)).toString()),MongoDBObject("gid"->true,"bhvt"->true,"icat"->true,"time"->true)).toArray.asScala.toList
  }

}
