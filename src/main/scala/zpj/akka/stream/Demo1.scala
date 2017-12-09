package zpj.akka.stream

import java.nio.file.Paths
import java.util.Date
import java.util.concurrent.TimeUnit

import akka.actor._
import akka.stream._
import akka.stream.scaladsl._
import akka._
import akka.util.ByteString
import org.reactivestreams.{Publisher, Subscriber}

import scala.concurrent._
import scala.concurrent.duration.{FiniteDuration, TimeUnit}
import concurrent.ExecutionContext.Implicits.global
import scala.runtime.Nothing$
import scala.util.Success
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
  final case class Author(handle: String)
  final case class Hashtag(name: String)
  final case class Tweet(author: Author, timestamp: Long, body: String) {
    def hashtags: Set[Hashtag] = body.split(" ").collect {
      case t if t.startsWith("#") ⇒ Hashtag(t.replaceAll("[^#\\w]", ""))
    }.toSet
  }
  def test05(): Unit = {
    authors.runWith(Sink.foreach(println))

/*    val writeAuthors: Sink[Author, NotUsed] = ???
    val writeHashtags: Sink[Hashtag, NotUsed] = ???
    val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit b =>
      import GraphDSL.Implicits._
      val bcast = b.add(Broadcast[Tweet](2))
      tweets ~> bcast.in
      bcast.out(0) ~> Flow[Tweet].map(_.author) ~> writeAuthors
      bcast.out(1) ~> Flow[Tweet].mapConcat(_.hashtags.toList) ~> writeHashtags
      ClosedShape
    })
    g.run()*/


    tweets
      .buffer(10, OverflowStrategy.dropHead)
      .map(_.body)
      .runWith(Sink.ignore)
  }

  def test06(): Unit = {
    val factorials = source.scan(BigInt(1))((acc, next) ⇒{println(acc,next);acc * next})
    factorials
      .zipWith(Source(0 to 100))((num, idx) ⇒ s"$idx! = $num")
      .throttle(1, new FiniteDuration(3,TimeUnit.SECONDS), 1, ThrottleMode.shaping)
      .runForeach(println)
  }
  val akkaTag = Hashtag("#akka")
  val author = Author("jack")
  val tweets: Source[Tweet, NotUsed]= Source(List.fill(30)(Tweet(author,new Date().getTime,"#akka hello jack")))
  val authors: Source[Author, NotUsed] =tweets.filter(_.hashtags.contains(akkaTag)).map(_.author)
  def test07(): Unit = {
    val count: Flow[Tweet, Int, NotUsed] = Flow[Tweet].map(_ ⇒ 1)
    val sumSink: Sink[Int, Future[Int]] = Sink.fold[Int, Int](0)(_ + _)
    val counterGraph: RunnableGraph[Future[Int]] =tweets .via(count).toMat(sumSink)(Keep.right)
    val sum: Future[Int] = counterGraph.run()
    sum.foreach(c ⇒ println(s"Total tweets processed: $c"))
  }

  def test08(): Unit = {
    val sink = Sink.fold[Int, Int](0)(_ + _)
    val runnable: RunnableGraph[Future[Int]] = Source(1 to 10).toMat(sink)(Keep.right)
    val sum1: Future[Int] = runnable.run()
    val sum2: Future[Int] = runnable.run()
  }

  def test09(): Unit = {
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


    // Explicitly creating and wiring up a Source, Sink and Flow
    Source(1 to 6).via(Flow[Int].map(_ * 2)).to(Sink.foreach(println(_)))

    // Starting from a Source
    val source = Source(1 to 6).map(_ * 2)
    source.to(Sink.foreach(println(_)))

    // Starting from a Sink
    val sink: Sink[Int, NotUsed] = Flow[Int].map(_ * 2).to(Sink.foreach(println(_)))
    Source(1 to 6).to(sink)

    // Broadcast to a sink inline
    val otherSink: Sink[Int, NotUsed] =
      Flow[Int].alsoTo(Sink.foreach(println(_))).to(Sink.ignore)
    Source(1 to 6).to(otherSink)
  }

  def test10(): Unit = {
    import GraphDSL.Implicits._
    RunnableGraph.fromGraph(GraphDSL.create() { implicit builder =>
      val A: Outlet[Int]                  = builder.add(Source.single(0)).out
      val B: UniformFanOutShape[Int, Int] = builder.add(Broadcast[Int](2))
      val C: UniformFanInShape[Int, Int]  = builder.add(Merge[Int](2))
      val D: FlowShape[Int, Int]          = builder.add(Flow[Int].map(_ + 1))
      val E: UniformFanOutShape[Int, Int] = builder.add(Balance[Int](2))
      val F: UniformFanInShape[Int, Int]  = builder.add(Merge[Int](2))
      val G: Inlet[Any]                   = builder.add(Sink.foreach(println)).in
      C     <~      F
      A  ~>  B  ~>  C     ~>      F
      B  ~>  D  ~>  E  ~>  F
      E  ~>  G
      ClosedShape
    })
  }

  def test11(): Unit = {
    val source = Source(1 to 10)
    val sink = Sink.fold[Int, Int](0)(_ + _)
    // connect the Source to the Sink, obtaining a RunnableGraph
//    val runnable: RunnableGraph[Future[Int]] = source.toMat(sink)(Keep.right)
    val runnable= source.toMat(sink)(Keep.both)
    // materialize the flow and get the value of the FoldSink
//    val sum: Future[Int] = runnable.run()
    val sum= runnable.run()
    Thread.sleep(2000)
    println(sum._1)
    println(sum._2)
  }

  def test12(): Unit = {
    // Explicitly creating and wiring up a Source, Sink and Flow
    Source(1 to 6).via(Flow[Int].map(_ * 2)).to(Sink.foreach(println(_)))
    // Starting from a Source
    val source = Source(1 to 6).map(_ * 2)
    source.to(Sink.foreach(println(_)))
    // Starting from a Sink
    val sink: Sink[Int, NotUsed] = Flow[Int].map(_ * 2).to(Sink.foreach(println(_)))
    Source(1 to 6).to(sink)
    // Broadcast to a sink inline
    val otherSink: Sink[Int, NotUsed] =
      Flow[Int].alsoTo(Sink.foreach(println(_))).to(Sink.ignore)
    Source(1 to 6).to(otherSink)
  }

  def test13(): Unit = {
    val source = Source(1 to 100)
    val sink = Sink.foreach(println _)
    val flow = Flow[Int].map(_ * 2).fold(0)(_ + _)
    source.via(flow).toMat(Sink.ignore)(Keep.left).run()

    val seq = Seq[Int](1,2,3)
    val s1: Source[Int,NotUsed] = Source(1 to 10)
    def toIterator() = seq.iterator
    val flow1: Flow[Int,Int,NotUsed] = Flow[Int].map(_ + 2)
    val flow2: Flow[Int,Int,NotUsed] = Flow[Int].map(_ * 3)
    val s2 = Source.fromIterator(toIterator)
    val s3 = s1 ++ s2

    val s5: Source[Int,NotUsed] = s3.via(flow1).async.viaMat(flow2)(Keep.right)
    val s6: Source[Int,NotUsed] = s3.viaMat(flow1)(Keep.right).async.viaMat(flow2)(Keep.right)
    (s5.toMat(sink)(Keep.right).run()).andThen {case _ => system.terminate()}
  }

  def test14(): Unit = {
    val topHeadSink = Sink.head[Int]
    val bottomHeadSink = Sink.head[Int]
    val sharedDoubler = Flow[Int].map(_ * 2)

    RunnableGraph.fromGraph(GraphDSL.create(topHeadSink, bottomHeadSink)((_, _)) { implicit builder =>
      (topHS, bottomHS) =>
        import GraphDSL.Implicits._
        val broadcast = builder.add(Broadcast[Int](2))
        Source.single(1) ~> broadcast.in

        broadcast.out(0) ~> sharedDoubler ~> topHS.in
        broadcast.out(1) ~> sharedDoubler ~> bottomHS.in
        ClosedShape
    })
  }

  def test15(): Unit = {
    import akka.actor.Actor
    import akka.actor.Timers
    import scala.concurrent.duration._

    class MyActor extends Actor with Timers {
      private case object TickKey
      private case object FirstTick
      private case object Tick
      timers.startPeriodicTimer(TickKey, Tick, 100000000.nano)
//      self ! Tick
      var startTime = System.nanoTime()
      def receive = {
        case  FirstTick => println("-------")
        case Tick ⇒{
          self ! FirstTick
          val nowTime  = System.nanoTime()
          val temp = nowTime-startTime
          startTime = nowTime
          val temp2 = temp - (500 * 1000000)
          println("时间差："+temp+"  误差："+temp2)

        }
      }
    }

    val system = ActorSystem("timer")
    val actor = system.actorOf(Props[MyActor],"timer")

  }

  def test16(): Unit = {
  val num = 1000000000l
    val startTime = System.nanoTime()
    while (true){
      val nowTime=System.nanoTime()
      if((nowTime - startTime ) % num == 0){
        println("-------"+nowTime)
      }
    }
  }

  def test17(): Unit = {


  }

  def test18(): Unit = {
//    val sources = Source.repeat(1)
    val publisher = new MyPublish
//    val sources = Source(1 to 5)
    val sources = Source.fromPublisher(publisher)
    val startTime = System.nanoTime()
//    val flow = Flow[Int].throttle(1, FiniteDuration(1000000000l, TimeUnit.NANOSECONDS), 1, ThrottleMode.shaping)
//    sources.via(flow).runForeach(i => println(System.nanoTime() - startTime+"--"+i))
    sources.runForeach(i => println(System.nanoTime() - startTime+"--"+i))
    while (true){
      Thread.sleep(1000)
      publisher.add(2)
    }
  }
  class MyPublish extends Publisher[Int]{
    var list = collection.mutable.ListBuffer.empty[Subscriber[_ >: Int]]
    override def subscribe(s: Subscriber[_ >: Int]): Unit = {
      println("----")
      list.append(s)
    }
    def add(i:  Int): Unit ={
      list.foreach(s=>{
        println("++++")
        s.onNext(i)
        println("---")
      })
    }
  }

  def test19(): Unit = {

  }

  def main(args: Array[String]): Unit = {
    test19()
//    test()
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
    val start = System.nanoTime()
    factorials
//      .zipWith(Source(0 to 100))((num, idx) ⇒ s"$idx! = $num")
      .throttle(1, FiniteDuration(1000000000l,TimeUnit.NANOSECONDS), 1, ThrottleMode.shaping)
      .runForeach(i => println(System.nanoTime() - start))

  }
}