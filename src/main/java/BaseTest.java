import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by PerkinsZhu on 2018/1/16 16:30
 **/
public class BaseTest {
    public static void main(String[] args) {
        //        new BaseTest().testThread();
        new BaseTest().testConcurrentHashMap();

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

    @Test
    public void testNetWork() throws IOException {
        //        URL url = new URL("http://www.baidu.com");
        URL url = new URL("http://www.ddd.com");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    @Test
    public void testHashTable() {
        Hashtable table = new Hashtable(10);
        table.put("a", 1);
        table.put("b", 2);
        table.forEach((Object k, Object v) -> {
            System.out.println(k + "-->" + v);
        });
        Enumeration itreator = table.keys();
        while (itreator.hasMoreElements()) {
            System.out.println(itreator.nextElement());
        }
        // System.out.println(table.get(null));
        System.out.println("===========");
        System.out.println(table.put("c", 3));
        System.out.println(table.put("b", 3));
        System.out.println(table.get("b"));
        System.out.println(table);
        System.out.println(table.toString());
        Hashtable table2 = (Hashtable) table.clone();
        System.out.println(table2);
        System.out.println(table2.equals(table));
        table2.replace("c", 4);
        System.out.println(table2.equals(table));
        System.out.println(table.equals(table2));
        ConcurrentHashMap chm = new ConcurrentHashMap(10);
        chm.put("a", 1);
        chm.put("b", 2);
        chm.put("c", 3);
        System.out.println(chm);


    }


    @Test
    public void testConcurrentHashMap() {
//        ConcurrentHashMap map = new ConcurrentHashMap();
        ConcurrentHashMap map = new ConcurrentHashMap(10);
        map.put("a", "111");
//        map.remove("a");
        System.out.println(1 << 30);
        System.out.println(3 << 2);
        System.out.println(3 << 3);
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


