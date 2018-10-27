package zpj.thread;

/**
 * Created by PerkinsZhu on 2018/10/27 13:34
 **/
public class Outter {
    private String name = "name";
    private int age = 10;

    public Outter(TestCase.Demo demo) {
        demo.odTask(new Inner());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class Inner {
        public Inner() {
            System.out.println("--->" + name);
            System.out.println("--->" + age);
        }

        String aaa = name;
    }

}
