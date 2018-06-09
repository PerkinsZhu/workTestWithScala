package cn.playscala.mongo

import cn.playscala.mongo.codecs.Macros._
import com.mongodb.async.client.MongoClients
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import play.api.libs.json.Json._
import play.api.libs.json.{JsObject, Json}
import zpj.playmong.{Article, Author, Person}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
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
  val authorCol = database.getCollection[Author]("common-author")


  def main(args: Array[String]): Unit = {
    //        testUser
    testQuery()
    Thread.sleep(3000)
  }


  private def testUser = {
    userCol.insertOne(Person("bbb", "jack", Some(120))).onComplete({
      case Failure(ex) => println(ex.getMessage)
      case Success(res) => println(res)
    })
    import play.api.libs.json.Json._
    userCol.find[Person](Json.obj("_id" -> "aaa")).list().foreach(println(_))
    userCol.find[Person](obj("_id" -> "aaa")).projection(obj("name" -> true)).list().foreach(println(_))
    userCol.find[JsObject](obj("_id" -> "aaa")).projection(obj("name" -> true)).list().foreach(println(_))
  }

  def testQuery(): Unit = {
    /*val author = Author(ObjectId.get().toHexString, "jack");
    authorCol.insertOne(author).onComplete({
      case Failure(ex) => println(ex.getMessage)
      case Success(res) => println(res)
    })
*/
    /*    val article = Article(ObjectId.get().toHexString, "playScala", "this is conten", "5b1b923e5e81292be4b8bbbe")
        articleCol.insertOne(article).onComplete({
          case Failure(ex) => println(ex.getMessage)
          case Success(res) => println(res)
        })*/

    articleCol.find[Article](obj("_id" -> "5b1b958b5e812927346f509b")).list().foreach(itm => println(s"aiticle :$itm"))
    authorCol.find[Author](obj("_id" -> "5b1b923e5e81292be4b8bbbe")).list().foreach(aut => println(s"author : $aut"))


    //    articleCol.find[Article](obj("_id" -> "5b1b958b5e812927346f509b")).fetch[Author]("authorId").list().foreach(item => println(s"级联查询结果： $item"))
    articleCol.find[Article](obj("_id" -> "5b1b958b5e812927346f509b")).fetch[Author]("authorId").list().foreach(item => println(s"级联查询结果： $item"))

  }

}
