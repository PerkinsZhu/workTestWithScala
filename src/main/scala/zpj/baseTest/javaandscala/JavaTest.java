package zpj.baseTest.javaandscala;

import org.junit.Test;
import scala.Function1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PerkinsZhu on 2018/9/29 19:08
 **/
public class JavaTest {

    @Test
    public void testScala() {
        Function1<String, String> function1 = null;
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        //java 无法调用scala 的lambda方法
        // new ScalaTest().fetchCache1(function1, list);
    }
}
