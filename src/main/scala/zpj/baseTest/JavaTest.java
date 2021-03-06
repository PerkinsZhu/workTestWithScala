package zpj.baseTest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by PerkinsZhu on 2017/12/4 14:36.
 */
public class JavaTest {
    private static Object result;
    private static ConcurrentHashMap<String, List<String>> answerListMap = new ConcurrentHashMap<String, List<String>>();

    public static void main(String[] args) {
        new JavaTest().testInterrupt();
    }

    @Test
    public void testPat() {
        String str = "换行测试\\n\r\n\r\n换行测试\\n\r\n\r\n换行测试\\n\r\n换行测试\r\n换行测试\\n换行测试\\n\\n323".replace("\\n\r\n", "\r\n").replace("\\n\n", "\r\n").replaceAll("\\\\n(?<!\\r\\n|\\n|$)", "\r\n").replace("\\n", "");
        System.out.println(str);

        String temp = "asdfasddddfasss";
        System.out.println(temp.replace("fas", "--"));
        System.out.println(temp.replaceAll("fas", "--"));
        /**
         * replace 参数为 (str,str); replactAll 的参数为：(regex,str)
         * 前者直接对所有一致的字符串进行替换
         * 后者使用正则表达式来匹配所有符合规则的字符串、
         */
    }

    @Test
    public void testHanZi() {
        String start = "\\u4e00";
        String end = "\\u9fa5";
        int s = Integer.parseInt(start.substring(2, start.length()), 16);
        int e = Integer.parseInt(end.substring(2, end.length()), 16);
        for (int i = s; i <= e; i++) {
            System.out.println((char) i);
        }
        System.out.println(e - s);
    }

    private void test03() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //                 System.out.println(Thread.currentThread().getName()+answerListMap.contains("HELLO"));
                    //                 System.out.println(Thread.currentThread().getName()+answerListMap.containsKey("HELLO"));
                    answerListMap.put("HELLO", null);
                    System.out.println(Thread.currentThread().getName() + answerListMap.get("HELLO"));
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

    //1.1 test2
    private static void test02() {
        System.out.printf(getResult() + "");
    }

    private static void test01() {
        int num = 0;
        while (true) {
            num++;
            if (num == 100) {
                break;
            }
        }
        System.out.printf("end");
    }

    //1.1 test4
    public static int getResult() {
        try {
            int res = 2 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 11;
        }
        return 2;
    }

    public void testCase() {
        char key = 'n';
        switch (key) {
            case 'n':
                System.out.println("AAA");
                break;
            case 'a':
                break;

        }
    }

    @Test
    public void testNaMiao() throws InterruptedException {
        long now = System.nanoTime();
        Thread.sleep(5000);
        for (int i = 0; i < 100; i++) {
            long a = System.nanoTime();
            if (a - now < 4 * 1000000000) {
                System.out.println(a);
                System.out.println(now);
            }
        }


    }

    @Test
    public void testCOW() {
        //TODO  测试 CopyOnWriteArrayList
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        HashMap<String, Integer> map = new HashMap<>(1000);
        new Thread(() -> {
            int i = 0;
            while (true) {
                list.add(i++);
                list.add(i++);
                list.add(i++);
                list.add(i++);
                map.put(i + "", i++);
                map.put(i + "", i++);
                map.put(i + "", i++);
                map.put(i + "", i++);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.remove(0);
                map.remove(i - 3 + "");
                System.out.println("========>" + list.size());
                System.out.println("========>" + map.size());
            }
        }).start();

        new Thread(() -> {
            while (true) {
                list.forEach((Object obj) -> {
                    if (list.size() > 5) {
                        list.remove(5);
                    }
                    System.out.println("--->" + obj);
                });
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    Object next = iterator.next();
                    if (list.size() > 5) {
                        list.remove(5);
                    }
                    System.out.println("====>" + next);
                }

                int j = 0;
                Iterator keys = map.keySet().iterator();
                while (keys.hasNext()) {
                    Object next = keys.next();
                    if (j % 2 == 0) {
                        map.remove(next); //HashMap 在循环中进行删除操作会导致ConcurrentModificationException异常
                        j++;
                    }
                    System.out.println("==---==>" + next);
                }


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Test
    public void testForEache() {
        // for 循环中的终止条件，在每次循环的时候都会调用，因此如果计算size较为复杂，则应该定义size 变量，for循环中比较该size，而不是调用.size()方法，这样可以避免每次都进行计算
        for (int i = 0; i < getSize(); i++) {
            System.out.println("--->" + i);
            if (i > 100) {
                System.exit(0);
            }
        }

    }

    int size = 0;

    private int getSize() {
        System.out.println("getSize--->" + size++);
        return size;
    }

    @Test
    public void testHashMapSize() {
        HashMap<String, String> map = new HashMap<>(10);
        map.size();
    }

    @Test
    public void testInterrupt() {
        Thread thread = new Thread(() -> {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("-->" + i++);
            }
            Thread.currentThread().interrupt();
            System.out.println("-->" + Thread.currentThread().isInterrupted());
        });
        thread.start();


        try {
            Thread.sleep(100);
            thread.interrupt();
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            System.out.println("main-->");
        }

    }

    @Test
    public void testLocalTime() {
        LocalTime nowTime = LocalTime.now();
        LocalTime aimTime = LocalTime.of(8, 0, 0, 0);
        long delay = Duration.between(LocalDateTime.now(), LocalDateTime.of(LocalDate.now(), aimTime)).toMinutes();
        if (aimTime.isBefore(nowTime)) {
            delay = (24 * 60) - Duration.between(LocalDateTime.of(LocalDate.now(), aimTime), LocalDateTime.now()).toMinutes();
        }

        System.out.println(LocalDate.now().plusDays(-1).toString());

        JSONObject json = JSONObject.parseObject("{\"list\":[]}");
        JSONArray array = json.getJSONArray("list");
        System.out.println(array);
        System.out.println(json);
        System.out.println(new Date().getTime());
    }

    @Test
    public void testList(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        System.out.println(list.subList(1,1+2));
    }
    @Test
    public void testJSONObject(){
        JSONObject json = new JSONObject();
        json.put("a",1);
        json.put("b",2);
        json.put("c",2);
        System.out.println(json.toJSONString());
    }
}
