package zpj.akka.stream

import java.nio.file.Paths

import akka.actor.{Actor, ActorSystem}
import akka.stream._
import akka.stream.scaladsl._
import akka.util.ByteString
import akka.{Done, NotUsed}
import org.junit.Test

import scala.Option
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success}

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

  @Test
  def testOther(): Unit = {
    val seq = Seq[Int](1, 2, 3)

    def toIterator() = seq.iterator

    val flow1: Flow[Int, Int, NotUsed] = Flow[Int].map(_ + 2)
    val flow2: Flow[Int, Int, NotUsed] = Flow[Int].map(_ * 3)
    val s1: Source[Int, NotUsed] = Source(1 to 10)
    val s2 = Source.fromIterator(toIterator)
    val s3 = s1 ++ s2
    val sink: Sink[Any, Future[Done]] = Sink.foreach(println)

    val s4: Source[Int, NotUsed] = s3.viaMat(flow1)(Keep.right)
    val s5: Source[Int, NotUsed] = s3.via(flow1).async.viaMat(flow2)(Keep.right)
    val s6: Source[Int, NotUsed] = s4.async.viaMat(flow2)(Keep.right)
    (s5.toMat(sink)(Keep.right).run()).andThen { case _ => system.terminate() }
  }

  @Test
  def test05(): Unit = {
    tweets
      .buffer(3, OverflowStrategy.dropHead)
      .map(_.author)
      .runWith(Sink.foreach(println))
  }

  @Test
  def testCount(): Unit = {
    val count: Flow[Tweet, Int, NotUsed] = Flow[Tweet].map(_ ⇒ 1)
    val sumSink: Sink[Int, Future[Int]] = Sink.fold[Int, Int](0)(_ + _)
    val counterGraph: RunnableGraph[Future[Int]] =
      tweets
        .via(count)
        .toMat(sumSink)(Keep.right)
    val sum: Future[Int] = counterGraph.run()
    sum.foreach(c ⇒ println(s"Total tweets processed: $c"))
  }


  @Test
  def test06(): Unit = {
    val source = Source(1 to 100)
    val f1 = Flow[Int].map(_ * 10).map(_.toDouble)
    val f2 = Flow[Double].map(_ * 10)
    val sink = Sink.foreach(println)
    val grap = source.via(f1).via(f2).toMat(sink)(Keep.right)
    grap.run().foreach(println _)
  }

  @Test
  def testDefineSource(): Unit = {
    // Create a source from an Iterable
    Source(List(1, 2, 3))

    // Create a source from a Future
    Source.fromFuture(Future.successful("Hello Streams!"))

    // Create a source from a single element
    Source.single("only one element")

    // an empty source
    Source.empty

    // Sink that folds over the stream and returns a Future
    // of the final result as its materialized value
    Sink.fold[Int, Int](0)(_ + _)

    // Sink that returns a Future as its materialized value,
    // containing the first element of the stream
    Sink.head

    // A Sink that consumes a stream without doing anything with the elements
    Sink.ignore

    // A Sink that executes a side-effecting call for every element of the stream
    Sink.foreach[String](println(_))

  }

  @Test
  def testRun(): Unit = {
    // Explicitly creating and wiring up a Source, Sink and Flow
    Source(1 to 6).via(Flow[Int].map(_ * 2)).to(Sink.foreach(item => println("1 --->" + item))).run()

    // Starting from a Source
    val source = Source(1 to 6).map(_ * 2)
    source.to(Sink.foreach(item => println("2 --->" + item))).run()

    // Starting from a Sink
    val sink: Sink[Int, NotUsed] = Flow[Int].map(_ * 2).to(Sink.foreach(item => println("3 --->" + item)))
    Source(1 to 6).to(sink).run()

    // Broadcast to a sink inline
    val otherSink: Sink[Int, NotUsed] = Flow[Int].alsoTo(Sink.foreach(item => println("4 --->" + item))).to(Sink.ignore)
    Source(1 to 6).to(otherSink).run()
  }

  @Test
  def test07(): Unit = {
    //    Source(1 to 10000).map(_  * 1).map(_ * 1).to(Sink.foreach(println)).run()
    Source(1 to 10000000).runForeach(println).onComplete({
      case Success(res) => println(res)
      case Failure(ex) => ex.printStackTrace()
    })
  }

  @Test
  def testActor(): Unit = {
    //    使用actor做为source源
    //    注意这里的溢出策略  如何自定义溢出策略？
    //    val matValuePoweredSource = Source.actorRef[String](bufferSize = 100, overflowStrategy = OverflowStrategy.fail)
    val matValuePoweredSource = Source.actorRef[String](bufferSize = 100, overflowStrategy = OverflowStrategy.dropHead)
    val (actorRef, source) = matValuePoweredSource.preMaterialize()
    for (i <- 1 to 10000) {
      actorRef ! "Hello!" + i
    }
    // pass source around for materialization
    source.runWith(Sink.foreach(println))
  }


  @Test
  def testFanInFanOut(): Unit = {
    val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._
      val in = Source(1 to 10)
      val out = Sink.foreach(println)
      val out2 = Sink.foreach[Int](item => println("---->" + item))
      val bcast = builder.add(Broadcast[Int](2))
      val merge = builder.add(Merge[Int](2))
      val f1, f2, f3, f4 = Flow[Int].map(_ + 10)
      in ~> f1 ~> bcast ~> f2 ~> merge ~> f3 ~> out
      bcast ~> f4 ~> merge
      ClosedShape
    })
    g.run()
  }

  @Test
  def test08(): Unit = {
    val topHeadSink = Sink.foreach[Int](a => println("a--->" + a))
    val bottomHeadSink = Sink.foreach[Int](b => println("b--->" + b))
    RunnableGraph.fromGraph(GraphDSL.create(topHeadSink, bottomHeadSink)((_, _)) { implicit builder =>
      (topHS, bottomHS) =>
        import GraphDSL.Implicits._
        //定义了一个两个out的broadcast
        val broadcast = builder.add(Broadcast[Int](2))
        Source(1 to 100) ~> broadcast.in

        broadcast ~> Flow[Int].map(_ * 2) ~> topHS.in
        broadcast ~> Flow[Int].map(_ * 10) ~> bottomHS.in
        ClosedShape
    }).run()
  }

  @Test
  def testZip(): Unit = {
    //    该方法是如何使用的？
    val pickMaxOfThree = GraphDSL.create() { implicit b ⇒
      import GraphDSL.Implicits._
      val zip1 = b.add(ZipWith[Int, Int, Int](_ * _))
      val zip2 = b.add(ZipWith[Int, Int, Int](_ + _))
      zip1.out ~> zip2.in0
      //注意这里的zip1和zip2的out和in
      //这里应该定义了一个先的Shape
      //这里返回的是一个输出，三个输入的 shape
      UniformFanInShape(zip2.out, zip1.in0, zip1.in1, zip2.in1)
    }

    val resultSink = Sink.head[Int]

    val g = RunnableGraph.fromGraph(GraphDSL.create(resultSink) { implicit b ⇒
      sink ⇒
        import GraphDSL.Implicits._
        // importing the partial graph will return its shape (inlets & outlets)
        val pm3 = b.add(pickMaxOfThree)
        val source = Source(1 to 3)

        //这里的pm3.in(0/1/2)是什么意思？
        Source(1 to 5).map(item => {
          println("1---->" + item);
          item * 1
        }) ~> pm3.in(0)
        Source(1 to 5).map(item => {
          println("2---->" + item);
          item * 1
        }) ~> pm3.in(1)
        Source(1 to 5).map(item => {
          println("3---->" + item);
          item * 1
        }) ~> pm3.in(2)
        pm3.out ~> sink.in
        ClosedShape
    })
    g.run().onComplete({
      case Success(data) => println(data)
      case Failure(ex) => ex.printStackTrace()
    })
  }


  @Test
  def testCreateGrap(): Unit = {
    val pairs = Source.fromGraph(GraphDSL.create() { implicit b ⇒
      import GraphDSL.Implicits._
      // prepare graph elements
      val zip = b.add(ZipN[Int](3))
      //      val zip1 = b.add(Zip[Int, Int])

      //      def ints = Source.fromIterator(() ⇒ Iterator.from(1))
      val ints = Source(1 to 100)
      // connect the graph
      //      ints.filter(_ % 2 != 0) ~> zip.in0
      //      ints.filter(_ % 2 == 0) ~> zip.in1

      ints.map(_ * 2) ~> zip.in(0)
      ints.map(_ * 10) ~> zip.in(1)
      ints.map(_ * 100) ~> zip.in(2)

      //      ints.map(_ * 1000) ~> zip1.in0
      //      ints.map(_ * 2000) ~> zip1.in1


      // expose port
      //            SourceShape(zip1.out)
      SourceShape(zip.out)
    })

    pairs.runForeach(println)
    /*val firstPair: Future[(Int, Int)] = pairs.runWith(Sink.last)
    firstPair.onComplete(res => {
      println(res)
      system.terminate()
    })*/
  }


  @Test
  def testBroadcast01(): Unit = {
    val pairUpWithToString =
      Flow.fromGraph(GraphDSL.create() { implicit b ⇒
        import GraphDSL.Implicits._
        // prepare graph elements
        //        定义了两个广播点
        val broadcast = b.add(Broadcast[Int](2))
        val zip = b.add(Zip[Int, String]())
        // connect the graph
        broadcast.out(0).map(identity) ~> zip.in0
        broadcast.out(1).map(_.toString) ~> zip.in1

        // expose ports
        //这里组合了broadcast的输入 和zip的输出
        FlowShape(broadcast.in, zip.out)
      })

    pairUpWithToString.runWith(Source(1 to 100), Sink.foreach(println))
  }

  @Test
  def testCombine(): Unit = {
    /*
    //使用combine组合多个数据源
        val sourceOne = Source(List(1))
        val sourceTwo = Source(List(2))
        val merged = Source.combine(sourceOne, sourceTwo)(Merge(_))
        merged.runWith(Sink.fold(0)(_ + _)).foreach(println _)
    */


    val matValuePoweredSource = Source.actorRef[String](bufferSize = 100, overflowStrategy = OverflowStrategy.dropHead)
    val (actorRef, source) = matValuePoweredSource.preMaterialize()
    for (i <- 1 to 10000) {
      actorRef ! "Hello!" + i
    }

    val sendRmotely = Sink.actorRef(actorRef, "Done")
    val localProcessing = Sink.foreach[Int](println)

    val sink = Sink.combine(sendRmotely, localProcessing)(Broadcast[Int](_))

    Source(List(0, 1, 2)).runWith(sink)
//      source.runForeach(println)
  }

}

