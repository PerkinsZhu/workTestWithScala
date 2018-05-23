package zpj.akka.http

/**
  * Created by PerkinsZhu on 2017/11/25 12:00. 
  */
import akka.actor._
import akka.stream._
import akka.stream.scaladsl._
import akka.http.scaladsl.model._
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import scala.concurrent._


object HelloHttp extends App {
  implicit val httpSys = ActorSystem("httpSys")
  implicit val httpMat = ActorMaterializer()
  implicit val httpEc = httpSys.dispatcher

  val (host,port) = ("192.168.10.192",60000)
  val services: Flow[HttpRequest, HttpResponse, Any] = path("hello") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,"<h> Hello World! </h>"))
    }
  }
  val futBinding: Future[Http.ServerBinding] = Http().bindAndHandle(services,host,port)
  println(s"Server running at $host $port. Press any key to exit ...")
  scala.io.StdIn.readLine()
  futBinding.flatMap(_.unbind()).onComplete(_ => httpSys.terminate())
}