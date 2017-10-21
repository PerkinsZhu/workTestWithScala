package zpj.machinelearning.tianchiMatch


import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import org.joda.time.DateTime
import zpj.database.MongodbTool

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
    var set:Set[String] = Set()
   /* val userList = users.find(MongoDBObject(),MongoDBObject("uid"->true)).forEach(item=>{set += item.get("uid").toString})
    println(set.size)*/
    val userData = getUserDate("11164332")
//    userData.foreach(println _)
    println("++++++++++++++++++++++++++++++++++++++++++++")
    userData.filter(_.get("bhvt")=="4").foreach(item=>{
      userData.filter(good=>{good.get("gid")==item.get("gid") &&good.get("time").toString <= item.get("time").toString}).foreach(println _)
      println("=============================")
    })
  }
  def getUserDate(uid:String):List[DBObject]={
    users.find(MongoDBObject("uid"->uid),MongoDBObject("gid"->true,"bhvt"->true,"icat"->true,"time"->true)).toArray.asScala.toList
  }
}
