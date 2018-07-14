package zpj.jsoup

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.junit.Test

/**
  * Created by PerkinsZhu on 2018/7/14 16:51
  **/
class TestJsoup {
  val str = "<p>\n    <span style=\"font-size: 16px;\">积分卡收到了积分卡拉斯</span>\n</p>\n<p>\n    福建省打开了房间\n</p>\n<p>\n    <br/>\n</p>\n<table>\n    <tbody>\n        <tr class=\"firstRow\">\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                人\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                123\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                234\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                234\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                2342\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                34234\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                2342\n            </td>\n        </tr>\n        <tr>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                234\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                34234\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                234\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                2342\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                234\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                4234\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                3423\n            </td>\n        </tr>\n        <tr>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                234\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                <p>\n                    234sdfjkasdjklfsd发哈上打开发lsdhfjkasd&#39;发送的扣\n                </p>\n                <p>\n                    饭卡的\n                </p>\n                <p>\n                    富含的\n                </p>\n                <p>\n                    打卡上的\n                </p>\n                <p>\n                    打卡上的\n                </p>\n                <p>\n                    发卡三等奖\n                </p>\n                <p>\n                    费\n                </p>\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                234\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                223423\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                423\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                4234\n            </td>\n            <td width=\"160\" valign=\"top\" style=\"word-break: break-all;\">\n                23423\n            </td>\n        </tr>\n    </tbody>\n</table>\n<p>\n    <img src=\"http://office.chatbot.cn:8888/file/5b49ad4c6503cf1fedbe2b5d\" title=\"\" alt=\"8407bc4d9fe6c3595cdd4180ea8d9aff.jpg\"/><br/><img src=\"http://office.chatbot.cn:8888/file/5b49ad546503cf1fedbe2b5f\" title=\"\" alt=\"timg.gif\"/><br/>\n</p>"

  @Test
  def testGetText(): Unit = {
    val document = Jsoup.parse(str);
    document.select("p").forEach((e: Element) => {
      if (e.hasText) {
        val size = e.childNodeSize()
        if (size > 1) {
          //          println(e.child(size-1))
          println(e.getAllElements.get(size - 2))
        } else {
          println(e.text())
        }
      }
    })
  }

  @Test
  def testMethod(): Unit = {
    println(HTMLSpirit.delHTMLTag(str).split("\n").map(_.trim).mkString(""))
  }

}
