package zpj.machinelearning

import play.api.libs.json.{JsObject, JsValue, Json}
import zpj.io.MLTool

import scala.collection.mutable.Map

/**
  * Created by PerkinsZhu on 2017/10/9 19:03. 
  */
object DesicionTree {
//  计算信息熵 注意：信息熵和特征没有关系，只和最后的结果有关系
  def getShannonEntropy(dataList: List[List[Any]]): Double = {
    import scala.collection.mutable.Map
    val labelMap: Map[String, Float] = Map()
    val dataSize = dataList.size
    dataList.foreach(item => {
      val key = item.last.toString
      labelMap += (key -> (labelMap.getOrElse(key, 0.0f) + 1.0f))
    })
    labelMap.map(item => {
      val pro = item._2 / dataSize
      pro * (Math.log(pro) / Math.log(2)) * -1
    }).sum
  }

  def getSplitData(dataList: List[List[Any]], i: Int, value: Any) = {
    //返回的数据为：那些在i位置上的特征值为Value的数据，移除该特征之后的其它特征和结果集。注意理解这里返回的是什么！！！！
    dataList.filter(item => item(i) == value).map(item => item.dropWhile(ele => ele == value))
  }

  /**
    * 计算决策树的下一个节点为什么特征，返回的是该特征在dataList中的index
    * @param dataList
    * @return
    */
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

  def createDesicionTree(dataList: List[List[Any]], labelList: List[Any]): String = {
    doHandel(dataList, labelList, None).toString
  }

  def majorityCnt(labels: List[Any]): String = {
    val map: Map[String, Int] = Map()
    labels.foreach(item => {
      map += (item.toString -> (map.getOrElse(item.toString, 0) + 1))
    })
    map.toList.sortBy(_._2).head._1
  }

  def doHandel(dataList: List[List[Any]], labelList: List[Any], dealLabel: Option[String]): JsObject = {
    //FIXME 看是否能够使用尾递归进行优化 这里使用json存储的决策树，使用尾递归需要修改树的子节点，这样比较麻烦。可使用树数据结构来进行存储决策树，这样就可以使用尾递归看来优化，可以减少大量代码
    val tempValue = dataList.map(item => item.reverse.head)
    if (tempValue.toSet.size == 1) {
      //没有分支了
      return Json.obj(dealLabel.get -> tempValue(0).toString)
    }
    if (dataList(0).length == 1) {
      //没有属性了
      return Json.obj(dealLabel.get -> majorityCnt(tempValue))
    }

    val bestFteureIndex = getBestFeture(dataList)
    val label = labelList(bestFteureIndex)


    val newLabels = labelList.filter(item => item != label)
    var treeNode: JsObject = Json.obj()
    val temp = dataList.map(item => item(bestFteureIndex)).toSet
    temp.foreach(item => {
      val splitData = getSplitData(dataList, bestFteureIndex, item)
      val tempNode = doHandel(splitData, newLabels, Some(item.toString));
      treeNode ++= (if (tempNode.keys.head == item) {
        Json.obj(item.toString -> tempNode.values.head)
      } else {
        Json.obj(item.toString -> tempNode.value)
      })
    })
    Json.obj(label.toString -> treeNode)
  }

  def main(args: Array[String]): Unit = {

    val dataSet = List(List("会呼吸", "会飞", "YES"), List("会呼吸", "不会飞", "YES"), List("不会呼吸", "会飞", "YES"), List("不会呼吸", "不会飞", "NO"))
    val labelSet = List("会呼吸吗？", "会飞吗？")

    //    print(getShannonEntropy(dataSet))
    //    print(getBestFeture(dataSet))
    val data = MLTool.readFileToList("E:\\zhupingjing\\sources\\git\\workTestWithScala\\src\\main\\scala\\zpj\\machinelearning\\testData\\data01.txt")
    //    println(createDesicionTree(data.init, data.last))
    println(createDesicionTree(dataSet, labelSet))
  }
}
