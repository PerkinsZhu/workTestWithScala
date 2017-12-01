package zpj.akka.stream

import java.nio.file.Paths
import java.util.Date
import java.util.concurrent.TimeUnit

import akka.actor._
import akka.stream._
import akka.stream.scaladsl._
import akka._
import akka.util.ByteString

import scala.concurrent._
import scala.concurrent.duration.{FiniteDuration, TimeUnit}

/**
  * Created by PerkinsZhu on 2017/11/25 13:54. 
  */

object SourceDemo extends App {
  implicit val sys = ActorSystem("demo")
  implicit val mat = ActorMaterializer()
  implicit val ec = sys.dispatcher

  val s1: Source[Int, NotUsed] = Source(1 to 10)
  val sink: Sink[Any, Future[Done]] = Sink.foreach(println)
  val rg1: RunnableGraph[NotUsed] = s1.to(sink)
  val rg2: RunnableGraph[Future[Done]] = s1.toMat(sink)(Keep.right)
  val res1: NotUsed = rg1.run()

  Thread.sleep(1000)

  val res2: Future[Done] = rg2.run()
  res2.andThen {
    case _ => //sys.terminate()
  }

  val seq = Seq[Int](1, 2, 3)

  def toIterator() = seq.iterator

  val flow1: Flow[Int, Int, NotUsed] = Flow[Int].map(_ + 2)
  val flow2: Flow[Int, Int, NotUsed] = Flow[Int].map(_ * 3)
  val s2 = Source.fromIterator(toIterator)
  val s3 = s1 ++ s2

  val s4: Source[Int, NotUsed] = s3.viaMat(flow1)(Keep.right)
  val s5: Source[Int, NotUsed] = s3.via(flow1).async.viaMat(flow2)(Keep.right)
  val s6: Source[Int, NotUsed] = s4.async.viaMat(flow2)(Keep.right)
  (s5.toMat(sink)(Keep.right).run()).andThen { case _ => } //sys.terminate()}

  s1.runForeach(println)
  val fres = s6.runFold(0)(_ + _)
  fres.onSuccess { case a => println(a) }
  fres.andThen { case _ => sys.terminate() }

}

object Demo {

  def test02(): Unit = {
    val source: Source[Int, NotUsed] = Source(1 to 100)
    val done: Future[Done] = source.runForeach(i ⇒ println(i))(materializer)
    implicit val ec = system.dispatcher
    done.onComplete(_ ⇒ system.terminate())
  }

  def test04(): Unit = {
    (1 to 100).map(i=>{print(i+"====");i+1}).map(it=>{println(it);it
      -1})
  }

  def test05(): Unit = {
    final case class Author(handle: String)
    final case class Hashtag(name: String)
    final case class Tweet(author: Author, timestamp: Long, body: String) {
      def hashtags: Set[Hashtag] = body.split(" ").collect {
        case t if t.startsWith("#") ⇒ Hashtag(t.replaceAll("[^#\\w]", ""))
      }.toSet
    }
   val akkaTag = Hashtag("#akka")
    val author = Author("jack")
    val tweets: Source[Tweet, NotUsed]= Source(List(Tweet(author,new Date().getTime,"#akka hello jack")))
    val authors: Source[Author, NotUsed] =tweets.filter(_.hashtags.contains(akkaTag)).map(_.author)
    authors.runWith(Sink.foreach(println))

    val writeAuthors: Sink[Author, NotUsed] = ???
    val writeHashtags: Sink[Hashtag, NotUsed] = ???
    val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit b =>
      import GraphDSL.Implicits._
      val bcast = b.add(Broadcast[Tweet](2))
      tweets ~> bcast.in
      bcast.out(0) ~> Flow[Tweet].map(_.author) ~> writeAuthors
      bcast.out(1) ~> Flow[Tweet].mapConcat(_.hashtags.toList) ~> writeHashtags
      ClosedShape
    })
    g.run()
  }

  def test06(): Unit = {
    val factorials = source.scan(BigInt(1))((acc, next) ⇒{println(acc,next);acc * next})
    factorials
      .zipWith(Source(0 to 100))((num, idx) ⇒ s"$idx! = $num")
      .throttle(1, new FiniteDuration(3,TimeUnit.SECONDS), 1, ThrottleMode.shaping)
      .runForeach(println)
  }

  def main(args: Array[String]): Unit = {
    test05()
  }
  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()
  val source: Source[Int, NotUsed] = Source(1 to 100)

  def test03(): Unit ={
    val factorials = source.scan(BigInt(1))((acc, next) ⇒ acc * next)
    val result: Future[IOResult] =factorials.map(num ⇒ ByteString(s"$num\n")).runWith(FileIO.toPath(Paths.get("factorials.txt")))
    factorials.map(item=>{print(item+"========");item.toString}).runWith(lineSink("factorial2.txt"))
}

  def lineSink(filename: String): Sink[String, Future[IOResult]] = Flow[String].map(s ⇒ {println(s);ByteString(s + "\n")}).toMat(FileIO.toPath(Paths.get(filename)))(Keep.right)

  def test() = {
    val factorials = source.scan(BigInt(1))((acc, next) ⇒ acc * next)
//    factorials.runForeach(println _)
//    val result: Future[IOResult] =  factorials.map(num ⇒ ByteString(s"$num\n")).runWith(FileIO.toPath(Paths.get("factorials.txt")))
    factorials
      .zipWith(Source(0 to 100))((num, idx) ⇒ s"$idx! = $num")
//      .throttle(1, 1.second, 1, ThrottleMode.shaping)
      .runForeach(println)
  }
}