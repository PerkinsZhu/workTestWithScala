package zpj.database.mongodbDriver

import com.mongodb.client.model.WriteModel
import org.bson.UuidRepresentation
import org.bson.codecs.UuidCodec
import org.bson.codecs.configuration.{CodecRegistries, CodecRegistry}
import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.model._
import org.mongodb.scala.result.{DeleteResult, UpdateResult}
import play.api.libs.json.{JsObject, Json}

import scala.concurrent.duration._
import scala.concurrent.Await
object MongodbDriverTool {
//  val uri: String = "mongodb://user1:pwd1@host1/?authSource=db1"
//  val uri: String = "mongodb://user1:pwd1@host1/?authSource=db1&authMechanism=SCRAM-SHA-1"

  val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
  val database: MongoDatabase = mongoClient.getDatabase("test")
  val collection: MongoCollection[Document] = database.getCollection("mydb");
  def observer[T] = new Observer[T] {
    override def onNext(result:T): Unit = println("onNext-----"+result)
    override def onError(e: Throwable): Unit = println("onError----"+e.printStackTrace())
    override def onComplete(): Unit = println("Completed")
  }
  def main(args: Array[String]): Unit = {
//    insert()
//    insertMany
//    query
//    update
//    delete
//    bulkOper
//    dealPerson
//    testFuture()
    testPerson()
    Thread.sleep(2000)
  }

  def testPerson(): Unit ={
//    val codecRegistry: CodecRegistry =CodecRegistries.fromRegistries(CodecRegistries.fromProviders(classOf[Student]),DEFAULT_CODEC_REGISTRY)
  /*  val codecRegistry: CodecRegistry =
      CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(new UuidCodec(UuidRepresentation.STANDARD)),
        MongoClient.getDefaultCodecRegistry())*/

   /*val database: MongoDatabase = mongoClient.getDatabase("test").withCodecRegistry(codecRegistry)
    val personCol: MongoCollection[Student] = database.getCollection[Student]("student")
    personCol.insertOne(Student("123456","jack",20,"sdss")).subscribe(observer[Completed])
*/
  }

  def testFuture(): Unit ={
    import org.mongodb.scala.model.Filters._
    val res = Await.result(collection.find().toFuture,1 seconds)
    res.foreach(e => {
    })
  }

    def  insert():Unit = {
      val doc: Document = Document("name" -> "MongoDB", "type" -> "database", "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))
      val observable = collection.insertOne(doc)
      observable.subscribe(observer[Completed])
    }
  def insertMany={
    val documents = (1 to 100) map { i: Int => Document("i" -> i) }
    val insertObservable = collection.insertMany(documents)
    insertObservable.subscribe(observer[Completed])
  }
  def query={
//    val findObservable = collection.find().first()
//    val findObservable = collection.find()

//    val findObservable = collection.find(gte("i", 71))
    val findObservable2 = collection.find(and(gt("i", 50), lte("i", 100)))

//    val findObservable = collection.find(exists("i")).sort(descending("i"))

//    val findObservable = collection.find().projection(include("i"))
    import org.mongodb.scala.model.Aggregates._

//    val findObservable = collection.aggregate(Seq(filter(gt("i", 0)), project(Document("""{ITimes10: {$multiply: ["$i", 10]}}"""))) )
    val findObservable = collection.aggregate(List(group(null, Accumulators.sum("total", "$i"))))

//    collection.find().subscribe((doc: Document) => println(doc.toJson()))

    findObservable.subscribe(observer[Document])

  }
  def update={
    import org.mongodb.scala.model.Updates._
//    val observable = collection.updateOne(equal("i", 10), set("i", 110))
    val observable = collection.updateMany(lt("i", 100), inc("i", 100))
    observable.subscribe(observer[UpdateResult])
  }

  def delete={
//    val observable = collection.deleteOne(equal("i", 110))
    val observable = collection.deleteMany(gte("i", 100))

    observable.subscribe(observer[DeleteResult])
  }

  def bulkOper ={
    val writes: List[WriteModel[_ <: Document]] = List(
      InsertOneModel(Document("_id" -> 4)),
      InsertOneModel(Document("_id"-> 5)),
      InsertOneModel(Document("_id" -> 6)),
      UpdateOneModel(Document("_id" -> 1), set("x", 2)),
      DeleteOneModel(Document("_id" -> 2)),
      ReplaceOneModel(Document("_id" -> 3), Document("_id" -> 3, "x" -> 4))
    )
    // 1. Ordered bulk operation - order is guaranteed
   val observable = collection.bulkWrite(writes)
    // 2. Unordered bulk operation - no guarantee of order of operation
//    collection.bulkWrite(writes, BulkWriteOptions().ordered(false))
    observable.subscribe(observer[BulkWriteResult])
  }
  def dealPerson(): Unit ={
    import org.mongodb.scala.bson.codecs.Macros._
    import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
    import org.bson.codecs.configuration.CodecRegistries.{fromRegistries, fromProviders}
    val codecRegistry = fromRegistries(fromProviders(classOf[Person]), DEFAULT_CODEC_REGISTRY )

    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("test").withCodecRegistry(codecRegistry)
    val collection: MongoCollection[Person] = database.getCollection("person")

   /* val person: Person = Person("Ada", "Lovelace")
    val observable = collection.insertOne(person)*/

    val people: Seq[Person] = Seq(
      Person("Charles", "Babbage"),
      Person("George", "Boole"),
      Person("Gertrude", "Blanch"),
      Person("Grace", "Hopper"),
      Person("Ida", "Rhodes"),
      Person("Jean", "Bartik"),
      Person("John", "Backus"),
      Person("Lucy", "Sanders"),
      Person("Tim", "Berners Lee"),
      Person("Zaphod", "Beeblebrox")
    )
    val observable = collection.insertMany(people)

    observable.subscribe(observer[Completed])


  }

}



import org.mongodb.scala.bson.ObjectId
object Person {
  def apply(firstName: String, lastName: String): Person =
    Person(new ObjectId(), firstName, lastName)
}
case class Person(_id: ObjectId, firstName: String, lastName: String)

object SexType extends Enumeration{
  val BOY = Value
  val GIRL = Value
}
  case class Student(_id:String,name:String,age:Int,sex:String)