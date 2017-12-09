package zpj.exception

import org.slf4j.LoggerFactory

/**
  * Created by PerkinsZhu on 2017/12/8 17:39. 
  */
/**
  * 使用时需要对全局设置  Thread.setDefaultUncaughtExceptionHandler(new MyUncatchException());
  */
class MyUncaughtException extends  Thread.UncaughtExceptionHandler{
  val logger = LoggerFactory.getLogger(this.getClass)
  override def uncaughtException(t: Thread, e: Throwable): Unit = {
    logger.error("\n"+e+"\n"+e.getStackTrace.map((el: StackTraceElement)=>{"\tat "+el.getClassName+"."+el.getMethodName+"("++el.getFileName+":"+el.getLineNumber+")"}).mkString("\n"))
  }
}