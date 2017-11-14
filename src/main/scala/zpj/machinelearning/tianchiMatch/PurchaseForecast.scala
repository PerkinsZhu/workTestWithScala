package zpj.machinelearning.tianchiMatch


import java.io.{File, FileWriter}

import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.commons.conversions.scala.JodaDateTimeDeserializer
import org.joda.time.DateTime
import zpj.database.MongodbTool
import org.joda.time.Days
import play.api.libs.json.JsObject

import scala.collection.JavaConverters._

/**
  * Created by PerkinsZhu on 2017/10/20 16:30. 
  */
object PurchaseForecast {
  val dbManager = MongodbTool("mongodb://localhost:27017/").mongoClient
  val goods =  dbManager("test").getCollection("goods")
  val users =  dbManager("test").getCollection("user")
/*  def loadData() = {//数据入库
    Source.fromFile("F:/zhupingjing/competition/tianchi/231522/fresh_comp_offline/tianchi_fresh_comp_train_user.csv").getLines().foreach(line => {
      val data = line.toString.split(",")
     users.insert(MongoDBObject("uid" -> data(0), "gid" -> data(1), "bhvt" -> data(2), "ul" -> data(3), "icat" -> data(4), "time" -> data(5)))
    })
    Source.fromFile("F:/zhupingjing/competition/tianchi/231522/fresh_comp_offline/tianchi_fresh_comp_train_item.csv").getLines().foreach(line => {
      val data = line.toString.split(",")
     goods.insert(MongoDBObject("gid" -> data(0), "gl" -> data(1), "icat" -> data(2)))
    })
  }*/

  /**
    *对某用户下的商品进行处理  找到该商品 加入购入车的时间 添加都藏的时间 购买的时间之间的关系
    * 特征：用户行为
    *   商品的浏览多少次才会购买？
    *    商品以前的浏览次数 * 用户的浏览权值 * W + 该用户的行为系数
    */

  def main(args: Array[String]): Unit = {
//   users.find(MongoDBObject(),MongoDBObject("uid"->true)).forEach(item=>{dealUser(item.get("uid").toString)})
   val allData = users.find(MongoDBObject(),MongoDBObject("uid"->true)).toArray.asScala.map(item=>{item.get("uid").toString}).toSet
     allData.foreach(dealUser(allData.size,_))
  }
  val writer = new FileWriter(new File("E:\\zhupingjing\\test\\tianchi_mobile_recommendation_predict.csv"))
  var num = 0;

  def getBuyGoods():List[String]={
    users.find(MongoDBObject("bhvt"-> "4" ),MongoDBObject("gid"->true)).toArray.asScala.map(_.get("gid").toString).toSet.toList
  }
  val buyGoods =getBuyGoods()
  def dealUser(size:Int, uid:String)={
    """
      |计算出用户所有购买商品所在的类别A
      |对用户浏览、收藏、加购物车的所有商品（该商品不属于A类别）进行计算分值
      |求出分值最高的五种商品为最终结果
    """
    num=num+1
    println("总数："+size+"个用户，正在处理第"+num+"用户："+uid)
    val userData = getUserDate(uid)

  /*  val buyType = userData.filter(_.get("bhvt")== "4").map(item=>{
      item.get("icat")
    }).toSet*/


/*    var dateMap=scala.collection.mutable.Map.empty[String,Float]
    userData.filter(ele=>{ele.get("bhvt") != "4" && !buyType.contains(ele.get("icat"))}).map(item=>{
      val score = getScore(Integer.parseInt(item.get("bhvt").toString),getTime(item.get("time").toString))
      val gid = item.get("gid").toString
      val newScore = dateMap.getOrElse(gid,0.0f)+score
      dateMap.put(gid,newScore)
    })*/
    var dateMap=scala.collection.mutable.Map.empty[String,Float]
    userData.filter(ele=>{buyGoods.contains(ele.get("gid"))}).map(item=>{
      val score = getScore(Integer.parseInt(item.get("bhvt").toString),getTime(item.get("time").toString))
      val gid = item.get("gid").toString
      val newScore = dateMap.getOrElse(gid,0.0f)+score
      dateMap.put(gid,newScore)
    })
    val temp = dateMap.toList.sortWith(_._2>_._2)

    (if (temp.length >5){temp.take(5)}else{temp}).foreach(item=>{
      writer.write(uid+","+item._1+"\n")
    })

  }
  import org.joda.time.format.DateTimeFormat

  def getTime(time:String):Int=Days.daysBetween(DateTimeFormat.forPattern("yyyy-MM-dd HH").parseDateTime(time), new DateTime(2014,12,19,23,0,0)).getDays
//  这里有一个可调参数
//  def getScore(typ:Int,time:Int):Float= if(typ == 4){(time-10)*typ*0.1f/(time)}else{typ*1.0f/(time)}
  //TODO 重新计算上次计分方式
  def getScore(typ:Int,time:Int):Float= if(typ == 4){0}else{typ*1.0f/(time)}
  def getUserDate(uid:String):List[DBObject]={
    users.find(MongoDBObject("uid"->uid),MongoDBObject("gid"->true,"bhvt"->true,"icat"->true,"time"->true)).toArray.asScala.toList
  }

  """
    |基于协同过滤模型来进行商品推荐
    |   1、计算用户的相似度
    |       根据以往购买商品类计算用户的购物相似度
    |       对每个用户出其最为相似的用户，然后推荐相似用户购买而该用户未购买的商品
    |    如计算用户的相似度？
    |       根据以往商品的购买记录来进行计算。但又不可完全一致。可以取最相似的两个或三个用户进行推荐。
  """.stripMargin

}
