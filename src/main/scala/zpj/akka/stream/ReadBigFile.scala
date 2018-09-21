package zpj.akka.stream

import java.nio.file.Paths

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl.Source
import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.Future
/** ``
  * Created by PerkinsZhu on 2018/9/20 14:34
  * */
object ReadBigFile {
  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  def testStream(): Unit = {

    val source: Source[Int, NotUsed] = Source(1 to 100)
    source.runForeach(println _)(materializer)

  }

  def lineSink(filename: String): Sink[String, Future[IOResult]] =
    Flow[String]
      .map(s ⇒ ByteString(s + "\n"))
      .toMat(FileIO.toPath(Paths.get(filename)))(Keep.right)

  def testLink(): Unit = {
    val source: Source[Int, NotUsed] = Source(1 to 100)
    val factorials = source.scan(BigInt(1))((acc, next) ⇒ acc * next)
    factorials.map(_.toString).runWith(lineSink("factorial2.txt"))
//    factorials.runForeach(println(_))
  }

  def testRead(): Unit = {
    val data = FileIO.toPath(Paths.get("factorial2.txt"))
    scala.io.Source.fromFile("").getLines().toStream.par.max



  }

  def main(args: Array[String]): Unit = {
//    testStream()
//    testLink()
    testRead()
  }

}