final case class Author(handle: String)

final case class Hashtag(name: String)

final case class Tweet(author: Author, timestamp: Long, body: String) {
  def hashtags: Set[Hashtag] = body.split(" ").collect {
    case t if t.startsWith("#") ⇒ Hashtag(t.replaceAll("[^#\\w]", ""))
  }.toSet
}


object TestAgainObj {

  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  def main(args: Array[String]): Unit = {
    testZip()
  }

  def test07(): Unit = {
    //    不能使用单元测试，否则会提前终止 system
    //    Source(1 to 10000).map(_  * 1).map(_ * 1).to(Sink.foreach(println)).run()
    //    Source(1 to 10000000).runForeach(println).onComplete({
    //      case Success(res) => println(res)
    //      case Failure(ex) => ex.printStackTrace()
    //    })
    val start = System.nanoTime()
    //    经测试，使用async时， 前程数量确实是增加了，但是处理时间却变长了。测试数据为千万量级
    //    Source(1 to 1000000).map(_ * 1).async.map(_ * 1).toMat(Sink.foreach(println))(Keep.right).run().onComplete({
    Source(1 to 1000000).map(_ * 1).map(_ * 1).toMat(Sink.foreach(println))(Keep.right).run().onComplete({
      case _ => println((System.nanoTime() - start)); system.terminate()
    })

    /*    val source = Source(1 to 100000)
        val f1 = Flow[Int].map(_ * 1).map(_.toDouble).async
        val f2 = Flow[Double].map(_ * 1).async
        val sink = Sink.foreach(println)
        val grap = source.via(f1).via(f2).toMat(sink)(Keep.right)
        grap.run().onComplete({
          case _ => println((System.nanoTime() - start)); system.terminate()
        })*/
  }


