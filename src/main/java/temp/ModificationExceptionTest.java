package temp;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by PerkinsZhu on 2018/9/11 17:21
 *
 *
 * 测试 ConcurrentModificationException异常
 **/
public class ModificationExceptionTest {
    static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(50);

    public static void main(String[] args) {
        doTest();
    }

    private static void doTest() {
        new Thread(() -> {
            while (true) {
                map.forEach((String key, Integer data) -> {
                    if (data % 5 == 0) {
                        System.out.println("删除--->" + key);
                        map.remove(key);
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            int num = 0;
            while (true) {
                System.out.println("添加--->" + num);
                map.put(num + "", num);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(map.size());
                num += 1;
            }
        }).start();

    }
}
