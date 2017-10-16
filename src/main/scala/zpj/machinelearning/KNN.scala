package zpj.machinelearning

import zpj.io.MLTool

/**
  * Created by PerkinsZhu on 2017/10/14 9:44. 
  */
object KNN extends Base {

  def main(args: Array[String]): Unit = {
    //    testData
    testErrorRate
  }
  def testData(): Unit = {
    val data = MLTool.readFileToList(dataPath + "/datingTestSet.txt")
    val dataList = data.map(ele => ele.init).map(item => {
      item.map(ele => ele.toString.toDouble)
    })
    val newData = autoNorm(dataList)
    val labelList = data.map(ele => ele.last)
    println(doHandel(List(88778.2, 1.232, 0.252), 50, newData, labelList))

  }

  //  自动测试正确率
  def testErrorRate(): Unit = {
    val data = MLTool.readFileToList(dataPath + "/datingTestSet.txt")
    val dataList = autoNorm(data.map(ele => ele.init).map(item => {
      //使用归一函数能够提高正确率到0.9以上，未使用只能达到0.8
      item.map(ele => ele.toString.toDouble)
    }))

    val labelList = data.map(ele => ele.last)
    val dataZip = dataList.zip(labelList)
    val result = for (i <- 1 until 50) yield {
      val ele = dataZip((new util.Random).nextInt(1000))
      doHandel(ele._1, 50, dataList, labelList) == ele._2
    }
    println("正确率：" + result.count(ele => ele).toDouble / result.size.toDouble)
  }

  def computeKnn(item: List[Double], testDate: List[Double]): Double = {
    Math.sqrt(testDate.zip(item).map(item => {
      val temp = (item._1 - item._2)
      temp * temp
    }).sum)
  }

  def doHandel(testDate: List[Double], dataRange: Int, dataList: List[List[Double]], labelList: List[Any]): Any = {
    val temp = labelList.zip(dataList.map(item => computeKnn(item, testDate))).sortWith(_._2 < _._2).take(dataRange).map(_._1)
    temp.distinct.map(item => (item, temp.count(ele => ele == item))).sortWith(_._2 > _._2).head._1
  }

  //归一函数
  def autoNorm(list: List[List[Double]]): List[List[Double]] = {
    val medium = (for (i <- 0 until list.head.size) yield {
      list.map(item => item(i))
    }).map(item => {
      (item.max, item.min)
    })
    list.map(item => item.zip(medium).map(ele => {
      val data = ele._1
      val temp = ele._2
      (data - temp._2) / (temp._1 - temp._2)
    }))
  }
}
