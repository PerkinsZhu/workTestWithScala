package cn.playscala.mongo

import cn.playscala.mongo.codecs.Macros._
import cn.playscala.mongo.internal.CodecHelper
import com.mongodb.async.client.MongoClients
import org.bson.{BSON, BsonDocument}
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.types.ObjectId
import org.junit.Test
import org.mongodb.scala.bson
import play.api.libs.json.Json._
import play.api.libs.json.{JsObject, Json}
import zpj.playmong.{Article, Author, Person}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
import scala.reflect.ClassTag
import scala.util.{Failure, Success}

object MongoCollectionSpec {
  //  Mongo.setModelsPackage("cn.playscala.mongo")

  val codecRegistry = fromRegistries(fromProviders(classOf[Person]), MongoClient.DEFAULT_CODEC_REGISTRY, MongoClients.getDefaultCodecRegistry)
  val authorRegistry = fromRegistries(fromProviders(classOf[Author]), MongoClient.DEFAULT_CODEC_REGISTRY, MongoClients.getDefaultCodecRegistry)
  val articleRegistry = fromRegistries(fromProviders(classOf[Article]), MongoClient.DEFAULT_CODEC_REGISTRY, MongoClients.getDefaultCodecRegistry)

  val mongoClient: MongoClient = MongoClient("mongodb://127.0.0.1:27017/test?authMode=scram-sha1")

  val database: MongoDatabase = mongoClient.getDatabase("test").addCodecRegistry(codecRegistry).addCodecRegistry(authorRegistry).addCodecRegistry(articleRegistry)

  val userCol = database.getCollection[Person]("user")

  val articleCol = database.getCollection[Article]("common-article")
  val articleColJson = database.getCollection("common-article")
  val authorCol = database.getCollection[Author]("common-author")
  val authorColJson = database.getCollection("common-author")


  def main(args: Array[String]): Unit = {
    //        testUser
    testQuery()
    Thread.sleep(3000)
  }


  @Test
  def testUser = {
    userCol.insertOne(Person("bbb", "jack", Some(120))).onComplete({
      case Failure(ex) => println(ex.getMessage)
      case Success(res) => println(res)
    })
    import play.api.libs.json.Json._
    userCol.find[Person](Json.obj("_id" -> "aaa")).list().foreach(println(_))
    userCol.find[Person](obj("_id" -> "aaa")).projection(obj("name" -> true)).list().foreach(println(_))
    userCol.find[JsObject](obj("_id" -> "aaa")).projection(obj("name" -> true)).list().foreach(println(_))
  }

  @Test
  def testQuery(): Unit = {
    /*    val author = Author(ObjectId.get().toHexString, "tom");
        authorCol.insertOne(author).onComplete({
          case Failure(ex) => println(ex.getMessage)
          case Success(res) => println(res)
        })*/

    /* val article = Article(ObjectId.get().toHexString, "think in scala", "this is scala think ", "5b34b151c602b48cb85d547d")
     articleCol.insertOne(article).onComplete({
       case Failure(ex) => println(ex.getMessage)
       case Success(res) => println(res)
     })*/

    //    articleCol.find[Article](obj("_id" -> "5b34b19bc602b45940af4b9d")).list().foreach(itm => println(s"aiticle :$itm"))
    //    authorCol.find[Author](obj("_id" -> "5b34b151c602b48cb85d547d")).list().foreach(aut => println(s"author : $aut"))


    //    articleCol.find[Article](obj("_id" -> "5b1b958b5e812927346f509b")).fetch[Author]("authorId").list().foreach(item => println(s"级联查询结果： $item"))
    articleCol.find[Article](obj("_id" -> "5b34b19bc602b45940af4b9d")).fetch[Author]("authorId").list().map(list => {
      list.foreach(println)
    }).onComplete({
      case Success(data) => println(data)
      case Failure(ex) => ex.printStackTrace()
    })
    Thread.sleep(1000000)
  }

}
