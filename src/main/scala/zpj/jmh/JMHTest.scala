package zpj.jmh

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scalaz.std.java.time

/**
  * Created by PerkinsZhu on 2018/6/23 9:58
  **/
@BenchmarkMode(Array(Mode.Throughput))
@Warmup(iterations = 3)
@Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
@Threads(8)
@Fork(2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
class JMHTestScala {
  @Benchmark
  def test(): Unit ={
    1 to 10000 foreach(i => i * i)
  }

}
