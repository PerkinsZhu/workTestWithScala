package zpj.machinelearning

import scala.io.Source

/**
  * Created by PerkinsZhu on 2017/10/16 13:57. 
  */
object Bayes extends Base {

  def main(args: Array[String]): Unit = {
    testData
  }

  def createDataForm(dataList: List[List[String]]): List[String] = {
    //创建词汇表，用来创建文档向量
    dataList.flatMap(item => item).distinct
  }

  def dataLisToVector(dataList: List[List[String]], dataForm: List[String]) = {
    dataList.map(item => {
      dataForm.map(ele => if (item.contains(ele)) 1 else 0)
    })
  }

  def getBayesArgs(vector: List[List[Int]], labelList: List[Int]) = {
    val baseRatio = labelList.sum.toDouble / labelList.length
    val initList = List.fill(vector.head.length)(1)
    val labelOne = (for (i <- 0 until labelList.length) yield if (labelList(i) == 1) vector(i) else Nil).filter(_ != Nil).foldLeft(initList)((x, y) => {
      x.zip(y).map(ele => ele._1 + ele._2)
    })
    val labelTwo = (for (i <- 0 until labelList.length) yield if (labelList(i) == 0) vector(i) else Nil).filter(_ != Nil).foldLeft(initList)((x, y) => {
      x.zip(y).map(ele => ele._1 + ele._2)
    })

    val oneSum = labelOne.sum + 2 - initList.sum
    val twoSum = labelTwo.sum + 2 - initList.sum

    (labelOne.map(item => {
      Math.log(item.toDouble / oneSum)
    }), labelTwo.map(item => {
      Math.log(item.toDouble / twoSum)
    }), baseRatio)
  }

  def classifyNB(dealList: List[List[Int]], bayesArgs: Tuple3[List[Double], List[Double], Double]): Int = {
    val labelOneRatioList = bayesArgs._1
    val labelTwoRatioList = bayesArgs._2
    val baseRatio = bayesArgs._3


    val resulOne = dealList.map(item => {
      item.zip(labelOneRatioList).map(item => {
        item._1 * item._2
      }).sum + Math.log(baseRatio)
    })
    val resulTwo = dealList.map(item => {
      item.zip(labelTwoRatioList).map(item => {
        item._1 * item._2
      }).sum + Math.log(1 - baseRatio)
    })
    if (resulOne.head > resulTwo.head) 1 else 0
  }


  def testData() = {
    val dataList = Source.fromFile(dataPath + "bayes.txt").getLines().toList.map(_.split("\\s+").toList)
    val labelList = List(0, 1, 0, 1, 0, 1)
    val dataForm = createDataForm(dataList) //创建词汇表

    val vector = dataLisToVector(dataList, dataForm)
    //创建词汇表向量
    val bayesArgs = getBayesArgs(vector, labelList) //训练 获取词汇表标准概率

    //    测试数据01
    val dealList = dataLisToVector(List(List("love", "my", "dalmation")), dataForm)
    val resultType = classifyNB(dealList, bayesArgs)
    println(resultType)

    //    测试数据02
    val dealList02 = dataLisToVector(List(List("hello","dog","cute","how")), dataForm)
    val resultType02 = classifyNB(dealList02, bayesArgs)
    println(resultType02)
  }

}
