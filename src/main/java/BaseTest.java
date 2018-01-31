import zpj.baseTest.Student;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by PerkinsZhu on 2018/1/16 16:30
 **/
public class BaseTest {
    public static void main(String[] args) {
//        new BaseTest().testThread();
        new BaseTest().testT();
    }

    class Speak{


        void show(){
            System.out.printf(" i am speak");
        };
        public void  read(){
            System.out.printf(" i am speak read");
        }
    }
    class Person extends Speak{

        @Override
        public void show() {
            System.out.printf("---------- i am show");
        }
    }
    class TestAny <T extends Speak>{
        Speak obj = new Speak();
        T t = (T)obj;
        public void test(){
            t.show();
            t.read();
        }

    }

    private void  testT(){
        TestAny any = new TestAny<Person>();
        any.test();

    }

    private void testThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(10); //创建一个线程池，大小是10
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-------" + Thread.currentThread().getName());
                }
            });
        }

        executorService.shutdown();
    }

    private void testThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Thread temp = new Thread(new Runnable() {
                        @Override
                        public void run() {


                            System.out.println("-----" + Thread.currentThread().getName());
                        }
                    });
                    temp.setDaemon(true);
                    temp.start();
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
        System.out.println(" i  AM over ");
    }
}

