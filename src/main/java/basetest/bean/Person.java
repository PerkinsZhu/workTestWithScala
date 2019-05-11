package basetest.bean;

/**
 * Created by PerkinsZhu on 2019/5/11 11:47
 **/
public class Person {
    protected final String name = "jack";
//    protected static Intger age;

    class Inner{}

    static  class StaticInner{}
    public void testInner(){
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
