package basetest.service;

/**
 * Created by PerkinsZhu on 2019/5/11 11:49
 **/
public class TestService {
    static String name = "静态变量";
    final String RUNTYPE = "我是常量";// final修改，不可修改，变为常量

    {
        System.out.println("普通代码块----" + RUNTYPE);
    }

    static {
        System.out.println("静态代码块----" + name);
    }

    public void test() {
        System.out.println("普通方法运行");

        String b = name;
        String c = RUNTYPE;
        // RUNTYPE = "SSS";   编译错误，禁止修改
        name = "新的变量";
        System.out.println(name);
    }
}
