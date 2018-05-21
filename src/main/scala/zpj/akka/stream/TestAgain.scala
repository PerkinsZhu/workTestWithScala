package zpj.akka.stream

import java.nio.file.Paths
import java.util.concurrent.TimeUnit

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import akka.util.ByteString
import org.junit.Test
import zpj.akka.stream.Demo.source

import scala.concurrent.duration._
import scala.concurrent.Future

/**
  * Created by PerkinsZhu on 2018/5/21 16:22
  **/
class TestAgain {
  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher
  val source: Source[Int, NotUsed] = Source(1 to 100)
  val tweets: Source[Tweet, NotUsed] = Source(
    Tweet(Author("rolandkuhn"), System.currentTimeMillis, "#akka rocks!") ::
      Tweet(Author("patriknw"), System.currentTimeMillis, "#akka !") ::
      Tweet(Author("bantonsson"), System.currentTimeMillis, "#akka !") ::
      Tweet(Author("drewhk"), System.currentTimeMillis, "#akka !") ::
      Tweet(Author("ktosopl"), System.currentTimeMillis, "#akka on the rocks!") ::
      Tweet(Author("mmartynas"), System.currentTimeMillis, "wow #akka !") ::
      Tweet(Author("akkateam"), System.currentTimeMillis, "#akka rocks!") ::
      Tweet(Author("bananaman"), System.currentTimeMillis, "#bananas rock!") ::
      Tweet(Author("appleman"), System.currentTimeMillis, "#apples rock!") ::
      Tweet(Author("drama"), System.currentTimeMillis, "we compared #apples to #oranges!") ::
      Nil)
  val akkaTag = Hashtag("#akka")

  @Test
  def test01(): Unit = {

    val factorials = source.scan(BigInt(1))((acc, next) ⇒ acc * next)
    source.runForeach(i => println(i))
    factorials.map(num ⇒ ByteString(s"$num\n")).runWith(FileIO.toPath(Paths.get("G:\\test\\factorials.txt")))
    factorials.map(_.toString).runWith(lineSink("G:\\test\\factorial2.txt"))
    factorials
      .zipWith(Source(0 to 100))((num, idx) ⇒ s"$idx! = $num")
      .throttle(1, 3.seconds, 1, ThrottleMode.shaping)
      .runForeach(println).onComplete(_ => system.terminate())
  }


  @Test
  def test02(): Unit = {
    tweets
      .map(_.hashtags) // Get all sets of hashtags ...
      .reduce(_ ++ _) // ... and reduce them to a single set, removing duplicates across all tweets
      .mapConcat(identity) // Flatten the stream of tweets to a stream of hashtags
      .map(_.name.toUpperCase) // Convert all hashtags to upper case
      .runWith(Sink.foreach(println)) // Attach the Flow to a Sink that will finally print the hashtags

  }

  def lineSink(filename: String): Sink[String, Future[IOResult]] = Flow[String].map(s ⇒ ByteString(s + "\n")).toMat(FileIO.toPath(Paths.get(filename)))(Keep.right)


  @Test
  def test03(): Unit = {
    // 该方法运行需要在main方法中运行
    val factorials = source.scan(1)((acc, next) ⇒ acc + next)
    factorials
      .zipWith(Source(0 to 100))((num, idx) ⇒ s"$idx! = $num")
      .throttle(1, 1 seconds, 1, ThrottleMode.shaping)
      .runForeach(println).onComplete(_ => system.terminate())
  }

  val authors: Source[Author, NotUsed] = tweets.filter(_.hashtags.contains(akkaTag)).map(_.author)

  @Test
  def test04(): Unit = {
    source.runWith(Sink.foreach(println))
  }


  val hashtags: Source[Hashtag, NotUsed] = tweets.mapConcat(_.hashtags.toList)

  @Test
  def testBroadCast(): Unit = {
    val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit b =>
      import GraphDSL.Implicits._
      val bcast = b.add(Broadcast[Tweet](2))
      tweets ~> bcast.in
      bcast.out(0) ~> Flow[Tweet].map(_.author) ~> Sink.foreach(println)
      bcast.out(1) ~> Flow[Tweet].mapConcat(_.hashtags.toList) ~> Sink.foreach(println)
      ClosedShape
    })
    g.run()
  }


}

final case class Author(handle: String)

final case class Hashtag(name: String)

final case class Tweet(author: Author, timestamp: Long, body: String) {
  def hashtags: Set[Hashtag] = body.split(" ").collect {
    case t if t.startsWith("#") ⇒ Hashtag(t.replaceAll("[^#\\w]", ""))
  }.toSet
}
