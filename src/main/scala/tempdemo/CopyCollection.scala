package tempdemo

import org.mongodb.scala.{Completed, Document, MongoClient, MongoCollection, MongoDatabase, Observer}
import org.mongodb.scala.model.Filters._

/**
  * Created by PerkinsZhu on 2018/9/10 10:30
  **/
object CopyCollection {
  val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
  val database: MongoDatabase = mongoClient.getDatabase("test")
  val chat: MongoCollection[Document] = database.getCollection("stat-chat");

  def observer[T] = new Observer[T] {
    override def onNext(result: T): Unit = println("onNext-----" + result)

    override def onError(e: Throwable): Unit = println("onError----" + e.printStackTrace())

    override def onComplete(): Unit = println("Completed")
  }

  def doTask(): Unit = {
    println(chat.countDocuments().subscribe(observer[Long]))
    chat.find(equal("_id", "7489550619740207")).collect().map(set => {
      set.map(doc => {
        val id = doc.get("_id")
        println(id)
        val newId = id + "1"
        doc.put("_id", newId)
        println(doc)
        doc
      })
    })

    def main(args: Array[String]): Unit = {
      doTask()
      Thread.sleep(Int.MaxValue)
    }
  }
}