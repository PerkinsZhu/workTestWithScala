package basetest.bean;

/**
 * Created by PerkinsZhu on 2019/5/11 11:47
 **/
public class Person {
    protected final String name = "jack";
    protected final String name2;

    static {
        System.out.println("静态代码块执行");
    }
    {
        System.out.println("非静态代码块执行");
    }

    public Person() {
        name2 = "200";
    }

    //    protected static Intger age;


    class Inner{}

    static  class StaticInner{}
    public void testInner(){

//        name2 = "q2w333";

        Person p = new Person();
        Inner inner = new Inner();
        Person.Inner in2 = p.new Inner();
        System.out.println(inner);
        System.out.println(in2);
        Person.StaticInner staticInner = new Person.StaticInner();
        Person.StaticInner staticInner2 = new Person.StaticInner();
        System.out.println(staticInner);
        System.out.println(staticInner2);


    }

    final String work (){

        return "";
    }
}
