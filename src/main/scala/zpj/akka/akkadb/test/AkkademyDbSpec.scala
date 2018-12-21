package zpj.akka.akkadb.test

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import akka.util.Timeout
import org.scalatest.{FunSpecLike, Matchers}
import zpj.akka.akkadb.{AkkademyDb, SetRequest}

import scala.concurrent.duration._

/**
  * Created by PerkinsZhu on 2018/12/20 14:05
  **/
class AkkademyDbSpec extends FunSpecLike with Matchers {
  implicit val system = ActorSystem()
  implicit val timeOut = Timeout(5 seconds)

  describe("akkademyDb") {
    describe("given SetRequest") {
      it("should place key/value into map") {
        val actorRef = TestActorRef(new AkkademyDb)
        println(actorRef.path)
        actorRef.dispatcher
        actorRef ! SetRequest("key", "value")
        val akkademyDb = actorRef.underlyingActor
        akkademyDb.map.get("key") should equal(Some("value"))
      }
    }
  }
}
