package zpj.http4s

import cats.effect.{Effect, IO, Resource}
import cats.implicits._
import org.eclipse.jetty.client.HttpClient
import org.http4s.Method._
import org.http4s.blaze.http.HttpClient
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.{HttpApp, ParseFailure, Request, Response, Status, Uri}
import org.junit.Test

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by PerkinsZhu on 2018/9/20 17:44
  **/
class HttpClient {

  @Test
  def test03(): Unit = {
    val baseUri = Uri.uri("http://foo.com")
    // baseUri: org.http4s.Uri = http://foo.com

    val withPath = baseUri.withPath("/bar/baz")
    // withPath: org.http4s.Uri = http://foo.com/bar/baz

    val withQuery = withPath.withQueryParam("hello", "world")


    val validUri = "https://my-awesome-service.com/foo/bar?wow=yeah"
    // validUri: String = https://my-awesome-service.com/foo/bar?wow=yeah

    val invalidUri = "yeah whatever"
    // invalidUri: String = yeah whatever

    val uri: Either[ParseFailure, Uri] = Uri.fromString(validUri)
    // uri: Either[org.http4s.ParseFailure,org.http4s.Uri] = Right(https://my-awesome-service.com/foo/bar?wow=yeah)

    val parseFailure: Either[ParseFailure, Uri] = Uri.fromString(invalidUri)
  }
}
