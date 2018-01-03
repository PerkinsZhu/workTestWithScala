package zpj.playjson

import zpj.machinelearning.test

/**
  * Created by PerkinsZhu on 2017/12/28 14:19. 
  */
object TestZ {

  import play.api.libs.json._
  import play.api.libs.functional.syntax._

  implicit val residentWrites: Writes[QASeg] = (
    (JsPath \ "sid").write[String] and
      (JsPath \ "questions").write[List[String]]
    ) (unlift(QASeg.unapply))

  implicit val placeWrites: Writes[TaskBody] = (
    (JsPath \ "typee").write[String] and
    (JsPath \ "robotId").write[String] and
      (JsPath \ "residents").write[List[QASeg]]
    ) (unlift(TaskBody.unapply))


  val place = TaskBody(
    "TYPE",
    "123456",
    List(
      QASeg("Fiver",List("asa","ccc")),
      QASeg("Fivaaaer",List("asssa","caacc"))
    )
  )

  def main(args: Array[String]): Unit = {
    println(Json.toJson(place))
  }

}
case class QASeg(sid: String,questions: List[String])
case class TaskBody(typee: String, robotId: String,semanticSlices: List[QASeg])