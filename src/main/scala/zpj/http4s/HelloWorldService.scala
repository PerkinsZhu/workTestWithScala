package zpj.http4s

import cats.effect.{Effect, IO}
import io.circe.Json
import org.http4s.{HttpService, Response, Status}
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import cats.effect._
import cats.implicits._
import fs2.{Chunk, Pull, Stream}
import io.circe._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.headers.{Allow, Date, Location}
import org.http4s.twirl._
import scala.concurrent.duration._
import scala.xml.Elem
import cats.effect._
import cats.implicits._
import fs2.Stream
import io.circe.Json
import org.http4s._
import org.http4s.MediaType
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.headers._
import org.http4s.server._
import org.http4s.server.middleware.authentication.BasicAuth
import org.http4s.server.middleware.authentication.BasicAuth.BasicAuthenticator
import org.http4s.twirl._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.global

class HelloWorldService[F[_] : Effect] extends Http4sDsl[F] {

  val service: HttpService[F] = {
    HttpService[F] {
      case GET -> Root / "hello" / name => Ok(Json.obj("message" -> Json.fromString(s"Hello, ${name}")))
      case GET -> Root / "getName" / name => Ok(Json.obj("message" -> Json.fromString(s"Hello,I am ${name}")))
      case GET -> Root / "getName" / name / "show" => Ok(Json.obj("message" -> Json.fromString(s"Hello,I am ${name}")))
      case POST -> Root / "data" / name / id / "END" => Ok(Json.obj("message" -> Json.fromString(name), "id" -> Json.fromString(id)))
      case GET -> Path("/test/hello") => Ok(Json.obj("message" -> Json.fromString("222"), "id" -> Json.fromString("333333")))
      case req@GET -> Root / "test1" => {
        //TODO 这里会执行两次 很奇怪
        println(req.body)
        println(req.params)
        println(req.method)
        Ok("222")
      }
      case GET -> Root / "bigstring2" => Ok(Stream.range(0, 1000).map(i => s"This is string number $i").covary[F])
      case req@GET -> Root / "challenge1" => {
        val body = req.bodyAsText

        def notGo = Stream.emit("Booo!!!")

        def newBodyP(toPull: Stream.ToPull[F, String]): Pull[F, String, Option[Stream[F, String]]] = toPull.uncons1.flatMap {
          case Some((s, stream)) =>
            if (s.startsWith("go")) {
              Pull.output1(s).as(Some(stream))
            } else {
              notGo.pull.echo.as(None)
            }
          case None =>
            Pull.pure(None)
        }

        Ok(body.repeatPull(newBodyP))
      }
      case GET -> Root / "GOGO" => MethodNotAllowed(Allow(GET))
      case GET -> Root / "redirect" => TemporaryRedirect(Location(uri("/http4s/")))
      case GET -> Root / "http4s" => Ok("<h2>This will have an html content type!</h2>", `Content-Type`(MediaType.`text/html`))
      //case GET -> Root / "sum" => Ok(html.submissionForm("sum"))
      case GET -> Root / "helloworld" => helloWorldService
      case HEAD -> Root / "helloworld" => helloWorldService
      case GET -> Root / "underflow" => Ok("foo", `Content-Length`.unsafeFromLong(4))
      case _ => Ok("12313")
    }
  }

  def helloWorldService: F[Response[F]] = Ok("Hello World!")
}
