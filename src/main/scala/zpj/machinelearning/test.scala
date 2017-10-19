package zpj.machinelearning

import org.nd4j.linalg.factory.Nd4j
import play.api.libs.json.Json

/**
  * Created by PerkinsZhu on 2017/10/10 19:34. 
  */
object test {

  def testND4S(): Unit = {
    //val data = Array(Array(0,1,2),Array(3,4,5)).toNDArray()
    //val data= (1 to 9).asNDArray(3,3)
    val data1 = Nd4j.create(Array(4, 6), Array(2, 3));
    val data = Nd4j.create(2, 3);
    println(data)
    println(data1.add(10).mul(2).sub(5).div(2).reshape(1, 24).reshape(3, 8))
    import org.nd4j.linalg.ops.transforms.Transforms._
    println(sigmoid(data))

    val x = Nd4j.create(Array[Array[Float]](Array[Float](1, 2, 3), Array[Float](4, 5, 6), Array[Float](7, 8, 9)))
    println(x)
    val arr = Array.ofDim[Int](2, 3)
    println(arr.length)
    val data2 = Nd4j.create(3, 1, Array[Int](1, 20))
    println(data2)
    println(Nd4j.create(Array[Float](1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f), Array[Int](2, 3)).transpose())
    val ddds = Nd4j.create(Array[Double](0.22222222222d), Array[Int](1,1))
      println(ddds)
  }


  def testOther(): Unit = {
    println("2.000".toDouble)
  }

  def main(args: Array[String]): Unit = {
    //    testJson
    //    testList()
    testND4S()
//    testOther()
  }

  def testList(): Unit = {
    val list = List("jack", "tome", "susan")
    print(
      list.dropWhile(ele => {
        ele == "jack"
      })
    )
    val data = List(List(12, 23, 12, 45), List(12, 22, 32, 13))
    print(data.reduceLeft((x, y) => {
      x.zip(y).map(ele => ele._2 + ele._1)
    }))

  }

  def testJson = {
    val json = Json.obj("会飞吗" -> 10)
    println(json + ("会飞吗" -> Json.obj("会" -> 20, "不会" -> 23)))

    val map = Map("hh" -> 10, "sd" -> 11)
    println(Json.toJson(map))
    println(Json.toJson(List(Json.obj("sd" -> 90), Json.obj("sds" -> 900))))
  }
}
