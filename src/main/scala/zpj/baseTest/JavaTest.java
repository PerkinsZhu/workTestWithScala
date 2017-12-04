package zpj.baseTest;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by PerkinsZhu on 2017/12/4 14:36.
 */
public class JavaTest {
    private static Object result;
    private static ConcurrentHashMap<String,List<String>> answerListMap = new ConcurrentHashMap<String,List<String>>();
    public static void main(String[] args) {
    new JavaTest().test03();
    }
    private void test03() {
     for(int i = 0; i< 10 ;i++)   {
         new Thread(new Runnable() {
             @Override
             public void run() {
//                 System.out.println(Thread.currentThread().getName()+answerListMap.contains("HELLO"));
//                 System.out.println(Thread.currentThread().getName()+answerListMap.containsKey("HELLO"));
                 answerListMap.put("HELLO",null);
                 System.out.println(Thread.currentThread().getName()+answerListMap.get("HELLO"));
//                 System.out.println(Thread.currentThread().getName()+answerListMap.containsKey("HELLO"));
             }
         }).start();
         try {
             Thread.sleep(1000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }

    }

    private static void test02() {
        System.out.printf(getResult()+"");
    }

    private static void test01() {
        int num = 0;
        while (true){
            num++;
            if(num == 100){
                break;
            }
        }
        System.out.printf("end");
    }

    public static int getResult() {
        try {
            int res = 2/0;
        } catch (Exception e) {
            e.printStackTrace();
            return 11;
        }
        return 2;
    }
}
