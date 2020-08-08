object AnnotMethodDemo extends App {

  @Benchmark
  def testMethod[T]: Double = {
    //val start = System.nanoTime()

    val x = 2.0 + 2.0
    Math.pow(x, x)

    //val end = System.nanoTime()
    //println(s"elapsed time is: ${end - start}")
  }
  @Benchmark
  def testMethodWithArgs(x: Double, y: Double) = {
    val z = x + y
    Math.pow(z,z)
  }

  testMethod[String]
  testMethodWithArgs(2.0,3.0)


}