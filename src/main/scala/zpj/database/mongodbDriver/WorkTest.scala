package zpj.database.mongodbDriver

import org.mongodb.scala.model.Filters._
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.bson.conversions.Bson
import scala.concurrent.Await
import concurrent.duration._

/**
  * Created by PerkinsZhu on 2017/12/21 15:07. 
  */
object WorkTest {
  val mongoClient: MongoClient = MongoClient("mongodb://qizhi:qizhi&123@106.75.6.179:52174/cloud-vip-test?authMode=scram-sha1")
  val database: MongoDatabase = mongoClient.getDatabase("cloud-vip-test")
  val qacol: MongoCollection[Document] = database.getCollection("common-qa");

  def main(args: Array[String]): Unit = {
    test01()
  }

  def test01(): Unit = {
   /* val res = Await.result(qacol.find(and(equal("robotId","59f19a20e80000e80059cfa6"),size("answers",0))).toFuture(),5 seconds)
    val totalNim = res.size*/
    val allQa = Await.result(qacol.find(equal("robotId","59f19a20e80000e80059cfa6")).toFuture(), 2 seconds)
    val res = allQa.map(doc => doc.get("questions").size).sum
    println(res / allQa.size)
  }
}
