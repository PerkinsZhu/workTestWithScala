package zpj.akka.http.demo

/**
  * Created by PerkinsZhu on 2017/11/25 10:56. 
  */
import java.io.File

import akka.actor._
import akka.dispatch.PriorityGenerator
import akka.dispatch.UnboundedPriorityMailbox
import com.typesafe.config._

object Wallet {
  sealed trait WalletMsg
  case object ZipUp extends WalletMsg    //锁钱包
  case object UnZip extends WalletMsg    //开钱包
  case class PutIn(amt: Double) extends WalletMsg   //存入
  case class DrawOut(amt: Double) extends WalletMsg //取出
  case object CheckBalance extends WalletMsg  //查看余额

  def props = Props(new Wallet)
}


class PriorityMailbox(settings: ActorSystem.Settings, config: Config) extends UnboundedPriorityMailbox (
    PriorityGenerator {
      case Wallet.ZipUp => 0
      case Wallet.UnZip => 0
      case Wallet.PutIn(_) => 0
      case Wallet.DrawOut(_) => 2
      case Wallet.CheckBalance => 4
      case PoisonPill => 4
      case otherwise => 4
    }
  )

class Wallet extends Actor {
  import Wallet._
  var balance: Double = 0
  var zipped: Boolean = true

  override def receive: Receive = {
    case ZipUp =>
      zipped = true
      println("Zipping up wallet.")
    case UnZip =>
      zipped = false
      println("Unzipping wallet.")
    case PutIn(amt) =>
      if (zipped) {
        self ! UnZip         //无论如何都要把钱存入
        self ! PutIn(amt)
      }
      else {
        balance += amt
        println(s"$amt put-in wallet.")
      }

    case DrawOut(amt) =>
      if (zipped)  //如果钱包没有打开就算了
        println("Wallet zipped, Cannot draw out!")
      else
      if ((balance - amt) < 0)
        println(s"$amt is too much, not enough in wallet!")
      else {
        balance -= amt
        println(s"$amt drawn out of wallet.")
      }

    case CheckBalance => println(s"You have $balance in your wallet.")
  }
}

object Actor101 extends App {
  val system = ActorSystem("actor101-demo",ConfigFactory.parseFile(new File("application.conf")))
  val temp =Wallet.props.withDispatcher("prio-dispatcher")
  println(temp)
  val wallet = system.actorOf(temp,"mean-wallet")

  wallet ! Wallet.UnZip
  wallet ! Wallet.PutIn(10.50)
  wallet ! Wallet.PutIn(20.30)
  wallet ! Wallet.DrawOut(10.00)
  wallet ! Wallet.ZipUp
  wallet ! Wallet.PutIn(100.00)
  wallet ! Wallet.CheckBalance

  Thread.sleep(1000)
  system.terminate()

}
