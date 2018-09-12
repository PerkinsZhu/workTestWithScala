package enumTest;

import org.junit.Test;

/**
 * Created by PerkinsZhu on 2018/9/12 20:07
 **/
public class TestEnum {
    @Test
    public void test() {
        System.out.println(Enterprises.GUANGFA);
        System.out.println(Enterprises.GUANGFA.toString());
        System.out.println(Enterprises.GUANGFA.name());
    }
}
