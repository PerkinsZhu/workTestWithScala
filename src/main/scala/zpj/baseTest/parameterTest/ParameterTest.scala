package zpj.baseTest.parameterTest

/**
  * Created by PerkinsZhu on 2018/1/17 10:47
  **/
class ParameterTest {
  def main(args: Array[String]): Unit = {
    val temp1 = (x:Int,y:Int)=> x * y
    doAction2(0)
  }

  def doAction(para:Int): Unit ={

  }
  def doAction2(para: =>Int): Unit ={

  }
  def doAction3(para: ()=>Int): Unit ={

  }
  def doAction4(para: (Int,Int)=>Int): Unit ={

  }

}
