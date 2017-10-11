package zpj.io

import scala.io.Source

/**
  * Created by PerkinsZhu on 2017/10/11 9:46. 
  */
object MLTool {
  def readFileToList(filePath: String): List[List[Any]] = {
    for (line <- Source.fromFile(filePath, "utf-8").getLines.toList) yield {
     line.split("\t").toList
    }
  }

  def main(args: Array[String]): Unit = {
   val data = readFileToList("E:\\zhupingjing\\sources\\git\\workTestWithScala\\src\\main\\scala\\zpj\\machinelearning\\testData\\data01.txt")
    println(data)
    println(data.last)
    println(data)
  }
}

