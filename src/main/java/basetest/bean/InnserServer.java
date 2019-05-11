package basetest.bean;

/**
 * Created by PerkinsZhu on 2019/5/11 13:07
 **/
public class InnserServer {


    public void testInner(){
        Person.StaticInner staticInner = new Person.StaticInner();
        Person.Inner inner = new Person().new Inner();
        Person.Inner inner2 = new Person().new Inner();

    }
}
