package zpj.thread;

import org.junit.Test;

/**
 * Created by PerkinsZhu on 2018/10/27 13:36
 **/
public class TestCase {
    @Test
    public void testEscape() {
        Outter outter = new Outter(new Demo());

        System.out.println(outter.toString());
    }

    class Demo{
        public void odTask(Outter.Inner inner){
            System.out.println(inner.aaa);

        }
    }
}
