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
}