import org.openjdk.jmh.annotations.Benchmark

/**
  * Created by PerkinsZhu on 2018/6/23 10:09
  **/
class JMHScalaTest {

  @Benchmark
  def test(): Unit ={
    1 to 10000 foreach(i => i * i)
  }
}
