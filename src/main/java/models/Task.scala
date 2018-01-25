package models

import play.api.libs.json.JsArray

/**
  * Created by PerkinsZhu on 2018/1/23 16:58
  **/
case class Task(robotId: String, name: String, semanticSlices: Option[JsArray], callBackUrl: String, questions: Option[JsArray])
