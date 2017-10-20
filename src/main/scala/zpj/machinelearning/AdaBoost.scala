package zpj.machinelearning

import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.string.NDArrayStrings
import zpj.io.MLTool

/**
  * Created by PerkinsZhu on 2017/10/19 17:34. 
  */
object AdaBoost extends Base{
  def main(args: Array[String]): Unit = {
    val dataList = MLTool.readFileToList(dataPath+"horseColicTraining2.txt")
    val dataArray = Nd4j.create(dataList.init.map(_.map(item=>{item.toString.toDouble})).map(_.toArray).toArray)
    val labelArray = Nd4j.create(dataList.map(_.last).map(_.toString.toDouble).toArray)
    val row = dataArray.shape()(0)
    val D =Nd4j.create(row,1).add(1.0d).div(row)
    buildStump(dataArray,labelArray,D)
  }

  def stumpClassify(dataArray: INDArray, colum: Int, threshVal: Double, eq: String) = {
    val row = dataArray.shape()(0)
    val columnData = dataArray.getColumn(colum)
    var result = Nd4j.create(1,1)
    println(result)
    if(eq == "lt"){
      for(i <- 0 until row){
        if(columnData.getDouble(i)<= threshVal){
          result.addRowVector(Nd4j.create(Array[Double](-1)))
        }else{
          result.addRowVector(Nd4j.create(Array[Double](1)))
        }
      }
    }else{

    }

  }

  def buildStump(dataArray:INDArray, labelArray:INDArray, D:INDArray):Unit={
    val steps = 10
    for( colum <- 0 until dataArray.shape()(1)){
        val data  = dataArray.getColumn(colum)
        val min = data.minNumber().doubleValue()
        val max = data.maxNumber().doubleValue()
        val stepSize = (max-min)/steps
      for(step <- -1 until steps+1){
        for(eq <-List("lt","gt")){
          val threshVal = min+step*stepSize
          stumpClassify(dataArray,colum,threshVal,eq)
        }
      }
    }
  }



}
