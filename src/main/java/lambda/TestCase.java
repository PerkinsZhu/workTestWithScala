package lambda;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by PerkinsZhu on 2018/8/2 15:47
 **/
public class TestCase {

    @Test
    public void testJobFunction() {
        startJob(new JobFuntion() {
            @Override
            public void doJob(String name) {
                System.out.println("hello world ！" + name);
            }
        });

        startJob(name -> System.out.println("hello world !" + name));

    }

    private void startJob(JobFuntion jobFuntion) {
        jobFuntion.doJob("JACK");
    }

    @Test
    public void testList() {
        Arrays.asList(1, 2, 3);
        Consumer<Object> sysout = System.out::println;
    }


    @Test
    public void testOptional() {
        Optional name = Optional.empty();
        Optional<Integer> age = Optional.of(12);
        // System.out.println(name.get());
        System.out.println(name.orElse("jack"));
        System.out.println(name.orElse("tome"));
    }

    @Test
    public void testStream() {
        String result = Stream.of("A", "B", "C", "D").reduce("--->", String::concat);
        System.out.println(result);
    }

    @Test
    public void testArray() {
        //        IntStream.iterate(1, i -> i < 100, i -> i + 1).filter(i -> i % 2 == 0).map(i -> i * 10).flatMap(i -> IntStream.range(i, i * 2)).forEach(System.out::println);
    }

    @Test
    public void testError() {
        try {
            throw new OutOfMemoryError("aaaa");
            // throw new RuntimeException("aaaa");
        } catch (Exception e) {
            System.out.println("----exception----");
            e.printStackTrace();
        } catch (Error er) {
            System.out.println("----error----");
            er.printStackTrace();
        }
    }

    @Test
    public void initCollection() {
        Set set = new HashSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);

        for (int i = 0; i < 100; i++) {
            set.add(i);
        }

        for (int i = 0; i < 10; i++) {
            final int index = i;
            Object[] array = set.toArray();
            new Thread(() -> {
                for (int j = index * 10; j < (index + 1) * 10; j++) {
                    System.out.println("threadName:" + Thread.currentThread().getName() + "-->" + array[j].toString());
                }
            }).start();
        }

        Object[] array = set.toArray();
        int sum = 0;
        for (int i = 0; i < set.size(); i++) {
            Integer temp = (Integer) array[i];
            sum += temp * 10 + 1;
        }
        int avg = sum / (array.length == 0 ? 1 : array.length);

        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

    }

    @Test
    public void testCAS() {

        AtomicInteger integer = new AtomicInteger(15);
        boolean tip = integer.compareAndSet(10, 9);
        System.out.println(tip);


        AtomicStampedReference<Integer> integer02 = new AtomicStampedReference<>(10, 100);
        System.out.println("stamp-->" + integer02.getStamp());
        System.out.println("reference-->" + integer02.getReference());
        boolean two = integer02.compareAndSet(10, 11, 100, 101);
        System.out.println(two);
        int[] stampHolder = new int[101];
        System.out.println(integer02.get(stampHolder));


    }

    @Test
    public void testConcurrentHashMap() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(10);
        map.put("jack", 23);
        
    }


}
