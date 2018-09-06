package lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
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
        } catch (Error er){
            System.out.println("----error----");
            er.printStackTrace();
        }
    }


}
