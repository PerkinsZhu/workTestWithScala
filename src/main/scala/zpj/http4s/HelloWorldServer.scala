package zpj.http4s

import cats.effect._
import cats.implicits._
import fs2.{Pull, Stream}
import io.circe.Json
import org.http4s.{HttpRoutes, Response}
import org.http4s.syntax._
import org.http4s.dsl.io._
import org.http4s.headers.{Allow, Location, `Content-Length`}
import org.http4s.server.blaze._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.headers._

import scala.concurrent.ExecutionContext.Implicits.global

object HelloWorldServer extends IOApp {

  val service = HttpRoutes.of[IO] {
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
    case GET -> Root / "GOGO" => MethodNotAllowed(Allow(GET))
    case GET -> Root / "redirect" => TemporaryRedirect(Location(uri("/http4s/")))
    case GET -> Root / "underflow" => Ok("foo", `Content-Length`.unsafeFromLong(4))
    case _ => Ok("12313")
  }.orNotFound

  def helloWorldService[F[_] : Effect]: org.http4s.HttpRoutes[F] = new HelloWorldService[F].service

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "192.168.10.192")
      .withHttpApp(service)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
