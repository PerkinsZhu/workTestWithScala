package zpj.machinelearning.tianchiMatch

import com.mongodb.{BasicDBObject, DBObject}
import com.mongodb.casbah.commons.MongoDBObject
import org.joda.time.{DateTime, Days}
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.{Json}
import zpj.database.MongodbTool
import zpj.machinelearning.tianchiMatch.PurchaseForecast.{users}

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
  val ubgc =  dbManager("test").getCollection("user_buy_goods_count")
  val buyDivAll =  dbManager("test").getCollection("buynum_allnum")
  val itemCount=  dbManager("test").getCollection("item_count")
  val f1DataColl=  dbManager("test").getCollection("f1_data")
  val f1rColl=  dbManager("test").getCollection("f1_recored")
  val allData = users.find(MongoDBObject(),MongoDBObject("uid"->true)).toArray.asScala.map(item=>{item.get("uid").toString}).toSet
  val goodsTable = users.find(MongoDBObject("bhvt"-> "4" ),MongoDBObject("gid"->true)).toArray.asScala.map(_.get("gid").toString).toSet.toList
  val ubgcData = ubgc.find(MongoDBObject(),MongoDBObject("uid"->true,"count"->true)).toArray.asScala.map(ele=>{(ele.get("uid").toString,Integer.parseInt(ele.get("count").toString))}).toList
  val allUserItem = users.find(MongoDBObject(),MongoDBObject("gid"->true,"bhvt"->true,"icat"->true,"time"->true,"uid"->true)).toArray.asScala.toList
  val dealUser = buyDivAll.find(MongoDBObject(Json.obj("resm"->Json.obj("$gte"->0.04633204638957977)).toString())).toArray.asScala.toList.map(_.get("uid").toString)
  var dealNum = 0;
  var allNum = allData.size;

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
    while(dealNum != allNum){Thread.sleep(2000)}
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

  def showBuyDAllOper(): Unit = {
    ubgcData.foreach(item=>{
      val uid = item._1
      val count = item._2
      val allOper = allUserItem.filter(_.get("uid").toString == uid).size
      val res=  count*1.0f / allOper
      val  resm = count*res
      println(uid+"   购买/操作记录 = "+res+"  乘积="+resm)
      buyDivAll.insert(MongoDBObject("uid"->uid,"count"->count,"res"->res,"resm"->resm))
    })
  }

  def showTimeWithCount(): Unit = {
    val resutlDate = for (i <- 0 to 30) yield {
      val time = new DateTime("2014-11-18").plusDays(i).toString("yyyy-MM-dd ..")
      val count = allUserItem.filter(item=>{
        item.get("time").toString.matches(time) && item.get("bhvt").toString == "4"
      } ).size
      itemCount.insert(MongoDBObject("time"->time,"count"->count))
    }
  }

  def getPreDataNew(startTime: String,endTime: String) = {
    users.find(MongoDBObject(Json.obj("time"->Json.obj("$lte"->endTime,"$gte"->startTime)).toString()),MongoDBObject("gid"->true,"bhvt"->true,"time"->true)).toArray.asScala.toList
  }


  def doSaveTof1(dayStep:Int,topN:Int,uid: String, rl: Int, bl: Int, P: Float, R: Float, f1: Float): Unit = {
    f1DataColl.insert(MongoDBObject("uid"->uid,"rl"->rl,"bl"->bl,"r"->R,"p"->P,"f"->f1))
  }

  def correctRateNew(validateData: List[(String, String)], userAndRecommend: List[(String, List[String])]): List[Tuple6[String,Int,Int,Float,Float,Float]] = {
    userAndRecommend.map(item=>{
      val uid = item._1
      val recommend = item._2
      val userBuyGoods = validateData.filter(_._1 == uid ).map(_._2)
      val correctNum = recommend.filter(userBuyGoods.contains(_)).length
      val P = correctNum*1.0f / recommend.length
      val R = if(userBuyGoods.length==0){0}else{correctNum*1.0f / userBuyGoods.length}
      val sumPR = P+R
      val f1 = if(sumPR==0){0.0f}else{2*P*R/sumPR}
      (uid,recommend.length,userBuyGoods.length,P,R,f1)
    })

  }

  def saveTof1(dayStep: Int, topN: Int, f1Data:List[Tuple6[String,Int,Int,Float,Float,Float]]): Unit = {
    f1Data.foreach(item=>{
      doSaveTof1(dayStep,topN,item._1,item._2,item._3,item._4,item._5,item._6)
    })
  }

  def doHandelNew(j: Int, dayStep: Int, topN:Int):Float = {
    val time = new DateTime("2014-11-28").plusDays(j)
    val endTime = time.toString("yyyy-MM-dd")
    val startTime = time.minusDays(dayStep + 1).toString("yyyy-MM-dd")

    val preData = getPreDataNew(startTime,endTime)
    val validateData = users.find(BasicDBObject.parse(Json.obj("bhvt" -> "4", "time" -> Json.obj("$regex" -> (endTime+" .."))).toString()), MongoDBObject("uid" -> true,"gid" -> true)).toArray().asScala.map(item=>{(item.get("uid").toString,item.get("gid").toString)}).toList
//    val dealUserSize = dealUser.length
//    var num = 0
    val userAndRecommend = dealUser.map(user => {
//      num = num+1
//      println(startTime+"--->"+endTime+" topN----"+topN+"   dayStep--"+dayStep+" 共 "+dealUserSize+" 个用户，正在处理第 "+num+" 个用户....")
      var dateMap = scala.collection.mutable.Map.empty[String, Int]
      preData.foreach(item => {
            val score =Integer.parseInt(item.get("bhvt").toString)
            val gid = item.get("gid").toString
            val newScore = dateMap.getOrElse(gid, 0) + score
            dateMap.put(gid, newScore)
          })
      val sortGoods = dateMap.toList.sortWith(_._2 > _._2)
      val recommend = (if (sortGoods.length > topN) {sortGoods.take(topN) } else {sortGoods}).map(_._1)
      (user,recommend)
      })
    val f1Data = correctRateNew(validateData,userAndRecommend)
    saveTof1(dayStep,topN,f1Data)
    val averageF1=f1Data.map(_._6).sum / f1Data.length
    averageF1
  }

  def countNum(res: Int): Unit= {
    dealNum = dealNum+1
    println("共 200 组，已经处理完成： "+dealNum+ " 组！")
  }

  def getDayNum(): Unit = {
    for (dayStep <- 1 to 10) {
      for (j <- 1 to 20) {
        val future = Future {
          for (topN <- 1 to 10) {
           val averageF1 = doHandelNew(j, dayStep, topN)
            println("topN: "+topN+" dayStep: "+dayStep+" avf1: "+averageF1)
            f1rColl.insert(MongoDBObject("topN"->topN,"dayStep"->dayStep,"avf1"->averageF1))
          }
          1
        }
        future onComplete{
          case Success(res)=>countNum(res)
          case Failure(e)=>print(e)
        }
      }
    }
    while(dealNum != 200){Thread.sleep(5000)}
  }

  def main(args: Array[String]): Unit = {
//    doHandel()
//    showBuyDAllOper()
//    showTimeWithCount()
    getDayNum()
  }
}
