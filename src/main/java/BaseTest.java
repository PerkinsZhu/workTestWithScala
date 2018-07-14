import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by PerkinsZhu on 2018/1/16 16:30
 **/
public class BaseTest {
    public static void main(String[] args) {
        //        new BaseTest().testThread();
        new BaseTest().testJVM();

    }

    private void testJVM() {
        List<Object> list = new ArrayList<>();
        while (true) {
            list.add(new Object[1024 * 254]);
            if (list.size() > 1000) {
                list.clear();
            }
        }
    }


    @Test
    public void testList() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");

        System.out.println(list);
        List temp = list.subList(0, list.size() - 1);
        System.out.println(temp);
        System.out.println(list);
    }


    class Speak {


        void show() {
            System.out.printf(" i am speak");
        }

        ;

        public void read() {
            System.out.printf(" i am speak read");
        }
    }

    class Person extends Speak {

        @Override
        public void show() {
            System.out.printf("---------- i am show");
        }
    }

    class TestAny<T extends Speak> {
        Speak obj = new Speak();
        T t = (T) obj;

        public void test() {
            t.show();
            t.read();
        }

    }

    private void testT() {
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


    @Test
    public void testJson() {
        String str = "{\"errcode\":40006,\"errmsg\":\"invalid meida size hint: [tKd7rA09183723]\"}";
        //        JsonNode json = play.libs.Json.parse(str);
        //        String media_id = json.get("media_id").asText();
    }

    @Test
    public void testExtends() {
        Ant ant = new Ant();
        System.out.println(ant.range + "");
        System.out.println(ant.array.size() + "");
    }

}

class Creature {
    int range = 10;
    ArrayList<String> array = new ArrayList<>(range);

    public Creature() {
        System.out.println(range + "  creature ");
        System.out.println(array.size() + "  creature ");
    }
}

class Ant extends Creature {
    int range = 2;

    public Ant() {
        System.out.println(range + "  Ant ");
    }
}


