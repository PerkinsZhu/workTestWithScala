package zpj.machinelearning

/**
  * Created by PerkinsZhu on 2017/10/9 19:03. 
  */
object DesicionTree {

  val dataSet = List(List(1, 1, "YES"), List(1, 0, "YES"), List(0, 1, "YES"), List(0, 0, "NO"))
  val labelSet = ("会飞吗？", "会呼吸吗？")

  def getShannonEntropy(dataArray: List[List[Any]]): Float = {
    import scala.collection.mutable.Map
    var labelMap: Map[String, Float] = Map()
    val dataSize = dataArray.size
    dataArray.foreach(item => {
      val key = item.reverse.head.toString
      labelMap += (key -> (labelMap.getOrElse(key, 0.0f) + 1.0f))
    })

    val temp = labelMap.map(item => {
      val pro = item._2 / dataSize
      pro * (Math.log(pro) / Math.log(2)) * -1
    }).sum
    println(temp)

    return 10
  }

  def main(args: Array[String]): Unit = {
    getShannonEntropy(dataSet)
  }
}
