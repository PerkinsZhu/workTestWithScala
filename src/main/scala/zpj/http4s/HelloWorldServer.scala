package zpj.http4s

import cats.effect.{Effect, IO}
import fs2.StreamApp
import org.http4s.{HttpService, Response, Status}
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext

object HelloWorldServer extends StreamApp[IO] {
  import scala.concurrent.ExecutionContext.Implicits.global

  def stream(args: List[String], requestShutdown: IO[Unit]) = ServerStream.stream[IO]
}

object ServerStream {

  def helloWorldService[F[_]: Effect] = new HelloWorldService[F].service

  def stream[F[_]: Effect](implicit ec: ExecutionContext) =
    BlazeBuilder[F]
      .bindHttp(8081, "0.0.0.0")
      .mountService(helloWorldService, "/")
      .mountService(helloWorldService, "/")
      .serve

}