  def testZip(): Unit = {
    val pickMaxOfThree = GraphDSL.create() { implicit b ⇒
      import GraphDSL.Implicits._
      val zip1 = b.add(ZipWith[Int, Int, Int](_ * _))
      val zip2 = b.add(ZipWith[Int, Int, Int](_ + _))
      zip1.out ~> zip2.in0
      //注意这里的zip1和zip2的out和in
      //这里应该定义了一个先的Shape
      UniformFanInShape(zip2.out, zip1.in0, zip1.in1, zip2.in1)
    }

    val resultSink = Sink.head[Int]

    val g = RunnableGraph.fromGraph(GraphDSL.create(resultSink) { implicit b ⇒
      sink ⇒
        import GraphDSL.Implicits._
        // importing the partial graph will return its shape (inlets & outlets)
        val pm3 = b.add(pickMaxOfThree)
        val source = Source(1 to 3)
        Source(1 to 5).map(item => {
          println("1---->" + item);
          item * 1
        }) ~> pm3.in(0)
        Source(1 to 5).map(item => {
          println("2---->" + item);
          item * 1
        }) ~> pm3.in(1)
        Source(1 to 5).map(item => {
          println("3---->" + item);
          item * 1
        }) ~> pm3.in(2)
        pm3.out ~> sink.in
        ClosedShape
    })
    g.run().onComplete({
      case Success(data) => println(data)
      case Failure(ex) => ex.printStackTrace()
    })
  }
}

final class RunWithMyself extends Actor {
  implicit val mat = ActorMaterializer()
  Source.maybe.runWith(Sink.onComplete {
    case Success(done) ⇒ println(s"Completed: $done")
    case Failure(ex) ⇒ println(s"Failed: ${ex.getMessage}")
  })

  def receive = {
    case "boom" ⇒
      context.stop(self) // will also terminate the stream
  }
}