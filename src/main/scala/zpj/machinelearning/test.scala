package zpj.machinelearning

import play.api.libs.json.Json

/**
  * Created by PerkinsZhu on 2017/10/10 19:34. 
  */
object test {
  def main(args: Array[String]): Unit = {
    val json = Json.obj("会飞吗" ->10)
    println(json +("会飞吗"-> Json.obj("会"->20,"不会"->23)))

    val map = Map("hh"->10,"sd"->11)
    println(Json.toJson(map))
    println(Json.toJson(List(Json.obj("sd"->90),Json.obj("sds"->900))))
  }

}
