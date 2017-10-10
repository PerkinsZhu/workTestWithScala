package zpj.machinelearning

import play.api.libs.json.{JsObject, Json}

import scala.collection.mutable.Map

/**
  * Created by PerkinsZhu on 2017/10/9 19:03. 
  */
object DesicionTree {

  val dataSet = List(List("会呼吸", "会飞", "YES"), List("会呼吸", "不会飞", "YES"), List("不会呼吸", "会飞", "YES"), List("不会呼吸", "不会飞", "NO"))
  val labelSet = List("会呼吸吗？", "会飞吗？")

  def getShannonEntropy(dataArray: List[List[Any]]): Double = {
    import scala.collection.mutable.Map
    val labelMap: Map[String, Float] = Map()
    val dataSize = dataArray.size
    dataArray.foreach(item => {
      val key = item.reverse.head.toString
      labelMap += (key -> (labelMap.getOrElse(key, 0.0f) + 1.0f))
    })

    val temp = labelMap.map(item => {
      val pro = item._2 / dataSize
      pro * (Math.log(pro) / Math.log(2)) * -1
    }).sum.toDouble
    temp
  }

  def getSplitData(dataList: List[List[Any]], i: Int, value: Any) = {
    dataList.filter(item => item(i) == value).map(item => item.dropWhile(ele => ele == value))
  }

  def getBestFeture(dataList: List[List[Any]]): Int = {
    val baseEntropy = getShannonEntropy(dataList)
    var tempEntrop = 0.0d
    var fetureIndex = 0
    for (i <- 0 until dataList(0).length - 1) {
      val branch = dataList.map(item => item(i)).toSet
      var itemEntrop = 0.0d
      for (value <- branch) {
        //计算各个分支的信息熵
        val tempDate = getSplitData(dataList, i, value)
        val prob = (tempDate.length toDouble) / dataList.length.toDouble
        itemEntrop = itemEntrop + prob * getShannonEntropy(tempDate)
      }
      itemEntrop = baseEntropy - itemEntrop
      if (itemEntrop > tempEntrop) {
        fetureIndex = i;
        tempEntrop = itemEntrop
      }
    }
    fetureIndex
  }

  def createDesicionTree(dataList: List[List[Any]], labelList: List[String]): String = {
    doHandel(dataList, labelList).toString
  }

  def majorityCnt(labels: List[Any]): String = {
    val map: Map[String, Int] = Map()
    labels.foreach(item => {
      map += (item.toString -> (map.getOrElse(item.toString, 0) + 1))
    })
    val temp = map.toList.sortBy(_._2)
    temp(0)._1
  }

  def doHandel(dataList: List[List[Any]], labelList: List[String]): JsObject = {
    val tempValue = dataList.map(item => item.reverse.head)
    if (tempValue.toSet.size == 1) {
      //没有分支了
      return Json.obj(tempValue(0).toString -> "")
    }
    if (dataList(0).length == 1) {
      //没有属性了
      return Json.obj(majorityCnt(tempValue) -> "")
    }

    val bestFteureIndex = getBestFeture(dataList)
    val label = labelList(bestFteureIndex)


    val newLabels = labelList.filter(item => item != label)
    val temp = dataList.map(item => item(bestFteureIndex)).toSet
    val tempNode = temp.map(item => {
      val splitData = getSplitData(dataList, bestFteureIndex, item)
      val temp = Json.obj(item.toString -> doHandel(splitData, newLabels))
      println(temp)
      temp
    })
    println(Json.toJson(tempNode))
    Json.obj(label.toString -> Json.toJson(tempNode))
  }

  def main(args: Array[String]): Unit = {
    //    print(getShannonEntropy(dataSet))
    //    print(getBestFeture(dataSet))
    println(createDesicionTree(dataSet, labelSet))
  }
}
