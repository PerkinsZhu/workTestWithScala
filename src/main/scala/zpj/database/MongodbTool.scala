package zpj.database

import com.mongodb.casbah.{MongoClient, MongoClientURI}
import play.api.libs.json.{JsObject, JsValue, Json}

/**
  * Created by PerkinsZhu on 2017/10/20 16:58. 
  */
class MongodbTool(url: String) {
  val mongoClient = MongoClient(MongoClientURI(url))
}

object MongodbTool {
  def apply(url: String): MongodbTool = new MongodbTool(url)
}
