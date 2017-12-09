package zpj.exception;

/**
 * Created by PerkinsZhu on 2017/12/8 17:05.
 */
import java.util.logging.*;
public class TestUncaughtExceptionHandling
{
    public static void main(String[] args){
        //This registers the exception
        //handler for every thread
        Thread.setDefaultUncaughtExceptionHandler
                (new MyUncaughtExceptionHandler());

        //raise ArithmeticException which is
        //runtime exception and uncaught
        //this will be handled by MyExceptionHandler
        int r=5/0;

    }
}


//simple UncaughtExceptionHandler which logs
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    //Get a logger. You can use LogConfigurator/Manager
    //This logger will conatin the stacktrace of all
    //the uncaught exceptions in the world.
    Logger log = Logger.getLogger("UncaughtExceptionHandler");

    public MyUncaughtExceptionHandler(){
        try{
            //All this is done by LogConfigurator.
            //Dont worry much.
            FileHandler handler =new FileHandler("c:\\uncaught_exception%u.log",true);
            handler.setFormatter(new SimpleFormatter());
            log.addHandler(handler);
            log.setUseParentHandlers(false);
        }catch(Exception e){
        }
    }
    //Implement your own way of logging here
    public void uncaughtException(final Thread t,final Throwable e) {
        String msg = String.format("Thread %s: %s",t.getName() , e.getMessage());
        LogRecord lr = new LogRecord(Level.SEVERE, msg);
        lr.setThrown(e);
        log.log(lr);

    }
}