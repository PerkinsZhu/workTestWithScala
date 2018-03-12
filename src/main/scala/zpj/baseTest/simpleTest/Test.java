package zpj.baseTest.simpleTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by PerkinsZhu on 2018/3/9 15:51
 **/
public class Test {

    //jprofile linux 测试
    public static void main(String[] args) {
        class Student {
            String name = "小张";
        }
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        List list = new ArrayList<Student>();
        for (int i = 0; i < 10; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        list.add(new Student());
                        System.out.println("当前大小:" + list.size());
                    }
                }
            });
        }
    }
}

