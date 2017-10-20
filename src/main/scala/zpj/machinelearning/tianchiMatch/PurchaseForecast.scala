package zpj.machinelearning.tianchiMatch


import com.mongodb.casbah.commons.MongoDBObject
import zpj.database.MongodbTool

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
    *
    */

  def main(args: Array[String]): Unit = {
    var set:Set[String] = Set()
    val userList = users.find(MongoDBObject(),MongoDBObject("uid"->true)).forEach(item=>{set += item.get("uid").toString})
    println(set.size)
  }
}
