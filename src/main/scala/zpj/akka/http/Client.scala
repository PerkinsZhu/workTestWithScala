package zpj.akka.http


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.BasicHttpCredentials
import akka.stream.ActorMaterializer
import akka.util.ByteString
import org.junit.Test

import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by PerkinsZhu on 2017/11/25 13:22. 
  */
object Client extends App {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://127.0.0.1:8088/hello"))
  responseFuture.onComplete {
    case Success(rs) => println(rs._3)
    case Failure(ex) => println(ex.getMessage)
  }
}


class TestCase {
  @Test
  def testClien(): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher


    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://localhost:8080/json",method = HttpMethods.POST))

    responseFuture
      .onComplete {
        case Success(res) => println(res)
        case Failure(_) => sys.error("something wrong")
      }

    Thread.sleep(Int.MaxValue)

  }


  def requestExample(): Unit ={
    import HttpMethods._

    val homeUri = Uri("/abc")
    HttpRequest(GET, uri = homeUri)

    HttpRequest(GET, uri = "/index")

    val data = ByteString("abc")
    HttpRequest(POST, uri = "/receive", entity = data)

    import HttpProtocols._
    import MediaTypes._
    import HttpCharsets._
    val userData = ByteString("abc")
    val authorization = headers.Authorization(BasicHttpCredentials("user", "pass"))
    HttpRequest(
      PUT,
      uri = "/user",
      entity = HttpEntity(`text/plain` withCharset `UTF-8`, userData),
      headers = List(authorization),
      protocol = `HTTP/1.0`)

    import akka.http.scaladsl.model.headers.`Raw-Request-URI`
    val req = HttpRequest(uri = "/ignored", headers = List(`Raw-Request-URI`("/a/b%2Bc")))
  }
}
