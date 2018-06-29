package basetest;

import javax.inject.Singleton;

public class SingletonTest {


    public static void main(String[] args) {
        Student student1 = new Student();
        Student student2 = new Student();
        System.out.println(student1);
        System.out.println(student2);
    }
}

@Singleton
class Student {
    public Student() {
    }

}