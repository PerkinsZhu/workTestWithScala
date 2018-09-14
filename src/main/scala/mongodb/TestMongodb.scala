package mongodb

import cn.playscala.mongo.{MongoClient, MongoDatabase}
import org.junit.Test
import play.api.libs.json.Json._
import play.api.libs.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * Created by PerkinsZhu on 2018/9/14 9:50
  **/
class TestMongodb {
  val mongoClient: MongoClient = MongoClient("mongodb://127.0.0.1:27017/test?authMode=scram-sha1")
  val database: MongoDatabase = mongoClient.getDatabase("test")
  val chat = database.getCollection[JsObject]("stat-chat")


  @Test
  def testInset(): Unit = {
    chat.find().skip(0).limit(100).list().map(list => {
      var num = 1;
      while (num < 1000000) {
        val newData = list.flatMap(data => {
          val _id = (data \ "_id").as[String]
          List(
            data.+(("_id", JsString(_id + num + "001"))).+(("isNewSession", JsString("false"))),
            data.+(("_id", JsString(_id + num + "002"))).+(("isNewSession", JsString("false"))),
            data.+(("_id", JsString(_id + num + "003"))).+(("isNewSession", JsString("false")))
          )
        })
        chat.insertMany(newData).onComplete({
          case Failure(exception) => exception.printStackTrace()
          case Success(data) =>
        })
        println(num)
        num = num + 1
      }
    })
    Thread.sleep(Int.MaxValue)
  }


  @Test
  def testSingleInsert(): Unit = {
    val list = List(Json.obj("_id" -> "aaa222aa", "data" -> "data"), Json.obj("_id" -> "bbb33bb", "data" -> "data"))
    chat.insertMany(list).onComplete({
      case Failure(exception) => exception.printStackTrace()
      case Success(data) => println(data)
    })
    //    chat.bulkWrite(list)
    Thread.sleep(Int.MaxValue)
  }
}
