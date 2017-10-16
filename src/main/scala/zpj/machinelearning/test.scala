package zpj.machinelearning

import play.api.libs.json.Json

/**
  * Created by PerkinsZhu on 2017/10/10 19:34. 
  */
object test {
  def main(args: Array[String]): Unit = {
    //    testJson
    testList()
  }

  def testList(): Unit = {
    val list = List("jack", "tome", "susan")
    print(
      list.dropWhile(ele => {
        ele == "jack"
      })
    )
   val data = List(List(12,23,12,45),List(12,22,32,13))
    print(data.reduceLeft((x,y)=>{
      x.zip(y).map(ele => ele._2+ele._1)
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
