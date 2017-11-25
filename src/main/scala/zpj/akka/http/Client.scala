package zpj.akka.http


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer

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
