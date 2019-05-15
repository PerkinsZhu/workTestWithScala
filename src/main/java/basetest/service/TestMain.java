package basetest.service;

import basetest.bean.Person;

/**
 * Created by PerkinsZhu on 2019/5/11 11:55
 **/
public class TestMain {
    public static void main(String[] args) {
        TestService server = new TestService();
        server.test();

        Person p2 = new Person();
        Person p3 = new Person();
        Person p = new Person();
        p.testInner();
//        Person.StaticInner staticInner = new Person.StaticInner();

    }
}
