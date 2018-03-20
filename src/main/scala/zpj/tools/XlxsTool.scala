package zpj.tools

;

import java.io.{File, FileOutputStream}

import com.mongodb.annotations.ThreadSafe
import org.apache.poi.xssf.usermodel.{XSSFRow, XSSFWorkbook};

/**
  * Created by PerkinsZhu on 2018/3/2 12:56
  **/

object XlxsTool {
  def apply: XlxsTool = new XlxsTool()

  def apply(titles: List[String]): XlxsTool = {
    val xlxs = new XlxsTool()
    xlxs.setTitles(titles)
    xlxs
  }
}

class XlxsTool {
  val workbook = new XSSFWorkbook()
  val sheet = workbook.createSheet()
  var colNum: Option[Int] = None
  var dealRowNum = 0;

  object rowLock

  def nextRow(): XSSFRow = {
    rowLock.synchronized[XSSFRow] {
      val row = sheet.createRow(dealRowNum)
      dealRowNum += 1
      row
    }
  }

  def setTitles(titles: List[String]): Unit = {
    colNum = Some(titles.length)
    appendRow(titles)
  }

  private def appendRow(titles: List[String]) = {
    val row = nextRow()
    for (i <- 0 until titles.size) {
      row.createCell(i).setCellValue(titles(i))
    }
  }

  @ThreadSafe
  def addRowDate(rowData: List[String]): Unit = {
    require(colNum.get == rowData.length)
    appendRow(rowData)
  }

  def saveToFile(filePath: String): Unit = {
    require(filePath.endsWith(".xlsx"))
    val out = new FileOutputStream(new File(filePath))
    workbook.write(out)
    workbook.close()
    out.flush()
    out.close()
  }

}
