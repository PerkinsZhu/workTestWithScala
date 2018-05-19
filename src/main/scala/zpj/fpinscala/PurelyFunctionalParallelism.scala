package zpj.fpinscala

/**
  * Created by PerkinsZhu on 2018/5/19 10:56
  **/
object PurelyFunctionalParallelism {
  def main(args: Array[String]): Unit = {
    //    println(sum(1 to 1000))
    //    println(sum3(1 to 1000))
    //    (1 to 1000000000).map(_ => (1 to 1000000000)).par.map(_.sum).map(println _)

  }

  def sum(ints: IndexedSeq[Int]): Int = {
    ints.size match {
      case x if (x <= 1) => ints.headOption getOrElse 0
      case _ => {
        val (l, r) = ints.splitAt((ints.length / 2))
        sum(l) + sum(r)
      }
    }
  }

  def sum2(ints: IndexedSeq[Int]): Int = {
    ints.size match {
      case x if (x <= 1) => ints.headOption getOrElse 0
      case _ => {
        val (l, r) = ints.splitAt(ints.length / 2)
        val sumL: Par[Int] = Par.unit(sum(l))
        val sumR: Par[Int] = Par.unit(sum(r))
        Par.get(sumL) + Par.get(sumR)
      }
    }
  }

  def sum3(ints: IndexedSeq[Int]): Int = {
    ints.size match {
      case x if (x <= 1) => ints.headOption getOrElse 0
      case _ => {
        val (l, r) = ints.splitAt(ints.length / 2)
        Par.map2(sum(l), sum(r))(_ + _)
      }
    }
  }

}

object Par {
  def unit[A](a: => A): Par[A] = new Par[A](a)

  def get[A](par: Par[A]): A = par.result

  def map2[A](a: A, b: A)(f: (A, A) => A): A = f(a, b)


}

class Par[A](value: A) {
  val result = value
}