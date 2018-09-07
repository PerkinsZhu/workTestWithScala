package zpj.http4s

import cats.effect.Effect
import io.circe.Json
import org.http4s.HttpService
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

class HelloWorldService[F[_] : Effect] extends Http4sDsl[F] {

  val service: HttpService[F] = {
    HttpService[F] {
      case GET -> Root / "hello" / name => Ok(Json.obj("message" -> Json.fromString(s"Hello, ${name}")))
      case GET -> Root / "getName" / name => Ok(Json.obj("message" -> Json.fromString(s"Hello,I am ${name}")))
      case GET -> Root / "getName" / name / "show" => Ok(Json.obj("message" -> Json.fromString(s"Hello,I am ${name}")))
      case POST -> Root / "data" / name / id / "END"=> Ok(Json.obj("message" -> Json.fromString(name), "id" -> Json.fromString(id)))
    }
  }
}
