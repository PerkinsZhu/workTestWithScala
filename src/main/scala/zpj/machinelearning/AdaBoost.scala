package zpj.machinelearning

import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import zpj.io.MLTool

/**
  * Created by PerkinsZhu on 2017/10/19 17:34. 
  */
object AdaBoost extends Base{
  def main(args: Array[String]): Unit = {
    val dataList = MLTool.readFileToList(dataPath+"horseColicTraining2.txt")
    val dataArray = Nd4j.create(dataList.init.map(_.map(item=>{item.toString.toDouble})).map(_.toArray).toArray)
    val labelArray = Nd4j.create(dataList.map(_.last).map(_.toString.toDouble).toArray)
    val dd = dataArray.shape()(0).toDouble
    println(dd)
    val temp =Nd4j.create(Array.ofDim[Double](2,3)).add(1.0001)
    println(temp)
    buildStump(dataArray,labelArray,23.0)
  }
  def buildStump(dataArray:INDArray,labelArray:INDArray,D:Double):Unit={
  }



}
