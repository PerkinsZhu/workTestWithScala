package zpj.baseTest.singleInstance;

/**
 * Created by PerkinsZhu on 2019/3/9 9:24
 **/
public class SingletonTest {
    public static void main(String[] args) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sleep over ");
        for (int i = 0; i < 100; i++) {
            Singleton a = Singleton.INSTANCE;
            System.out.println("----hello ---");
            a.doTask();
            Singleton.INSTANCE.doTask();
        }
    }
}
